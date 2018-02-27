package cn.evun;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.evun.adapter.MyAdapter;
import cn.evun.frgment.ClientFragment;
import cn.evun.frgment.ContactFragment;
import cn.evun.swipedemo.R;
import cn.evun.view.NoScrollViewPager;

/**
 * Created by Shuai.Li13 on 2017/7/5.
 */

public class MyActitivty extends AppCompatActivity {

    private NoScrollViewPager viewPager;
    private TextView tvClient;
    private TextView tvContact;
    private View client;
    private View contact;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.my_activity);
        initView();
        initViewPager();
    }

    private void initViewPager() {

        List<Fragment> frgList=new ArrayList<>();
        ClientFragment clientFragment = new ClientFragment();
        ContactFragment contactFragment=new ContactFragment();
        frgList.add(clientFragment);
        frgList.add(contactFragment);
        viewPager.setAdapter(new MyAdapter(getSupportFragmentManager(),frgList));
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                switch (position){
                    case 0:
                        tvClient.setSelected(true);
                        tvClient.setTextColor(getColor(R.color.green));
                        client.setBackgroundResource(R.color.green);
                        tvContact.setSelected(false);
                        contact.setBackground(null);

                        break;
                    case 1:
                        tvContact.setSelected(true);
                        contact.setBackgroundResource(R.color.green);
                        tvClient.setSelected(false);
                        client.setBackground(null);
                        break;
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        viewPager.setCurrentItem(0);
        viewPager.setOffscreenPageLimit(0);
        viewPager.isCanScroll(false);
    }

    private void initView() {
        viewPager = (NoScrollViewPager) findViewById(R.id.viewpager);
        tvClient = (TextView) findViewById(R.id.tv_client);
        tvContact = (TextView) findViewById(R.id.tv_contact);
        client = findViewById(R.id.client);
        contact = findViewById(R.id.contact);
    }

    public void onClick(View view) {

        switch (view.getId()){
            case R.id.ll_client:
                viewPager.setCurrentItem(0);
                tvClient.setSelected(true);
                client.setBackgroundResource(R.color.green);
                tvContact.setSelected(false);
                contact.setBackground(null);

                break;
            case R.id.ll_contact:
                viewPager.setCurrentItem(1);
                tvContact.setSelected(true);
                contact.setBackgroundResource(R.color.green);
                tvClient.setSelected(false);
                client.setBackground(null);
                break;
        }
    }
}
