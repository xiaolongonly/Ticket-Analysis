package com.standards.library.rx;


import rx.Subscriber;

public abstract class SimpleSubscribe<T> extends Subscriber<T> {
    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(Throwable e) {
    }

    @Override
    public void onNext(T t) {
        onSuccess(t);
    }

    @Override
    public void onStart() {
        super.onStart();
    }


    public abstract void onSuccess(T t);

}
