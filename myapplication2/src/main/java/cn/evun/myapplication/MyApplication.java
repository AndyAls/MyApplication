package cn.evun.myapplication;

import android.app.Application;

import com.tencent.smtt.sdk.QbSdk;

/**
 * Created by Shuai.Li13 on 2017/6/7.
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        QbSdk.initX5Environment(getApplicationContext(),null);
    }
}
