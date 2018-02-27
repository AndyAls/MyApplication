package com.blink.test.bannerview;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private int[] imgs = {R.drawable.a,R.drawable.b,R.drawable.c,R.drawable.d};
    private List<View> viewList;
    BannerView bannerView;
    private String path ="";
    private ImageView iv;
    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        iv = (ImageView) findViewById(R.id.iv);
        tv = (TextView) findViewById(R.id.tv);
        viewList = new ArrayList<View>();
        for (int i = 0; i < imgs.length; i++) {
            ImageView image = new ImageView(this);
            image.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            //设置显示格式
            image.setScaleType(ImageView.ScaleType.CENTER_CROP);
            image.setImageResource(imgs[i]);
            viewList.add(image);
        }
        bannerView = (BannerView) findViewById(R.id.banner);
        bannerView.setViewList(viewList);
        bannerView.startLoop(true);
//        bannerView.setTransformAnim(true);
        initData();
    }

    private void initData() {


        //构造一个带有文本的对象
        SpannableString spannableString =new SpannableString("中华人民共和国");
        /**
         * 参一 设置的属性
         * 参二 开始的文本索引
         * 参三 结束的文本索引
         * 参四 有四种显示的模式 EXCLUSIVE 不包含 INCLUSIVE包含
         *
         */
        spannableString.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                Toast.makeText(MainActivity.this,"dianjile",Toast.LENGTH_SHORT).show();
            }
        },0,2,Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        tv.setMovementMethod(LinkMovementMethod.getInstance());
        tv.setText(spannableString);
    }

    public void onClick(View v){

        switch (v.getId()){

            case R.id.iv:
                int i = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
                if(i!= PackageManager.PERMISSION_GRANTED){//!授权


                    ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.CAMERA},2);
                }
                else if (ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.CAMERA)){

                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setMessage("权限很重要 ,请授权")
                            .setTitle("申请权限")
                            .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    finish();
                                }
                            });
                    builder.setPositiveButton("授权", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.CAMERA},2);
                        }
                    });
                }

                else {

                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    intent.putExtra(MediaStore.Images.Media.ORIENTATION,0);
                    path=Environment.getExternalStorageDirectory().getPath()+"/中华人民共和国/"+ System.currentTimeMillis()+".jpg";
                    File f = new File(path);
                    Uri uri = Uri.fromFile(f);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT,uri);
                    startActivityForResult(intent,1);
                }

                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode==2){


        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK){
            if (requestCode==1){

                int i = PicUtils.readPicDegree(path);
                Bitmap bitmap = PicUtils.getSmallBitmap(path);
                Bitmap bitmap1 = PicUtils.rotateDegree(bitmap, i);
                iv.setImageBitmap(bitmap1);

            }
        }
    }
}
