package com.dong.okhttpdemo;

import android.os.Handler;
import android.os.Looper;
import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import java.io.IOException;
import java.util.Map;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by 川东 on 2016/12/4.
 */

public class OkhttpHelper {

    private static OkhttpHelper mOkhttpHelper;
    private OkHttpClient mClient;
    private Gson mGson;
    private Handler mHandler;

    private OkhttpHelper() {
        mClient = new OkHttpClient();
        mGson = new Gson();
        mHandler = new Handler(Looper.getMainLooper());

    }

    public synchronized static OkhttpHelper getIntance() {
        if (mOkhttpHelper == null) {
            mOkhttpHelper = new OkhttpHelper();
        }
        return mOkhttpHelper;
    }

    public void get(String url, BaseCallback callback) {
        Request request = buildRequest(url, null, HttpMethodType.Get);
        doRequset(request, callback);
    }

    public void post(String url, Map<String, String> params, BaseCallback callback) {
        Request request = buildRequest(url, params, HttpMethodType.Post);
        doRequset(request, callback);
    }


    public void doRequset(final Request request, final BaseCallback callback) {
        callback.onRequsetBefore(request);
        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callback.onFailure(call, e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                callback.onResponse(response);
                if (response.isSuccessful()) {
                    String result = response.body().string();
                    if (callback.mType == String.class) {

                        successCallback(callback, response, result);
                    } else {
                        try {
                            Object Object = mGson.fromJson(result, callback.mType);
                            successCallback(callback, response, Object);
                        } catch (JsonIOException e) {
                          errorCallback(callback,response,response.code(),e);
                        }
                    }
                } else {
                    errorCallback(callback,response,response.code(),null);
                }
            }
        });

    }

    private Request buildRequest(String url, Map<String, String> params, HttpMethodType type) {
        Request.Builder builder = new Request.Builder();
        if (type == HttpMethodType.Get) {
            builder.url(url).build();
        } else {
            RequestBody body = buidBody(params);
            builder.url(url).post(body).build();
        }


        return builder.build();
    }

    private RequestBody buidBody(Map<String, String> params) {
        FormBody.Builder bulid = new FormBody.Builder();

        for (Map.Entry<String, String> entry : params.entrySet()) {
            bulid.add(entry.getKey(), entry.getValue());
        }

        return bulid.build();
    }

    enum HttpMethodType {

        Get,
        Post

    }

    public void successCallback(final BaseCallback callback, final Response response, final Object object) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                callback.onSuccess(response, object);
            }
        });
    }

    public void errorCallback(final BaseCallback callback, final Response response, final int code, final Exception e) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
               callback.onError(response,code,e);
            }
        });
    }
}
