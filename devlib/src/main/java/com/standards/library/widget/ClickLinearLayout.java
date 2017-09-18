package com.standards.library.widget;

import android.content.Context;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.standards.library.R;
import com.zhy.autolayout.AutoLinearLayout;


/**
 * @author xiaolong
 * @version v1.0
 * @function <描述功能>
 * @date 2016/10/24-16:23
 */
public class ClickLinearLayout extends AutoLinearLayout {
    //    private Drawable mDrawable;
    private boolean isTouchOutside;

    public ClickLinearLayout(Context context) {
        super(context);
    }

    public ClickLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ClickLinearLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                isTouchOutside = false;
                //在按下事件中设置滤镜
                setFilter();
                break;
            case MotionEvent.ACTION_UP:
                //由于捕获了Touch事件，需要手动触发Click事件
                if (!isTouchOutside) {
                    performClick();
                }
            case MotionEvent.ACTION_CANCEL:
                //在CANCEL和UP事件中清除滤镜
                removeFilter();
                break;
            case MotionEvent.ACTION_MOVE:
                //在CANCEL和UP事件中清除滤镜
                if ((event.getX() < 0 || event.getX() > getWidth()) ||
                        event.getY() < 0 || event.getY() > getHeight()) {
                    if (!isTouchOutside) {
                        isTouchOutside = true;
                        removeFilter();
                    }
                }
                break;
            default:
                break;
        }
        return true;
    }

    /**
     * 设置滤镜
     */
    private void setFilter() {
        if (getBackground() != null) {
            getBackground().setColorFilter(getResources().getColor(R.color.filter), PorterDuff.Mode.MULTIPLY);
        }
        invalidate();
//        mDrawable = getBackground();
//        setBackgroundResource(R.color.gainsboro);
//        Drawable[] drawables = getCompoundDrawables();
//        if (drawables != null && drawables.length > 0) {
//            for (Drawable dw : drawables) {
//                if (dw != null) dw.setColorFilter(Color.rgb(92, 43, 43), PorterDuff.Mode.MULTIPLY);      //设置滤镜效果，#DCDCDC;
//            }
//        }
    }

    /**
     * 清除滤镜
     */
    private void removeFilter() {
        if (getBackground() != null) {
            getBackground().clearColorFilter();
        }
        invalidate();
//        setBackground(mDrawable);
//        Drawable[] drawables = getCompoundDrawables();
//        if (drawables != null && drawables.length > 0) {
//            for (Drawable dw : drawables) {
//                if (dw != null) dw.clearColorFilter();
//            }
//        }
    }
}
