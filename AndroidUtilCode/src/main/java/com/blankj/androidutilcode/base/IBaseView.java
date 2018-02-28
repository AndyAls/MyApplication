package com.blankj.androidutilcode.base;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

/**
 * <pre>
 *     desc  :
 * </pre>
 */
interface IBaseView {

    /**
     * 初始化数据
     *
     * @param bundle 传递过来的bundle
     */
    void initData(final Bundle bundle);

    /**
     * 绑定布局
     *
     * @return 布局Id
     */
    int bindLayout();

    /**
     * 初始化view
     */
    void initView(final Bundle savedInstanceState, final View view);

    /**
     * 业务操作
     *
     * @param context 上下文
     */
    void doBusiness(final Context context);

    /**
     * 视图点击事件
     *
     * @param view 视图
     */
    void onWidgetClick(final View view);
}
