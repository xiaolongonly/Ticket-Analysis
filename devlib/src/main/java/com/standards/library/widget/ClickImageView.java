package com.standards.library.widget;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ImageView;

import com.standards.library.R;


/**
 * Created by T048 on 2016/10/21.
 * 有滤镜效果的TextView（点击自带背景色）
 */

public class ClickImageView extends android.support.v7.widget.AppCompatImageView {
    private boolean isTouchOutside;

    public ClickImageView(Context context) {
        super(context);
    }

    public ClickImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ClickImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                //在按下事件中设置滤镜
                setFilter();
                break;
            case MotionEvent.ACTION_UP:
                //由于捕获了Touch事件，需要手动触发Click事件
                if (!isTouchOutside) {
                    performClick();
                }
            case MotionEvent.ACTION_CANCEL:
                removeFilter();
                isTouchOutside = false;
                break;
            case MotionEvent.ACTION_MOVE:
                //在CANCEL和UP事件中清除滤镜
                if ((event.getX() < 0 || event.getX() > getWidth()) ||
                        event.getY() < 0 || event.getY() > getHeight()) {
                    isTouchOutside = true;
                    removeFilter();
                }
            default:
                break;
        }
        return true;
    }

    /**
     * 设置滤镜
     */
    private void setFilter() {
        Drawable drawable = getDrawable();
        if (drawable != null) {
            drawable.setColorFilter(getResources().getColor(R.color.filter2), PorterDuff.Mode.MULTIPLY);      //设置滤镜效果，#DCDCDC;
        }
//        if (getBackground() != null) {
//            getBackground().setColorFilter(getResources().getColor(R.color.filter), PorterDuff.Mode.MULTIPLY);
//        }
        invalidate();
    }

    /**
     * 清除滤镜
     */
    private void removeFilter() {
//        if (getBackground() != null) {
//            getBackground().clearColorFilter();
//        }
        Drawable drawable = getDrawable();
        if (drawable != null) {
            drawable.clearColorFilter();
        }
        invalidate();
    }


}
