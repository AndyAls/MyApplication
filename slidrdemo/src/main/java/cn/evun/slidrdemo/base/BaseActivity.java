package cn.evun.slidrdemo.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;

/**
 * Created by Shuai.Li13 on 2017/8/9.
 */

public abstract class BaseActivity extends AppCompatActivity implements IBaseView {

    protected View contentView;
    protected BaseActivity mActivity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mActivity=this;
        setBaseView();
        Bundle bundle = getIntent().getExtras();
        initView(savedInstanceState, contentView);
        doBusiness(this);
        initData(bundle);

    }

    protected void setBaseView() {

        setContentView(contentView= LayoutInflater.from(mActivity).inflate(bindLayout(),null));
    }


}
