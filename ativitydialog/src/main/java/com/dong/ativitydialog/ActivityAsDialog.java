package com.dong.ativitydialog;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class ActivityAsDialog extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_as_dialog);

    }
    public void tip(View view){
        Toast.makeText(ActivityAsDialog.this,"你可以设置每一项的点击事件",Toast.LENGTH_SHORT).show();
        finish();
    }
}
