package cn.evun.opengl;

import android.app.Application;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.text.TextUtils;
import android.util.DisplayMetrics;

import java.util.Locale;

/**
 * Created by Shuai.Li13 on 2017/9/19.
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        setLan();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        setLan();
    }

    /**设置语言信息*/
    private void setLan() {

        Locale locale ;
        String language = SpUtils.getStringParam(this, "language", "");
        if (TextUtils.isEmpty(language)){
            //用户没有设置,赋值为系统语言环境
            locale=getResources().getConfiguration().locale;
        }else {
            //用户设置过
            switch (language){
                case "chinese":
                    locale=Locale.SIMPLIFIED_CHINESE;
                    break;
                case "english":
                    locale=Locale.ENGLISH;
                    break;
                default:
                    locale=getResources().getConfiguration().locale;
                    break;
            }
        }

        // 获得res资源对象
        Resources resources = getResources();
        // 获得配置信息对象
        Configuration config = resources.getConfiguration();
        // 获得屏幕参数：主要是分辨率，像素等。
        DisplayMetrics dm = resources.getDisplayMetrics();
        // 语言
        config.locale = locale;
        //更新配置信息
        resources.updateConfiguration(config, dm);
    }

}
