package com.dong.work;

import android.app.Service;
import android.content.Intent;
import android.media.MediaRecorder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import java.io.File;

/**
 * Created by 川东 on 2016/11/4.
 */
public class PhoneListenService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        TelephonyManager manager = (TelephonyManager) this.getSystemService(TELEPHONY_SERVICE);
        manager.listen(new myPhoneListen(), PhoneStateListener.LISTEN_CALL_STATE);


    }

    private class myPhoneListen extends PhoneStateListener {
        private MediaRecorder recorder = null;

        @Override
        public void onCallStateChanged(int state, String incomingNumber) {


            switch (state) {
                //没有接通，正常状态下
                case TelephonyManager.CALL_STATE_IDLE:
                    Log.i("520it","没有接通，正常状态下");
                   if(recorder!=null){
                       recorder.release();
                       recorder=null;
                       Toast.makeText(getApplicationContext(),"监听成功",Toast.LENGTH_SHORT).show();
                   }
                    break;
                //响铃状态下
                case TelephonyManager.CALL_STATE_RINGING:
                    Log.i("520it","响铃状态下");
                    break;
                //接通状态下
                case TelephonyManager.CALL_STATE_OFFHOOK:
                    Log.i("520it","接通状态下");
                    try {
                        // 初始化一个录音器,
                        recorder = new MediaRecorder();
                        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
                        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
                        recorder.setOutputFile("/sdcard/tel.3gp");
                        File file=new File("/sdcard/tel.3gp");
                        if (file.exists()){
                            Log.i("520it","监听文件已存在");
                        }
                        recorder.prepare();
                        recorder.start();


                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
            }
            super.onCallStateChanged(state, incomingNumber);
        }

    }
}
