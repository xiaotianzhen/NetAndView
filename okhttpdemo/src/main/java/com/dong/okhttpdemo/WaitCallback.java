package com.dong.okhttpdemo;

import android.content.Context;
import android.content.DialogInterface;
import java.io.IOException;
import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;


/**
 * Created by 川东 on 2016/12/5.
 */

public abstract class WaitCallback<T> extends BaseCallback<T> {
    private Context mContext;
    private WaitDialog mDialog;
    private boolean canLoading;

    public WaitCallback(Context context, boolean canCancle, boolean canLoading){
        this.mContext=context;
        this.canLoading=canLoading;
        if (context!=null){
            mDialog=new WaitDialog(mContext);
            mDialog.setCancelable(canCancle);
            mDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    mDialog.cancel();
                }
            });
        }
    }
    @Override
    public void onRequsetBefore(Request request) {
        if(mDialog!=null&&canLoading&&!mDialog.isShowing()){
            mDialog.show();
        }
    }

    @Override
    public void onFailure(Call call, IOException e) {
           if (mDialog!=null&&canLoading&&mDialog.isShowing()){
               mDialog.cancel();
           }
    }

    @Override
    public void onResponse(Response response) {
        if (mDialog!=null&&canLoading&&mDialog.isShowing()){
            mDialog.cancel();
        }
    }
}
