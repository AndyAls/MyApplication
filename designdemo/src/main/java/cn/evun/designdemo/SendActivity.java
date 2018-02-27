package cn.evun.designdemo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

/**
 * Created by Shuai.Li13 on 2017/8/4.
 */

public class SendActivity extends BaseBackActivity{

    public static void start(Context context){
        Intent intent = new Intent(context,SendActivity.class);
        context.startActivity(intent);
    }
    @Override
    public void initData(Bundle bundle) {

    }

    @Override
    public int bindLayout() {
        return R.layout.send_activity;
    }

    @Override
    public void initView(Bundle savedInstanceState, View view) {

    }

    @Override
    public void doBusiness(Context context) {

    }

    @Override
    public void onWidgetClick(View view) {

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
