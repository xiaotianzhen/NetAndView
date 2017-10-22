package com.yicooll.dong.customindicator;

import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ViewPager mViewPager;
    private int[] imagesId = new int[]{R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher};
    private List<ImageView> mImageViews = new ArrayList<ImageView>();
    private Indicator mIndicator;

    Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
           int posititon= mViewPager.getCurrentItem();
            if(posititon==mImageViews.size()-1){
                posititon=-1;
            }
            mViewPager.setCurrentItem(posititon+1);
            mHandler.sendEmptyMessageDelayed(1,5000);
        }
    };
    private void initData() {
        for (int i = 0; i < imagesId.length; i++) {
            ImageView im = new ImageView(getApplicationContext());
            im.setScaleType(ImageView.ScaleType.CENTER_CROP);
            im.setBackgroundResource(imagesId[i]);
            mImageViews.add(im);
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mViewPager = (ViewPager) findViewById(R.id.viewPager);
        initData();
        mIndicator= (Indicator) findViewById(R.id.indicator);
        mIndicator.setNumber(mImageViews.size()+1);
        mIndicator.setIndicatorBgColor(Color.BLACK);
        mIndicator.setIndicatorForeColor(Color.BLACK);
        mViewPager.setCurrentItem(0);
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                mIndicator.setOffset(position,positionOffset);
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mViewPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return mImageViews.size();
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView(mImageViews.get(position));
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                container.addView(mImageViews.get(position));
                return mImageViews.get(position);
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }
        });
             mHandler.sendEmptyMessageDelayed(1,5000);
    }


}
