package com.standards.library.listview.manager;

import android.content.Context;

import com.standards.library.app.ReturnCodeConfig;
import com.standards.library.model.ListData;
import com.standards.library.rx.ErrorThrowable;

import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public abstract class BaseGroupListManager<T> implements IGroupManager<T> {
    private int totalCount = 0;
    public int currentPage = 1;

    @Override
    public Observable<List<T>> refreshData(final Context context) {
        currentPage = 1;
        return getData(context).flatMap(new Func1<ListData<T>, Observable<List<T>>>() {
            @Override
            public Observable<List<T>> call(ListData<T> listData) {
                    List<T> list = listData.list;
                currentPage = 2;
                if (list != null && list.size() > 0) {
                    setTotalCount(listData.size);
                    return Observable.just(list);
                } else {
                    return Observable.error(new ErrorThrowable(ReturnCodeConfig.CODE_LOCAL_EMPTY, "暂无数据"));
                }
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<List<T>> loadMoreData(final Context context) {
        return getData(context).flatMap(new Func1<ListData<T>, Observable<List<T>>>() {
            @Override
            public Observable<List<T>> call(ListData<T> listData) {
                if (listData.list.size() == 0) {
                    return Observable.error(new ErrorThrowable(ReturnCodeConfig.CODE_LOCAL_EMPTY, "暂无数据"));
                } else {
                    ++currentPage;
                    setTotalCount(listData.size);
                    return Observable.just(listData.list);
                }
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    @Override
    public int getTotalCount() {
        return totalCount;
    }

    protected abstract Observable<ListData<T>> getData(Context context);
}
