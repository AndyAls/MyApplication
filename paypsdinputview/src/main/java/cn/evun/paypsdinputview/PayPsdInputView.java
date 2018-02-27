package cn.evun.paypsdinputview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.EditText;

/**
 * Created by Shuai.Li13 on 2017/5/27.
 */

public class PayPsdInputView extends EditText {

    private final Context mContext;
    private int maxCount=6;
    private int circleColor = Color.BLACK;
    private int bottomLineColor = Color.CYAN;
    private int radius = 10;
    private int divideLineWidth = 2;
    private int divideLineColor = Color.GRAY;
    private int rectAngle = 0;
    private int psyType = 0;
    private Paint circlePaint;
    private Paint bottomLinePaint;
    private int borderColor = Color.GRAY;
    private Paint borderPaint;
    private Paint divideLinePaint;
    private int height;
    private int width;
    private int divideLineWStartX;
    private float startX;
    private float startY;

    public PayPsdInputView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        getAttr(attrs);
        initPaint();
    }

    private void initPaint() {

        circlePaint = getIPaint(5, Paint.Style.FILL, circleColor);
        bottomLinePaint = getIPaint(2, Paint.Style.FILL, bottomLineColor);
        borderPaint = getIPaint(3, Paint.Style.STROKE, borderColor);
        divideLinePaint = getIPaint(divideLineWidth, Paint.Style.FILL, borderColor);
    }

    private Paint getIPaint(int strokeWidth, Paint.Style style, int color) {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStrokeWidth(strokeWidth);
        paint.setStyle(style);
        paint.setColor(color);
        paint.setAntiAlias(true);
        return paint;
    }

    private void getAttr(AttributeSet attrs) {

        TypedArray typedArray = mContext.obtainStyledAttributes(attrs, R.styleable.PayPsdInputView);
        maxCount = typedArray.getInt(R.styleable.PayPsdInputView_maxCount, maxCount);
        circleColor = typedArray.getColor(R.styleable.PayPsdInputView_circleColor, circleColor);
        bottomLineColor = typedArray.getColor(R.styleable.PayPsdInputView_bottomLineColor, bottomLineColor);
        radius = typedArray.getDimensionPixelOffset(R.styleable.PayPsdInputView_radius, radius);
        divideLineWidth = typedArray.getDimensionPixelOffset(R.styleable.PayPsdInputView_divideLineWidth, divideLineWidth);
        divideLineColor = typedArray.getColor(R.styleable.PayPsdInputView_divideLineColor, divideLineColor);
        rectAngle = typedArray.getDimensionPixelOffset(R.styleable.PayPsdInputView_rectAngle, rectAngle);
        psyType = typedArray.getInt(R.styleable.PayPsdInputView_psyType, psyType);
        typedArray.recycle();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        width=w;
        height=h;
        divideLineWStartX =w/maxCount;
        startX =w/maxCount/2;
        startY =w/maxCount/2;

    }
}
