package com.dong.work;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.dong.work.server.LoginServer;

public class LoginDemo extends AppCompatActivity {
    private EditText name, password,et_path;
    private String username;
    private String pwd;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            if (msg.what == 1) {
                String result = (String) msg.obj;
                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
            }
            super.handleMessage(msg);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_demo);
        name = (EditText) findViewById(R.id.et_name);
        password = (EditText) findViewById(R.id.et_password);
        et_path= (EditText) findViewById(R.id.et_path);

    }

    private void isEmpty() {
        username = name.getText().toString().trim();
        pwd = password.getText().toString().trim();
        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(pwd)) {
            name.setError("用户名或密码不能为空");
            name.requestFocus();
        }
    }

    public void getLogin(View view) {
        isEmpty();
        final String path = "http://192.168.1.103:8080/web/LoginServlet";
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String result = LoginServer.getSendData(path, username, pwd);
                    Message msg = Message.obtain();
                    msg.what = 1;
                    msg.obj = result;
                    handler.sendMessage(msg);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }).start();


    }

    public void postLogin(View view) {
        isEmpty();
        final String path = "http://192.168.1.103:8080/web/LoginServlet";
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String result = LoginServer.postSendData(path, username, pwd);
                    Message msg = Message.obtain();
                    msg.what = 1;
                    msg.obj = result;
                    handler.sendMessage(msg);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    public void postLoginFile(View view){
        isEmpty();
        final String filePath=et_path.getText().toString().trim();
        if(filePath.equals("")){
            et_path.setError("文件路径不能为空");
            et_path.requestFocus();
        }
        final String path = "http://192.168.1.103:8080/web/LoginServlet";
         new Thread(new Runnable() {
             @Override
             public void run() {
                 try {
                    String result= LoginServer.postSendDataAddFile(path, username, pwd, filePath);
                     Message msg = Message.obtain();
                     msg.what = 1;
                     msg.obj = result;
                     handler.sendMessage(msg);
                 }catch (Exception e){
                     e.printStackTrace();
                 }
             }
         }).start();

    }
}
