package com.example.administrator.qinengapp.pager;

import android.view.Gravity;
import android.widget.TextView;

import com.example.administrator.qinengapp.activity.MainActivity;
import com.example.administrator.qinengapp.base.BasePager;

/**
 * Created by Administrator on 2016/12/6.
 */
public class InformationPager extends BasePager {
    public InformationPager(MainActivity mainActivity) {
        super(mainActivity);
    }

    @Override
    public void initDate() {
        TextView textView = new TextView(context);
        textView.setText("能源资讯");
        textView.setGravity(Gravity.CENTER);
        flBasepagerContent.removeAllViews();
        flBasepagerContent.addView(textView);

    }

}
