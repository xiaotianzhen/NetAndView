package com.dong.nohttp;

import android.content.Context;

import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.able.Cancelable;
import com.yolanda.nohttp.rest.OnResponseListener;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.RequestQueue;

/**
 * Created by 川东 on 2016/11/30.
 */

public class CallServer {
    //构造方法私有化
    private  RequestQueue mQueue;
    private CallServer() {
        mQueue = NoHttp.newRequestQueue();
    }


    private static CallServer callServer;

    public synchronized static CallServer getInstance() {
        if (callServer == null) {
            callServer=new CallServer();
        }

        return callServer;
    }
    public <T> void add(int what, Request<T> request, Context context, HttpListener<T> listener, boolean cancancle, boolean isloading){

        mQueue.add(what,request,new HttpResposeListener<T>(context, request,listener,cancancle,isloading ));

    }
}
