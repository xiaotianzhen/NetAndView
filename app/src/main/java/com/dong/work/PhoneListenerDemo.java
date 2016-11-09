package com.dong.work;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class PhoneListenerDemo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_listener_demo);
        Intent intent=new Intent(this,PhoneListenService.class);
        startService(intent);


    }
}
