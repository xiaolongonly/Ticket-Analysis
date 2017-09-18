package com.standards.library.widget;

import android.content.Context;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.TextView;

import com.standards.library.R;


/**
 * Created by T048 on 2016/10/21.
 * 有滤镜效果的TextView（点击自带背景色）
 */

public class ClickTextView extends TextView {
    private boolean isTouchOutside;

    public ClickTextView(Context context) {
        super(context);
    }

    public ClickTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ClickTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (isEnabled()) {
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
        }
        return true;
    }

    /**
     * 设置滤镜
     */
    private void setFilter() {

//        Drawable[] drawables = getCompoundDrawables();
//        if (drawables != null && drawables.length > 0) {
//            for (Drawable dw : drawables) {
//                if (dw != null)
//                    dw.setColorFilter(getResources().getColor(R.color.filter), PorterDuff.Mode.MULTIPLY);      //设置滤镜效果，#DCDCDC;
//            }
//        }
        if (getBackground() != null) {
            getBackground().setColorFilter(getResources().getColor(R.color.filter), PorterDuff.Mode.MULTIPLY);
        }
        invalidate();
    }

    /**
     * 清除滤镜
     */
    private void removeFilter() {
        if (getBackground() != null) {
            getBackground().clearColorFilter();
        }
//        Drawable[] drawables = getCompoundDrawables();
//        if (drawables != null && drawables.length > 0) {
//            for (Drawable dw : drawables) {
//                if (dw != null) dw.clearColorFilter();
//            }
//        }
        invalidate();
    }


}
