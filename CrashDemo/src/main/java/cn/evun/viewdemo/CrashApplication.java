package cn.evun.viewdemo;

import android.app.Application;

/**
 * Created by Shuai.Li13 on 2017/5/2.
 */

public class CrashApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        CrashHandler crashHandler = CrashHandler.getInstance();
        crashHandler.init(getApplicationContext());
    }
}
