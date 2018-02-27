package com.example.okhttpdemo;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.Resource;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

public class MainActivity1 extends AppCompatActivity {
    private static final String PATH ="http://img5.imgtn.bdimg.com/it/u=3322969063,3185532596&fm=27&gp=0.jpg";
    private int screenWidth;
    private static final String path="https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1506592110864&di=012cdbda7a62545a332503d7b2fbf1fa&imgtype=0&src=http%3A%2F%2Fd.hiphotos.baidu.com%2Fimage%2Fpic%2Fitem%2Fd6ca7bcb0a46f21f3dc8d392ff246b600d33ae77.jpg";
    private ImageView id_iv;
    private ProgressBar pb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);
        WindowManager wm=getWindowManager();
        DisplayMetrics outMetrics=new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        screenWidth=outMetrics.widthPixels;
        id_iv= (ImageView) findViewById(R.id.id_iv);
        pb= (ProgressBar) findViewById(R.id.pb);
        Glide.with(this)
                .load(PATH)
                .centerCrop()
                .dontAnimate()

//                .listener(new RequestListener<String, GlideDrawable>() {
//            @Override
//            public boolean onException(Exception e, String s, Target<GlideDrawable> target, boolean b) {
//                return false;
//            }
//
//            @Override
//            public boolean onResourceReady(final GlideDrawable glideDrawable, String s, Target<GlideDrawable> target, boolean b, boolean b1) {
//                Log.e("TAG", "onResourceReady: target-------->"+target );
//                Log.e("TAG", "onResourceReady: glideDrawable-------->"+glideDrawable );
//                        Glide.with(MainActivity1.this).load(path).
//                                centerCrop().placeholder(glideDrawable).
//                                diskCacheStrategy(DiskCacheStrategy.NONE).
//                                skipMemoryCache(true).dontAnimate().
//                                crossFade(1000).
//                                listener(new RequestListener<String, GlideDrawable>() {
//                                    @Override
//                                    public boolean onException(Exception e, String s, Target<GlideDrawable> target, boolean b) {
//                                        return false;
//                                    }
//
//                                    @Override
//                                    public boolean onResourceReady(GlideDrawable glideDrawable, String s, Target<GlideDrawable> target, boolean b, boolean b1) {
//                                        pb.setVisibility(View.GONE);
//                                        return false;
//                                    }
//                                }).
//                                into(id_iv);
//                return false;
//            }
//        }).
        .transform(new MyTransformtion(this)).
                diskCacheStrategy(DiskCacheStrategy.ALL).
                placeholder(new ColorDrawable(Color.BLACK))
                .dontAnimate().into(id_iv);

        //createLoadingDialog(this).show();
    }
    public Dialog createLoadingDialog(Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.wechat_dialog, null);// 得到加载view
        Dialog loadingDialog = new Dialog(context, R.style.loading_dialog);// 创建自定义样式dialog
        loadingDialog.setCancelable(false);// 不可以用“返回键”取消
        loadingDialog.setContentView(v);// 设置布局
        /**
         * 设置Dialog的宽度为屏幕宽度的61.8%，高度为自适应
         */
        WindowManager.LayoutParams lp = loadingDialog.getWindow().getAttributes();
        lp.width= (int) (screenWidth*0.618f);
        lp.height=lp.WRAP_CONTENT;
        loadingDialog.getWindow().setAttributes(lp);
        return loadingDialog;
    }
    public class MyTransformtion extends BitmapTransformation{

        public MyTransformtion(Context context) {
            super(context);
        }
        @Override
        protected Bitmap transform(BitmapPool bitmapPool, Bitmap bitmap, int i, int i1) {
            Log.e("TAG", "transform: bitmap-------->"+bitmap );
            return FastBlur.doBlur(bitmap,10,true);
        }
        @Override
        public String getId() {
            return "com.cisetech.dialogdemo.MyTransformtion";
        }
    }
}
