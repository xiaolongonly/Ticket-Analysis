package com.standards.library.listview.adapter;

import java.util.List;

public interface IGroupAdapter<T> {

	void setItems(List<T> list);

	void addItems(List<T> list);

	void notifyDataSetChanged();

	void removeItem(int position);

	void removeItemRange(int startPos, int count);

	void removeAllItem();

	/**
	 * 修改Item项
	 */
	void replaceItem(int position, T item);

	void addItem(int position, T item);

	void noMoreDataCallback();

	void onLoadMoreStart();

	void onLoadMoreReset();

	int getAllItemCount();

	int getDataCount();

	void onLoadMoreFailData(int failCode);

}
