package cn.evun.touchdemo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Shuai.Li13 on 2017/7/25.
 */

public class TouchView extends View {

    private Paint paint;

    public TouchView(Context context) {
        super(context);
    }

    public TouchView(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
    }


    private int getMySize(int defaultSize, int measureSpec) {
        int mySize = defaultSize;

        int mode = MeasureSpec.getMode(measureSpec);
        int size = MeasureSpec.getSize(measureSpec);

        switch (mode) {
            case MeasureSpec.UNSPECIFIED: {//如果没有指定大小，就设置为默认大小
                mySize = defaultSize;
                break;
            }
            case MeasureSpec.AT_MOST: {//如果测量模式是最大取值为size
                //我们将大小取最大值,你也可以取其他值
                mySize = size;
                break;
            }
            case MeasureSpec.EXACTLY: {//如果是固定的大小，那就不要去改变它
                mySize = size;
                break;
            }
        }
        return mySize;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = getMySize(100, widthMeasureSpec);
        int height = getMySize(100, heightMeasureSpec);

        if (width < height) {
            height = width;
        } else {
            width = height;
        }

        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int r=getMeasuredHeight()/2;
        int centerX=getLeft()+r;
        int centerY=getTop()+r;
        paint.setAntiAlias(true);
        paint.setColor(Color.parseColor("#ff0000"));
        canvas.drawCircle(centerX,centerY,r,paint);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()){

            case MotionEvent.ACTION_DOWN:
                print(event,"onTouchEvent");
                break;
            case MotionEvent.ACTION_MOVE:
                print(event,"onTouchEvent");
                break;
            case MotionEvent.ACTION_UP:
                print(event,"onTouchEvent");
                break;
        }
        return super.onTouchEvent(event);
    }

    private void print(MotionEvent event, String msg) {
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN: {
                Log.e("--> down ", msg);
                break;
            }
            case MotionEvent.ACTION_MOVE: {
                Log.e("--> move ", msg);
                break;
            }
            case MotionEvent.ACTION_UP: {
                Log.e("--> up ", msg);
                break;
            }

        }

    }
}
