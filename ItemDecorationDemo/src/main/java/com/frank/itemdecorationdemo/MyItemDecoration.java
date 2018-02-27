package com.frank.itemdecorationdemo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.nio.charset.CharacterCodingException;

/**
 * Created by Shuai.Li13 on 2017/10/18.
 */

class MyItemDecoration extends RecyclerView.ItemDecoration {
    private Context mContext;
    private Paint mPaint;
    private int mHeight;

    public MyItemDecoration(CustomActivity customActivity) {
        mContext=customActivity;
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.RED);
    }

    /**
     * @param outRect 宽高为0的矩阵
     * @param view 每个条目的itemView
     * @param parent 本身
     * @param state  状态
     *
     * <p> skjdnjskandjknfajdnf </p>
     *               <s>
     *               ssss    <s/>
     *               ccode
     *
     */
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.top=10;
        mHeight = 10;
        int position = parent.getChildAdapterPosition(view);

    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View view = parent.getChildAt(i);
            c.drawRect(view.getLeft(),view.getTop()-mHeight,parent.getWidth(),view.getTop(),mPaint);
        }
    }
    class Left{};
}

