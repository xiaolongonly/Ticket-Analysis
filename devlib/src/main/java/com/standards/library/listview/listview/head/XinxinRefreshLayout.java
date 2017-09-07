package com.standards.library.listview.listview.head;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.standards.library.R;

import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrUIHandler;
import in.srain.cube.views.ptr.indicator.PtrIndicator;

/**
 * 普通的下拉刷新头部
 *
 * @data: 2015/12/4 11:01
 * @version: V1.0
 */
public class XinxinRefreshLayout extends PtrFrameLayout {
    private static final String TAG = XinxinRefreshLayout.class.getSimpleName();

    public XinxinRefreshLayout(Context context) {
        super(context);
        initViews(context);
    }

    public XinxinRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initViews(context);
    }

    public XinxinRefreshLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initViews(context);
    }

    private void initViews(Context context) {
        final RefreshHeader header = new RefreshHeader(context);
        this.setHeaderView(header);
        this.addPtrUIHandler(header);
    }

    private static class RefreshHeader extends FrameLayout implements PtrUIHandler {

        private ImageView imageMoneyDown;

        private AnimationDrawable animationDrawable;

        public RefreshHeader(Context context) {
            super(context);
            View header = LayoutInflater.from(context).inflate(R.layout.header_refresh_common, this);
            imageMoneyDown = (ImageView) header.findViewById(R.id.imageDown);

            animationDrawable = (AnimationDrawable) imageMoneyDown.getBackground();
            animationDrawable.stop();
        }

        @Override
        public void onUIReset(PtrFrameLayout ptrFrameLayout) {
            Log.i(TAG, "onUIReset");
            animationDrawable.stop();
        }

        @Override
        public void onUIRefreshPrepare(PtrFrameLayout ptrFrameLayout) {
            Log.i(TAG, "onUIRefreshPrepare");
            animationDrawable.stop();
        }

        @Override
        public void onUIRefreshBegin(PtrFrameLayout ptrFrameLayout) {
            Log.i(TAG, "onUIRefreshBegin");
            animationDrawable.start();
        }

        @Override
        public void onUIRefreshComplete(PtrFrameLayout ptrFrameLayout) {
            Log.i(TAG, "onUIRefreshComplete");
            animationDrawable.stop();
        }

        @Override
        public void onUIPositionChange(PtrFrameLayout ptrFrameLayout, boolean b, byte b1, PtrIndicator ptrIndicator) {
//            LogUtil.i(TAG, "onUIPositionChange");
        }
    }
}
