package com.shanks.auctiondemo.demo.View;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;

import com.shanks.auctiondemo.demo.Utils.LogUtil;

/**
 * Created by moxun on 15/9/9.
 */
public class ViewGroupImplB extends LinearLayout {

    String tag = "LinearLayout Inner";

    public ViewGroupImplB(Context context) {
        super(context);
    }

    public ViewGroupImplB(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ViewGroupImplB(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        LogUtil.Log(0, tag, ev.getAction(), "super");
        return super.dispatchTouchEvent(ev);
    }

    int count=0;
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
//        count++;
//        if(count==10){
//            LogUtil.Log(1, tag, ev.getAction(), "true");
//            return true;
//        }
        LogUtil.Log(1, tag, ev.getAction(), "true");
        return true;
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
