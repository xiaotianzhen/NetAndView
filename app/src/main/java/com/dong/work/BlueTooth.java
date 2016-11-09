package com.dong.work;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;

/**
 * Created by 川东 on 2016/11/8.
 */

public class BlueTooth {
    public static final int CONNECT_FAILED = 0;
    public static final int CONNECT_SUCCESS = 1;
    public static final int WRITE_FAILED = 2;
    public static final int READ_FAILED = 3;
    public static final int DATA = 4;
    private boolean isconnet = true;

    private BluetoothDevice device;
    private Handler handler;
    private  BluetoothSocket socket;

    public  BlueTooth(BluetoothDevice device, Handler handler) {
        this.device = device;
        this.handler = handler;
    }

    public void connect() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                BluetoothSocket tmp = null;
                Method method;
                try {
                    method = device.getClass().getMethod("createRfcommSocket", new Class[]{int.class});
                    tmp = (BluetoothSocket) method.invoke(device, 1);

                } catch (Exception e) {
                    e.printStackTrace();
                }
                socket = tmp;
                try {
                    socket.connect();
                } catch (Exception e) {
                    e.printStackTrace();
                    setState(CONNECT_FAILED);
                }
                setState(CONNECT_SUCCESS);

                if (isconnet) {
                    Log.i("520it", "***************************"+isconnet);
                    try {
                        OutputStream os = socket.getOutputStream();
                        os.write(getHexBytes("123"));
                        Log.i("520it", "***************************写入成功"+getHexBytes("你好啊，天真"));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }
        }).start();
    }
    private void setState(int state) {
        Message msg = handler.obtainMessage();
        msg.what = state;
        handler.sendMessage(msg);
    }
    private byte[] getHexBytes(String message) {
        int len = message.length() / 2;
        char[] chars = message.toCharArray();
        String[] hexStr = new String[len];
        byte[] bytes = new byte[len];
        for (int i = 0, j = 0; j < len; i += 2, j++) {
            hexStr[j] = "" + chars[i] + chars[i + 1];
            bytes[j] = (byte) Integer.parseInt(hexStr[j], 16);
        }
        return bytes;
    }
}
