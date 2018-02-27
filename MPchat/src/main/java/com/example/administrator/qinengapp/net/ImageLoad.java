package com.example.administrator.qinengapp.net;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.widget.ImageView;

import com.example.administrator.qinengapp.R;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

/**
 * Created by Administrator on 2016/12/7.
 */

public class ImageLoad {

    public static void getBitmap(Context context, String url, ImageView imageView) {
        Picasso.with(context).load(url).error(R.mipmap.ic_launcher).into(imageView);
    }

    /**
     * 圆形图片
     * @param context
     * @param url
     * @param imageView
     */
    public static void setCircleBitmap(Context context, String url, ImageView imageView) {
        Picasso.with(context).load(url).transform(new CircleTransForm()).error(R.mipmap.ic_launcher).into(imageView);
    }

    /**
     * 圆角图片
     * @param context
     * @param url
     * @param imageView
     * @param n   圆角度数
     */
    public static void setRangleBitmap(Context context, String url, ImageView imageView, int n) {
        Picasso.with(context).load(url).transform(new RangleTransForm(n)).error(R.mipmap.ic_launcher).into(imageView);
    }

    public static class CircleTransForm implements Transformation {
        @Override
        public Bitmap transform(Bitmap source) {
            int width = source.getWidth();
            int height = source.getHeight();
            int min = Math.min(width, height);

            int width_min = (width - min) / 2;
            int height_min = (height - min) / 2;

            Bitmap bitmap = Bitmap.createBitmap(source, width_min, height_min, min, min);
            if (bitmap != source) {
                source.recycle();
            }
            Bitmap circleBit = Bitmap.createBitmap(min, min, source.getConfig());
            Canvas canvas = new Canvas(circleBit);
            Paint paint = new Paint();
            BitmapShader shader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
            paint.setShader(shader);
            paint.setAntiAlias(true);
            float radius = min / (float) 2;
            canvas.drawCircle(radius, radius, radius, paint);
            bitmap.recycle();
            return circleBit;
        }

        @Override
        public String key() {
            return "circle";
        }

    }

    public static class RangleTransForm implements Transformation {

        //设置圆角的角度
        private int rangle = 0;

        public RangleTransForm(int rangle) {
            this.rangle = rangle;
        }

        @Override
        public Bitmap transform(Bitmap source) {
            Bitmap bitmap = Bitmap.createBitmap(source.getWidth(),source.getHeight(),source.getConfig());
            Canvas canvas = new Canvas(bitmap);
            Paint paint = new Paint();
            BitmapShader shader = new BitmapShader(source, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
            paint.setShader(shader);
            paint.setAntiAlias(true);
            RectF rectF = new RectF(0,0,source.getWidth(),source.getHeight());
            canvas.drawRoundRect(rectF,rangle,rangle,paint);
            source.recycle();
            return bitmap;
        }

        @Override
        public String key() {
            return "rangle";
        }
    }

}
