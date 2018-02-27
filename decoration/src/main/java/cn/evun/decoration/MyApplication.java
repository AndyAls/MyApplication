package cn.evun.decoration;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;

/**
 * Created by Shuai.Li13 on 2017/2/23.
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        if (LeakCanary.isInAnalyzerProcess(this)){

            return;
        }
        LeakCanary.install(this);
    }
}
