package cn.evun.viewdemo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Shuai.Li13 on 2017/2/7.
 */

public class DrawableView extends View {


    private Path path;
    private Paint paint;

    public DrawableView(Context context) {
        this(context, null);
    }

    public DrawableView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);


    }

    public DrawableView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        path = new Path();
        paint = new Paint();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setColor(Color.RED);
        paint.setStrokeWidth(10);
        paint.setStyle(Paint.Style.STROKE);

        path.reset();
        path.moveTo(8, 8);
        path.quadTo(50, 50, 100, 100);

        canvas.drawPath(path, paint);
        canvas.drawPoint(50, 50, paint);
        canvas.drawLine(8, 8, 50, 50, paint);
        canvas.drawLine(100, 100, 50, 50, paint);
    }
}
