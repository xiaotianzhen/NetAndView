package com.dong.work;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.qianwang.work.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static com.dong.work.BlueTooth.DATA;


public class BlueToothDemo extends AppCompatActivity implements View.OnClickListener {
    private Button bt_on, bt_off, bt_visible;
    private ListView lv;
    private BluetoothAdapter BA = null;
    private Set<BluetoothDevice> bluetoothDevices;
    private List<String> list = new ArrayList<String>();
    private List<BluetoothDevice> listDevice = new ArrayList<BluetoothDevice>();
    private BlueToothReciver reciver;
    private BlueTooth client;
    private final String lockName = "BOLUTEK";
    private boolean isconnet = true;


    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case BlueTooth.CONNECT_FAILED:
                    Toast.makeText(BlueToothDemo.this, "连接失败", Toast.LENGTH_LONG).show();

                    break;
                case BlueTooth.CONNECT_SUCCESS:
                    Toast.makeText(BlueToothDemo.this, "连接成功", Toast.LENGTH_LONG).show();
                    break;
                case BlueTooth.READ_FAILED:
                    Toast.makeText(BlueToothDemo.this, "读取失败", Toast.LENGTH_LONG).show();
                    break;
                case BlueTooth.WRITE_FAILED:
                    Toast.makeText(BlueToothDemo.this, "写入失败", Toast.LENGTH_LONG).show();
                    break;
                case DATA:
                    Toast.makeText(BlueToothDemo.this, msg.arg1 + "", Toast.LENGTH_LONG).show();
                    break;
            }
            super.handleMessage(msg);
        }

    };

    private void initView() {
        bt_visible = (Button) findViewById(R.id.bt_visible);
        bt_on = (Button) findViewById(R.id.bt_on);
        bt_off = (Button) findViewById(R.id.bt_off);
        lv = (ListView) findViewById(R.id.lv);

        bt_visible.setOnClickListener(this);
        bt_on.setOnClickListener(this);
        bt_off.setOnClickListener(this);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (BA.isEnabled()) {
                    BA.cancelDiscovery();
                    BluetoothDevice device = listDevice.get(position);
                    client = new BlueTooth(device, handler);
                    try {
                        client.connect();

                    } catch (Exception e) {
                        e.printStackTrace();
                        isconnet = false;
                    }
                } else {
                    Toast.makeText(BlueToothDemo.this, "请先开启蓝牙", Toast.LENGTH_LONG).show();
                }
               // BluetoothSocket socket = null;


            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blue_tooth);
        initView();
        BA = BluetoothAdapter.getDefaultAdapter();
        BA.startDiscovery();
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        reciver = new BlueToothReciver();
        registerReceiver(reciver, filter);




    }

    private class BlueToothReciver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

            Log.i("520it", "***************************你好");
            if ("BluetoothDevice.ACTION_FOUND".equals(intent.getAction())) {

                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                if (isLock(device)) {
                    list.add(device.getName());
                }
                listDevice.add(device);
            }
            showDevices();
        }
    }

    private boolean isLock(BluetoothDevice device) {
        boolean islock = device.getName().equals(lockName);
        boolean isSingName = list.indexOf(device.getName()) == -1;
        return islock && isSingName;

    }


    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_on:
                if (!BA.isEnabled()) {
                    Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    startActivityForResult(intent, 0);
                    Toast.makeText(this, "蓝牙开启", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(this, "蓝牙已经开启", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.bt_off:
                if (BA.isEnabled()) {
                    BA.disable();
                    Toast.makeText(this, "蓝牙已经关闭", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.bt_visible:

                Intent getVisible = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
                getVisible.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
                startActivityForResult(getVisible, 0);
                break;

        }
    }

    private void showDevices() {

        bluetoothDevices = BA.getBondedDevices();
        for (BluetoothDevice bd : bluetoothDevices) {
            listDevice.add(bd);
            list.add(bd.getName());
        }
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, list);
        lv.setAdapter(adapter);

    }


    protected void onDestroy() {
        unregisterReceiver(reciver);
        super.onDestroy();
    }


}
