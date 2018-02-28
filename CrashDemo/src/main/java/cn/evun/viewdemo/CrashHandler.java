package cn.evun.viewdemo;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.os.Looper;
import android.os.Process;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by Shuai.Li13 on 2017/5/2.
 */

public class CrashHandler implements Thread.UncaughtExceptionHandler {
    private static final String TAG = "-----";
    private Thread.UncaughtExceptionHandler mDefaultHandler;
    private static CrashHandler INSTANCE = new CrashHandler();
    private Context context;
    private Map<String,String> infos = new HashMap<>();
    private CrashHandler(){};
    public static CrashHandler getInstance(){
        return INSTANCE;
    }

    public void init(Context context){
        this.context=context;
        mDefaultHandler=Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }
    @Override
    public void uncaughtException(Thread t, Throwable e) {

        if (!handleException(e)&&mDefaultHandler!=null){
            mDefaultHandler.uncaughtException(t,e);
        }else {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
            Process.killProcess(Process.myPid());
            System.exit(1);
        }
    }
    //自定义异常处理,收集错误信息,发送错误报告
    private boolean handleException(Throwable ex){
        if (ex==null){
            return false;
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                Looper.prepare();
                Toast.makeText(context,"抱歉 程序异常,即将退出",Toast.LENGTH_SHORT).show();
                Looper.loop();
            }
        }).start();
        CollectDeviceInfo(context);
        saveCrashInfo2File(ex);
        return true;
    }

    private String saveCrashInfo2File(Throwable ex) {
        StringBuffer sb = new StringBuffer();
        Set<Map.Entry<String, String>> entries = infos.entrySet();
        for (Map.Entry<String,String> entry:entries) {
            String key = entry.getKey();
            String value = entry.getValue();
            sb.append(key+"="+value+"\n");
        }
        Log.e(TAG, "sb: =="+sb);
        Writer writer = new StringWriter();
        PrintWriter printWriter=new PrintWriter(writer);
        ex.printStackTrace(printWriter);
        Throwable cause = ex.getCause();
        while (cause!=null){
            cause.printStackTrace(printWriter);
            cause=cause.getCause();
        }
        printWriter.close();
        String result = writer.toString();
        sb.append(result);
        try {
            long timeStamp=System.currentTimeMillis();
            String fileName=timeStamp+".txt";
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
                String path=Environment.getExternalStorageDirectory()+File.separator+"lishuai/";
                File dir = new File(path);
                if (!dir.exists()){
                    dir.mkdirs();
                }
                FileOutputStream fos = new FileOutputStream(path+fileName);
                fos.write(sb.toString().getBytes());
                fos.close();
            }
            return fileName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //收集错误的设备信息
    private void CollectDeviceInfo(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo pi = packageManager.getPackageInfo(context.getPackageName(), PackageManager.GET_ACTIVITIES);
            if (pi!=null){
                String versionName=pi.versionName==null?"null":pi.versionName;
                String versionCode=pi.versionCode+"";
                infos.put("versionName" ,versionName);
                infos.put("versionCode",versionCode);
                Log.e(TAG, "versionName==="+versionName+"versionCode"+versionCode);
            }
        } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
        }
        try {
            Field[] fields = Build.class.getDeclaredFields();
            for (Field filed:fields){
                filed.setAccessible(true);
                infos.put(filed.getName(),filed.get(null).toString());
                Log.e(TAG, "filed.getName()== "+filed.getName()+"filed.get(null).toString()"+filed.get(null).toString());
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
