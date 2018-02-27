package com.example.administrator.qinengapp.base;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.administrator.qinengapp.R;

/**
 * Created by Administrator on 2016/12/2.
 */

public class BasePager {

    protected Context context;
    protected FrameLayout flBasepagerContent;
    public View mView;

    public BasePager(Context context) {
        this.context = context;
        mView = initView();
    }

    public View initView() {
        View view =  View.inflate(context, R.layout.basepager, null);
        initID(view);
        return view;
    }

    private void initID(View view) {
        flBasepagerContent = (FrameLayout) view.findViewById(R.id.fl_basepager_content);
    }

    public void initDate(){}

}
