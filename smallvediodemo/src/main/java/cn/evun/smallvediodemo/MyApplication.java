package cn.evun.smallvediodemo;

import android.app.Application;
import android.os.Environment;

import java.io.File;

import mabeijianxi.camera.VCamera;
import mabeijianxi.camera.util.DeviceUtils;

/**
 * Created by Shuai.Li13 on 2017/5/4.
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        initVedio();
    }

    private void initVedio() {

        File dicm = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
        if (DeviceUtils.isZte()){

            if (dicm.exists()){

                VCamera.setVideoCachePath(dicm+"/mabeijianxi/");
            }else {
                VCamera.setVideoCachePath(dicm.getPath().replace("/sdcard/","/sdcard-ext/")+"/mabeijianxi/");
            }
        }else {
            VCamera.setVideoCachePath(dicm+"/mabeijianxi/");
        }
        VCamera.setDebugMode(true);
        VCamera.initialize(getApplicationContext());
    }
}
