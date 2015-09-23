package com.paimai.auctiondemo.demo.View;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;

import com.paimai.auctiondemo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by moxun on 15/9/7.
 */
public class FlowLayout extends ViewGroup {

    private int dirtyMark;
    private boolean isDirty;
    private List<List<Integer>> dirtySets = new ArrayList<>();
    private final int DEFAULT_ANIM_DURATION = 300;

    public FlowLayout(Context context) {
        super(context);
        setLayoutAnimation(
                new LayoutAnimationController(AnimationUtils.loadAnimation(
                        getContext(), R.anim.list_animation), 0.3f));
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        setLayoutAnimation(
                new LayoutAnimationController(AnimationUtils.loadAnimation(
                        getContext(), R.anim.list_animation), 0.3f));
    }

    public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setLayoutAnimation(
                new LayoutAnimationController(AnimationUtils.loadAnimation(
                        getContext(), R.anim.list_animation), 0.3f));
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            if (child.getVisibility() != GONE) {
                Location location = (Location) child.getTag();
                child.layout(location.left, location.top, location.right, location.bottom);
            }
        }
        if (isDirty) {
            startAnim();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int contentWidth = MeasureSpec.getSize(widthMeasureSpec);
        int contentHeight = MeasureSpec.getSize(heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        int topOffset = getPaddingTop();
        int leftOffset = getPaddingLeft();

        int selfWidth = 0, selfHeight = 0;
        int currentLineWidth = 0, currentLineHeight = 0;

        int childCount = getChildCount();

        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            if (child.getVisibility() == GONE)
                continue;

            measureChild(child, widthMeasureSpec, heightMeasureSpec);
            MarginLayoutParams layoutParams = (MarginLayoutParams) child.getLayoutParams();

            int childWidth = Math.max(child.getMeasuredWidth(), getSuggestedMinimumWidth()) + layoutParams.leftMargin + layoutParams.rightMargin;
            int childHeight = Math.max(child.getMeasuredHeight(), getSuggestedMinimumHeight()) + layoutParams.topMargin + layoutParams.bottomMargin;

            if (currentLineWidth + childWidth > contentWidth  - getPaddingLeft() - getPaddingRight()) {
                //需要另起一行
                currentLineWidth = Math.max(currentLineWidth,childWidth);
                selfWidth = Math.max(selfWidth, currentLineWidth);
                currentLineWidth = childWidth;
                selfHeight += currentLineHeight;
                currentLineHeight = childHeight;
                Log.d("new line","child at" + i +", max line width " + selfWidth +", content width" + contentWidth);

                child.setTag(new Location(child, leftOffset, selfHeight + topOffset, childWidth + leftOffset, selfHeight + child.getMeasuredHeight() + topOffset));
            } else {
                //不需要换行
                child.setTag(new Location(child, currentLineWidth + leftOffset, selfHeight + topOffset, currentLineWidth + child.getMeasuredWidth() + topOffset, selfHeight + child.getMeasuredHeight() + topOffset));
                currentLineWidth += childWidth;
                currentLineHeight = Math.max(currentLineHeight, childHeight);
                Log.d("update line width","child at" + i +", line width " + currentLineWidth);
            }

            if (i == childCount - 1) {
                selfWidth = Math.max(currentLineWidth, selfWidth) + getPaddingRight() + getPaddingLeft();
                selfHeight += currentLineHeight + getPaddingTop() + getPaddingBottom();
                Log.d("finally","line width " + selfWidth);
            }

        }

        setMeasuredDimension(widthMode == MeasureSpec.EXACTLY ? contentWidth : selfWidth,
                heightMode == MeasureSpec.EXACTLY ? contentHeight : selfHeight);

    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }

    @Override
    public void addView(View child) {
        super.addView(child);
        AlphaAnimation alphaAnimation = new AlphaAnimation(0, 1);
        TranslateAnimation translateAnimation = new TranslateAnimation(30, 0, 30, 0);
        AnimationSet animationSet = new AnimationSet(true);
        animationSet.addAnimation(alphaAnimation);
        animationSet.addAnimation(translateAnimation);
        animationSet.setDuration(DEFAULT_ANIM_DURATION);
        child.startAnimation(animationSet);
    }

    @Override
    public void addView(View child, int index) {
        if (index == -1) {
            super.addView(child,index);
            return;
        }
        dirtyMark = index + 1;
        isDirty = true;
        markDirty(index - 1);
        super.addView(child, index);
        AlphaAnimation alphaAnimation = new AlphaAnimation(0, 1);
        ScaleAnimation scaleAnimation = new ScaleAnimation(0,1,0,1, Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        AnimationSet animationSet = new AnimationSet(true);
        animationSet.setDuration(DEFAULT_ANIM_DURATION);
        animationSet.addAnimation(alphaAnimation);
        animationSet.addAnimation(scaleAnimation);
        child.startAnimation(animationSet);
    }

    @Override
    public void removeViewAt(int index) {
        dirtyMark = index;
        isDirty = true;
        AlphaAnimation alphaAnimation = new AlphaAnimation(1, 0);
        ScaleAnimation scaleAnimation = new ScaleAnimation(1,0,1,0, Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        AnimationSet animationSet = new AnimationSet(true);
        animationSet.addAnimation(alphaAnimation);
        animationSet.addAnimation(scaleAnimation);
        animationSet.setDuration(DEFAULT_ANIM_DURATION);
        getChildAt(index).startAnimation(animationSet);
        markDirty(index);
        super.removeViewAt(index);
    }

    private void markDirty(int index) {
        dirtySets.clear();
        for (int i = index + 1; i < getChildCount(); i++) {
            ArrayList<Integer> point = new ArrayList<>();
            point.add(getChildAt(i).getLeft());
            point.add(getChildAt(i).getTop());
            dirtySets.add(point);
        }
        Log.d("dirty set", dirtySets.toString());
    }

    private void startAnim() {
        for (int i = dirtyMark; i < getChildCount(); i++) {
            int fromXDelta = dirtySets.get(i - dirtyMark).get(0) - getChildAt(i).getLeft();
            int fromYDelta = dirtySets.get(i - dirtyMark).get(1) - getChildAt(i).getTop();
            if (fromXDelta != 0 || fromYDelta != 0) {
                TranslateAnimation translateAnimation = new TranslateAnimation(fromXDelta, 0, fromYDelta, 0);
                translateAnimation.setDuration(DEFAULT_ANIM_DURATION);
                Log.d("child " + i, "from:" + dirtySets.get(i - dirtyMark).get(0) + "," + dirtySets.get(i - dirtyMark).get(1) +
                        " translate to:" + getChildAt(i).getLeft() + "," + getChildAt(i).getTop());
                getChildAt(i).startAnimation(translateAnimation);
            }
        }
        isDirty = false;
    }

    @Override
    public void removeView(View view) {
        //super.removeView()内部实现逻辑和super.removeViewAt()一致
        removeViewAt(indexOfChild(view));
    }

    class Location {
        public int left, top, right, bottom;

        public Location(View view, int left, int top, int right, int bottom) {
            MarginLayoutParams layoutParams = (MarginLayoutParams) view.getLayoutParams();
            this.left = left + layoutParams.leftMargin;
            this.top = top + layoutParams.topMargin;
            this.right = right + layoutParams.leftMargin;
            this.bottom = bottom + layoutParams.topMargin;
        }
    }
}
