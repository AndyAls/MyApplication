package cn.evun.viewdemo;

import android.graphics.drawable.Drawable;

/**
 * Created by Shuai.Li13 on 2017/4/5.
 */

public class ClassInfo {

    private String appName;
    private String packageName;
    private String className;
    private Drawable icon;

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public Drawable getIcon() {
        return icon;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }
}
