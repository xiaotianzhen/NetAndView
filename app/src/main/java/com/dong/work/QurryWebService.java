package com.dong.work;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.dong.work.utils.NumberParse;
import com.dong.work.utils.StreamTool;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class QurryWebService extends AppCompatActivity {
    private Button bt_find;
    private EditText et_number;

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            if (msg.what == 1) {
                String result = (String) msg.obj;
                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(), "查询出错", Toast.LENGTH_SHORT).show();
            }
            super.handleMessage(msg);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qurry_web_service);

        bt_find = (Button) findViewById(R.id.bt_find);
        et_number = (EditText) findViewById(R.id.et_number);

        bt_find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            String number = et_number.getText().toString().trim();
                            String path = "http://ws.webxml.com.cn/WebServices/MobileCodeWS.asmx";
                            URL url = new URL(path);
                            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                            conn.setRequestMethod("POST");
                            conn.setConnectTimeout(5000);
                            InputStream is = getAssets().open("data.xml");
                            String data = StreamTool.streamToString(is);
                            String content = data.replace("$mobile", number);
                            Log.i("520it", "***********" + content);
                            conn.setRequestProperty("Content-Type", "application/soap+xml; charset=utf-8");
                            conn.setRequestProperty("Content-Length", content.length() + "");
                            conn.setDoOutput(true);
                            OutputStream os = conn.getOutputStream();
                            os.write(content.getBytes());
                            os.close();
                            is.close();
                            if (200 == conn.getResponseCode()) {
                                Log.i("520it", "***********" + conn.getResponseCode());
                                InputStream in = conn.getInputStream();
                                String result = NumberParse.parseNumber(in);
                                Log.i("520it", "***********" + result);
                                Message msg = Message.obtain();
                                msg.what = 1;
                                msg.obj = result;
                                handler.sendMessage(msg);
                            }


                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        });

    }
}
