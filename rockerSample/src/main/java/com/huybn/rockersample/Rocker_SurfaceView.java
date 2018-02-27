package com.huybn.rockersample;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.graphics.PorterDuff.Mode;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.WindowManager;

public class Rocker_SurfaceView extends SurfaceView implements Callback, Runnable {
	private Thread th;
	private SurfaceHolder sfh;
	private Canvas canvas;
	private Paint paint;
	private boolean flag;
	static int RockerCircleX,RockerCircleY,RockerCircleR,SmallRockerCircleX,SmallRockerCircleY,SmallRockerCircleR;
	static int speed,Angle;
	private static int[] d=new int[3];
	private Paint p;
	double tempRad;
	Bitmap bitmap,bitmap2;
	Display display;
	private boolean b;
	Point size;
	
	public Rocker_SurfaceView(Context context, AttributeSet attrs) {
		super(context, attrs);
		Log.d("HuybnTag","MySurfaceView");
		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE); 
	    display = wm.getDefaultDisplay();
	    size = new Point();
		display.getSize(size);
		
    	RockerCircleR=size.x/4;
    	SmallRockerCircleR=RockerCircleR/4;
    	SmallRockerCircleX=RockerCircleX=size.x/2;
    	SmallRockerCircleY=RockerCircleY=RockerCircleR*3/2;
    	
//		this.setKeepScreenOn(true);
		sfh = this.getHolder();
		sfh.addCallback(this);
		paint = new Paint();
		paint.setAntiAlias(true);
		p = new Paint();
		p.setAntiAlias(true);
		setFocusable(true);
		setFocusableInTouchMode(true);
		setZOrderOnTop(true);
		sfh.setFormat(PixelFormat.TRANSLUCENT);
		
		bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.rocker); 
        bitmap = Bitmap.createScaledBitmap(bitmap, RockerCircleR*2, RockerCircleR*2, false); 
        bitmap2 = BitmapFactory.decodeResource(getResources(), R.drawable.rocker_point); 
        bitmap2 = Bitmap.createScaledBitmap(bitmap2, SmallRockerCircleR*2, SmallRockerCircleR*2, false); 
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if(Math.abs(RockerCircleX-event.getX())+Math.abs(RockerCircleY-event.getY())<10){
			SmallRockerCircleX = (int) event.getX();
			SmallRockerCircleY = (int) event.getY();
		}
		else
			if(event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_MOVE) {
				if (Math.sqrt(Math.pow((RockerCircleX - (int) event.getX()), 2) + Math.pow((RockerCircleY - (int) event.getY()), 2)) < RockerCircleR) {
					SmallRockerCircleX = (int) event.getX();
					SmallRockerCircleY = (int) event.getY();
					speed = (int) (Math.sqrt(Math.pow(RockerCircleX-event.getX(), 2) + Math.pow(RockerCircleY-event.getY(), 2))/RockerCircleR*6);	//速度值为0~7
				}else{
					Point point=getBorderPoint(RockerCircleX, RockerCircleY, event.getX(), event.getY());
					SmallRockerCircleX = point.x;	//设定后，会直接显示位置
					SmallRockerCircleY = point.y;
					speed = (int) (Math.sqrt(Math.pow(RockerCircleX-event.getX(), 2) + Math.pow(RockerCircleY-event.getY(), 2))/RockerCircleR*6);
					if(speed>7) speed=7;		//限制速度在7以内
				}
				Angle=(getAngle(RockerCircleX, RockerCircleY, event.getX(), event.getY())+360)%360;		//取得角度
				int len=(int) (Math.sqrt(Math.pow(RockerCircleX-event.getX(), 2) + Math.pow(RockerCircleY-event.getY(), 2)));

			}else if (event.getAction() == MotionEvent.ACTION_UP) {
				speed=0; Angle=0;
				SmallRockerCircleX = RockerCircleX;
				SmallRockerCircleY = RockerCircleY;
			}
		turnBack();

		return true;
	}

	private void turnBack() {		//摇杆有动作时，重新设定View的大小以触发Main的监听动作
		if(!(d[0]==speed && d[1]==Angle)){
			d[0]=speed; d[1]=Angle;
			if(b) sfh.setFixedSize(size.x+1, size.x/4*3+20);
			else  sfh.setFixedSize(size.x, size.x/4*3+20);
			b=!b;
		}
	}
	public static void Rocker_Player(int Angle, int len){	//使摇杆显示传入值
		speed = len*12/RockerCircleR;		//让主程式读取
		len=(int)(RockerCircleR*len/100);
		if(len>RockerCircleR) len=RockerCircleR;
		SmallRockerCircleX = RockerCircleX+(int)(len*Math.sin(Angle/180.0*Math.PI));
		SmallRockerCircleY = RockerCircleY-(int)(len*Math.cos(Angle/180.0*Math.PI));
//		Log.d("HuybnTag","Angle= "+Angle+" ,len= "+len+" ,x= "+x+" ,y="+y+" sin= "+(double)(Math.round(Math.sin(Angle/180.0*Math.PI)*100))/100+" cos= "+(double)(Math.round(Math.cos(Angle/180.0*Math.PI)*100))/100);
	}
	public static void reset(){		//重置摇杆位置
		SmallRockerCircleX = RockerCircleX;
		SmallRockerCircleY = RockerCircleY;
	}
	public void surfaceCreated(SurfaceHolder holder) {
//		Log.d("HuybnTag","surfaceCreated");
		th = new Thread(this);
		flag = true;
		th.start();
	}

	public static int getAngle (float px1, float py1, float px2, float py2) {
		float lenA = py1 - py2;
        float lenB = px1 - px2;
        float lenC = (float)Math.sqrt(lenA*lenA+lenB*lenB);
        float ang = (float)Math.acos(lenA/lenC);
        ang = ang * (px2 < px1 ? -1 : 1); 
        return ((int)Math.round(ang/Math.PI*180));
  }
	
    public Point getBorderPoint (float px1, float py1, float px2, float py2) {
        float lenA = px2-px1;
        float lenB = py2-py1;
        float lenC = (float)Math.sqrt(lenA*lenA+lenB*lenB);
        float ang = (float)Math.acos(lenA/lenC);
        ang = ang * (py2 < py1 ? -1 : 1); 
        return new Point((int)px1 + (int)(RockerCircleR * Math.cos(ang)), (int)py1 + (int)(RockerCircleR * Math.sin(ang)));
    }
	
	public void draw() {
		try {
			canvas = sfh.lockCanvas();
			canvas.drawColor(Color.TRANSPARENT,Mode.CLEAR);
//			canvas.drawPaint(p);
//			paint.setColor(0x70aaaaaa);	//绘制摇杆背景
//			canvas.drawCircle(RockerCircleX, RockerCircleY, RockerCircleR, paint);
//			paint.setColor(0x70ff0000);	//绘制摇杆
//			canvas.drawCircle(SmallRockerCircleX, SmallRockerCircleY, SmallRockerCircleR, paint);
			
			Paint mPaint = null;
			mPaint = new Paint();
	        mPaint.setColor(Color.GREEN);
//	        mPaint.setAntiAlias(true);//抗锯齿
			canvas.drawBitmap(bitmap, RockerCircleX-RockerCircleR, RockerCircleY-RockerCircleR, mPaint);
            canvas.drawBitmap(bitmap2, SmallRockerCircleX - SmallRockerCircleR, SmallRockerCircleY - SmallRockerCircleR, mPaint);
            
			paint.setColor(0xbbffffff);	//绘制中心点
			canvas.drawCircle(RockerCircleX, RockerCircleY, RockerCircleR/12, paint);

		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			try {
				if (canvas != null)
					sfh.unlockCanvasAndPost(canvas);
			} catch (Exception e2) {

			}
		}
	}

	public void run() {
		Log.d("Huybn", "run");
		// TODO Auto-generated method stub
		while (flag) {
			draw();
			try {
				Thread.sleep(20);	//每次画面更新间隔
			} catch (Exception ex) {
			}
		}
	}

	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
		Log.d("Huybn", "surfaceChanged");
	}

	public void surfaceDestroyed(SurfaceHolder holder) {
		flag = false;
		Log.d("Huybn", "surfaceDestroyed");
	}
}