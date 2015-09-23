package com.paimai.auctiondemo.demo.View;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;

import com.paimai.auctiondemo.demo.Utils.LogUtil;

/**
 * Created by moxun on 15/9/9.
 */
public class ViewGroupImplA extends LinearLayout {

    String tag = "LinearLayout Outer";

    public ViewGroupImplA(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public ViewGroupImplA(Context context) {
        super(context);
    }

    public ViewGroupImplA(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        LogUtil.Log(0, tag, ev.getAction(), "super");
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        LogUtil.Log(1, tag, ev.getAction(), "false");
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        LogUtil.Log(2, tag, event.getAction(), "true");
        consumeEvent(2, event.getAction());
        return true;
    }

    private void consumeEvent(int i,int ev) {
        LogUtil.Log(i, tag, ev);
    }
}
