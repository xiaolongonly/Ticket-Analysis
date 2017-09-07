package com.standards.library.listview.listview.zoom;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

/**
 * <请描述这个类是干什么的>
 *
 * @data: 2016/6/7 9:10
 * @version: V1.0
 */
public class pullToZoomScrollView extends PullToZoomBase<ScrollView> {
    public pullToZoomScrollView(Context context) {
        super(context);
    }

    public pullToZoomScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void pullHeaderToZoom(int newScrollValue) {

    }

    @Override
    protected ScrollView createRootView(Context context, AttributeSet attrs) {
        return null;
    }

    @Override
    protected void smoothScrollToTop() {

    }

    @Override
    protected boolean isReadyForPullStart() {
        return false;
    }

    @Override
    public void handleHeader() {

    }




}
