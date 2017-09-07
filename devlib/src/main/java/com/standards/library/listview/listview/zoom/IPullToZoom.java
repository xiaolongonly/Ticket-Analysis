package com.standards.library.listview.listview.zoom;

import android.view.View;

/**
 * <请描述这个类是干什么的>
 *
 * @data: 2016/5/10 15:36
 * @version: V1.0
 */
public interface IPullToZoom<T extends View> {

    /**
     * Get the Wrapped root View.
     *
     * @return The View which is currently wrapped
     */
    T getPullRootView();

    /**
     * Whether Pull-to-Refresh is enabled
     *
     * @return enabled
     */
    boolean isPullToZoomEnabled();

    /**
     * Returns whether the Widget is currently in the Zooming state
     *
     * @return true if the Widget is currently zooming
     */
    boolean isZooming();

    /**
     * Returns whether the Widget is currently in the Zooming anim type
     *
     * @return true if the anim is parallax
     */
    boolean isParallax();

    boolean isHideHeader();

    void handleHeader();

}
