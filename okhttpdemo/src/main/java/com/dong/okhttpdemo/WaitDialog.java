package com.dong.okhttpdemo;

import android.app.ProgressDialog;
import android.content.Context;

/**
 * Created by 川东 on 2016/11/30.
 */

public class WaitDialog extends ProgressDialog {

    public WaitDialog(Context context) {
        super(context);

        //不能操作界面其他地方，等待dialog结束
        setCanceledOnTouchOutside(false);
        setProgressStyle(ProgressDialog.STYLE_SPINNER);
        setMessage("正在加载中，请稍后。。。");
    }

    public WaitDialog(Context context, int theme) {
        super(context, theme);
    }
}
