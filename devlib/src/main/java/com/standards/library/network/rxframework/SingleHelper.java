package com.standards.library.network.rxframework;

import java.lang.reflect.Type;

import retrofit2.Call;
import retrofit2.CallAdapter;
import rx.Observable;
import rx.Single;

public class SingleHelper {
    static CallAdapter<Single<?>> makeSingle(final CallAdapter<Observable<?>> callAdapter) {
        return new CallAdapter<Single<?>>() {
            @Override
            public Type responseType() {
                return callAdapter.responseType();
            }

            @Override
            public <R> Single<?> adapt(Call<R> call) {
                Observable<?> observable = callAdapter.adapt(call);
                return observable.toSingle();
            }
        };
    }
}
