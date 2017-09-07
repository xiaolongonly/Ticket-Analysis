package com.standards.library.listview.listview.zoom;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AbsListView;


import com.standards.library.listview.adapter.IGroupAdapter;
import com.standards.library.listview.listview.IGroupListView;

import rx.Observable;
import rx.Subscriber;

/**
 * <请描述这个类是干什么的>
 *
 * @data: 2016/5/22 13:44
 * @version: V1.0
 */
public class PullToZoomRecycleView<T> implements IGroupListView<T> {
    private PullToZoomViewEx zoomViewEx;

    @Override
    public void initView(Context context, IGroupAdapter<T> adapter) {
        zoomViewEx = new PullToZoomViewEx(context, null);
        zoomViewEx.setParallax(false);
        GridLayoutManager layoutManager = new GridLayoutManager(context, 1);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        zoomViewEx.getPullRootView().setLayoutManager(layoutManager);
//        zoomViewEx.getPullRootView().setItemAnimator(new MyItemAnimator());

        zoomViewEx.getPullRootView().setAdapter((RecyclerView.Adapter) adapter);
    }

    public void setHeaderView(View headerView) {
        zoomViewEx.setHeaderView(headerView);
    }

    public void setHeaderViewParams(AbsListView.LayoutParams layoutParams) {
        zoomViewEx.setHeaderLayoutParams(layoutParams);
    }

    public void setZoomView(View view) {
        zoomViewEx.setZoomView(view);
    }


    @Override
    public Observable<Integer> initListener() {
        return Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(final Subscriber<? super Integer> subscriber) {
                zoomViewEx.setOnLoadMoreListener(new PullToZoomViewEx.OnLoadMoreListener() {
                    @Override
                    public void onLoadMore() {
                        subscriber.onNext(LoadMore);
                    }
                });
            }
        });
    }

    @Override
    public View getRootView() {
        return zoomViewEx;
    }

    @Override
    public View getRecycleView() {
        return zoomViewEx.getPullRootView();
    }

    @Override
    public boolean isAutoReFresh() {
        return true;
    }

    @Override
    public void AutoRefresh() {

    }

    @Override
    public void onRefreshComplete() {

    }

    public void setHeaderListener(PullToZoomViewEx.onHeaderScrollListener listener) {
        zoomViewEx.setHeaderListener(listener);
    }
}
