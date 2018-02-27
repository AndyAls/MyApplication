package cn.evun.touchdemo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Shuai.Li13 on 2017/7/26.
 */

public class PathView extends View {

    private  Paint mPaint;
    private PointF start,end,control1,control2;
    private int centerX,centerY;
    private boolean b=true;

    public PathView(Context context) {
        super(context,null);

    }

    public PathView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setColor(Color.BLACK);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setTextSize(60);
        mPaint.setStrokeWidth(8);
        start=new PointF(0,0);
        end=new PointF(0,0);
        control1=new PointF(0,0);
        control2=new PointF(0,0);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        centerX=w/2;
        centerY=h/2;

        //初始化贝塞尔曲线控制点和数据点
        start.x=centerX-300;
        start.y=centerY;
        end.x=centerX+300;
        end.y=centerY;
        control1.x=centerX;
        control1.y=centerY-100;
        control2.x=centerX-50;
        control2.y=centerY+100;

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        //根据触摸位置更改控制点位置
        if (b){
            control1.x=event.getX();
            control1.y=event.getY();
        }else {
            control2.x=event.getX();
            control2.y=event.getY();
        }

        invalidate();
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setColor(Color.RED);
        mPaint.setStrokeWidth(20);
        canvas.drawPoint(start.x,start.y,mPaint);
        canvas.drawPoint(end.x,end.y,mPaint);
        canvas.drawPoint(control1.x,control1.y,mPaint);
        canvas.drawPoint(control2.x,control2.y,mPaint);


        //绘制辅助线
        mPaint.setStrokeWidth(5);
        mPaint.setColor(Color.GREEN);
        canvas.drawLine(start.x, start.y, control1.x, control1.y, mPaint);
        canvas.drawLine(control1.x, control1.y,control2.x, control2.y, mPaint);
        canvas.drawLine(control2.x, control2.y,end.x, end.y, mPaint);

        //绘制贝塞尔曲线

        mPaint.setStrokeWidth(10);
        mPaint.setColor(Color.BLUE);

        Path path =new Path();
        path.moveTo(start.x,start.y);
//        path.quadTo(control1.x,control1.y,end.x,end.y); //绘制二阶贝塞尔曲线
        path.cubicTo(control1.x,control1.y,control2.x,control2.y,end.x,end.y);
        canvas.drawPath(path,mPaint);
    }

    public void setMode(boolean b) {
        this.b=b;
    }
}
