package com.dong.nohttp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.OnResponseListener;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.RequestQueue;
import com.yolanda.nohttp.rest.Response;

public class MainActivity extends AppCompatActivity implements HttpListener<String> {
    private TextView tv_show;
    private String postUrl = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv_show = (TextView) findViewById(R.id.tv_show);

    }

    public void btn_get(View view) {
        String path = "http://baidu.com";
        RequestQueue queue = NoHttp.newRequestQueue();

        final Request<String> request = NoHttp.createStringRequest(path, RequestMethod.GET);

        queue.add(0, request, new OnResponseListener<String>() {


            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                tv_show.setText(response.get());
            }

            @Override
            public void onFailed(int what, Response<String> response) {

            }

            @Override
            public void onFinish(int what) {

            }
        });

    }

    public void btn_post(View view) {
        RequestQueue queue = NoHttp.newRequestQueue();

        Request<String> requset = NoHttp.createStringRequest(postUrl, RequestMethod.POST);

        //常规的添加方式  要看你请求的地址需要什么样的格式，请求头，json什么的
        requset.add("username", "zhangsan");
        requset.add("password", "123456");
        //不常规的请求方式
        requset.addHeader("Content_Type", "application/json");
        requset.setDefineRequestBodyForJson("{\"username\":\"zhangsan\",\"password\":\"123456\"");

        queue.add(1, requset, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<String> response) {

            }

            @Override
            public void onFailed(int what, Response<String> response) {

            }

            @Override
            public void onFinish(int what) {

            }
        });
    }

    public void btn_fengzhuang(View view) {
        String path = "http://baidu.com";
        Request<String> request = NoHttp.createStringRequest(path, RequestMethod.GET);
        CallServer.getInstance().add(0, request,MainActivity.this, this, true, true);
    }

    @Override
    public void onSucceed(int what, Response<String> response) {

        switch (what) {
            case 0:
                tv_show.setText(response.get());
                break;
        }
    }

    @Override
    public void onFailed(int what, Response<String> response) {
        tv_show.setText(response.get());
    }
}
