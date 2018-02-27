package cn.evun.opengl;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;

import java.util.Locale;

/**
 * Created by Shuai.Li13 on 2017/9/19.
 */

public class MyActivity extends Activity {

    private Button button;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_main);

        ChooseLan();
        setOrientation();
        button = new Button(this);
    }

    //横竖屏切换
    int orientation;
    private void setOrientation() {

       orientation = getResources().getConfiguration().orientation;
        button.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               switch (orientation){
                   case Configuration.ORIENTATION_LANDSCAPE://横屏
                       orientation = Configuration.ORIENTATION_PORTRAIT;
                       break;
                   case Configuration.ORIENTATION_PORTRAIT:
                       orientation=Configuration.ORIENTATION_LANDSCAPE;
                       break;
               }

               Resources resources = getResources();
               Configuration config = getResources().getConfiguration();
               config.orientation=orientation;
               DisplayMetrics displayMetrics = resources.getDisplayMetrics();
               resources.updateConfiguration(config,displayMetrics);

           }
       });
    }

    private void ChooseLan() {

        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("请选择语言")
                .setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, new String[]{"中文,English"}), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //获取系统配置的Locale信息
                        Locale locale = getResources().getConfiguration().locale;
                        switch (which) {

                            case 0:
                                //在设置语言界面保存设置的语言环境,在用户重新进入应用时使应用处于设置后的
                                SpUtils.putParam(MyActivity.this, "language", "chinese");
                                //Locale赋值为当前设置的语言环境
                                locale = Locale.SIMPLIFIED_CHINESE;
                                break;
                            case 1:
                                SpUtils.putParam(MyActivity.this, "language", "english");
                                locale = Locale.ENGLISH;
                                break;

                        }
                        //设置语言环境
                        setLan(locale);
                        dialog.dismiss();
                    }
                }).show();
    }

    private void setLan(Locale locale) {

        // 获得res资源对象
        Resources resources = getResources();
        // 获得配置信息对象
        Configuration config = resources.getConfiguration();
        // 获得屏幕参数：主要是分辨率，像素等。
        DisplayMetrics dm = resources.getDisplayMetrics();
        // 语言
        config.locale = locale;
        resources.updateConfiguration(config, dm);

        // 刷新activity才能马上奏效
        startActivity(new Intent().setClass(this,
                MyActivity.class));
        finish();
    }
}


