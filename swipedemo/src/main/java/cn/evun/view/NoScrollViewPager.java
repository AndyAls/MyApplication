package cn.evun.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 */

public class NoScrollViewPager extends ViewPager {

    private boolean canScroll =true;

    public NoScrollViewPager(Context context) {
        super(context);
    }

    public NoScrollViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return this.canScroll&&super.onTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return this.canScroll&&super.onInterceptTouchEvent(ev);
    }

    public void isCanScroll(boolean b){
        this.canScroll =b;
    }
}
