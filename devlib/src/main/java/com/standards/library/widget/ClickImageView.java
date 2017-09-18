package com.standards.library.widget;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

/**
 * @author xiaolong
 * @version v1.0
 * @function <描述功能>
 * @date 2016/12/1-16:21
 */
public class ClickImageView extends ImageView {
    private boolean isTouchOutside;
    private Animator anim1;
    private Animator anim2;
    private Handler mHandler = new Handler();


    public ClickImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {

        PropertyValuesHolder valueHolder_1 = PropertyValuesHolder.ofFloat(
                "scaleX", 1f, 0.9f);
        PropertyValuesHolder valuesHolder_2 = PropertyValuesHolder.ofFloat(
                "scaleY", 1f, 0.9f);
        anim1 = ObjectAnimator.ofPropertyValuesHolder(this, valueHolder_1,
                valuesHolder_2);
        anim1.setDuration(200);
        anim1.setInterpolator(new LinearInterpolator());

        PropertyValuesHolder valueHolder_3 = PropertyValuesHolder.ofFloat(
                "scaleX", 0.9f, 1f);
        PropertyValuesHolder valuesHolder_4 = PropertyValuesHolder.ofFloat(
                "scaleY", 0.9f, 1f);
        anim2 = ObjectAnimator.ofPropertyValuesHolder(this, valueHolder_3,
                valuesHolder_4);
        anim2.setDuration(200);
        anim2.setInterpolator(new LinearInterpolator());
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                isTouchOutside = false;
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        anim2.end();
                        anim1.start();
                    }
                });
                break;
            case MotionEvent.ACTION_MOVE:
                //在CANCEL和UP事件中清除滤镜
                if ((event.getX() < 0 || event.getX() > getWidth()) ||
                        event.getY() < 0 || event.getY() > getHeight()) {
                    if (!isTouchOutside) {
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                anim1.end();
                                anim2.start();
                            }
                        });
                        isTouchOutside = true;
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                if (!isTouchOutside) {
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            anim1.end();
                            anim2.start();
                        }
                    });
                    performClick();
                }
                break;
            case MotionEvent.ACTION_CANCEL:
                break;
        }
        return true;
    }


    @Override
    protected void onDetachedFromWindow() {
        // TODO Auto-generated method stub
        super.onDetachedFromWindow();
    }

}