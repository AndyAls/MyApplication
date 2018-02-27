package com.example.administrator.qinengapp.pager;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.qinengapp.R;
import com.example.administrator.qinengapp.base.BasePager;
import com.example.administrator.qinengapp.view.LineChartFragment;

/**
 * Created by Administrator on 2016/12/5.
 */
public class HomePager extends BasePager {

    private final FragmentManager manager;

    public HomePager(Context context, FragmentManager supportFragmentManager) {

        super(context);
        this.manager=supportFragmentManager;
    }

    @Override
    public void initDate() {
        View view = View.inflate(context, R.layout.fragment_home, null);

        flBasepagerContent.removeAllViews();
        flBasepagerContent.addView(view);

    }

}
