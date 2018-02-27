package cn.evun.netscrolldemo;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private LinearLayout frameLayout;
    private List<String> mDatas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData(1);
    }

    private void initData(int pager) {
        mDatas=new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            mDatas.add("pager"+pager+"第"+i+"个item");
        }
    }

    private void initView() {

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        frameLayout = (LinearLayout) findViewById(R.id.fl);
        toolbar.setTitle("闹着玩");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        for (int i = 0; i < 20; i++) {
            tabLayout.addTab(tabLayout.newTab().setText("标题"+i));
        }
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                initData(tab.getPosition()+1);
                setView();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    private void setView() {


        frameLayout.removeAllViews();
        for (int i = 0; i <mDatas.size() ; i++) {

            TextView tv = new TextView(this);
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,100);
            tv.setLayoutParams(params);
            tv.setText(mDatas.get(i));
            frameLayout.addView(tv);
        }
    }

}
