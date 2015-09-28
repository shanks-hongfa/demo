package com.shanks.auctiondemo.demo.View.RefreshableLayout;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.LinearLayout;

import com.shanks.auctiondemo.demo.Utils.dp2px;

/**
 * Created by moxun on 15/9/9.
 */
public class RefreshableLayout extends LinearLayout {
    private IRefreshListener refreshListener;
    private IRefreshTriggerListener refreshTriggerListener;
    private boolean isAbleToRefresh,isAbleToLoadMore;
    private View mTarget,header,footer;
    private int displayHeight,displayWidth;
    private float damp = 1f;
    private int currentOffset = 0;
    private boolean isInDrag;
    private int refreshableOffset;

    public RefreshableLayout(Context context) {
        this(context,null);
    }

    public RefreshableLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RefreshableLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOrientation(VERTICAL);
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        displayHeight = display.getHeight();
        displayWidth = display.getWidth();
    }

    public void setHeaderView(View headerView) {
        header = headerView;
        header.measure(MeasureSpec.makeMeasureSpec(displayWidth, MeasureSpec.EXACTLY), MeasureSpec.makeMeasureSpec(displayHeight, MeasureSpec.AT_MOST));
        refreshableOffset = header.getMeasuredHeight();
        addView(header, 0);
    }

    public void setFooterView(View footerView) {
        footer = footerView;
    }

    public void setRefreshTriggerListener(IRefreshTriggerListener listener) {
        this.refreshTriggerListener = listener;
    }

    public void setRefreshListener(IRefreshListener listener) {
        this.refreshListener = listener;
    }

    private void ensureTarget() {
        if (mTarget == null) {
            for (int i=0;i<getChildCount();i++) {
                View child = getChildAt(i);
                if (child != header && child != footer) {
                    mTarget = child;
                    return;
                }
            }
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (mTarget == null) {
            ensureTarget();
            if (mTarget == null) {
                return;
            }
        }
        if (header != null && !isInDrag && !isAbleToRefresh) {
            scrollTo(0, refreshableOffset);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (mTarget == null) {
            ensureTarget();
        } else {
            mTarget.measure(widthMeasureSpec, heightMeasureSpec);
        }
        setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec),mTarget.getMeasuredHeight() + refreshableOffset);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_MOVE) {
            if (canRefresh() && ev.getY() < downPoint || !isValidMove(Math.abs((int)ev.getY() - downPoint))) {
                Log.e("intercept cancel","cancel");
                return false;
            } else if (canRefresh()) {
                Log.e("intercept event", "intercept touch event");
                return true;
            }
        } else if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            downPoint = (int)ev.getY();
            lastPoint = downPoint;
            return false;
        }
        return false;
    }

    private boolean isValidMove(int distance) {
        boolean valid = distance > ViewConfiguration.get(getContext()).getScaledTouchSlop();
        Log.e("is valid move",valid +"");
        return valid;
    }

    int downPoint;
    float lastPoint;
    int dragLength;
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_MOVE:
                if ((int)event.getY() - downPoint > 0) {
                    int offset = (int)((lastPoint - event.getY()) * computeDamp(event.getY()));
                    scrollBy(0, offset);
                    dragLength += offset;
                    refreshTriggerListener.onUpdateDistance((int)event.getY() - downPoint);
                    if (Math.abs(dragLength) >= refreshableOffset) {
                        refreshTriggerListener.onRefreshable();
                        isAbleToRefresh = true;
                    }
                    lastPoint = event.getY();
                }
                isInDrag = true;
                break;
            case MotionEvent.ACTION_UP:
                if (isAbleToRefresh) {
                    doRefresh();
                    refreshTriggerListener.onRefreshing();
                } else {
                    scrollTo(0,header.getMeasuredHeight());
                }
                isInDrag = false;
                dragLength = 0;
                break;
        }
        return true;
    }

    private void doRefresh() {
        scrollTo(0, 0);
        refreshTriggerListener.onRefreshing();
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                refreshListener.onRefresh();
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPreExecute();
                onRefreshComplete();
            }
        }.execute();
    }

    private void onRefreshComplete() {
        scrollTo(0,header.getMeasuredHeight());
        isAbleToRefresh = false;
        refreshListener.onFinish();
        refreshTriggerListener.onRefreshComplete();
    }

    private float computeDamp(float y) {
        return damp - ((y - downPoint) / dp2px.dip2px(400) > 0.9 ? 0.9f : (y - downPoint) / dp2px.dip2px(400));
    }

    private boolean canRefresh() {
        if (Build.VERSION.SDK_INT < 14) {
            if (mTarget instanceof AbsListView) {
                final AbsListView absListView = (AbsListView) mTarget;
                return !(absListView.getChildCount() > 0 && (absListView
                        .getFirstVisiblePosition() > 0 || absListView
                        .getChildAt(0).getTop() < absListView.getPaddingTop()));
            } else {
                return !(mTarget.getScrollY() > 0);
            }
        } else {
            return !ViewCompat.canScrollVertically(mTarget, -1);
        }
    }
}
