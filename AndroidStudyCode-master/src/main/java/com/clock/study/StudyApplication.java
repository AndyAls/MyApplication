package com.clock.study;

import android.app.Application;

import com.clock.study.manager.FolderManager;
import com.clock.utils.crash.CrashExceptionHandler;

public class StudyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        configCollectCrashInfo();
    }

    /**
     * 配置奔溃信息的搜集
     */
    private void configCollectCrashInfo() {
        CrashExceptionHandler crashExceptionHandler = new CrashExceptionHandler(this, FolderManager.getCrashLogFolder());
        Thread.setDefaultUncaughtExceptionHandler(crashExceptionHandler);
    }
}
