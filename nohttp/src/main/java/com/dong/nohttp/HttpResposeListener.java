package com.dong.nohttp;

import android.content.Context;
import android.content.DialogInterface;

import com.yolanda.nohttp.rest.OnResponseListener;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.Response;

/**
 * Created by 川东 on 2016/11/30.
 */

public class HttpResposeListener<T> implements OnResponseListener<T> {
    private HttpListener<T> mListener;
    private WaitDialog mDialog;
    private boolean cancancle, isloading;//可以加载
    private Request<T> mRequest;
    private Context mContext;

    public HttpResposeListener(Context context, Request<T> request, HttpListener<T> listener, boolean cancancle, boolean isloading) {
        this.mListener = listener;
        this.mContext = context;
        this.mRequest = request;
        this.isloading = isloading;
        this.cancancle = cancancle;
        if (context != null) {
            mDialog = new WaitDialog(context);
            mDialog.setCancelable(cancancle);
            mDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    mDialog.cancel();
                }
            });
        }

    }

    @Override
    public void onStart(int what) {

        if (isloading && mDialog != null && !mDialog.isShowing()) {
            mDialog.show();
        }

    }

    @Override
    public void onSucceed(int what, Response<T> response) {
        if (mListener != null) {
            mListener.onSucceed(what, response);
        }

    }

    @Override
    public void onFailed(int what, Response<T> response) {
        if (mListener != null) {
            mListener.onFailed(what, response);
        }

    }

    @Override
    public void onFinish(int what) {
        if (mDialog != null && isloading && mDialog.isShowing()) {
            mDialog.cancel();
        }
    }
}
