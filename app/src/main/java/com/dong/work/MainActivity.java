package com.dong.work;



import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText url_et;
    private ProgressBar progressBar;
    private Button load_btn;
    private TextView pb_show;
    boolean stopFlag=false;
    boolean flag=true;
    int total=0;
    private void initView() {
        url_et = (EditText) findViewById(R.id.et_url);
        progressBar = (ProgressBar) findViewById(R.id.progressbar);
        load_btn = (Button) findViewById(R.id.load_btn);
        pb_show = (TextView) findViewById(R.id.pb_show);
    }
     Handler handler=new Handler(){
         @Override
         public void handleMessage(Message msg) {
             Log.i("520it", "保存的进度" + total);
             progressBar.setProgress(total);
             int max = progressBar.getMax();
             if (total >= (max - 1)) {
                 total = max;
                 flag = false;
             }
             int result = total * 100 / max;
             pb_show.setText("当前进度 :" + result + "%");


             super.handleMessage(msg);
         }
     };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        final String path = "http://192.168.1.105:8080/youdao.exe";
        url_et.setText(path);
        load_btn.setOnClickListener(this);



    }

    public String getFileName(String path) {
        int startIndex = path.lastIndexOf("/") + 1;
        String fileName = path.subSequence(startIndex, path.length()).toString();
        return fileName;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.load_btn:

                if ("开始下载".equals(load_btn.getText().toString())) {
                    load_btn.setText("暂停");
                    stopFlag=false;
                } else {
                    load_btn.setText("开始下载");
                    stopFlag=true;
                }
                new Thread() {

                    @Override
                    public void run() {
                        super.run();


                        try {
                            final String path = "http://192.168.1.105:8080/youdao.exe";
                            URL url = new URL(path);
                            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                            conn.setRequestMethod("GET");
                            conn.setConnectTimeout(5000);
                            int code = conn.getResponseCode();
                            Log.i("520it", "********************" + code);
                            if (code == 200) {
                                int len = conn.getContentLength();
                                Log.i("520it", "***********" + getFileName(path));
                                if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                                    File rootPath = Environment.getExternalStorageDirectory();
                                    Log.i("520it", "********************" + rootPath);
                                    RandomAccessFile file = new RandomAccessFile(
                                            "/mnt/sdcard/" + getFileName(path), "rwd");
                                    file.setLength(len);
                                    progressBar.setMax(len);
                                    Log.i("520it", "********************" + len);
                                }
                                int threadNum = 3;
                                int block = len / 3;

                                for (int i = 0; i < 3; i++) {
                                    int startPosition = i * block;
                                    int endPosition = (i + 1) * block;
                                    if (i == (threadNum - 1)) {
                                        endPosition = len;
                                    }
                                    loadThread thread = new loadThread(i, startPosition, endPosition, path);
                                    thread.start();
                                }


                            } else {
                                Toast.makeText(MainActivity.this, "网路连接出错", Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {

                            e.printStackTrace();
                        }

                        while (flag) {
                            try {
                                sleep(1000);
                                // 如果total > = 文件长度
                                Message msg = new Message();
                                handler.sendMessage(msg);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                        }
                    }
                }.start();



                break;
        }
    }

    class loadThread extends Thread {
        private int threadId;
        private int startPosition;
        private int endPosition;
        private String path;

        public loadThread(int threadId, int startPosition, int endPosition, String path) {
            this.threadId = threadId;
            this.startPosition = startPosition;
            this.endPosition = endPosition;
            this.path = path;
        }

        @Override
        public void run() {
            try {
                File positionFile = new File("/mnt/sdcard/"+threadId+"positionFile.txt");
                File pbFile=new File("/mnt/sdcard/"+threadId+"File.txt");
                URL url = new URL(path);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setConnectTimeout(5000);
                conn.setRequestProperty("Rang", "byte=" + startPosition + "-" + endPosition);


                InputStream is = conn.getInputStream();
                File rootPath = Environment.getExternalStorageDirectory();
                RandomAccessFile file = new RandomAccessFile("/mnt/sdcard/" + getFileName(path), "rwd");
                if (positionFile.exists()) {
                    FileInputStream fi = new FileInputStream(positionFile);
                    ByteArrayOutputStream bo = new ByteArrayOutputStream();
                    byte[] buffer = new byte[1024];
                    int n = 0;
                    while ((n = fi.read(buffer)) != -1) {
                        bo.write(buffer, 0, n);
                        bo.flush();
                    }

                    byte[] result = bo.toByteArray();
                    String str = new String(result);
                    fi.close();
                    bo.close();
                    int newStartPosition = Integer.valueOf(str);
                    Log.i("520it",threadId+"保存的位置"+newStartPosition);
                    if (newStartPosition > startPosition) {
                        startPosition = newStartPosition;
                    }
                }
                Log.i("520it", "线程" + threadId + "开始位置" + startPosition + "结束位置" + endPosition);



                int currentPosition=startPosition;
                file.seek(startPosition);
                byte[] buff = new byte[1024];
                int len = 0;

                while ((len = is.read(buff)) != -1) {
                    if (stopFlag){
                        return;
                    }
                    file.write(buff, 0, len);

                   currentPosition += len;
                  synchronized (MainActivity.this){
                      total+=len;

                  }
                    FileOutputStream fo = new FileOutputStream(positionFile);
                    fo.write(String.valueOf(currentPosition).getBytes());

                    FileOutputStream pbo=new FileOutputStream(pbFile);
                    pbo.write(String.valueOf(total).getBytes());
                    fo.flush();
                    fo.close();
                    pbo.flush();
                    pbo.close();
                }
                is.close();
                file.close();

               /* if(pbFile.exists()){
                    FileInputStream  pbi=new FileInputStream(pbFile);
                    ByteArrayOutputStream pbbo=new ByteArrayOutputStream();
                    byte[] bu = new byte[1024];
                    int m = 0;
                    while ((m = pbi.read(bu)) != -1) {
                        pbbo.write(bu, 0, m);
                        pbbo.flush();
                    }

                    byte[] pbByte=pbbo.toByteArray();
                    String fileNum=new String(pbByte);
                    pbbo.close();
                    pbi.close();
                    int num=Integer.valueOf(fileNum);
                    total+=num;

                }*/



                Log.i("520it", threadId + "已经下载完成");

                if(positionFile.exists()){
                    positionFile.delete();
                }
                if(pbFile.exists()){
                    pbFile.delete();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        public String getFileName(String path) {
            int startIndex = path.lastIndexOf("/") + 1;
            String fileName = path.subSequence(startIndex, path.length()).toString();
            return fileName;
        }
    }
}
