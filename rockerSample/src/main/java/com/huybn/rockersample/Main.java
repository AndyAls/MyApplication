package com.huybn.rockersample;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.SurfaceHolder.Callback;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.os.Build;

public class Main extends Activity {
	private SurfaceView mSurfaceView;
	private SensorManager sensorManager;
	private Sensor sensor;
	private float x,y,z,gravity[] = new float[3];
	private boolean gravityControl;
	private int sens=50;
	static TextView tv1,tv_gravity;
	private Button btGravity,btReset;
	private SeekBar sb_sens;
	private Handler handler = new Handler();;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);	//重力感应
		sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		findViews();
		Listeners();
	}

	private Button.OnClickListener clickListener=new Button.OnClickListener(){
		public void onClick(View v){
			switch(v.getId()){
			case R.id.button_gravity:		//重力感应
				if(!gravityControl){
					sb_sens.setProgress(sens);				//设定显示的进度
					tv_gravity.setText((sens-50)+"%");
					sensorManager.registerListener(gListener, sensor,SensorManager.SENSOR_DELAY_UI);//开始接收重力感应的资讯
					handler.postDelayed(XYZtimer, 200);			//自动重置、校正重力感应初始值
				}else{
					gravityControl=false;
					Rocker_SurfaceView.Rocker_Player(0,0);	//重置摇杆位置
					sensorManager.unregisterListener(gListener);
				}
				break;
			case R.id.button_reset:
				x=gravity[0];
				y=gravity[1];
				z=gravity[2];
				break;
			}
		}
	};
	private void Listeners(){
		Display display = getWindowManager().getDefaultDisplay();	//取得萤幕大小
		Point size = new Point();									//
		display.getSize(size);										//
		mSurfaceView.getHolder().setFixedSize(size.x, size.x/4*3+20);	//设定摇杆操作区的大小
		SurfaceHolder holder=mSurfaceView.getHolder();
		//		holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		holder.addCallback(new Callback(){
			public void surfaceDestroyed(SurfaceHolder holder){}
			public void surfaceCreated(SurfaceHolder holder){}
			public void surfaceChanged(SurfaceHolder holder, int format, int width, int height){	//当摇杆有变化时 会呼叫回来
				tv1.setText("角度= "+Rocker_SurfaceView.Angle+"		速度= "+Rocker_SurfaceView.speed);
			}
		}
				);
		sb_sens.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {		//重力感应灵敏度
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser){
				sens = progress;
				if(sens>50) tv_gravity.setText("+"+(sens-50)+"%");
				else tv_gravity.setText((sens-50)+"%");
			}
			public void onStartTrackingTouch(SeekBar seekBar) { }
			public void onStopTrackingTouch(SeekBar seekBar) { }
		});
	}

	private SensorEventListener gListener = new SensorEventListener() {	//重力感应监听者
		@Override
		public void onAccuracyChanged(Sensor arg0, int arg1) {
		}
		@Override
		public void onSensorChanged(SensorEvent e) {
			gravity[0] = (e.values[0]+gravity[0])/2;	//暂存当前重力值
			gravity[1] = (e.values[1]+gravity[1])/2;	//为了避免数值变动太快，用前一次的数值来做平均
			gravity[2] = (e.values[2]+gravity[2])/2;
			if(gravityControl){			//确定已自动校正重力感应
				int Angle=getAngle(x-gravity[0], y-gravity[1]);	//把移动值转为角度
				Angle=(Angle+360)%360;							//避免角度为负数

				int len=(int)(Math.sqrt(Math.pow(x-gravity[0],2)+Math.pow(y-gravity[1],2))*sens-30);	//"-30"为使停止比较容易
				if(len>0){	
					if(Rocker_SurfaceView.speed>7) Rocker_SurfaceView.speed=7;
					if(Rocker_SurfaceView.speed<-7) Rocker_SurfaceView.speed=-7;
				}else{
					Rocker_SurfaceView.speed=0;
					Angle=0; len=0;
				}
					
				tv1.setText("角度= "+Angle+"		速度= "+Rocker_SurfaceView.speed);
				Rocker_SurfaceView.Rocker_Player(Angle,len);	//在摇杆上显示当前速度、方向
			}
		}
	};
	private Runnable XYZtimer = new Runnable() {	//自动校正重力感应
		public void run() {
			gravityControl=true;			//标记重力感应状态为开始
			x=gravity[0];
			y=gravity[1];
			z=gravity[2];
		}
	};

	private void findViews(){
		tv1=(TextView) findViewById(R.id.textView1);
		mSurfaceView = (SurfaceView) findViewById(R.id.rudder);		//摇杆
		
		btGravity = (Button) findViewById(R.id.button_gravity); btGravity.setOnClickListener(clickListener);
		btReset = (Button) findViewById(R.id.button_reset); btReset.setOnClickListener(clickListener);
		tv_gravity=(TextView) findViewById(R.id.textView_gravity);
		sb_sens = (SeekBar) findViewById(R.id.seekBar_sensitivity);
		sb_sens.setProgress(50);
	}
	public static int getAngle (float lenA, float lenB) {		//传入两个长度，取角度atan
		float ang = (float)Math.atan(lenA/lenB);
		return ((int)Math.round(ang/Math.PI*180)+(lenB>0? 0:180));	//反atan返回的值一直只会0~180，故需要自行调整
	}
	
	public void onPause(){
    	super.onPause();
    	if(gravityControl){		//停止重力感应
    		gravityControl=false;
    		sensorManager.unregisterListener(gListener);
    	}
    	Rocker_SurfaceView.Rocker_Player(0,0);	//重置摇杆位置
    }

}
