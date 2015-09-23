package com.paimai.auctiondemo;

import android.app.Application;

/**
 * Created by shanksYao on 9/15/15.
 */
public class ShanksApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ////
        RouterInit.init();
    }
}
