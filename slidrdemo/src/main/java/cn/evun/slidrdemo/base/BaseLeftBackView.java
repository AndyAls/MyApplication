package cn.evun.slidrdemo.base;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.r0adkll.slidr.Slidr;
import com.r0adkll.slidr.model.SlidrConfig;
import com.r0adkll.slidr.model.SlidrPosition;

import cn.evun.slidrdemo.R;

/**
 * Created by Shuai.Li13 on 2017/8/9.
 */

public abstract class BaseLeftBackView extends BaseActivity {


    private Toolbar toolbar;
    private FrameLayout fl;

    @Override
    protected void setBaseView() {

        contentView= LayoutInflater.from(mActivity).inflate(R.layout.base_left_back_activity,null);
        setContentView(contentView);

        SlidrConfig config=new SlidrConfig.Builder()
                .position(SlidrPosition.LEFT)
                .edge(true)
                .edgeSize(0.18f)
                .build();
        Slidr.attach(this,config);


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        fl = (FrameLayout) findViewById(R.id.fl);

        CollapsingToolbarLayout ctl= (CollapsingToolbarLayout) findViewById(R.id.ctl);
        toolbar.setTitleTextColor(getColor(R.color.white));
        ctl.setCollapsedTitleGravity(Gravity.RELATIVE_HORIZONTAL_GRAVITY_MASK);
        ctl.setExpandedTitleColor(Color.parseColor("#00ffffff"));
        ctl.setCollapsedTitleTextColor(getColor(R.color.white));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        fl.addView(LayoutInflater.from(mActivity).inflate(bindLayout(),fl,false));
    }

    public ActionBar getToolBar(){
        return getSupportActionBar();
    }
}
