package com.dong.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by 川东 on 2016/11/12.
 */

public class CircleImageView extends ImageView {
    private int outCircleWidth;
    private int outCircleColor;
    private int viewWidth;
    private int viewHight;
    private Bitmap image;
    private Paint paint;

    public CircleImageView(Context context) {
        super(context);
        setup(context, null);
    }

    public CircleImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setup(context, attrs);
    }

    public CircleImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setup(context, attrs);
    }

    private void setup(Context context, AttributeSet attrs) {
        if (attrs != null) {
            TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.CircleImageView);
            int len = array.getIndexCount();
            for (int i = 0; i < len; i++) {
                int attr = array.getIndex(i);
                switch (attr) {
                    case R.styleable.CircleImageView_outCircleColor:
                        this.outCircleColor = array.getColor(R.styleable.CircleImageView_outCircleColor, Color.WHITE);
                        break;
                    case R.styleable.CircleImageView_outCircleWidth:
                        this.outCircleWidth = (int) array.getDimension(R.styleable.CircleImageView_outCircleWidth, 0);
                        break;
                }
            }
        }
        paint = new Paint();
        //抗锯齿
        paint.setAntiAlias(true);
        paint.setColor(outCircleColor);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int width = measureWidth(widthMeasureSpec);
        int height = measureHight(heightMeasureSpec);
        viewWidth = width - outCircleWidth * 2;
        viewHight = height - outCircleWidth * 2;
        setMeasuredDimension(width, height);
    }

    private int measureHight(int heightMeasureSpec) {
        int result = 0;
        int mode = MeasureSpec.getMode(heightMeasureSpec);
        int size = MeasureSpec.getSize(heightMeasureSpec);
        if (mode == MeasureSpec.EXACTLY) {
            result = size;
        } else {
            result = viewHight;
        }

        return result;
    }

    private int measureWidth(int widthMeasureSpec) {
        int result = 0;
        int mode = MeasureSpec.getMode(widthMeasureSpec);
        int size = MeasureSpec.getSize(widthMeasureSpec);
        if (mode == MeasureSpec.EXACTLY) {
            result = size;
        } else {
            result = viewWidth;
        }

        return result;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        loadBtimap();
        if (image != null) {
            int min = Math.min(viewWidth, viewHight);
            int viewCenter = min / 2;
            image = Bitmap.createScaledBitmap(image, viewWidth, viewHight, false);
            canvas.drawCircle(viewCenter + outCircleWidth, viewCenter + outCircleWidth, viewCenter + outCircleWidth, paint);
            canvas.drawBitmap(drawCircleImage(image, min), outCircleWidth, outCircleWidth, null);
        }

    }

    private Bitmap drawCircleImage(Bitmap image, int min) {
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        Bitmap target = Bitmap.createBitmap(min, min, Bitmap.Config.ARGB_8888);
        //产生同样大小的画布；
        Canvas canvas=new Canvas(target);
        canvas.drawCircle(min/2,min/2,min/2,paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(image,0,0,paint);

        return target;
    }

    private void loadBtimap() {
        BitmapDrawable bitmapDrawable = (BitmapDrawable) this.getDrawable();
        if (bitmapDrawable != null) {
            image = bitmapDrawable.getBitmap();
        }
    }

   private void setBorderWidth(int width){
        this.outCircleWidth=width;
       this.invalidate();
   }
    private void setBorderColor(int color){
           if(paint!=null){
               paint.setColor(color);
           }
        this.invalidate();
    }
}
