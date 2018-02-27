package com.example.lshapp.shortvideodemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.File;
import java.io.FileInputStream;
import java.text.DecimalFormat;

/**
 * Created by Administrator on 2016/3/21.
 */
public class EditVideoActivity extends Activity {
    public static final int SIZETYPE_B = 1;// 获取文件大小单位为B的double值
    public static final int SIZETYPE_KB = 2;// 获取文件大小单位为KB的double值
    public static final int SIZETYPE_MB = 3;// 获取文件大小单位为MB的double值
    public static final int SIZETYPE_GB = 4;// 获取文件大小单位为GB的double值

    private TextView eimgxie;
    private EditText edit_content;
    private String videoPath;
    private String framePicPath;
    private ImageView imageviewvideo;
    private TextView tvvideosize;
    private TextView tvlztime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editvideo);

        TextView enametv = (TextView) findViewById(R.id.enametv);
        enametv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(videoPath)) {
                    File file = new File(videoPath);
                    file.delete();
                }
                if (!TextUtils.isEmpty(framePicPath)) {
                    File file = new File(framePicPath);
                    file.delete();
                }
                finish();
            }
        });
        String videoDuration = getIntent().getStringExtra("videoDuration");
        int videoSize = getIntent().getIntExtra("videoSize", 0);

        Log.d("videoSize", "videoDuration:" + videoDuration + "");

        double videofilesize = FormetFileSize(videoSize, SIZETYPE_MB);
        Log.d("videoSize", "videofilesize:" + videofilesize + "");

        videoPath = getIntent().getStringExtra("videoPath");
        framePicPath = getIntent().getStringExtra("framePicPath");

        tvvideosize = (TextView) findViewById(R.id.tvvideosize);
        tvlztime = (TextView) findViewById(R.id.tvlztime);
        tvvideosize.setText("视频大小：" + videofilesize + "MB");
        tvlztime.setText("录制时长：" + videoDuration);


        FrameLayout framelayoutvideo = (FrameLayout) findViewById(R.id.framelayoutvideo);
        imageviewvideo = (ImageView) findViewById(R.id.imageviewvideo);

//        ImageLoader.getInstance().displayImage("file://" + framePicPath, imageviewvideo);
        Glide.with(EditVideoActivity.this).load(framePicPath).error(R.drawable.jc_play_normal).into(imageviewvideo);
        InitView();
        framelayoutvideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(videoPath)) {

                    Intent intent = new Intent(EditVideoActivity.this, WidthMatchVideo.class);
                    intent.putExtra("videoPath", videoPath);
                    startActivity(intent);
                } else {
                    Toast.makeText(EditVideoActivity.this, "视频路径无效", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    /**
     * 转换文件大小,指定转换的类型
     *
     * @param fileS
     * @param sizeType
     * @return
     */
    private static double FormetFileSize(long fileS, int sizeType) {
        DecimalFormat df = new DecimalFormat("#.00");
        double fileSizeLong = 0;
        switch (sizeType) {
            case SIZETYPE_B:
                fileSizeLong = Double.valueOf(df.format((double) fileS));
                break;
            case SIZETYPE_KB:
                fileSizeLong = Double.valueOf(df.format((double) fileS / 1024));
                break;
            case SIZETYPE_MB:
                fileSizeLong = Double.valueOf(df.format((double) fileS / 1048576));
                break;
            case SIZETYPE_GB:
                fileSizeLong = Double.valueOf(df
                        .format((double) fileS / 1073741824));
                break;
            default:
                break;
        }
        return fileSizeLong;
    }


    /**
     * 获取指定文件大小
     *
     * @param
     * @return
     * @throws Exception
     */
    private static long getFileSize(File file) throws Exception {
        long size = 0;
        if (file.exists()) {
            FileInputStream fis = null;
            fis = new FileInputStream(file);
            size = fis.available();
        } else {
            file.createNewFile();
            Log.e("获取文件大小", "文件不存在!");
        }
        return size;
    }

    /**
     * 获取指定文件夹
     *
     * @param f
     * @return
     * @throws Exception
     */
    private static long getFileSizes(File f) throws Exception {
        long size = 0;
        File flist[] = f.listFiles();
        for (int i = 0; i < flist.length; i++) {
            if (flist[i].isDirectory()) {
                size = size + getFileSizes(flist[i]);
            } else {
                size = size + getFileSize(flist[i]);
            }
        }
        return size;
    }


    private void InitView() {
        ImageView eback = (ImageView) findViewById(R.id.eback);
        edit_content = (EditText) findViewById(R.id.et_sendmessage);
        eback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        eimgxie = (TextView) findViewById(R.id.eimgxie);
        eimgxie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                UploadHelper.upload(new File(selectedPhotos.get(0)));

                if (TextUtils.isEmpty(videoPath) || TextUtils.isEmpty(framePicPath)) {
                    Toast.makeText(EditVideoActivity.this, "录制视频失败，请重新录制", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //上传视频

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            //退出时删除录制的视频和图片
            if (!TextUtils.isEmpty(videoPath)) {
                File file = new File(videoPath);
                file.delete();
            }
            if (!TextUtils.isEmpty(framePicPath)) {
                File file = new File(framePicPath);
                file.delete();
            }

            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


}
