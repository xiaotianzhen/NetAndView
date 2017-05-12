package com.dong.sortviewpagerdemo;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.dong.sortviewpagerdemo.adapters.myAdater;
import com.dong.sortviewpagerdemo.adapters.myGridViewAdapter;
import com.dong.sortviewpagerdemo.entitys.IconInfo;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ViewPager mViewPager;
    private List<View> views = new ArrayList<View>();
    private List<IconInfo> list1 = new ArrayList<IconInfo>();
    private List<IconInfo> list2 = new ArrayList<IconInfo>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initView();
    }

    private void initView() {
        mViewPager = (ViewPager) findViewById(R.id.viewPager);
        initData();
        View grid1 = LayoutInflater.from(getApplicationContext()).inflate(R.layout.gradview_layout, null);
        View grid2 = LayoutInflater.from(getApplicationContext()).inflate(R.layout.gradview_layout, null);
        GridView gridView1 = (GridView) grid1.findViewById(R.id.gridView);
        GridView gridView2 = (GridView) grid2.findViewById(R.id.gridView);
        views.add(gridView1);
        views.add(gridView2);
        mViewPager.setAdapter(new myAdater(views));
        gridView1.setAdapter(new myGridViewAdapter(list1, getApplicationContext()));
        gridView2.setAdapter(new myGridViewAdapter(list2, getApplicationContext()));
        gridView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this, "点击了"+(position+1)+"位置", Toast.LENGTH_SHORT).show();
            }
        });
        gridView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this, "点击了"+(8+position+1)+"位置", Toast.LENGTH_SHORT).show();
            }
        });

    }


    private void initData() {
        String[] iconNames = getResources().getStringArray(R.array.iconName);
        TypedArray iconArray = getResources().obtainTypedArray(R.array.icon);

        for (int i = 0; i < iconNames.length; i++) {

            if (i < 8) {
                list1.add(new IconInfo(iconNames[i], iconArray.getResourceId(i, 0)));
            } else {
                list2.add(new IconInfo(iconNames[i], iconArray.getResourceId(i, 0)));
            }
        }


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
