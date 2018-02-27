package com.fxiaoke.plugin.crm;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fxiaoke.plugin.crm.stickycalendar.StickyCalendar;
import com.fxiaoke.plugin.crm.stickycalendar.utils.DateBean;
import com.fxiaoke.plugin.crm.stickycalendar.utils.SpecialCalendar;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements StickyCalendar.IStickyCalendarListener {

    private StickyCalendar mCalendar;
    static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    static List list = new ArrayList();
    static {
        try {
            long time = format.parse("2016-8-11").getTime();
            list.add(format.parse("2016-8-11").getTime());
            list.add(format.parse("2016-8-12").getTime());
            list.add(format.parse("2016-8-14").getTime());
            list.add(format.parse("2016-8-16").getTime());
            list.add(format.parse("2016-8-19").getTime());
            list.add(format.parse("2016-8-20").getTime());
            list.add(format.parse("2016-8-21").getTime());
            list.add(format.parse("2016-8-25").getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private ListView list1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mCalendar = (StickyCalendar) findViewById(R.id.calendar);
        addExtraViews();
        mCalendar.setCalendarListener(this);
        upodateEventDates();
//        list1 = (ListView) findViewById(R.id.list);

    }

    private void initListview() {
        list1 = new ListView(this);
        list1.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return 30;
            }

            @Override
            public Object getItem(int position) {
                return null;
            }

            @Override
            public long getItemId(int position) {
                return 0;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {

                View view = View.inflate(parent.getContext(),android.R.layout.simple_list_item_1,null);
                TextView tv = (TextView) view.findViewById(android.R.id.text1);
                tv.setText("item"+position);
                return view;
            }
        });
    }

    private void addExtraViews(){
//        TextView tv = new TextView(this);
//        tv.setTextSize(32);
//        tv.setGravity(Gravity.CENTER);
//        tv.setText("这里可以加入自己的Fragment或者其它的View");
//        initListview();
        View view = View.inflate(this,R.layout.item_tab_new,null);
        TextView tv= (TextView) view.findViewById(R.id.tv);
        ListView ls = (ListView) view.findViewById(R.id.ls);
        ls.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return 30;
            }

            @Override
            public Object getItem(int position) {
                return null;
            }

            @Override
            public long getItemId(int position) {
                return 0;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = View.inflate(parent.getContext(),android.R.layout.simple_list_item_1,null);
                TextView tv = (TextView) view.findViewById(android.R.id.text1);
                tv.setText("item"+position);
                return view;
            }
        });
        tv.setText("中华人民共和国");
        LinearLayout ll = (LinearLayout) findViewById(mCalendar.getFragId());
        if (view!=null){
            ll.addView(view);
//            ,new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        }

    }


    @Override
    public void onChooseDate(Date date) {
        System.out.println("日期:"+new SimpleDateFormat("yyyy-MM-dd").format(date)+" 被点击");
        String s1 = new SimpleDateFormat("yyyyMMdd").format(date);
        String s2 = new SimpleDateFormat("yyyyMMdd").format(System.currentTimeMillis());
        int i = Integer.parseInt(s1);
        int i1 = Integer.parseInt(s2);
    }

    @Override
    public void onPageSelected(SpecialCalendar.CalendarType type, int position, Date date) {
        System.out.println(type.name()+"选择:" + position + " "+new SimpleDateFormat("yyyy-MM-dd").format(date));
        upodateEventDates();
    }

    private void upodateEventDates(){
        mCalendar.postDelayed(new Runnable() {
            @Override
            public void run() {
                mCalendar.updateEventDates(list);
            }
        },1000);
    }
}
