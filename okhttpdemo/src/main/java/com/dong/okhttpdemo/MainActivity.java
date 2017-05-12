package com.dong.okhttpdemo;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    private TextView tv_show;
    private String result = "";
    private OkhttpHelper mHelper = OkhttpHelper.getIntance();
    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            result = (String) msg.obj;
            tv_show.setText(result);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv_show = (TextView) findViewById(R.id.tv_show);
    }

    public void btn_get(View view) {
        String url = "http://www.baidu.com";
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).method("GET", null).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    result = response.body().string();
                    Message msg = Message.obtain();
                    msg.obj = result;
                    mHandler.sendMessage(msg);
                }
            }
        });
    }

    public void btn_fengzhuang(View view) {
        String url = "http://www.baidu.com";
        mHelper.get(url, new WaitCallback<String>(MainActivity.this,true,true) {

            @Override
            public void onSuccess(Response response, String s) {
                           tv_show.setText(s);
            }

            @Override
            public void onError(Response response, int Code, Exception e) {

            }
        });
    }


}
