package cn.xiaolong.ticketsystem.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

/**
 * @author xiaolong
 * @version v1.0
 * @function <描述功能>
 * @date: 2017/9/13 15:25
 */

public class FocuseRelativeLayout extends RelativeLayout {
    public FocuseRelativeLayout(Context context) {
        super(context);
    }

    public FocuseRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FocuseRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public FocuseRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return true;
    }
}
