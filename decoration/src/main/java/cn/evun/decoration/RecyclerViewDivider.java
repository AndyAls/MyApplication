package cn.evun.decoration;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Shuai.Li13 on 2017/2/23.
 */

public class RecyclerViewDivider extends RecyclerView.ItemDecoration {


    private final int mOriention;
    private final int[] attrs = new int[]{android.R.attr.listDivider};
    private Drawable mDrawable;
    private int mDrividerHeight;
    private Paint mPaint;


    public RecyclerViewDivider(Context context, int oriention) {

        if (oriention != LinearLayoutManager.VERTICAL && oriention != LinearLayoutManager.HORIZONTAL) {

            throw new IllegalArgumentException("请输入正确的参数");
        }
        mOriention = oriention;
        TypedArray typedArray = context.obtainStyledAttributes(attrs);
        mDrawable = typedArray.getDrawable(0);
        typedArray.recycle();


    }

    public RecyclerViewDivider(Context context, int oriention, int drawableId) {
        this(context, oriention);
        mDrawable = ContextCompat.getDrawable(context, drawableId);
        mDrividerHeight = mDrawable.getIntrinsicHeight();

    }

    public RecyclerViewDivider(Context context, int oriention, int drividerHeight, int drividerColor) {

        this(context, oriention);
        mDrividerHeight = drividerHeight;
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(drividerColor);
        mPaint.setStyle(Paint.Style.FILL);

    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.set(0, 0, 0, mDrividerHeight);
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        if (mOriention == LinearLayoutManager.VERTICAL) {
            drawVertical(c, parent);
        } else {
            drawHorizontal(c, parent);
        }
    }

    private void drawHorizontal(Canvas c, RecyclerView parent) {

        int left = parent.getPaddingLeft();
        int right = parent.getMeasuredWidth()-parent.getPaddingRight();
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = parent.getChildAt(i);
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) childAt.getLayoutParams();
            int top = childAt.getBottom()+layoutParams.bottomMargin;
            int bottom = top+mDrividerHeight;
            if (mDrawable!=null){

                mDrawable.setBounds(left,top,right,bottom);
                mDrawable.draw(c);
            }
            if (mPaint!=null){

                c.drawRect(left,top,right,bottom,mPaint);
            }
        }
    }

    private void drawVertical(Canvas c, RecyclerView parent) {

        int top = parent.getPaddingTop();
        int bottom = parent.getMeasuredHeight()-parent.getPaddingBottom();
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = parent.getChildAt(i);
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) childAt.getLayoutParams();
            int left = childAt.getRight()+layoutParams.rightMargin;
            int right = left+mDrividerHeight;
            if (mDrawable!=null){
                mDrawable.setBounds(left,top,right,bottom);
            }
            if (mPaint!=null){

                c.drawRect(left,top,right,bottom,mPaint);
            }
        }


    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
    }
}
