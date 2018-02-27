package cn.evun.touchdemo;

import android.animation.Animator;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.PointF;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.io.File;

import javax.crypto.Mac;
import javax.crypto.spec.PBEKeySpec;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "---";
    private PathView view;
    private RadioGroup rg;
    private EditText editText;
    private TextView animView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        view = (PathView) findViewById(R.id.view);
        rg = (RadioGroup) findViewById(R.id.rg);
        editText = (EditText) findViewById(R.id.et1);
        animView = (TextView) findViewById(R.id.animView);
        animView.setOnClickListener(this);
//        initListener();
        init();
        logPath();
    }

    private void logPath() {

        File filesDir = this.getFilesDir();//内存卡根目录
        Log.e(TAG, "getFilesDir==> "+filesDir);
        File cacheDir = getCacheDir();//内存卡缓存目录
        Log.e(TAG, "getCacheDir==> "+cacheDir);
        File storageDirectory = Environment.getExternalStorageDirectory();//外部储存卡根目录
        Log.e(TAG, "Environment.getExternalStorageDirectory==> "+storageDirectory);
        File externalFilesDir = getExternalFilesDir(null);//外部储存卡公共目录
        Log.e(TAG, "getExternalFilesDir==> "+externalFilesDir);
        File lishuai = getExternalFilesDir("lishuai");
        Log.e(TAG, "getExternalFilesDir(\"lishuai\")==> "+lishuai);
        File externalCacheDir = getExternalCacheDir();//外部储存卡缓存目录
        Log.e(TAG, "getExternalCacheDir()==> "+externalCacheDir);

    }

    private void init() {

        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.rb1:
                        view.setMode(true);
                        break;
                    case R.id.rb2:
                        view.setMode(false);
                        break;
                }
            }
        });

        InputFilter inputFilter = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {

                Log.e(TAG, "source:-->"+source+"==start-->"+start+"==end-->"+end+"==dest-->"
                        +dest.toString()+"==dstart-->"+dstart+"==dend-->"+dend);
                if (source.equals(" ")){
                    return "0";
                }
                return null;
            }
        };
        InputFilter filter = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                if (source.toString().equals("9")){
                    return "%%";
                }
                return null;
            }
        };
        InputFilter.AllCaps allCaps = new InputFilter.AllCaps();
        InputFilter.LengthFilter lengthFilter=new InputFilter.LengthFilter(15);

        editText.setFilters(new InputFilter[]{inputFilter,allCaps,lengthFilter});
    }

    private void initListener() {
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        print(event,"onTouch");
                        break;
                    case MotionEvent.ACTION_MOVE:
                        print(event,"onTouch");
                        break;
                    case MotionEvent.ACTION_UP:
                        print(event,"onTouch");
                        break;
                }
                return false;
            }
        });
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("-->", "onClick");
            }
        });
        animView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.e(TAG, "x=="+event.getX()+"-->y=="+event.getY());
                return false;
            }
        });
    }

    private void print(MotionEvent event, String msg) {
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN: {
//                Log.e("--> down ", msg);
                break;
            }
            case MotionEvent.ACTION_MOVE: {
//                Log.e("--> move ", msg);
                break;
            }
            case MotionEvent.ACTION_UP: {
//                Log.e("--> up ", msg);
                break;
            }

        }

    }

    @Override
    public void onClick(View v) {
        Animator animator = ViewAnimationUtils.createCircularReveal(animView,0, 0, 20, 200);
        Log.e(TAG, "animViewLeft=="+animView.getLeft()+"-->animViewRight=="+animView.getTop());
        animator.setDuration(500);
        animator.start();
        GestureDetector.OnGestureListener lister= new GestureDetector.OnGestureListener() {
            @Override
            public boolean onDown(MotionEvent e) {
                return false;
            }

            @Override
            public void onShowPress(MotionEvent e) {

            }

            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return false;
            }

            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                return false;
            }

            @Override
            public void onLongPress(MotionEvent e) {

            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                return false;
            }
        };

    }
}
