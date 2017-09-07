package com.standards.library.listview.listview.head;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.FrameLayout;

import com.standards.library.R;

import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrUIHandler;
import in.srain.cube.views.ptr.header.MaterialHeader;
import in.srain.cube.views.ptr.indicator.PtrIndicator;

/**
 * 普通的下拉刷新头部
 *
 * @data: 2015/12/4 11:01
 * @version: V1.0
 */
public class CommonRefreshLayout extends PtrFrameLayout {
    private static final String TAG = CommonRefreshLayout.class.getSimpleName();

    public CommonRefreshLayout(Context context) {
        super(context);
        initViews(context);
    }

    public CommonRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initViews(context);
    }

    public CommonRefreshLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initViews(context);
    }

    private void initViews(Context context) {
//        final RefreshHeader header = new RefreshHeader(context);
//        this.setHeaderView(header);
//        this.addPtrUIHandler(header);

        final MaterialHeader header = new MaterialHeader(context);
        int[] colors = context.getResources().getIntArray(R.array.refresh_color);
        header.setColorSchemeColors(colors);
        header.setLayoutParams(new LayoutParams(-1, -2));
        header.setPadding(0, 14, 0, 14);
        this.setHeaderView(header);
        this.addPtrUIHandler(header);
    }

    private static class RefreshHeader extends FrameLayout implements PtrUIHandler {

        private final int ROTATE_ANIM_DURATION = 500;

        private View loadView;
        private View pullView;
        private View errorView;

        private Animation rotateAnim;
        private Animation showAnim;
        private Animation hideAnim;

        public RefreshHeader(Context context) {
            super(context);
            View header = LayoutInflater.from(context).inflate(R.layout.header_refresh_joke, this);
            loadView = header.findViewById(R.id.image_load);
            pullView = header.findViewById(R.id.image_down);
            errorView = header.findViewById(R.id.image_error);

            rotateAnim = new RotateAnimation(0, 359, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            rotateAnim.setDuration(ROTATE_ANIM_DURATION);
            rotateAnim.setRepeatCount(-1);
            rotateAnim.setInterpolator(new LinearInterpolator());// 设置匀速，无加速度不卡顿
            rotateAnim.setRepeatMode(Animation.RESTART);

            showAnim = new AlphaAnimation(0, 1.0f);
            showAnim.setDuration(ROTATE_ANIM_DURATION);
            showAnim.setFillAfter(true);

            hideAnim = new AlphaAnimation(1.0f, 0);
            hideAnim.setDuration(ROTATE_ANIM_DURATION);
            hideAnim.setFillAfter(true);
        }

        @Override
        public void onUIReset(PtrFrameLayout ptrFrameLayout) {
            Log.i(TAG, "onUIReset");
            pullView.setVisibility(VISIBLE);
            loadView.clearAnimation();
            loadView.setVisibility(GONE);
            errorView.setVisibility(GONE);
        }

        @Override
        public void onUIRefreshPrepare(PtrFrameLayout ptrFrameLayout) {
            Log.i(TAG, "onUIRefreshPrepare");
            loadView.setVisibility(GONE);
            pullView.setVisibility(VISIBLE);
            errorView.setVisibility(GONE);
        }

        @Override
        public void onUIRefreshBegin(PtrFrameLayout ptrFrameLayout) {
            Log.i(TAG, "onUIRefreshBegin");
            pullView.setVisibility(GONE);
            loadView.setVisibility(VISIBLE);
            loadView.startAnimation(rotateAnim);
            errorView.setVisibility(GONE);
        }

        @Override
        public void onUIRefreshComplete(PtrFrameLayout ptrFrameLayout) {
            Log.i(TAG, "onUIRefreshComplete");
//            if (Utils.isNetworkAvailable(getContext())) {
//                pullView.setVisibility(VISIBLE);
//                errorView.setVisibility(GONE);
//            } else {
            pullView.setVisibility(GONE);
            errorView.setVisibility(VISIBLE);
//            }

            loadView.clearAnimation();
            loadView.setVisibility(GONE);
        }

        @Override
        public void onUIPositionChange(PtrFrameLayout ptrFrameLayout, boolean b, byte b1, PtrIndicator ptrIndicator) {
//            LogUtil.i(TAG, "onUIPositionChange");
        }
    }
}
