package com.example.lshapp.shortvideodemo;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;


/**
 * Created by Administrator on 2016/8/8.
 */
public class WidthMatchVideo extends Activity {

    //    private VideoView videoView;
    private String uri;
    private String videoPath;
    private int videoid;
    private CommonVideoView videoView;
    //    private MediaController mc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_widthmatchvideo);
        videoPath = getIntent().getStringExtra("videoPath");

        videoid = getIntent().getIntExtra("videoid", 0);
        Log.d("logresult", videoid + "");


        if (videoid == 0) {
            uri = videoPath;
        }
        //我的缓存小视屏代码
//        else {
//            String videourl = PrefsTools.getStringPrefs(WidthMatchVideo.this, "dearvideo", "dearvideo" + videoid, "");
//            Log.d("logresult", "A+" + videourl + "");
//            if (TextUtils.isEmpty(videourl)) {
//                //如果之前没有缓存
//
//                if (!CommonUtils.isNetworkAvailable(WidthMatchVideo.this)) {
//                    Toast toast = Toast.makeText(this, "请检查网络", Toast.LENGTH_SHORT);
//                    toast.setGravity(Gravity.TOP, 0, 100);
//                    toast.show();
//                    return;
//                }
//                uri = videoPath;
//                new Thread() {
//                    public void run() {
//                        try {
//                            Log.d("logresult", "C+" + videoid + "");
//
//                            String dirName = Environment.getExternalStorageDirectory() + "/Dearbaobei/";
//                            File file = new File(dirName);
//                            if (!file.exists()) {
//                                file.mkdir();
//                            }
//                            String dvf = dirName + "video" + videoid + ".mp4";
//                            File file1 = new File(dvf);
//                            if (file1.exists()) {
//                                file1.delete();
//                            }
//                            xData(videoPath, dvf);
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }.start();
//
//            } else {
//                File file1 = new File(videourl);
//                int size1 = (int) file1.length();
//                Log.d("logresult", "B+" + videourl + "+size1=" + size1);
//                if (file1.exists() && size1 > 0) {
//                    Log.d("logresult", "h+" + videourl + "");
//                    uri = videourl;
//                } else {
//                    String dirName = Environment.getExternalStorageDirectory() + "/Dearbaobei/";
//                    File file = new File(dirName);
//                    if (!file.exists()) {
//                        file.mkdir();
//                    }
//                    String dvf = dirName + "video" + videoid + ".mp4";
//                    if (file1.exists()) {
//                        file1.delete();
//                    }
//                    xData(videoPath, dvf);
//                    uri = videoPath;
//
//                }
//            }
//        }


        videoView = (CommonVideoView) findViewById(R.id.common_videoView);
        videoView.start(uri);

        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.linearlayout);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
//

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            videoView.setFullScreen();
        } else {
            videoView.setNormalScreen();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }


//    private HttpHandler handler;
//    //下载视频缓存到本地
//
//    public void xData(String videoPath, final String dvf) {
//        HttpUtils http = new HttpUtils();
//        handler = http.download(videoPath, dvf, true, false,
//                new RequestCallBack<File>() {
//                    @SuppressWarnings("deprecation")
//                    @Override
//                    public void onStart() {
//                        Log.d("logresult", "开始");
//                    }
//
//                    @Override
//                    public void onLoading(long total, long current,
//                                          boolean isUploading) {
//                        super.onLoading(total, current, isUploading);
//                        Log.d("logresult", "开始+" + total + current);
//                    }
//
//                    @Override
//                    public void onSuccess(ResponseInfo<File> responseInfo) {
//                        PrefsTools.setStringPrefs(WidthMatchVideo.this, "dearvideo", "dearvideo" + videoid, dvf);
//                        Log.d("logresult", "ok");
//                    }
//
//                    @Override
//                    public void onFailure(HttpException error, String msg) {
//                        Log.d("logresult", "失败" + msg);
//                    }
//                });
//    }
}
