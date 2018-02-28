package com.blankj.androidutilcode;

import com.blankj.utilcode.util.Utils;

import java.io.File;

/**
 * <pre>
 *     desc  :
 * </pre>
 */
public class Config {
    public static final String PKG      = "com.blankj.androidutilcode";
    public static final String TEST_PKG = "com.blankj.testinstall";
    public static final String GITHUB   = "https://github.com/AndyAls";
    public static final String BLOG     = "http://blog.csdn.net/Andy_l1";


    private static String testApkPath;

    public static String getTestApkPath() {
        if (testApkPath == null)
            testApkPath = Utils.getContext().getCacheDir().getAbsolutePath() + File.separatorChar + "apk" + File.separatorChar + "test_install.apk";
        return testApkPath;
    }
}