package com.standards.library.listview;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.standards.library.app.ReturnCodeConfig;
import com.standards.library.listview.IGroupPresenter;
import com.standards.library.rx.CSubscriber;
import com.standards.library.rx.ErrorThrowable;
import com.standards.library.listview.adapter.IGroupAdapter;
import com.standards.library.listview.listview.IGroupListView;
import com.standards.library.listview.loading.IGroupLoadingHelp;
import com.standards.library.listview.loading.OnFailClickListener;
import com.standards.library.listview.manager.IGroupManager;

import java.util.ArrayList;
import java.util.List;

import rx.functions.Action1;

public class ListGroupPresenter<T> implements IGroupPresenter<T> {

    private IGroupManager<T> mManager;
    private IGroupAdapter<T> mAdapter;

    private IGroupListView<T> mListView;
    private IGroupLoadingHelp mLoadingHelp;
    //根布局
    private ViewGroup mRootView;
    private Context mContext;

    //是否可以加载更多的标记
    private boolean mShouldLoadMoreFlag;
    //正在下拉加载的标记
    private boolean mIsLoadingFlag;
    //正在加载更多的标记
    private boolean mIsLoadingMoreFlag;
    //没有更多数据的标记
    private boolean mNoMoreDataFlag;

//    CSubscriber subscriber;

    private ListGroupPresenter(Context context) {
        this.mContext = context;

        this.mShouldLoadMoreFlag = false;
        this.mIsLoadingFlag = false;
        this.mIsLoadingMoreFlag = false;
        this.mNoMoreDataFlag = false;
    }

    public static <T> ListGroupPresenter<T> create(Context context, IGroupListView<T> groupListView, IGroupManager<T> groupManager, IGroupAdapter<T> groupAdapter, IGroupLoadingHelp groupLoadingHelp) {
        ListGroupPresenter<T> group = new ListGroupPresenter<T>(context);

        group.mListView = groupListView;
        group.mManager = groupManager;
        group.mLoadingHelp = groupLoadingHelp;
        group.mAdapter = groupAdapter;

        group.onCreateView();

        return group;
    }

    private void onCreateView() {
        mListView.initView(mContext, mAdapter);

        mListView.initListener().subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer type) {
                if (type == IGroupListView.Refresh) {
                    mNoMoreDataFlag = false;
                    mAdapter.onLoadMoreReset();
                    refreshData();
                }
                if (type == IGroupListView.LoadMore) {
                    loadMoreData();
                }
            }
        });

        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        FrameLayout relativeLayout = new FrameLayout(mContext);
        relativeLayout.setLayoutParams(lp);
        relativeLayout.addView(mListView.getRootView(), lp);

        mRootView = relativeLayout;

        mLoadingHelp.createLoadingPage(mRootView);
        mLoadingHelp.setOnFailClickListener(new OnFailClickListener() {
            @Override
            public void onFailClick(int failCode) {
                mAdapter.onLoadMoreReset();
                refreshData();
            }
        });
        if (mListView.isAutoReFresh()) {
            mListView.AutoRefresh();
        } else {
            mLoadingHelp.showLoading();
        }
        refreshData();
    }

    private void loadMoreData() {
        if (!mShouldLoadMoreFlag || mIsLoadingMoreFlag) {
            return;
        }
        if (mNoMoreDataFlag || !canLoadMoreData()) {
            mAdapter.noMoreDataCallback();
            return;
        }

        mManager.loadMoreData(mContext)
                .subscribe(new CSubscriber<List<T>>() {
                    @Override
                    public void onPrepare() {
                        mIsLoadingMoreFlag = true;
                        mAdapter.onLoadMoreStart();
                    }

                    @Override
                    public void onError(ErrorThrowable throwable) {
                        mIsLoadingMoreFlag = false;
                        mShouldLoadMoreFlag = false;

                        loadMoreDataFail(throwable);
                    }

                    @Override
                    public void onSuccess(List<T> list) {
                        mIsLoadingMoreFlag = false;
                        mAdapter.onLoadMoreReset();
                        mAdapter.addItems(list);
                    }
                });
    }

    private boolean canLoadMoreData() {
        if (mManager.getTotalCount() > mAdapter.getDataCount()) {
            return true;
        } else {
            return false;
        }
    }

    private void loadMoreDataFail(ErrorThrowable throwable) {
        if (ReturnCodeConfig.getInstance().isEmptyCode(throwable.code)) {
            mNoMoreDataFlag = true;
            mAdapter.noMoreDataCallback();
        } else {
            mAdapter.onLoadMoreFailData(throwable.code);
        }
    }

    private void refreshData() {
        if (mIsLoadingFlag) return;

        mManager.refreshData(mContext)
                .subscribe(new CSubscriber<List<T>>() {
                    @Override
                    public void onPrepare() {
                        mShouldLoadMoreFlag = false;
                        mIsLoadingFlag = true;
                    }

                    @Override
                    public void onError(ErrorThrowable throwable) {
                        Log.d(ListGroupPresenter.class.getSimpleName(), "errorMsg:" + throwable.msg + "\nerrorCode:" + throwable.code);
                        mIsLoadingFlag = false;
                        refreshDataFail(throwable);

                        mAdapter.setItems(new ArrayList<T>());
                        mListView.onRefreshComplete();
                    }

                    @Override
                    public void onSuccess(List<T> data) {
                        mShouldLoadMoreFlag = true;
                        mIsLoadingFlag = false;
                        mAdapter.onLoadMoreReset();

                        mAdapter.setItems(data);
                        mLoadingHelp.hideLoading();
                    }


                    @Override
                    public void onCompleted() {
                        super.onCompleted();
                        mListView.onRefreshComplete();
                    }
                });
    }

    private void refreshDataFail(ErrorThrowable throwable) {
        mLoadingHelp.hideLoading();
        if (ReturnCodeConfig.getInstance().isEmptyCode(throwable.code)) {
            mLoadingHelp.showEmptyLoadingPage();
        } else {
            mLoadingHelp.showFailPage(throwable.code);
        }

    }

    public void onRefresh() {
        mAdapter.onLoadMoreReset();
        mLoadingHelp.showLoading();
        refreshData();
    }

    public void onRefreshNoLoading() {
        mAdapter.onLoadMoreReset();
        refreshData();
    }

    @Override
    public View getRootView() {
        return mRootView;
    }

    @Override
    public void setItems(List<T> list) {
        mAdapter.setItems(list);
    }

    @Override
    public void addItems(List<T> list) {
        mAdapter.addItems(list);
    }

    @Override
    public void removeItem(int position) {
        mAdapter.removeItem(position);
    }

    @Override
    public void removeItemRange(int startPos, int count) {
        mAdapter.removeItemRange(startPos, count);
    }

    @Override
    public void removeAllItem() {
        mAdapter.removeAllItem();
    }

    @Override
    public void replaceItem(int position, T item) {
        mAdapter.replaceItem(position, item);
    }

    @Override
    public void addItem(int position, T item) {
        mAdapter.addItem(position, item);
    }

}
