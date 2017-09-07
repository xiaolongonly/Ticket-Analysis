package com.standards.library.network.rxframework;

import com.standards.library.app.AppContext;
import com.standards.library.cache.DataProvider;
import com.standards.library.cache.RxCache;
import com.google.gson.Gson;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.URLDecoder;
import java.util.concurrent.atomic.AtomicBoolean;

import okhttp3.Request;
import okio.Buffer;
import retrofit2.Call;
import retrofit2.CallAdapter;
import retrofit2.Response;
import retrofit2.Retrofit;
import rx.Observable;
import rx.Producer;
import rx.Scheduler;
import rx.Subscriber;
import rx.exceptions.Exceptions;
import rx.functions.Action0;
import rx.subscriptions.Subscriptions;

/**
 * <请描述这个类是干什么的>
 *
 * @version: V1.0
 */
public final class MyRxJavaCallAdapterFactory extends CallAdapter.Factory {
    private static final String TAG = MyRxJavaCallAdapterFactory.class.getSimpleName();

    private static boolean isCache = false;

    /**
     * Returns an instance which creates synchronous observables that do not operate on any scheduler
     * by default.
     */
    public static MyRxJavaCallAdapterFactory create() {
        return new MyRxJavaCallAdapterFactory(null);
    }

    /**
     * Returns an instance which creates synchronous observables that
     * {@linkplain Observable#subscribeOn(Scheduler) subscribe on} {@code scheduler} by default.
     */
    public static MyRxJavaCallAdapterFactory createWithScheduler(Scheduler scheduler) {
        if (scheduler == null) throw new NullPointerException("scheduler == null");
        return new MyRxJavaCallAdapterFactory(scheduler);
    }

    private final Scheduler scheduler;

    private MyRxJavaCallAdapterFactory(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    @Override
    public CallAdapter<?> get(Type returnType, Annotation[] annotations, Retrofit retrofit) {
        if (annotations[0].annotationType().equals(RxCache.class)) {
            isCache = true;
        } else {
            isCache = false;
        }

        Class<?> rawType = getRawType(returnType);
        String canonicalName = rawType.getCanonicalName();
        boolean isSingle = "rx.Single".equals(canonicalName);
        boolean isCompletable = "rx.Completable".equals(canonicalName);
        if (rawType != Observable.class && !isSingle && !isCompletable) {
            return null;
        }
        if (!isCompletable && !(returnType instanceof ParameterizedType)) {
            String name = isSingle ? "Single" : "Observable";
            throw new IllegalStateException(name + " return type must be parameterized"
                    + " as " + name + "<Foo> or " + name + "<? extends Foo>");
        }

        if (isCompletable) {
            // Add Completable-converter wrapper from a separate class. This defers classloading such that
            // regular Observable operation can be leveraged without relying on this unstable RxJava API.
            // Note that this has to be done separately since Completable doesn't have a parametrized
            // type.
            return CompletableHelper.createCallAdapter(scheduler);
        }

        Type observableType = getParameterUpperBound(0, (ParameterizedType) returnType);

        CallAdapter<Observable<?>> callAdapter = new SimpleCallAdapter(observableType, scheduler);
        if (isSingle) {
            // Add Single-converter wrapper from a separate class. This defers classloading such that
            // regular Observable operation can be leveraged without relying on this unstable RxJava API.
            return SingleHelper.makeSingle(callAdapter);
        }

        return callAdapter;
    }

    static final class CallOnSubscribe<T> implements Observable.OnSubscribe<Response<T>> {
        private final Call<T> originalCall;

        CallOnSubscribe(Call<T> originalCall) {
            this.originalCall = originalCall;
        }

        @Override
        public void call(final Subscriber<? super Response<T>> subscriber) {
            // Since Call is a one-shot type, clone it for each new subscriber.
            Call<T> call = originalCall.clone();

            // Wrap the call in a helper which handles both unsubscription and backpressure.
            RequestArbiter<T> requestArbiter = new RequestArbiter<>(call, subscriber);
            subscriber.add(Subscriptions.create(requestArbiter));
            subscriber.setProducer(requestArbiter);
        }
    }

    static final class RequestArbiter<T> extends AtomicBoolean implements Action0, Producer {
        private final Call<T> call;
        private final Subscriber<? super Response<T>> subscriber;

        RequestArbiter(Call<T> call, Subscriber<? super Response<T>> subscriber) {
            this.call = call;
            this.subscriber = subscriber;
        }

        @Override
        public void request(long n) {
            if (n < 0) throw new IllegalArgumentException("n < 0: " + n);
            if (n == 0) return; // Nothing to do when requesting 0.
            if (!compareAndSet(false, true)) return; // Request was already triggered.

            try {
                Response<T> response = call.execute();
                if (!subscriber.isUnsubscribed()) {
                    subscriber.onNext(response);
                }
            } catch (Throwable t) {
                Exceptions.throwIfFatal(t);
                if (!subscriber.isUnsubscribed()) {
                    subscriber.onError(t);
                }
                return;
            }

            if (!subscriber.isUnsubscribed()) {
                subscriber.onCompleted();
            }
        }

        @Override
        public void call() {
            call.cancel();
        }
    }

    static final class SimpleCallAdapter implements CallAdapter<Observable<?>> {
        private final Type responseType;
        private final Scheduler scheduler;

        SimpleCallAdapter(Type responseType, Scheduler scheduler) {
            this.responseType = responseType;
            this.scheduler = scheduler;
        }

        @Override
        public Type responseType() {
            return responseType;
        }

        @Override
        public <R> Observable<R> adapt(final Call<R> call) {
            Observable<R> observable = Observable.create(new CallOnSubscribe<>(call)) //
                    .lift(OperatorMapResponseToBodyOrError.<R>instance());

            if (isCache) {
                if (AppContext.isNetworkAvailable()) {
                    addToCache(observable, call);
                } else {
                    observable = getFromCache(call);
                }
            }

            if (scheduler != null) {
                return observable.subscribeOn(scheduler);
            }

            return observable;
        }

        private <R> Observable<R> getFromCache(Call<R> call) {
            Object o = null;
            try {
                o = new Gson().fromJson(DataProvider.getInstance().getCacheFromDisker(getKey(call.request())), responseType);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return (Observable<R>) Observable.just(o);
        }

        private <R> void addToCache(Observable<R> observable, final Call<R> call) {
            observable.subscribe(new Subscriber<R>() {
                @Override
                public void onCompleted() {

                }

                @Override
                public void onError(Throwable e) {

                }

                @Override
                public void onNext(R r) {
                    try {
                        DataProvider.getInstance().putStringToDisker(new Gson().toJson(r), getKey(call.request()));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        }


        private String getKey(Request request) throws IOException {
            StringBuilder stringBuilder = new StringBuilder();
            Buffer buffer = new Buffer();
            request.body().writeTo(buffer);
            String decode = URLDecoder.decode(buffer.readUtf8(), "UTF-8");
            String[] split = decode.split("&");
            for (String s : split) {
                String[] param = s.split("=");
                if (!param[0].equals("accessToken")) {
                    stringBuilder.append(s);
                }
            }
//            L.d(TAG,request.url().toString()+stringBuilder.toString());
            return request.url().toString() + stringBuilder.toString();
        }

    }

}
