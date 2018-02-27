package cn.evun.swiperecyclerview;

import android.content.Context;
import android.content.IntentFilter;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import cn.evun.swiperecyclerview.interf.OnListViewListener;

/**
 * Created by Shuai.Li13 on 2017/7/10.
 */

public class MyListView extends ListView {
    private float downX=0;
    private float downY=0;
    long time=0;
    private OnListViewListener mListener;

    public MyListView(Context context) {
        super(context);
    }

    public MyListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    public boolean onTouchEvent(MotionEvent ev) {

        if (ev.getAction()==MotionEvent.ACTION_DOWN){
            time=System.currentTimeMillis();
            downX= ev.getX();
            downY=ev.getY();
        }else if (ev.getAction()==MotionEvent.ACTION_MOVE){

            float v = downX - ev.getX();
            float v1 = downY - ev.getY();
            if (Math.abs(v1)>10){
                time=0;
            }
        }
        else if (ev.getAction()==MotionEvent.ACTION_UP){
            long leadTime=System.currentTimeMillis()-time;
            if (leadTime<500){
                Log.e("----","onInterceptTouchEvent");
                getParent().requestDisallowInterceptTouchEvent(false);
                this.setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        if (mListener!=null){
                            mListener.onItemClick(parent,view,position,id);
                            Log.e("----", "onItemClick: " );
                        }
                    }
                });
                return true;
            }else {
                this.setOnScrollListener(new OnScrollListener() {
                    @Override
                    public void onScrollStateChanged(AbsListView view, int scrollState) {

                    }

                    @Override
                    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                        if (mListener!=null){
                            mListener.onScroll(view,firstVisibleItem,visibleItemCount,totalItemCount);
                        }
                    }
                });
                return true;
            }
        }
        return super.onTouchEvent(ev);
    }

    public void setOnListViewListener(OnListViewListener listener){
        this.mListener=listener;
    }
}
