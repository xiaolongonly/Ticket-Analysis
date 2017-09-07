package com.standards.library.listview.listview;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.standards.library.R;
import com.standards.library.listview.adapter.IGroupAdapter;

import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import in.srain.cube.views.ptr.PtrUIHandler;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * <recycleView组件>
 *
 * @data: 2015/11/21 20:16
 * @version: V1.0
 */
public class RecycleListViewImpl<T> implements IGroupListView<T> {

    private static final int DEFAULT_SPAN_COUNT = 1;

    private View mRootView;
    private RecyclerView mListView;
    private IGroupAdapter<T> mAdapter;
    private boolean isVertical = true;
    private boolean isAutoRefresh;

    //还不清楚GridLayoutManager与LinearLayoutManager的性能差别。假如性能有区别，之后需要根据SpanCount使用不同的LayoutManager
    private GridLayoutManager mLayoutManager;

    private PtrFrameLayout refreshLayout;
    private PtrUIHandler ptrUIHandler;

    private boolean mRefreshEnable, mLoadMoreEnable;

    public RecycleListViewImpl(boolean refreshEnable, boolean loadMoreEnable) {
        this(refreshEnable, loadMoreEnable, true);
    }

    public RecycleListViewImpl(boolean refreshEnable, boolean loadMoreEnable, boolean isAutoRefresh) {
        this.mRefreshEnable = refreshEnable;
        this.mLoadMoreEnable = loadMoreEnable;
        this.isAutoRefresh = isAutoRefresh;
    }


    @Override
    public void initView(Context context, IGroupAdapter<T> mAdapter) {
        mRootView = LayoutInflater.from(context).inflate(R.layout.group_recycle_listview, null);
        mListView = (RecyclerView) mRootView.findViewById(R.id.my_recycler_view);
        refreshLayout = (PtrFrameLayout) mRootView.findViewById(R.id.refreshLayout);
        if (ptrUIHandler != null) {
            refreshLayout.setHeaderView((View) ptrUIHandler);
        }
        if (mLayoutManager == null) {
            mLayoutManager = new GridLayoutManager(context, DEFAULT_SPAN_COUNT);
            mLayoutManager.setOrientation(isVertical ? LinearLayoutManager.VERTICAL : LinearLayoutManager.HORIZONTAL);
        }
        mListView.setLayoutManager(mLayoutManager);
//        mListView.setItemAnimator(new MyItemAnimator());
        refreshLayout.setEnabled(mRefreshEnable);

        try {
            mListView.setAdapter((RecyclerView.Adapter) mAdapter);
            this.mAdapter = mAdapter;
        } catch (Exception e) {
            throw new RuntimeException("适配器adpter必须继承RecyclerView.Adapter抽象类和实现IGroupAdapter接口");
        }

    }

    public void selectItem(int position) {
        if (mListView != null) {
            mListView.smoothScrollToPosition(position);
        }
    }

    public void setRefreshHeaderView(PtrUIHandler ptrUIHandler) {
        if (!(ptrUIHandler instanceof View)) {
            throw new RuntimeException("头部View必须继承View，而且实现PtrUIHandler接口");
        }
        this.ptrUIHandler = ptrUIHandler;
    }

    public RecyclerView getRecyclerView() {
        return mListView;
    }

    @Override
    public Observable<Integer> initListener() {
        return Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(final Subscriber<? super Integer> subscriber) {
                if (mLoadMoreEnable) {
                    mListView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                        int dy;

                        @Override
                        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                            super.onScrollStateChanged(recyclerView, newState);
                            if (newState != 0 || mAdapter == null) return;

                            int position = mLayoutManager.findLastVisibleItemPosition();
                            if (position == mAdapter.getAllItemCount() - 1 && dy > 0) {
                                subscriber.onNext(LoadMore);
                            }
                        }

                        @Override
                        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                            super.onScrolled(recyclerView, dx, dy);
                            this.dy = dy;
                        }
                    });
                }
                if (mRefreshEnable) {
                    // 下拉刷新
                    refreshLayout.setPtrHandler(new PtrHandler() {
                        @Override
                        public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                            return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
                        }

                        @Override
                        public void onRefreshBegin(final PtrFrameLayout frame) {
                            subscriber.onNext(Refresh);
                        }
                    });
                }
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public View getRootView() {
        return mRootView;
    }

    @Override
    public View getRecycleView() {
        return mListView;
    }

    @Override
    public boolean isAutoReFresh() {
        return isAutoRefresh;
    }

    @Override
    public void AutoRefresh() {
        refreshLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                refreshLayout.autoRefresh();
            }
        }, 150);
    }

    @Override
    public void onRefreshComplete() {
        if (refreshLayout.isRefreshing()) {
            refreshLayout.refreshComplete();
        }
    }

    public void setLayoutManager(GridLayoutManager layoutManager) {
        mLayoutManager = layoutManager;
    }

    public boolean getRefreshEnable() {
        return mRefreshEnable;
    }

    public void setRefreshEnable(boolean isEnable) {
        mRefreshEnable = isEnable;
        if (isEnable) {
            refreshLayout.setEnabled(true);
        } else {
            refreshLayout.setEnabled(false);
        }
    }
}
