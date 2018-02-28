package com.blankj.androidutilcode.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.blankj.androidutilcode.Config;
import com.blankj.androidutilcode.R;
import com.blankj.androidutilcode.base.BaseBackActivity;
import com.blankj.utilcode.util.ActivityUtils;

/**
 *     desc  : Activity工具类Demo
 */
public class ActivityActivity extends BaseBackActivity {

    private String imageActivityClassName;

    public static void start(Context context) {
        Intent starter = new Intent(context, ActivityActivity.class);
        context.startActivity(starter);
    }

    @Override
    public void initData(Bundle bundle) {
        imageActivityClassName = Config.PKG + ".activity.ImageActivity";
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_activity;
    }


    @Override
    public void initView(Bundle savedInstanceState, View view) {
        getToolBar().setTitle(getString(R.string.demo_activity));

        findViewById(R.id.btn_launch_image_activity).setOnClickListener(this);
        TextView tvAboutActivity = (TextView) findViewById(R.id.tv_about_activity);
        tvAboutActivity.setText("Is ImageActivity Exists: " + ActivityUtils.isActivityExists(Config.PKG, imageActivityClassName)
                + "\ngetLauncherActivity: " + ActivityUtils.getLauncherActivity(Config.PKG)
                + "\ngetTopActivity: " + ActivityUtils.getTopActivity()
        );
    }

    @Override
    public void doBusiness(Context context) {

    }

    @Override
    public void onWidgetClick(View view) {
        switch (view.getId()) {
            case R.id.btn_launch_image_activity:
                ActivityUtils.startActivity(Config.PKG, imageActivityClassName);
                break;
            default:
                break;
        }
    }


    @Override
    protected void onPause() {
        super.onPause();
        Log.e("---", "onPause: SendActivity");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("---", "onResume: SendActivity");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e("---", "onResume: SendActivity");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e("---", "onStop: SendActivity");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("---", "onDestroy: SendActivity");
    }
}