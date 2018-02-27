package cn.evun.recycleviewdemo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;

/**
 * Created by Shuai.Li13 on 2017/2/14.
 */

public class DeviderItemDecoration extends RecyclerView.ItemDecoration {


    private int mOriention = LinearLayoutManager.VERTICAL;
    private int itemSize = 1;
    private Paint paint;

    public DeviderItemDecoration(Context context, int oriention) {

        this.mOriention = oriention;
        if (oriention!=LinearLayoutManager.HORIZONTAL&&oriention!=LinearLayoutManager.VERTICAL){

            throw new IllegalArgumentException("请输入正确的参数");
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, int itemPosition, RecyclerView parent) {
        super.getItemOffsets(outRect, itemPosition, parent);
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent) {
        super.onDraw(c, parent);
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent) {
        super.onDrawOver(c, parent);
    }
}
