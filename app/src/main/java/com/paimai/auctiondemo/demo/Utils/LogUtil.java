package com.paimai.auctiondemo.demo.Utils;

import android.util.Log;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by moxun on 15/9/9.
 */
public class LogUtil {
    private static String names[] = {"dispatchTouchEvent()","onInterceptTouchEvent()","onTouchEvent()"};

    public static void e(String tag,String desc) {
        Log.e(tag,desc);
    }

    public static void w(String tag,String desc) {
        Log.w(tag, desc);
    }

    public static void d(String tag,String desc) {
        Log.d(tag, desc);
    }

    public static void i(String tag,String desc) {
        Log.i(tag, desc);
    }

    public static void v(String tag,String desc) {
        Log.v(tag, desc);
    }

    public static void Log(int name,String tag,int event,String result) {
        String Name = names[name];
        Log.e(tag, "In " + tag + ", event is " + Event.event.get(event) + ", " + Name + " return " + result);
    }

    public static void Log(int name,String tag,int event) {
        Log.e(tag,"Event "+Event.event.get(event) + " has been consumed in method " + names[name]);
    }

    public static class Event {
        public static Map<Integer,String> event = new HashMap<>();
        static {
            event.put(0,"ACTION_DOWN");
            event.put(1,"ACTION_UP");
            event.put(2,"ACTION_MOVE");
            event.put(3,"ACTION_CANCEL");
        }
    }

}
