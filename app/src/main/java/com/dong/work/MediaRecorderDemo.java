package com.dong.work;

import android.hardware.Camera;
import android.media.MediaRecorder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import java.io.IOException;

public class MediaRecorderDemo extends AppCompatActivity {
    private MediaRecorder mr;
    private Button bt_start;
    private Button bt_stop;
    private SurfaceView sv;
    private SurfaceHolder holder;

    private void initView() {
        bt_start = (Button) findViewById(R.id.bt_startRecorder);
        bt_stop = (Button) findViewById(R.id.bt_stopRecorder);
        sv = (SurfaceView) findViewById(R.id.sv);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置全屏在setcontentView之前
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_media_recorder_demo);
        initView();
        holder = sv.getHolder();
        holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        holder.addCallback(new myRecordercallback());

    }

    private class myRecordercallback implements SurfaceHolder.Callback {

        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            MediaRecorderDemo.this.holder = holder;

        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {

        }
    }

    public void startRecorder(View view) {
        bt_start.setEnabled(false);
        bt_stop.setEnabled(true);
        try {

            mr = new MediaRecorder();
            mr.reset();
            mr.setAudioSource(MediaRecorder.AudioSource.MIC);
            mr.setVideoSource(MediaRecorder.VideoSource.CAMERA);

            mr.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            mr.setVideoSize(2368, 4208);
            mr.setVideoFrameRate(3);

            mr.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            mr.setVideoEncoder(MediaRecorder.VideoEncoder.H263);


            mr.setOutputFile("/mnt/sdcard/itcast.3gp");
            mr.setPreviewDisplay(holder.getSurface());
            mr.prepare();
            mr.start();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void stopRecorder(View view) {
        bt_start.setEnabled(true);
        bt_stop.setEnabled(false);
        if(mr!=null){
            Log.i("520it","******************录制已结束");
            mr.stop();
            mr.release();
            mr = null;
        }


    }

}
