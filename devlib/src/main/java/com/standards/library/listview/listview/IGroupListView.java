package com.standards.library.listview.listview;

import android.content.Context;
import android.view.View;

import com.standards.library.listview.adapter.IGroupAdapter;

import rx.Observable;

/**
 * 
 * @author ml_bright
 * @email 2504509903@qq.com
 * @date 2015-10-16 下午4:14:56
 * @version V1.0
 */
public interface IGroupListView<T> {
	int Refresh = 0;
	int LoadMore = 1;
	void initView(Context context, IGroupAdapter<T> adapter);

	Observable<Integer> initListener();

	View getRootView();

	View getRecycleView();

	boolean isAutoReFresh();

	void AutoRefresh();

	/**
	 * 结束下拉刷新
	 */
	void onRefreshComplete();

}
