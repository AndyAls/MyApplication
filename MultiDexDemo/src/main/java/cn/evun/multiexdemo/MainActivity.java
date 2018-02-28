package cn.evun.multiexdemo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.yanzhenjie.statusview.StatusUtils;
import com.yanzhenjie.statusview.StatusView;

public class MainActivity extends AppCompatActivity {

    private ImageView iv1, iv2;
    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        iv1 = (ImageView) findViewById(R.id.iv1);
        iv2 = (ImageView) findViewById(R.id.iv2);
        initData();
        StatusUtils.setLayoutFullScreen(this);
    }

    private void initData() {

        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.andy);
        iv1.setImageBitmap(bitmap);
//        Bitmap bitmap1 = BlurUtil.doBlur(bitmap, 5, true);
        iv2.setImageBitmap(bitmap);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (bitmap != null && !bitmap.isRecycled()) {
            bitmap.recycle();
            bitmap = null;
        }
    }
}
