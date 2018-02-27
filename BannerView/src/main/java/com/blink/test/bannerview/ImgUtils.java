package com.blink.test.bannerview;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by Shuai.Li13 on 2017/3/24.
 */

public class ImgUtils {

    public static String compress(File file, String path) {

        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            options.inSampleSize = 2;
            FileInputStream is = new FileInputStream(file);
            Bitmap bitmap = BitmapFactory.decodeStream(is, null, options);
            is.close();
            int scale=1;
            while (options.outWidth/scale/2>=75&&options.outHeight/scale/2>=75){

                scale*=2;
            }
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inSampleSize=scale;
            is = new FileInputStream(file);
            Bitmap selectBitmap = BitmapFactory.decodeStream(is, null, o);
            is.close();
            File file1 = new File(path);
            FileOutputStream os = new FileOutputStream(file1);
            selectBitmap.compress(Bitmap.CompressFormat.PNG,30,os);
            String absolutePath = file1.getAbsolutePath();
            return absolutePath;

        } catch (Exception e) {
            return null;
        }
    }
}
