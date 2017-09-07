package com.standards.library.listview.listview.anim;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class ExpandableViewHoldersUtil {

    public static void openH(final RecyclerView.ViewHolder holder, final View expandView, final boolean animate, int heightAnimateTime, final ViewHolderAnimator.AnimatorListener animatorListener) {
        if (animate) {
            expandView.setVisibility(View.VISIBLE);
            final Animator animator = ViewHolderAnimator.ofItemViewHeight(holder, heightAnimateTime);
            animator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    if(animatorListener != null) {
                        animatorListener.onAnimatorEnd();
                    }
                    final ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(expandView, View.ALPHA, 1);
                    alphaAnimator.addListener(new ViewHolderAnimator.ViewHolderAnimatorListener(holder));
                    alphaAnimator.start();
                }
            });
            animator.start();
        }
        else {
            expandView.setVisibility(View.VISIBLE);
            expandView.setAlpha(1);
        }
    }

    public static void closeH(final RecyclerView.ViewHolder holder, final View expandView, final boolean animate, int heightAnimateTime, final ViewHolderAnimator.AnimatorListener animatorListener) {
        if (animate) {
            expandView.setVisibility(View.GONE);
            final Animator animator = ViewHolderAnimator.ofItemViewHeight(holder, heightAnimateTime);
            expandView.setVisibility(View.VISIBLE);
            animator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    if(animatorListener != null) {
                        animatorListener.onAnimatorEnd();
                    }
                    expandView.setVisibility(View.GONE);
                    expandView.setAlpha(0);
                }
                @Override
                public void onAnimationCancel(Animator animation) {
                    expandView.setVisibility(View.GONE);
                    expandView.setAlpha(0);
                }
            });
            animator.start();
        }
        else {
            expandView.setVisibility(View.GONE);
            expandView.setAlpha(0);
        }
    }

    public static interface Expandable {
        public View getExpandView();
    }

    public static class KeepOneH<VH extends RecyclerView.ViewHolder & Expandable> {
        private int _opened = -1;
        private ViewHolderAnimator.AnimatorListener animatorListener;

        //高度动画渐显的总时间
        public int heightTime;

        public KeepOneH(int heightTime) {
            this.heightTime = heightTime;
        }

        public void bind(VH holder, int pos) {
            if (pos == _opened)
                ExpandableViewHoldersUtil.openH(holder, holder.getExpandView(), false, heightTime, new ViewHolderAnimator.AnimatorListener() {
                    @Override
                    public void onAnimatorEnd() {
                        if(animatorListener != null) {
                            animatorListener.onAnimatorEnd();
                        }
                    }
                });
            else
                ExpandableViewHoldersUtil.closeH(holder, holder.getExpandView(), false, heightTime, null);
        }

        @SuppressWarnings("unchecked")
        public void toggle(VH holder) {
            if (_opened == holder.getPosition()) {
                _opened = -1;
                ExpandableViewHoldersUtil.closeH(holder, holder.getExpandView(), true, heightTime, null);
            }
            else {
                int previous = _opened;
                _opened = holder.getPosition();
                ExpandableViewHoldersUtil.openH(holder, holder.getExpandView(), true, heightTime, new ViewHolderAnimator.AnimatorListener() {
                    @Override
                    public void onAnimatorEnd() {
                        if (animatorListener != null) {
                            animatorListener.onAnimatorEnd();
                        }
                    }
                });

                final VH oldHolder = (VH) ((RecyclerView) holder.itemView.getParent()).findViewHolderForPosition(previous);
                if (oldHolder != null) {
                    ExpandableViewHoldersUtil.closeH(oldHolder, oldHolder.getExpandView(), true, heightTime, new ViewHolderAnimator.AnimatorListener() {
                        @Override
                        public void onAnimatorEnd() {
//                            if (animatorListener != null) {
//                                animatorListener.onAnimatorEnd();
//                            }
                        }
                    });
                } else {

                }
            }
        }

        public void setAnimatorListener(ViewHolderAnimator.AnimatorListener animatorListener) {
            this.animatorListener = animatorListener;
        }

        public boolean isOpen(VH holder) {
            return _opened == holder.getPosition();
        }
    }

}
