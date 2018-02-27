package cn.evun.decoration;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by Shuai.Li13 on 2017/2/23.
 */

public class ActCanvas extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new CustumView(this));
    }

    private class CustumView extends View {

        private final Paint paint;

        public CustumView(Context context) {

            super(context);
            paint = new Paint(Paint.ANTI_ALIAS_FLAG);
            paint.setColor(Color.YELLOW);
            paint.setStrokeWidth(2);
            paint.setStrokeJoin(Paint.Join.BEVEL);
            paint.setStrokeCap(Paint.Cap.BUTT);

        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);

//            canvas.drawCircle(500,500,300,paint);

            RectF  oval= new RectF(50,50,400,600);
            canvas.drawArc(oval,0,90,true,paint);
        }

    }
}
