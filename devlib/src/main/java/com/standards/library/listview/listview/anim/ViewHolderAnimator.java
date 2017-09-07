package com.standards.library.listview.listview.anim;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

public class ViewHolderAnimator {

    public interface AnimatorListener {
        void onAnimatorEnd();
    }

    public static class ViewHolderAnimatorListener extends AnimatorListenerAdapter {
        private final RecyclerView.ViewHolder _holder;
        private AnimatorListener _animatorListener;

        public ViewHolderAnimatorListener(RecyclerView.ViewHolder holder) {
            this(holder, null);
        }

        public ViewHolderAnimatorListener(RecyclerView.ViewHolder holder, AnimatorListener animatorListener) {
            _holder = holder;
            _animatorListener = animatorListener;
        }

        @Override
        public void onAnimationStart(Animator animation) {
            _holder.setIsRecyclable(false);
        }

        @Override
        public void onAnimationEnd(Animator animation) {
            _holder.setIsRecyclable(true);
            if(_animatorListener != null) {
                _animatorListener.onAnimatorEnd();
            }
        }

        @Override
        public void onAnimationCancel(Animator animation) {
            _holder.setIsRecyclable(true);
        }
    }

    public static class LayoutParamsAnimatorListener extends AnimatorListenerAdapter {
        private final View _view;
        private final int _paramsWidth;
        private final int _paramsHeight;

        public LayoutParamsAnimatorListener(View view, int paramsWidth, int paramsHeight) {
            _view = view;
            _paramsWidth = paramsWidth;
            _paramsHeight = paramsHeight;
        }

        @Override
        public void onAnimationEnd(Animator animation) {
            final ViewGroup.LayoutParams params = _view.getLayoutParams();
            params.width = _paramsWidth;
            params.height = _paramsHeight;
            _view.setLayoutParams(params);
        }
    }

    public static Animator ofItemViewHeight(RecyclerView.ViewHolder holder, int heightAnimateTime) {
        View parent = (View) holder.itemView.getParent();
        if (parent == null)
            throw new IllegalStateException("Cannot animate the layout of a view that has no parent");

        int start = holder.itemView.getMeasuredHeight();
        holder.itemView.measure(View.MeasureSpec.makeMeasureSpec(parent.getMeasuredWidth(), View.MeasureSpec.AT_MOST), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        int end = holder.itemView.getMeasuredHeight();

        final Animator animator = LayoutAnimator.ofHeight(holder.itemView, start, end, heightAnimateTime);
        animator.addListener(new ViewHolderAnimatorListener(holder));

        animator.addListener(new LayoutParamsAnimatorListener(holder.itemView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        return animator;
    }

}
