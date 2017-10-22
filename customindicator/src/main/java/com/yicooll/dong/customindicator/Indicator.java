package com.yicooll.dong.customindicator;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
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
        // offset = position * radiu * 3 + positionOffset * radiu * 3;
        offset = position * radiu * 4 + positionOffset * radiu * 4;
        invalidate();
    }

    public Indicator(Context context) {
        this(context, null);
        initPain();
    }

    public Indicator(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        initPain();
        instanceAttrs(context, attrs);
    }

    public Indicator(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
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

    public void setSize(int size) {
        this.radiu = size;
        invalidate();
    }

    public void setNumber(int number) {
        this.indicatorNum = number;
        invalidate();
    }

    public void setIndicatorBgColor(int colorId) {
        mBgPain.setColor(colorId);
        invalidate();
    }

    public void setIndicatorForeColor(int colorId) {
        mForePaint.setColor(colorId);
        invalidate();
    }

    private void initPain() {

        mForePaint = new Paint();
        mForePaint.setAntiAlias(true);
        mForePaint.setColor(indicatorForeColor);
        //画笔的大小
        mForePaint.setStyle(Paint.Style.FILL);


        mBgPain = new Paint();
        mBgPain.setAntiAlias(true);
        mBgPain.setColor(indicatorBgColor);
        //画笔的大小
        mBgPain.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int i = 0; i < indicatorNum; i++) {
            canvas.drawCircle(radiu + i * radiu * 4, radiu, radiu, mBgPain);
        }
        /**
         * 1--6   4,10   8,14
         * radius  3  7  11
         */
        RectF rectF = new RectF(offset, 0, 6 * radiu + offset, 2 * radiu);
        canvas.drawRoundRect(rectF, 3 * radiu, 60, mForePaint);
    }
}