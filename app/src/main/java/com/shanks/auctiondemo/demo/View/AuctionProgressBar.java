package com.shanks.auctiondemo.demo.View;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.view.View;
import android.view.WindowManager;

import com.paimai.auctiondemo.R;
import com.shanks.auctiondemo.demo.Utils.dp2px;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by moxun on 15/9/9.
 */
public class AuctionProgressBar extends View implements Runnable {

    private float progress; //指针在进度条上的百分比

    private int lotsCount = 100;
    private int currentLots;
    private Map<Integer, Rect> focusSetsByNum = new HashMap<>();

    private int progressBarWidth;
    private int displayWidth;
    private Paint bgPaint;
    private Paint rectPaint;
    private Bitmap arrow, arrow1;
    private boolean isArrow1Show;
    private int delay;

    private Handler handler = new Handler(Looper.getMainLooper());

    public AuctionProgressBar(Context context) {
        super(context);
        init(context);
    }

    public AuctionProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public AuctionProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        displayWidth = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getWidth();
        arrow = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.progress_arrow), $px(4), $px(7), true);
        arrow1 = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.progress_arrow1), $px(4), $px(7), true);

        bgPaint = new Paint();
        bgPaint.setColor(Color.argb(255, 255, 204, 102));
        bgPaint.setStyle(Paint.Style.FILL);

        rectPaint = new Paint();
        rectPaint.setColor(Color.argb(255, 128, 0, 21));
        rectPaint.setStyle(Paint.Style.FILL);
    }

    public void clearAllFocus() {
        focusSetsByNum.clear();
        invalidate();
    }

    public void setLotsCount(int lotsCount) {
        this.lotsCount = lotsCount - 1;
    }

    public void addFocusByNum(int num) {
        //组件内序号从0开始
        if (lotsCount >= 0) {
            float rate = (float) num / lotsCount;
            int offsetX = (int) ((progressBarWidth - $px(4)) * rate);
            Rect rect = new Rect(offsetX + getPaddingLeft(), getPaddingTop(), offsetX + getPaddingLeft() + $px(4), getPaddingTop() + $px(4));
            focusSetsByNum.put(num, rect);
            invalidate();
        } else {
            throw new IllegalArgumentException("Please call setLotsCount() before.");
        }
    }

    public void addFocusBySet(List<Integer> focusSet) {
        for (int i : focusSet) {
            addFocusByNum(i);
        }
    }

    public void removeFocusByNum(int num) {
        focusSetsByNum.remove(num);
        invalidate();
    }

    public void updateFocusAndProgress(List<Integer> list) {
        if (list != null && !list.isEmpty()) {
            clearAllFocus();
            setCurrentLots(list.get(0));
            if (list.size() > 1) {
                addFocusBySet(list.subList(1, list.size()));
            }
        } else {
            setCurrentLots(0);
        }
    }

    public PointF getCoordByNum(int num) {
        float rate = (float) num / lotsCount;
        float x = progressBarWidth * rate + $px(2);
        float y = $px(1);

        return new PointF(x, y);
    }

    public void setCurrentLots(int currentLots) {
        if (currentLots >= 0 && currentLots <= lotsCount && currentLots != this.currentLots) {
            this.currentLots = currentLots; //组件内维护的currentLots从0开始
            handler.post(this);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(measureWidth(widthMeasureSpec), measureHeight(heightMeasureSpec));
    }

    private int measureHeight(int measureSpec) {
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        int result = arrow.getHeight();
        switch (specMode) {
            case MeasureSpec.AT_MOST:
                result = arrow.getHeight() + getPaddingTop() + getPaddingBottom();
                break;
            case MeasureSpec.EXACTLY:
                result = specSize;
                break;
        }
        return result;
    }

    private int measureWidth(int measureSpec) {
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        int result = displayWidth;
        switch (specMode) {
            case MeasureSpec.AT_MOST:
                result = displayWidth;
                break;
            case MeasureSpec.EXACTLY:
                result = specSize;
                break;
        }
        progressBarWidth = result - getPaddingLeft() - getPaddingRight();
        return result;
    }

    @Override
    protected void onDraw(Canvas canvas) {

        Rect background = new Rect(getPaddingLeft(), getPaddingTop(), progressBarWidth + getPaddingLeft(), getPaddingTop() + $px(4));
        canvas.drawRect(background, bgPaint);

        Iterator iterator2 = focusSetsByNum.entrySet().iterator();
        while (iterator2.hasNext()) {
            Rect rect = (Rect) ((Map.Entry) iterator2.next()).getValue();
            canvas.drawRect(rect, rectPaint);
        }

        if (!isArrow1Show) {
            canvas.drawBitmap(arrow1, (progressBarWidth - $px(4)) * progress + getPaddingLeft()
                    , getPaddingTop(), null);
        } else {
            canvas.drawBitmap(arrow, (progressBarWidth - $px(4)) * progress + getPaddingLeft()
                    , getPaddingTop(), null);
        }

        delay++;
        if (delay == 20) {
            delay = 0;
            isArrow1Show = !isArrow1Show;
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        handler.post(this);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        handler.removeCallbacksAndMessages(null);
    }

    @Override
    public void run() {
        float nextProgress = (float) currentLots / lotsCount;
        float temp = (nextProgress - progress) / 10; //滑动减速
        if (Math.abs(progress - (float) currentLots / lotsCount) > 0.001f) {
            progress = progress + temp;
        } else {
            progress = nextProgress;
        }
        invalidate();
        handler.postDelayed(this, 50);
    }

    private int $px(float dp) {
        if (isInEditMode()) {
            return (int) (dp * 2);
        } else {
            return dp2px.dip2px(dp);
        }
    }
}
