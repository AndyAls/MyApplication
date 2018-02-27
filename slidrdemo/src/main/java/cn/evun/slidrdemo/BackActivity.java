package cn.evun.slidrdemo;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import cn.evun.slidrdemo.base.BaseLeftBackView;

/**
 * Created by Shuai.Li13 on 2017/8/9.
 */

public class BackActivity extends BaseLeftBackView{

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private List<String> mDatas=new ArrayList<>();




    @Override
    public void initData(Bundle bundle) {

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_back;
    }

    @Override
    public void initView(Bundle savedInstanceState, View view) {


        for (int i = 0; i < 20; i++) {
            mDatas.add("Tab"+i);
        }
        getToolBar().setTitle("李帅");
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        tabLayout.setupWithViewPager(viewPager);
        MyAdapter adapter = new MyAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
    }

    @Override
    public void doBusiness(Context context) {

    }

    @Override
    public void onWidgetClick(View view) {

    }


    private class MyAdapter extends FragmentPagerAdapter{

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            return MyFragment.newInstance(mDatas.get(position));
        }

        @Override
        public int getCount() {
            return mDatas.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mDatas.get(position);
        }
    }
}
