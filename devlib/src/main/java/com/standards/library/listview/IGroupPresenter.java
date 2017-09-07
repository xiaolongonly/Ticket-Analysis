package com.standards.library.listview;

import android.view.View;

import java.util.List;

public interface IGroupPresenter<T> {

	
	View getRootView();


	void setItems(List<T> list);

	void addItems(List<T> list);

	void removeItem(int position);

	void removeItemRange(int startPos, int count);

	void removeAllItem();

	/**
	 * 修改Item项
	 */
	void replaceItem(int position, T item);

	void addItem(int position, T item);
}
