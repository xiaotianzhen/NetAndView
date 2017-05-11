package com.dong.customindicator;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by 川东 on 2016/11/20.
 */

public class Indicator extends View {

    private Paint mForePaint, mBgPain;
    private float offset;
    private int radiu = 20;
    private int indicatorNum = 4;
    private int indicatorBgColor = Color.RED;
    private int indicatorForeColor = Color.BLUE;

    public void setOffset(int position, float positionOffset) {
        offset = position * radiu * 3 + positionOffset * radiu * 3;
        invalidate();
    }

    public Indicator(Context context) {
        super(context);
        initPain();
    }

    public Indicator(Context context, AttributeSet attrs) {
        super(context, attrs);
        initPain();
        instanceAttrs(context, attrs);
    }


    private void instanceAttrs(Context context, AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.Indicator);
        radiu = array.getInteger(R.styleable.Indicator_radiu, radiu);
        indicatorNum = array.getInteger(R.styleable.Indicator_indicatorNum, indicatorNum);
        indicatorBgColor = array.getColor(R.styleable.Indicator_indicatorBgColor, indicatorBgColor);
        indicatorForeColor = array.getColor(R.styleable.Indicator_indicatorForeColor, indicatorForeColor);
    }
    public void setSize(int size){
        this.radiu=size;
        invalidate();
    }
    public void setNumber(int number){
        this.indicatorNum=number;
        invalidate();
    }
    public void setIndicatorBgColor(int colorId){
        this.indicatorBgColor=colorId;
        invalidate();
    }
    public void setIndicatorForeColor(int colorId){
        this.indicatorForeColor=colorId;
        invalidate();
    }

    private void initPain() {

        mForePaint = new Paint();
        mForePaint.setAntiAlias(true);
        mForePaint.setColor(indicatorForeColor);
        //画笔的大小
        mForePaint.setStrokeWidth(4);
        //空心
        mForePaint.setStyle(Paint.Style.FILL);


        mBgPain = new Paint();
        mBgPain.setAntiAlias(true);
        mBgPain.setColor(indicatorBgColor);
        //画笔的大小
        mBgPain.setStrokeWidth(4);
        //空心
        mBgPain.setStyle(Paint.Style.STROKE);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        for (int i = 0; i < indicatorNum; i++) {
            canvas.drawCircle(radiu + i * radiu * 3, 60, radiu, mBgPain);
        }

            canvas.drawCircle(radiu+ offset, 60, radiu, mForePaint);


    }
}
