package cn.evun.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import cn.evun.javebean.Cheeses;

/**
 * 自定义快速索引
 */
public class QuickIndexBar extends View {

	private Paint paint;
	private float cellWidth;
	private float cellHeight;

	public QuickIndexBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	private void init() {
		paint = new Paint();
		paint.setColor(Color.GRAY);
		paint.setTextSize(30);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
//		canvas.drawText("#",10f,20f,paint);
//		canvas.drawText("#",10f,50f,paint);
//		canvas.drawText("#",10f,80f,paint);
//		canvas.drawText("#",10f,110f,paint);
//		canvas.drawText("#",10f,140f,paint);
		for (int i = 0; i < Cheeses.LETTERS.length; i++) {
			String letter = Cheeses.LETTERS[i];
			Rect rect = new Rect();
			//获取不同的字母的宽高
			//将字母放入一个矩形容器中,矩形容器的宽高就是字母的宽高
			//获取文字边界
			paint.getTextBounds(letter, 0, 1, rect);
			//矩形的宽高就是文本的宽高
			int textWidth = rect.width();
			int textHeight = rect.height();
			float x = cellWidth * 0.5f - textWidth * 0.5f;
			float y = cellHeight * 0.5f + textHeight * 0.5f + i * cellHeight;
			canvas.drawText(letter, x, y, paint);
		}

	}

	//获取cellWidth和cellHeight
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		//获取当前控件的测量宽高
		cellWidth = this.getMeasuredWidth();
		cellHeight = this.getMeasuredHeight() * 1.0f / Cheeses.LETTERS.length;
	}

	//当前索引
	private int currentIndex = -1;
	//上一个索引
	private int lastIndex = -1;

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
			case MotionEvent.ACTION_MOVE:
				if (listener != null) {
					//每次滑动前,将当前索引赋值给上一个索引
					lastIndex = currentIndex;
					//目的:找到触摸的位置对应的letter
					float y = event.getY();
					//修改当前索引
					currentIndex = (int) (y / cellHeight);
					if (currentIndex > Cheeses.LETTERS.length - 1) {
						currentIndex = Cheeses.LETTERS.length - 1;
					} else if (currentIndex < 0) {
						currentIndex = 0;
					}
					if (lastIndex != currentIndex) {
						String letter = Cheeses.LETTERS[currentIndex];
//					Log.i("test", "letter:" + letter);
						listener.onLetterChanged(letter);
					}
				}
				break;
			case MotionEvent.ACTION_UP:
				if(listener!=null){
					listener.onLetterDismiss();
				}
				break;
		}
		//想要处理事件
		return true;
	}

	private OnLetterChangedListener listener;

	public interface OnLetterChangedListener {
		void onLetterChanged(String letter);
		void onLetterDismiss();
	}

	public void setOnLetterChangedListener(OnLetterChangedListener listener) {
		this.listener = listener;
	}
}
