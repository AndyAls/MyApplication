package com.example.okhttpdemo;

import android.app.ActivityManager;
import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.cache.MemoryCache;
import com.bumptech.glide.module.GlideModule;

/**
 * Created by Shuai.Li13 on 2017/10/25.
 */

public class MyGlideMoudel implements GlideModule {
    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        ActivityManager  manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo info = new ActivityManager.MemoryInfo();
        manager.getMemoryInfo(info);
        builder.setDecodeFormat(info.lowMemory?DecodeFormat.PREFER_RGB_565:DecodeFormat.PREFER_ARGB_8888);
    }

    @Override
    public void registerComponents(Context context, Glide glide) {
    }
}
