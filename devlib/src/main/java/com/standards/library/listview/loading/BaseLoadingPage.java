package com.standards.library.listview.loading;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.FrameLayout;

/**
 * <请描述这个类是干什么的>
 *
 * @data: 2016/6/1 10:34
 * @version: V1.0
 */
public abstract class BaseLoadingPage extends FrameLayout implements IGroupLoadingHelp {
    protected Context mContext;

    public BaseLoadingPage(Context context) {
        super(context);
        this.mContext = context;
    }

    public BaseLoadingPage(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
    }

    public BaseLoadingPage(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
    }

}
