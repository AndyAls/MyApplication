package com.example.administrator.qinengapp.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.qinengapp.base.BasePager;
import com.example.administrator.qinengapp.pager.HomePager;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/12/6.
 */
public class ContentPagerAdapter extends PagerAdapter {


    private final ArrayList<BasePager> pagers;

    public ContentPagerAdapter(ArrayList<BasePager> pagers) {
        this.pagers=pagers;
    }

    @Override
    public int getCount() {
        return pagers.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        BasePager basePager = pagers.get(position);
        View view = basePager.mView;
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }


    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}
