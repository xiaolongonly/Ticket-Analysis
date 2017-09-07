package com.standards.library.listview.listview.anim;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.view.View;
import android.view.ViewGroup;

public class LayoutAnimator {

    public static class LayoutHeightUpdateListener implements ValueAnimator.AnimatorUpdateListener {

        private final View _view;

        public LayoutHeightUpdateListener(View view) {
            _view = view;
        }

        @Override
        public void onAnimationUpdate(ValueAnimator animation) {
            final ViewGroup.LayoutParams lp = _view.getLayoutParams();
            lp.height = (int) animation.getAnimatedValue();
            _view.setLayoutParams(lp);
        }

    }

    public static Animator ofHeight(View view, int start, int end, int animateTime) {
        final ValueAnimator animator = ValueAnimator.ofInt(start, end);
        animator.setDuration(animateTime);
        animator.addUpdateListener(new LayoutHeightUpdateListener(view));
        return animator;
    }
}
