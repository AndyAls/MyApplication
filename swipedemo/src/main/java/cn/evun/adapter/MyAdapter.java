package cn.evun.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;

import java.util.List;

/**
 * Created by Shuai.Li13 on 2017/7/5.
 */
public class MyAdapter extends FragmentPagerAdapter {
    private final List<Fragment> mList;

    public MyAdapter(FragmentManager supportFragmentManager, List<Fragment> frgList) {
        super(supportFragmentManager);
        this.mList=frgList;

    }

    @Override
    public Fragment getItem(int position) {
        return mList.get(position);
    }

    @Override
    public int getCount() {
        return mList.size();
    }
}
