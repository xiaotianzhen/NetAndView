package com.dong.nohttp.app;

import android.app.Application;

import com.yolanda.nohttp.NoHttp;

/**
 * Created by 川东 on 2016/11/29.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        NoHttp.initialize(getApplicationContext());
    }
}
