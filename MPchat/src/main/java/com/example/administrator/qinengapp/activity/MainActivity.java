package com.example.administrator.qinengapp.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.administrator.qinengapp.R;
import com.example.administrator.qinengapp.adapter.ContentPagerAdapter;
import com.example.administrator.qinengapp.base.BasePager;
import com.example.administrator.qinengapp.pager.HomePager;
import com.example.administrator.qinengapp.pager.InformationPager;
import com.example.administrator.qinengapp.pager.MyPager;
import com.example.administrator.qinengapp.pager.TransactionPager;

import java.util.ArrayList;

import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    private ViewPager vpCentent;
    private ArrayList<BasePager> pagers;
    private RadioGroup rgButtomTable;
    private RadioButton rbBottomHome;
    public Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initView();
        initDate();
    }

    private void initDate() {
        pagers = new ArrayList<BasePager>();
        pagers.add(new HomePager(this,getSupportFragmentManager()));
        pagers.add(new TransactionPager(this));
        pagers.add(new InformationPager(this));
        pagers.add(new MyPager(this));

        vpCentent.setAdapter(new ContentPagerAdapter(pagers));
        vpCentent.addOnPageChangeListener(new ContentPageChangeListener());

        pagers.get(0).initDate();
        rgButtomTable.check(R.id.home);

        rgButtomTable.setOnCheckedChangeListener(new BottomCheckedChangeListener());

    }

    private void initView() {
        vpCentent = (ViewPager) findViewById(R.id.vp_centent);
        rgButtomTable = (RadioGroup) findViewById(R.id.rg_buttom_table);
        rbBottomHome = (RadioButton) findViewById(R.id.home);
    }

    class ContentPageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            pagers.get(position).initDate();
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

    class BottomCheckedChangeListener implements RadioGroup.OnCheckedChangeListener {

        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch (checkedId) {
                case R.id.home:

                    vpCentent.setCurrentItem(0, false);

                    break;

                case R.id.transaction:

                    vpCentent.setCurrentItem(1, false);

                    break;

                case R.id.information:

                    vpCentent.setCurrentItem(2, false);

                    break;

                case R.id.main:

                    vpCentent.setCurrentItem(3, false);

                    break;

                default:
                    break;
            }
        }
    }
}
