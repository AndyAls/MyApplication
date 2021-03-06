package com.blankj.androidutilcode.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.blankj.androidutilcode.R;
import com.blankj.androidutilcode.base.BaseBackActivity;

/**
 * <pre>
 *     desc  : Bar工具类Demo
 * </pre>
 */
public class BarActivity extends BaseBackActivity {

    public static void start(Context context) {
        Intent starter = new Intent(context, BarActivity.class);
        context.startActivity(starter);
    }

    @Override
    public void initData(Bundle bundle) {

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_bar;
    }

    @Override
    public void initView(Bundle savedInstanceState, View view) {
        getToolBar().setTitle(getString(R.string.demo_bar));

        findViewById(R.id.btn_status_bar_color).setOnClickListener(this);
        findViewById(R.id.btn_status_bar_alpha).setOnClickListener(this);
        findViewById(R.id.btn_status_bar_image_view).setOnClickListener(this);
        findViewById(R.id.btn_status_bar_fragment).setOnClickListener(this);
        findViewById(R.id.btn_status_bar_swipe_back).setOnClickListener(this);
        findViewById(R.id.btn_status_bar_drawer).setOnClickListener(this);
        findViewById(R.id.btn_nav_bar).setOnClickListener(this);
    }

    @Override
    public void doBusiness(Context context) {

    }

    @Override
    public void onWidgetClick(View view) {
        switch (view.getId()) {
            case R.id.btn_status_bar_color:
                BarStatusColorActivity.start(this);
                break;
            case R.id.btn_status_bar_alpha:
                BarStatusAlphaActivity.start(this);
                break;
            case R.id.btn_status_bar_image_view:
                BarStatusImageViewActivity.start(this);
                break;
            case R.id.btn_status_bar_fragment:
                BarStatusFragmentActivity.start(this);
                break;
            case R.id.btn_status_bar_swipe_back:
                BarStatusSwipeBackActivity.start(this);
                break;
            case R.id.btn_status_bar_drawer:
                BarStatusDrawerActivity.start(this);
                break;
            case R.id.btn_nav_bar:
                BarNavActivity.start(this);
                break;
        }
    }
}
