package cn.evun.designdemo;

import android.app.Application;

import com.blankj.utilcode.util.Utils;


/**
 * Created by Shuai.Li13 on 2017/8/4.
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Utils.init(this);
    }
}
