package cn.evun.smallvediodemo;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.TextView;

import mabeijianxi.camera.LocalMediaCompress;
import mabeijianxi.camera.MediaRecorderActivity;
import mabeijianxi.camera.model.AutoVBRMode;
import mabeijianxi.camera.model.LocalMediaConfig;
import mabeijianxi.camera.model.MediaRecorderConfig;
import mabeijianxi.camera.model.OnlyCompressOverBean;

public class MainActivity extends Activity {

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.tv);
        setText();
    }

    private void setText() {
        String originText = "#重磅消息#近日谷歌放出Android N的第二个开发者预览版(Developer Preview)";

        String effect1 = "<font color='#FF0000'>#重磅消息#</font> <br> 近日谷歌放出Android " +
                "N的第二个开发者预览版<a href='http://developer.android.com/index.html'>(Developer Preview)</a>";

        String effect2 = "<font color='#303F9F'>#重磅消息#</font> 近日谷歌放出Android " +
                "N的第二个开发者预览版<a href='http://developer.android.com/index.html'>(Developer Preview)</a>";
        StringBuilder sb = new StringBuilder(originText);
        sb.append("<br><br><br><br>");
        sb.append(effect1);
        sb.append("<br><br><br><br>");
        sb.append(effect2);
        textView.setText(Html.fromHtml(sb.toString()));
        textView.setMovementMethod(LinkMovementMethod.getInstance());
        textView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {

                int maxHeight = textView.getMeasuredHeight();
                int height = textView.getHeight();
                float v = px2dp(height);
                System.out.println(maxHeight+"-max"+"==="+"height-"+height+"v---"+v);
                textView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
        textView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {

            }
        });
        textView.getViewTreeObserver().addOnGlobalFocusChangeListener(new ViewTreeObserver.OnGlobalFocusChangeListener() {
            @Override
            public void onGlobalFocusChanged(View oldFocus, View newFocus) {

            }
        });

    }
    public float px2dp(int px){
        DisplayMetrics displayMetrics = this.getResources().getDisplayMetrics();
        return px/displayMetrics.density;

    }

    public void onClick(View v){
        switch (v.getId()){
            case R.id.bt:
                //录制视频
                MediaRecorderConfig config = new MediaRecorderConfig.Buidler()
                        .doH264Compress(new AutoVBRMode())
                        .setMediaBitrateConfig(new AutoVBRMode())
                        .smallVideoHeight(480)
                        .smallVideoWidth(320)
                        .recordTimeMax(10*1000)
                        .maxFrameRate(20)
                        .captureThumbnailsTime(1)
                        .recordTimeMin(1000).build();

                MediaRecorderActivity.goSmallVideoRecorder(MainActivity.this,SendSmallVideoActivity.class.getName() , config);

                LocalMediaConfig.Buidler buidler = new LocalMediaConfig.Buidler();
//                final LocalMediaConfig config1 = buidler
//                        .setVideoPath(path)
//                        .captureThumbnailsTime(1)
//                        .doH264Compress(new AutoVBRMode())
//                        .setFramerate(15)
//                        .build();
//                OnlyCompressOverBean onlyCompressOverBean = new LocalMediaCompress(config1).startCompress();
                break;
        }
    }
}
