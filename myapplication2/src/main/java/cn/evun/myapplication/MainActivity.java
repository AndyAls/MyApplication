package cn.evun.myapplication;

import android.annotation.SuppressLint;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;

import com.tencent.smtt.sdk.WebSettings;

public class MainActivity extends AppCompatActivity {

    private X5WebView x5WebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        x5WebView = (X5WebView) findViewById(R.id.webview);
        initView();
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void initView() {
        WebSettings settings = x5WebView.getSettings();
        settings.setJavaScriptEnabled(true);
        x5WebView.loadUrl("http://www.igap.cc/geely/bocmobilereport/bocMobileAndroid.htm");
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode==KeyEvent.KEYCODE_BACK){
            if (x5WebView.canGoBack()){
                x5WebView.goBack();
                return false;
            }else {
                finish();
                return false;
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
