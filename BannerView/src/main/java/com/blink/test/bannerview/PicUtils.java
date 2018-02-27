package com.blink.test.bannerview;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by Shuai.Li13 on 2017/3/28.
 */

public class PicUtils {

    /**
     * 获取inSampleSize
     *
     * @param options
     * @param reqWidth
     * @param reqHeight
     */
    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {

        options.inJustDecodeBounds = true;
        int outHeight = options.outHeight;
        int outWidth = options.outWidth;
        int inSampleSize = 1;
        if (outHeight > reqHeight || outWidth > reqWidth) {

            int hightRatio = Math.round((float) outHeight / (float) reqHeight);
            int widhtRatio = Math.round((float) outWidth / (float) reqWidth);
            inSampleSize = hightRatio >= widhtRatio ? widhtRatio : hightRatio;
        }

        return inSampleSize;
    }

    /**
     * 根据路径得到一个小的压缩的bitmap
     *
     * @param filePath
     * @return
     */
    public static Bitmap getSmallBitmap(String filePath) {

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);
        File f = new File(filePath);
        String absolutePath = f.getAbsolutePath();
        String path = f.getPath();
        String parent = f.getParent();
        String name = f.getName();
        long totalSpace = f.getTotalSpace();
        long length = f.length();
        options.inSampleSize = calculateInSampleSize(options, 480, 800);
        options.inJustDecodeBounds = false;
        Bitmap bitmap = BitmapFactory.decodeFile(filePath, options);
        return bitmap;
    }

    /**
     * 将图片解码成string
     *
     * @param filePath
     * @return
     */
    public static String bitmapToString(String filePath) {

        Bitmap smallBitmap = getSmallBitmap(filePath);
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        smallBitmap.compress(Bitmap.CompressFormat.PNG, 50, os);
        byte[] bytes = os.toByteArray();
        String s = Base64.encodeToString(bytes, Base64.DEFAULT);
        return s;
    }

    /**
     * <strong>读取照片的角度</strong>
     * <dr/>
     *
     * @param path
     * @return
     */
    public static int readPicDegree(String path) {

        int degree = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {

                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return degree;
    }


    /**
     * 旋转照片的角度
     * @param bitmap
     * @param degree
     * @return
     */
    public static Bitmap rotateDegree(Bitmap bitmap, int degree) {

        if (bitmap != null) {

            Matrix m = new Matrix();
            m.setRotate(degree);
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), m, false);

        }
        return bitmap;
    }
}
