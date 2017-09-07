/*
package com.cncn.library.listview.listview.anim;//


import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.animation.AnimatorCompatHelper;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorCompat;
import android.support.v4.view.ViewPropertyAnimatorListener;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ItemAnimator;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


//去除局部刷新闪烁/
//
//
// // 去掉alpha(0)
*/
/*oldViewAnim.alpha(0).setListener(new VpaListenerAdapter() {...}).start();
        oldViewAnim.setListener(new VpaListenerAdapter() {...}).start();

// 去掉alpha(1)
        newViewAnimation.translationX(0).translationY(0).setDuration(getChangeDuration()).
        alpha(1).setListener(new VpaListenerAdapter() {...}).start();
        newViewAnimation.translationX(0).translationY(0).setDuration(getChangeDuration()).
        setListener(new VpaListenerAdapter() {...}).start();
        最后使用修改后的动画。

        recyclerView.setItemAnimator(new MyItemAnimator());*//*

public class MyItemAnimator extends ItemAnimator {
    private static final boolean DEBUG = false;
    private ArrayList<ViewHolder> mPendingRemovals = new ArrayList();
    private ArrayList<ViewHolder> mPendingAdditions = new ArrayList();
    private ArrayList<MoveInfo> mPendingMoves = new ArrayList();
    private ArrayList<ChangeInfo> mPendingChanges = new ArrayList();
    private ArrayList<ArrayList<ViewHolder>> mAdditionsList = new ArrayList();
    private ArrayList<ArrayList<MoveInfo>> mMovesList = new ArrayList();
    private ArrayList<ArrayList<ChangeInfo>> mChangesList = new ArrayList();
    private ArrayList<ViewHolder> mAddAnimations = new ArrayList();
    private ArrayList<ViewHolder> mMoveAnimations = new ArrayList();
    private ArrayList<ViewHolder> mRemoveAnimations = new ArrayList();
    private ArrayList<ViewHolder> mChangeAnimations = new ArrayList();

    public MyItemAnimator() {
    }

    @Override
    public boolean animateDisappearance(@NonNull ViewHolder viewHolder, @NonNull ItemHolderInfo preLayoutInfo, @Nullable ItemHolderInfo postLayoutInfo) {
        return false;
    }

    @Override
    public boolean animateAppearance(@NonNull ViewHolder viewHolder, @Nullable ItemHolderInfo preLayoutInfo, @NonNull ItemHolderInfo postLayoutInfo) {
        return false;
    }

    @Override
    public boolean animatePersistence(@NonNull ViewHolder viewHolder, @NonNull ItemHolderInfo preLayoutInfo, @NonNull ItemHolderInfo postLayoutInfo) {
        return false;
    }

    @Override
    public boolean animateChange(@NonNull ViewHolder oldHolder, @NonNull ViewHolder newHolder, @NonNull ItemHolderInfo preLayoutInfo, @NonNull ItemHolderInfo postLayoutInfo) {
        return false;
    }

    public void runPendingAnimations() {
        boolean removalsPending = !this.mPendingRemovals.isEmpty();
        boolean movesPending = !this.mPendingMoves.isEmpty();
        boolean changesPending = !this.mPendingChanges.isEmpty();
        boolean additionsPending = !this.mPendingAdditions.isEmpty();
        if(removalsPending || movesPending || additionsPending || changesPending) {
            Iterator additions = this.mPendingRemovals.iterator();

            while(additions.hasNext()) {
                ViewHolder adder = (ViewHolder)additions.next();
                this.animateRemoveImpl(adder);
            }

            this.mPendingRemovals.clear();
            ArrayList additions1;
            Runnable adder1;
            if(movesPending) {
                additions1 = new ArrayList();
                additions1.addAll(this.mPendingMoves);
                this.mMovesList.add(additions1);
                this.mPendingMoves.clear();
                final ArrayList finalAdditions1 = additions1;
                adder1 = new Runnable() {
                    public void run() {
                        Iterator i$ = finalAdditions1.iterator();

                        while(i$.hasNext()) {
                            MyItemAnimator.MoveInfo moveInfo = (MyItemAnimator.MoveInfo)i$.next();
                            MyItemAnimator.this.animateMoveImpl(moveInfo.holder, moveInfo.fromX, moveInfo.fromY, moveInfo.toX, moveInfo.toY);
                        }

                        finalAdditions1.clear();
                        MyItemAnimator.this.mMovesList.remove(finalAdditions1);
                    }
                };
                if(removalsPending) {
                    View removeDuration = ((MyItemAnimator.MoveInfo)additions1.get(0)).holder.itemView;
                    ViewCompat.postOnAnimationDelayed(removeDuration, adder1, this.getRemoveDuration());
                } else {
                    adder1.run();
                }
            }

            if(changesPending) {
                additions1 = new ArrayList();
                additions1.addAll(this.mPendingChanges);
                this.mChangesList.add(additions1);
                this.mPendingChanges.clear();
                final ArrayList finalAdditions = additions1;
                adder1 = new Runnable() {
                    public void run() {
                        Iterator i$ = finalAdditions.iterator();

                        while(i$.hasNext()) {
                            MyItemAnimator.ChangeInfo change = (MyItemAnimator.ChangeInfo)i$.next();
                            MyItemAnimator.this.animateChangeImpl(change);
                        }

                        finalAdditions.clear();
                        MyItemAnimator.this.mChangesList.remove(finalAdditions);
                    }
                };
                if(removalsPending) {
                    ViewHolder removeDuration1 = ((MyItemAnimator.ChangeInfo)additions1.get(0)).oldHolder;
                    ViewCompat.postOnAnimationDelayed(removeDuration1.itemView, adder1, this.getRemoveDuration());
                } else {
                    adder1.run();
                }
            }

            if(additionsPending) {
                additions1 = new ArrayList();
                additions1.addAll(this.mPendingAdditions);
                this.mAdditionsList.add(additions1);
                this.mPendingAdditions.clear();
                final ArrayList finalAdditions2 = additions1;
                adder1 = new Runnable() {
                    public void run() {
                        Iterator i$ = finalAdditions2.iterator();

                        while(i$.hasNext()) {
                            ViewHolder holder = (ViewHolder)i$.next();
                            MyItemAnimator.this.animateAddImpl(holder);
                        }

                        finalAdditions2.clear();
                        MyItemAnimator.this.mAdditionsList.remove(finalAdditions2);
                    }
                };
                if(!removalsPending && !movesPending && !changesPending) {
                    adder1.run();
                } else {
                    long removeDuration2 = removalsPending?this.getRemoveDuration():0L;
                    long moveDuration = movesPending?this.getMoveDuration():0L;
                    long changeDuration = changesPending?this.getChangeDuration():0L;
                    long totalDelay = removeDuration2 + Math.max(moveDuration, changeDuration);
                    View view = ((ViewHolder)additions1.get(0)).itemView;
                    ViewCompat.postOnAnimationDelayed(view, adder1, totalDelay);
                }
            }

        }
    }

    public boolean animateRemove(ViewHolder holder) {
        this.resetAnimation(holder);
        this.mPendingRemovals.add(holder);
        return true;
    }

    private void animateRemoveImpl(final ViewHolder holder) {
        View view = holder.itemView;
        final ViewPropertyAnimatorCompat animation = ViewCompat.animate(view);
        this.mRemoveAnimations.add(holder);
        animation.setDuration(this.getRemoveDuration()).alpha(0.0F).setListener(new MyItemAnimator.VpaListenerAdapter() {
            public void onAnimationStart(View view) {
                MyItemAnimator.this.dispatchRemoveStarting(holder);
            }

            public void onAnimationEnd(View view) {
                animation.setListener((ViewPropertyAnimatorListener)null);
                ViewCompat.setAlpha(view, 1.0F);
                MyItemAnimator.this.dispatchRemoveFinished(holder);
                MyItemAnimator.this.mRemoveAnimations.remove(holder);
                MyItemAnimator.this.dispatchFinishedWhenDone();
            }
        }).start();
    }

    public boolean animateAdd(ViewHolder holder) {
        this.resetAnimation(holder);
        ViewCompat.setAlpha(holder.itemView, 0.0F);
        this.mPendingAdditions.add(holder);
        return true;
    }

    private void animateAddImpl(final ViewHolder holder) {
        View view = holder.itemView;
        final ViewPropertyAnimatorCompat animation = ViewCompat.animate(view);
        this.mAddAnimations.add(holder);
        animation.alpha(1.0F).setDuration(this.getAddDuration()).setListener(new MyItemAnimator.VpaListenerAdapter() {
            public void onAnimationStart(View view) {
                MyItemAnimator.this.dispatchAddStarting(holder);
            }

            public void onAnimationCancel(View view) {
                ViewCompat.setAlpha(view, 1.0F);
            }

            public void onAnimationEnd(View view) {
                animation.setListener((ViewPropertyAnimatorListener)null);
                MyItemAnimator.this.dispatchAddFinished(holder);
                MyItemAnimator.this.mAddAnimations.remove(holder);
                MyItemAnimator.this.dispatchFinishedWhenDone();
            }
        }).start();
    }

    public boolean animateMove(ViewHolder holder, int fromX, int fromY, int toX, int toY) {
        View view = holder.itemView;
        fromX = (int)((float)fromX + ViewCompat.getTranslationX(holder.itemView));
        fromY = (int)((float)fromY + ViewCompat.getTranslationY(holder.itemView));
        this.resetAnimation(holder);
        int deltaX = toX - fromX;
        int deltaY = toY - fromY;
        if(deltaX == 0 && deltaY == 0) {
            this.dispatchMoveFinished(holder);
            return false;
        } else {
            if(deltaX != 0) {
                ViewCompat.setTranslationX(view, (float)(-deltaX));
            }

            if(deltaY != 0) {
                ViewCompat.setTranslationY(view, (float)(-deltaY));
            }

            this.mPendingMoves.add(new MyItemAnimator.MoveInfo(holder, fromX, fromY, toX, toY));
            return true;
        }
    }

    private void animateMoveImpl(final ViewHolder holder, int fromX, int fromY, int toX, int toY) {
        View view = holder.itemView;
        final int deltaX = toX - fromX;
        final int deltaY = toY - fromY;
        if(deltaX != 0) {
            ViewCompat.animate(view).translationX(0.0F);
        }

        if(deltaY != 0) {
            ViewCompat.animate(view).translationY(0.0F);
        }

        final ViewPropertyAnimatorCompat animation = ViewCompat.animate(view);
        this.mMoveAnimations.add(holder);
        animation.setDuration(this.getMoveDuration()).setListener(new MyItemAnimator.VpaListenerAdapter() {
            public void onAnimationStart(View view) {
                MyItemAnimator.this.dispatchMoveStarting(holder);
            }

            public void onAnimationCancel(View view) {
                if(deltaX != 0) {
                    ViewCompat.setTranslationX(view, 0.0F);
                }

                if(deltaY != 0) {
                    ViewCompat.setTranslationY(view, 0.0F);
                }

            }

            public void onAnimationEnd(View view) {
                animation.setListener((ViewPropertyAnimatorListener)null);
                MyItemAnimator.this.dispatchMoveFinished(holder);
                MyItemAnimator.this.mMoveAnimations.remove(holder);
                MyItemAnimator.this.dispatchFinishedWhenDone();
            }
        }).start();
    }

    public boolean animateChange(ViewHolder oldHolder, ViewHolder newHolder, int fromX, int fromY, int toX, int toY) {
        float prevTranslationX = ViewCompat.getTranslationX(oldHolder.itemView);
        float prevTranslationY = ViewCompat.getTranslationY(oldHolder.itemView);
        float prevAlpha = ViewCompat.getAlpha(oldHolder.itemView);
        this.resetAnimation(oldHolder);
        int deltaX = (int)((float)(toX - fromX) - prevTranslationX);
        int deltaY = (int)((float)(toY - fromY) - prevTranslationY);
        ViewCompat.setTranslationX(oldHolder.itemView, prevTranslationX);
        ViewCompat.setTranslationY(oldHolder.itemView, prevTranslationY);
        ViewCompat.setAlpha(oldHolder.itemView, prevAlpha);
        if(newHolder != null && newHolder.itemView != null) {
            this.resetAnimation(newHolder);
            ViewCompat.setTranslationX(newHolder.itemView, (float)(-deltaX));
            ViewCompat.setTranslationY(newHolder.itemView, (float)(-deltaY));
            ViewCompat.setAlpha(newHolder.itemView, 0.0F);
        }

        this.mPendingChanges.add(new MyItemAnimator.ChangeInfo(oldHolder, newHolder, fromX, fromY, toX, toY));
        return true;
    }

    private void animateChangeImpl(final MyItemAnimator.ChangeInfo changeInfo) {
        ViewHolder holder = changeInfo.oldHolder;
        View view = holder == null?null:holder.itemView;
        ViewHolder newHolder = changeInfo.newHolder;
        final View newView = newHolder != null?newHolder.itemView:null;
        ViewPropertyAnimatorCompat newViewAnimation;
        if(view != null) {
            newViewAnimation = ViewCompat.animate(view).setDuration(this.getChangeDuration());
            this.mChangeAnimations.add(changeInfo.oldHolder);
            newViewAnimation.translationX((float)(changeInfo.toX - changeInfo.fromX));
            newViewAnimation.translationY((float)(changeInfo.toY - changeInfo.fromY));
            final ViewPropertyAnimatorCompat finalNewViewAnimation1 = newViewAnimation;
            newViewAnimation.setListener(new MyItemAnimator.VpaListenerAdapter() {
                public void onAnimationStart(View view) {
                    MyItemAnimator.this.dispatchChangeStarting(changeInfo.oldHolder, true);
                }

                public void onAnimationEnd(View view) {
                    finalNewViewAnimation1.setListener((ViewPropertyAnimatorListener)null);
                    ViewCompat.setAlpha(view, 1.0F);
                    ViewCompat.setTranslationX(view, 0.0F);
                    ViewCompat.setTranslationY(view, 0.0F);
                    MyItemAnimator.this.dispatchChangeFinished(changeInfo.oldHolder, true);
                    MyItemAnimator.this.mChangeAnimations.remove(changeInfo.oldHolder);
                    MyItemAnimator.this.dispatchFinishedWhenDone();
                }
            }).start();
        }

        if(newView != null) {
            newViewAnimation = ViewCompat.animate(newView);
            this.mChangeAnimations.add(changeInfo.newHolder);
            final ViewPropertyAnimatorCompat finalNewViewAnimation = newViewAnimation;
            newViewAnimation.translationX(0.0F).translationY(0.0F).setDuration(this.getChangeDuration()).setListener(new MyItemAnimator.VpaListenerAdapter() {
                public void onAnimationStart(View view) {
                    MyItemAnimator.this.dispatchChangeStarting(changeInfo.newHolder, false);
                }

                public void onAnimationEnd(View view) {
                    finalNewViewAnimation.setListener((ViewPropertyAnimatorListener)null);
                    ViewCompat.setAlpha(newView, 1.0F);
                    ViewCompat.setTranslationX(newView, 0.0F);
                    ViewCompat.setTranslationY(newView, 0.0F);
                    MyItemAnimator.this.dispatchChangeFinished(changeInfo.newHolder, false);
                    MyItemAnimator.this.mChangeAnimations.remove(changeInfo.newHolder);
                    MyItemAnimator.this.dispatchFinishedWhenDone();
                }
            }).start();
        }

    }

    private void endChangeAnimation(List<ChangeInfo> infoList, ViewHolder item) {
        for(int i = infoList.size() - 1; i >= 0; --i) {
            MyItemAnimator.ChangeInfo changeInfo = (MyItemAnimator.ChangeInfo)infoList.get(i);
            if(this.endChangeAnimationIfNecessary(changeInfo, item) && changeInfo.oldHolder == null && changeInfo.newHolder == null) {
                infoList.remove(changeInfo);
            }
        }

    }

    private void endChangeAnimationIfNecessary(MyItemAnimator.ChangeInfo changeInfo) {
        if(changeInfo.oldHolder != null) {
            this.endChangeAnimationIfNecessary(changeInfo, changeInfo.oldHolder);
        }

        if(changeInfo.newHolder != null) {
            this.endChangeAnimationIfNecessary(changeInfo, changeInfo.newHolder);
        }

    }

    private boolean endChangeAnimationIfNecessary(MyItemAnimator.ChangeInfo changeInfo, ViewHolder item) {
        boolean oldItem = false;
        if(changeInfo.newHolder == item) {
            changeInfo.newHolder = null;
        } else {
            if(changeInfo.oldHolder != item) {
                return false;
            }

            changeInfo.oldHolder = null;
            oldItem = true;
        }

        ViewCompat.setAlpha(item.itemView, 1.0F);
        ViewCompat.setTranslationX(item.itemView, 0.0F);
        ViewCompat.setTranslationY(item.itemView, 0.0F);
        this.dispatchChangeFinished(item, oldItem);
        return true;
    }

    public void endAnimation(ViewHolder item) {
        View view = item.itemView;
        ViewCompat.animate(view).cancel();

        int i;
        for(i = this.mPendingMoves.size() - 1; i >= 0; --i) {
            MyItemAnimator.MoveInfo additions = (MyItemAnimator.MoveInfo)this.mPendingMoves.get(i);
            if(additions.holder == item) {
                ViewCompat.setTranslationY(view, 0.0F);
                ViewCompat.setTranslationX(view, 0.0F);
                this.dispatchMoveFinished(item);
                this.mPendingMoves.remove(i);
            }
        }

        this.endChangeAnimation(this.mPendingChanges, item);
        if(this.mPendingRemovals.remove(item)) {
            ViewCompat.setAlpha(view, 1.0F);
            this.dispatchRemoveFinished(item);
        }

        if(this.mPendingAdditions.remove(item)) {
            ViewCompat.setAlpha(view, 1.0F);
            this.dispatchAddFinished(item);
        }

        ArrayList var7;
        for(i = this.mChangesList.size() - 1; i >= 0; --i) {
            var7 = (ArrayList)this.mChangesList.get(i);
            this.endChangeAnimation(var7, item);
            if(var7.isEmpty()) {
                this.mChangesList.remove(i);
            }
        }

        for(i = this.mMovesList.size() - 1; i >= 0; --i) {
            var7 = (ArrayList)this.mMovesList.get(i);

            for(int j = var7.size() - 1; j >= 0; --j) {
                MyItemAnimator.MoveInfo moveInfo = (MyItemAnimator.MoveInfo)var7.get(j);
                if(moveInfo.holder == item) {
                    ViewCompat.setTranslationY(view, 0.0F);
                    ViewCompat.setTranslationX(view, 0.0F);
                    this.dispatchMoveFinished(item);
                    var7.remove(j);
                    if(var7.isEmpty()) {
                        this.mMovesList.remove(i);
                    }
                    break;
                }
            }
        }

        for(i = this.mAdditionsList.size() - 1; i >= 0; --i) {
            var7 = (ArrayList)this.mAdditionsList.get(i);
            if(var7.remove(item)) {
                ViewCompat.setAlpha(view, 1.0F);
                this.dispatchAddFinished(item);
                if(var7.isEmpty()) {
                    this.mAdditionsList.remove(i);
                }
            }
        }

        if(this.mRemoveAnimations.remove(item)) {
            ;
        }

        if(this.mAddAnimations.remove(item)) {
            ;
        }

        if(this.mChangeAnimations.remove(item)) {
            ;
        }

        if(this.mMoveAnimations.remove(item)) {
            ;
        }

        this.dispatchFinishedWhenDone();
    }

    private void resetAnimation(ViewHolder holder) {
        AnimatorCompatHelper.clearInterpolator(holder.itemView);
        this.endAnimation(holder);
    }

    public boolean isRunning() {
        return !this.mPendingAdditions.isEmpty() || !this.mPendingChanges.isEmpty() || !this.mPendingMoves.isEmpty() || !this.mPendingRemovals.isEmpty() || !this.mMoveAnimations.isEmpty() || !this.mRemoveAnimations.isEmpty() || !this.mAddAnimations.isEmpty() || !this.mChangeAnimations.isEmpty() || !this.mMovesList.isEmpty() || !this.mAdditionsList.isEmpty() || !this.mChangesList.isEmpty();
    }

    private void dispatchFinishedWhenDone() {
        if(!this.isRunning()) {
            this.dispatchAnimationsFinished();
        }

    }

    public void endAnimations() {
        int count = this.mPendingMoves.size();

        int listCount;
        View changes;
        for(listCount = count - 1; listCount >= 0; --listCount) {
            MyItemAnimator.MoveInfo i = (MyItemAnimator.MoveInfo)this.mPendingMoves.get(listCount);
            changes = i.holder.itemView;
            ViewCompat.setTranslationY(changes, 0.0F);
            ViewCompat.setTranslationX(changes, 0.0F);
            this.dispatchMoveFinished(i.holder);
            this.mPendingMoves.remove(listCount);
        }

        count = this.mPendingRemovals.size();

        ViewHolder var9;
        for(listCount = count - 1; listCount >= 0; --listCount) {
            var9 = (ViewHolder)this.mPendingRemovals.get(listCount);
            this.dispatchRemoveFinished(var9);
            this.mPendingRemovals.remove(listCount);
        }

        count = this.mPendingAdditions.size();

        for(listCount = count - 1; listCount >= 0; --listCount) {
            var9 = (ViewHolder)this.mPendingAdditions.get(listCount);
            changes = var9.itemView;
            ViewCompat.setAlpha(changes, 1.0F);
            this.dispatchAddFinished(var9);
            this.mPendingAdditions.remove(listCount);
        }

        count = this.mPendingChanges.size();

        for(listCount = count - 1; listCount >= 0; --listCount) {
            this.endChangeAnimationIfNecessary((MyItemAnimator.ChangeInfo)this.mPendingChanges.get(listCount));
        }

        this.mPendingChanges.clear();
        if(this.isRunning()) {
            listCount = this.mMovesList.size();

            int j;
            int var10;
            ArrayList var11;
            for(var10 = listCount - 1; var10 >= 0; --var10) {
                var11 = (ArrayList)this.mMovesList.get(var10);
                count = var11.size();

                for(j = count - 1; j >= 0; --j) {
                    MyItemAnimator.MoveInfo item = (MyItemAnimator.MoveInfo)var11.get(j);
                    ViewHolder view = item.holder;
                    View view1 = view.itemView;
                    ViewCompat.setTranslationY(view1, 0.0F);
                    ViewCompat.setTranslationX(view1, 0.0F);
                    this.dispatchMoveFinished(item.holder);
                    var11.remove(j);
                    if(var11.isEmpty()) {
                        this.mMovesList.remove(var11);
                    }
                }
            }

            listCount = this.mAdditionsList.size();

            for(var10 = listCount - 1; var10 >= 0; --var10) {
                var11 = (ArrayList)this.mAdditionsList.get(var10);
                count = var11.size();

                for(j = count - 1; j >= 0; --j) {
                    ViewHolder var12 = (ViewHolder)var11.get(j);
                    View var13 = var12.itemView;
                    ViewCompat.setAlpha(var13, 1.0F);
                    this.dispatchAddFinished(var12);
                    var11.remove(j);
                    if(var11.isEmpty()) {
                        this.mAdditionsList.remove(var11);
                    }
                }
            }

            listCount = this.mChangesList.size();

            for(var10 = listCount - 1; var10 >= 0; --var10) {
                var11 = (ArrayList)this.mChangesList.get(var10);
                count = var11.size();

                for(j = count - 1; j >= 0; --j) {
                    this.endChangeAnimationIfNecessary((MyItemAnimator.ChangeInfo)var11.get(j));
                    if(var11.isEmpty()) {
                        this.mChangesList.remove(var11);
                    }
                }
            }

            this.cancelAll(this.mRemoveAnimations);
            this.cancelAll(this.mMoveAnimations);
            this.cancelAll(this.mAddAnimations);
            this.cancelAll(this.mChangeAnimations);
            this.dispatchAnimationsFinished();
        }
    }

    void cancelAll(List<ViewHolder> viewHolders) {
        for(int i = viewHolders.size() - 1; i >= 0; --i) {
            ViewCompat.animate(((ViewHolder)viewHolders.get(i)).itemView).cancel();
        }

    }

    private static class VpaListenerAdapter implements ViewPropertyAnimatorListener {
        private VpaListenerAdapter() {
        }

        public void onAnimationStart(View view) {
        }

        public void onAnimationEnd(View view) {
        }

        public void onAnimationCancel(View view) {
        }
    }

    private static class ChangeInfo {
        public ViewHolder oldHolder;
        public ViewHolder newHolder;
        public int fromX;
        public int fromY;
        public int toX;
        public int toY;

        private ChangeInfo(ViewHolder oldHolder, ViewHolder newHolder) {
            this.oldHolder = oldHolder;
            this.newHolder = newHolder;
        }

        private ChangeInfo(ViewHolder oldHolder, ViewHolder newHolder, int fromX, int fromY, int toX, int toY) {
            this(oldHolder, newHolder);
            this.fromX = fromX;
            this.fromY = fromY;
            this.toX = toX;
            this.toY = toY;
        }

        public String toString() {
            return "ChangeInfo{oldHolder=" + this.oldHolder + ", newHolder=" + this.newHolder + ", fromX=" + this.fromX + ", fromY=" + this.fromY + ", toX=" + this.toX + ", toY=" + this.toY + '}';
        }
    }

    private static class MoveInfo {
        public ViewHolder holder;
        public int fromX;
        public int fromY;
        public int toX;
        public int toY;

        private MoveInfo(ViewHolder holder, int fromX, int fromY, int toX, int toY) {
            this.holder = holder;
            this.fromX = fromX;
            this.fromY = fromY;
            this.toX = toX;
            this.toY = toY;
        }
    }
}
*/
