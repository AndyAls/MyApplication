package com.blankj.androidutilcode.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;

/**
 * <pre>
 *     desc  : Activity基类
 * </pre>
 */
public abstract class BaseActivity extends AppCompatActivity
        implements IBaseView, View.OnClickListener {

    /**
     * 当前Activity渲染的视图View
     */
    protected View contentView;
    /**
     * 上次点击时间
     */
    private long lastClick = 0;

    protected BaseActivity mActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = this;
        setBaseView();
        Bundle bundle = getIntent().getExtras();
        initData(bundle);
        initView(savedInstanceState, contentView);
        doBusiness(this);
    }

    protected void setBaseView() {
        setContentView(contentView = LayoutInflater.from(this).inflate(bindLayout(), null));
    }

    /**
     * 判断是否快速点击
     *
     * @return {@code true}: 是<br>{@code false}: 否
     */
    private boolean isFastClick() {
        long now = System.currentTimeMillis();
        if (now - lastClick >= 200) {
            lastClick = now;
            return false;
        }
        return true;
    }

    @Override
    public void onClick(final View view) {
        if (!isFastClick()) onWidgetClick(view);
    }
}
