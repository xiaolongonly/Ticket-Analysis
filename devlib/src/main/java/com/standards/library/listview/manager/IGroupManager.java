package com.standards.library.listview.manager;

import android.content.Context;

import java.util.List;

import rx.Observable;

public interface IGroupManager<T> {
    Observable<List<T>> refreshData(Context context);

    Observable<List<T>> loadMoreData(Context context);

    int getTotalCount();
}
