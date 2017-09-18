package com.standards.library.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.standards.library.R;
import com.zhy.autolayout.AutoRelativeLayout;


/**
 * @author xiaolong
 * @version v1.0
 * @function <描述功能>
 * @date 2016/10/24-16:23
 */
public class ClickRelativeLayout extends AutoRelativeLayout {
    //    private Drawable mDrawable;
    private boolean isTouchOutside;

    public ClickRelativeLayout(Context context) {
        super(context);
    }

    public ClickRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ClickRelativeLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
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
                isTouchOutside = false;
                break;
            case MotionEvent.ACTION_MOVE:
//                if (mRectF == null) {
//                    mRectF = calcViewScreenLocation();
//                    LogUtil.d("top:" + mRectF.top + " left:" + mRectF.left + " right:" + mRectF.right
//                            + " bottom:" + mRectF.bottom);
//                }
//                LogUtil.d(event.getX() + " " + event.getY());
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

//    /**
//     * 计算指定的 View 在屏幕中的坐标。
//     */
//    public RectF calcViewScreenLocation() {
//        int[] location = new int[2];
//        // 获取控件在屏幕中的位置，返回的数组分别为控件左顶点的 x、y 的值
//        getLocationOnScreen(location);
//        return new RectF(location[0], location[1], location[0] + getWidth(),
//                location[1] + getHeight());
//    }
}
