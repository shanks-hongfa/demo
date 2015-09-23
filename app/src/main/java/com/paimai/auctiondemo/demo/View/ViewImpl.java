package com.paimai.auctiondemo.demo.View;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.paimai.auctiondemo.demo.Utils.LogUtil;

/**
 * Created by moxun on 15/9/9.
 */
public class ViewImpl extends View {

    String tag = "View";

    public ViewImpl(Context context) {
        super(context);
    }

    public ViewImpl(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ViewImpl(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        LogUtil.Log(0, tag, event.getAction(), "super");
        return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        LogUtil.Log(2, tag, event.getAction(), "false");
        consumeEvent(2, event.getAction());
        return true;
    }

    private void consumeEvent(int i,int ev) {
        LogUtil.Log(i, tag, ev);
    }
}
