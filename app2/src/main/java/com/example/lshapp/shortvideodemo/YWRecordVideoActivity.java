package com.example.lshapp.shortvideodemo;

/**
 * Created by Administrator on 2016/7/27.
 */
//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.hardware.Camera;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.LocalBroadcastManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.im.Config;
import com.im.SoInstallMgr;
import com.im.Util;
import com.im.av.logic.manage.IMCommitManager;
import com.im.av.view.WxAlertDialog;
import com.taobao.av.logic.media.TaoMediaRecorder;
import com.taobao.av.ui.view.CircularProgressDrawable;
import com.taobao.av.ui.view.NewDialog;
import com.taobao.av.ui.view.SizeChangedNotifier;
import com.taobao.av.ui.view.recordline.ClipManager;
import com.taobao.av.ui.view.recordline.RecorderTimeline;
import com.taobao.av.ui.view.recordline.VideoBean;
import com.taobao.av.util.CameraHelper;
import com.taobao.av.util.DensityUtil;
import com.taobao.av.util.FileUtils;
import com.taobao.av.util.MediaFileUtils;
import com.taobao.av.util.PermissionUtils;
import com.taobao.av.util.SystemUtil;
import com.taobao.media.MediaEncoder;
import com.taobao.media.MediaEncoderMgr;

import java.io.File;
import java.util.Formatter;
import java.util.Locale;
import java.util.UUID;

//重点在900行开始和251行开始
public class YWRecordVideoActivity extends Activity implements View.OnClickListener, SizeChangedNotifier.Listener {
    private boolean mSuccessBroadcast = false;
    private int _quality = 1;
    private boolean _isNeedUpload = false;
    private int _maxDuration = '\uea60';
    private final int DeltaTime = 100;
    private final int MinToMaxDuration = 9000;
    private final int MinMAXDuration = 6000;
    private final int MaxMAXDuration = 180000;
    private final int Type_ShortVideo = 0;
    private final int Type_LongVideo = 1;
    private boolean enableClickRecord = false;
    private int mVideoType = 0;
    private Handler mSafeHandler;
    private Camera mCamera;
    private boolean mHasFlashLight = true;
    private SurfaceView mCameraPreview;
    private SurfaceHolder mSurfaceHolder;
    private boolean isVideoRecording = false;
    private TaoMediaRecorder mTaoMediaRecorder;
    private int cameraPosition = SystemUtil.getCameraFacingBack();
    private final int TargetPreviewSize = 480;
    private int mPreviewWidth = 640;
    private int mPreviewHeight = 480;
    private AudioManager mAudioManager;
    private ImageView iv_back;
    private ImageView iv_light;
    private ImageView iv_camerarotate;
    private RecorderTimeline mRecorderTimeline;
    private ClipManager mClipManager;
    private CheckBox btn_delete_last_clip;
    private ImageView iv_Recorderbg;
    private ImageView iv_Recorder;
    private CheckBox iv_ok;
    private final float Alpha_Disable = 0.5F;
    private final float Alpha_Enable = 1.0F;
    private Animation alpahAnimation;
    private Animation scaleAnimation;
    private View mProgressDialogView;
    private CircularProgressDrawable mProgressDrawable;
    private ImageView mProgressView;
    private TextView mProgressTextView;
    private TextView tv_recordtime;
    private StringBuilder mFormatBuilder;
    private Formatter mFormatter;
    private String mPubTitle;
    private ImageView iv_notice_recordlimit;
    private int mType;
    private ImageView min_capture_duration_point;
    private String TAG;
    private String model;
    private Context mContext;
    private boolean mSurfaceAcquired;
    private final SurfaceHolder.Callback surfaceCallback;
    private int mVideoIndex;
    private boolean isUserPress;
    private boolean isLongPress;
    private Runnable mLongPressRunnable;
    private final View.OnTouchListener Recorder_OnTouchListener;
    private boolean isFirstStartRecord;
    private long startTime;
    private Runnable _runnableTimer;
    private TranslateAnimation anim_mintime_notice;
    private final AudioManager.OnAudioFocusChangeListener mAudioFocusListener;


    //录制时间在onCreate方法里设置
    public YWRecordVideoActivity() {
        this.mType = Config.CURRENT_TYPE;
        this.TAG = "IMRecordVideo";
        this.mSurfaceAcquired = false;
        this.surfaceCallback = new SurfaceHolder.Callback() {
            public void surfaceCreated(SurfaceHolder holder) {
                YWRecordVideoActivity.this.mSurfaceAcquired = true;
                YWRecordVideoActivity.this.startPreview();
            }

            public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {

            }

            public void surfaceDestroyed(SurfaceHolder holder) {
                YWRecordVideoActivity.this.mSurfaceAcquired = false;
                if (YWRecordVideoActivity.this.iv_light != null) {
                    YWRecordVideoActivity.this.iv_light.setImageResource(com.taobao.taorecorder.R.drawable.aliwx_sv_wx_shiny_nor);
                }

                YWRecordVideoActivity.this.stopPreview();
            }
        };
        this.mVideoIndex = 0;
        this.isUserPress = false;
        this.isLongPress = false;
        this.mLongPressRunnable = new Runnable() {
            public void run() {
                if (!YWRecordVideoActivity.this.isVideoRecording && YWRecordVideoActivity.this.mTaoMediaRecorder != null && YWRecordVideoActivity.this.mTaoMediaRecorder.canStartRecord()) {
                    YWRecordVideoActivity.this.isLongPress = true;
                    YWRecordVideoActivity.this.startRecord();
                } else if (YWRecordVideoActivity.this.mTaoMediaRecorder == null) {
                    YWRecordVideoActivity.this.recordError();
                }

            }
        };

        this.Recorder_OnTouchListener = new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getActionMasked()) {
                    case 0:
                        if (!YWRecordVideoActivity.this.isUserPress) {
                            if (YWRecordVideoActivity.this.isVideoRecording && YWRecordVideoActivity.this.enableClickRecord && 1 == YWRecordVideoActivity.this.mVideoType) {
                                YWRecordVideoActivity.this.stopRecord();
                                return true;
                            }

                            YWRecordVideoActivity.this.isUserPress = true;
                        }

                        YWRecordVideoActivity.this.getSafeHandler().postDelayed(YWRecordVideoActivity.this.mLongPressRunnable, 250L);
                        break;
                    case 1:
                    case 3:
                        if (YWRecordVideoActivity.this.isUserPress) {
                            YWRecordVideoActivity.this.getSafeHandler().removeCallbacks(YWRecordVideoActivity.this.mLongPressRunnable);
                            if (YWRecordVideoActivity.this.isLongPress) {
                                if (YWRecordVideoActivity.this.isVideoRecording) {
                                    YWRecordVideoActivity.this.stopRecord();
                                }
                            } else if (!YWRecordVideoActivity.this.isVideoRecording && YWRecordVideoActivity.this.mTaoMediaRecorder != null && YWRecordVideoActivity.this.mTaoMediaRecorder.canStartRecord() && YWRecordVideoActivity.this.enableClickRecord && 1 == YWRecordVideoActivity.this.mVideoType) {
                                YWRecordVideoActivity.this.startRecord();
                            } else if (YWRecordVideoActivity.this.mTaoMediaRecorder == null) {
                                YWRecordVideoActivity.this.recordError();
                            }
                        }

                        YWRecordVideoActivity.this.isUserPress = false;
                        YWRecordVideoActivity.this.isLongPress = false;
                    case 2:
                }

                return true;
            }
        };
        this.isFirstStartRecord = true;
        this.startTime = 0L;
        this._runnableTimer = new Runnable() {
            public void run() {
                if (YWRecordVideoActivity.this.mTaoMediaRecorder != null) {
                    if (YWRecordVideoActivity.this.startTime == 0L && !YWRecordVideoActivity.this.mTaoMediaRecorder.isRecording()) {
                        YWRecordVideoActivity.this.getSafeHandler().postDelayed(this, 25L);
                    } else {
                        if (YWRecordVideoActivity.this.startTime == 0L) {
                            YWRecordVideoActivity.this.startTime = System.currentTimeMillis();
                        }
                        YWRecordVideoActivity.this.mClipManager.onRecordFrame(System.currentTimeMillis() - YWRecordVideoActivity.this.startTime);
                        YWRecordVideoActivity.this.setRecordTime();
                        if (YWRecordVideoActivity.this.mClipManager.isMinDurationReached()) {
                            YWRecordVideoActivity.this.iv_ok.setVisibility(View.VISIBLE);
                        } else {
                            YWRecordVideoActivity.this.iv_ok.setVisibility(View.VISIBLE);
                        }

                        if (YWRecordVideoActivity.this.mClipManager.isMaxDurationReached()) {
                            YWRecordVideoActivity.this.onMaxDurationReached();
                        } else {
                            YWRecordVideoActivity.this.getSafeHandler().postDelayed(this, 25L);
                        }
                    }
                }
            }
        };
        this.mAudioFocusListener = new AudioManager.OnAudioFocusChangeListener() {
            public void onAudioFocusChange(int focusChange) {
                switch (focusChange) {
                    case -3:
                    case -2:
                    case -1:
                    case 0:
                    case 1:
                    default:
                }
            }
        };
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(com.taobao.taorecorder.R.layout.aliwx_sv_recorder_activity_recorder);

        this.mContext = this;

        try {
            this._quality = 0;
            //录制时间
            this._maxDuration = 20000;
            this._maxDuration += 100;
        } catch (Exception var5) {

        }

        if (this.checkIsUnSupportVersion()) {
            Toast.makeText(this, this.getString(com.taobao.taorecorder.R.string.taorecorder_notsupport), Toast.LENGTH_SHORT).show();
            this.finish();
        } else {
            if (MediaEncoder.isLoadMediaEncodeFailed() && SoInstallMgr.loadFileSo("MediaEncode", "", this.mContext)) {
                MediaEncoder.setLoadMediaEncodeFailed(false);
            }

            this.setVideoType();
            this.mSafeHandler = new Handler();
            SizeChangedNotifier frame1 = (SizeChangedNotifier) this.findViewById(com.taobao.taorecorder.R.id.camera_frame);
            frame1.setOnSizeChangedListener(this);
            this.mCameraPreview = (SurfaceView) this.findViewById(com.taobao.taorecorder.R.id.camera_view);
            this.mSurfaceHolder = this.mCameraPreview.getHolder();
            this.addSurfaceCallBack();
            this.mSurfaceHolder.setType(3);
            this.mAudioManager = (AudioManager) this.getApplication().getSystemService(Context.AUDIO_SERVICE);
            this.requestAudioFocus();
            this.iv_back = (ImageView) this.findViewById(com.taobao.taorecorder.R.id.iv_back);
            this.iv_back.setOnClickListener(this);
            this.iv_light = (ImageView) this.findViewById(com.taobao.taorecorder.R.id.iv_light);
            this.iv_light.setOnClickListener(this);
            this.iv_camerarotate = (ImageView) this.findViewById(com.taobao.taorecorder.R.id.iv_camerarotate);
            this.iv_camerarotate.setOnClickListener(this);
            this.mClipManager = new ClipManager();
            this.mClipManager.setMaxDuration(this._maxDuration);
            this.mClipManager.setMinDuration(3000);
            this.mRecorderTimeline = new RecorderTimeline(this.findViewById(com.taobao.taorecorder.R.id.record_timeline), this.mClipManager);
            this.mRecorderTimeline.setItemDrawable(com.taobao.taorecorder.R.drawable.aliwx_sv_recorder_timeline_clip_selector);
            this.btn_delete_last_clip = (CheckBox) this.findViewById(com.taobao.taorecorder.R.id.btn_delete_last_clip);
            this.btn_delete_last_clip.setOnClickListener(this);
            this.btn_delete_last_clip.setAlpha(0.5F);
            this.iv_Recorderbg = (ImageView) this.findViewById(com.taobao.taorecorder.R.id.iv_Recorderbg);
            this.iv_Recorderbg.setOnTouchListener(this.Recorder_OnTouchListener);
            this.iv_Recorder = (ImageView) this.findViewById(com.taobao.taorecorder.R.id.iv_Recorder);
            this.iv_ok = (CheckBox) this.findViewById(com.taobao.taorecorder.R.id.iv_ok);
            this.iv_ok.setOnClickListener(this);
            this.iv_ok.setAlpha(1.0F);
            this.iv_ok.setVisibility(View.VISIBLE);
//            this.iv_ok.setTextColor(this.getResources().getColor(R.color.bghuise));


            this.alpahAnimation = AnimationUtils.loadAnimation(this, com.taobao.taorecorder.R.anim.taorecorder_alpha_reverse);
            this.scaleAnimation = AnimationUtils.loadAnimation(this, com.taobao.taorecorder.R.anim.taorecorder_scale_reverse);
            this.initProgressDialog();
            this.tv_recordtime = (TextView) this.findViewById(com.taobao.taorecorder.R.id.tv_recordtime);
            this.mFormatBuilder = new StringBuilder();
            this.mFormatter = new Formatter(this.mFormatBuilder, Locale.getDefault());
            this.findViewById(com.taobao.taorecorder.R.id.rl_recorder_controller).setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    YWRecordVideoActivity.this.btn_delete_last_clip.setChecked(false);
                }
            });
            PermissionUtils.checkPermission(this);
            this.iv_notice_recordlimit = (ImageView) this.findViewById(com.taobao.taorecorder.R.id.iv_notice_recordlimit);
            this.min_capture_duration_point = (ImageView) this.findViewById(com.taobao.taorecorder.R.id.min_capture_duration_point);
            this.changeStyle();
            this.model = Build.MODEL;
            if (Config.isDebug()) {
                Log.d(this.TAG + "@sv", "Build.MODEL = " + this.model);
            }
        }
    }

    private void addSurfaceCallBack() {
        this.mSurfaceHolder.removeCallback(this.surfaceCallback);
        this.mSurfaceHolder.addCallback(this.surfaceCallback);
    }

    private void changeStyle() {
        TextView tempTextView;
        Bitmap bitmap;
        if (this.mType == 1) {
//            this.iv_Recorder.setBackgroundResource(com.taobao.taorecorder.R.drawable.aliwx_sv_st_video_record);
            tempTextView = Util.createTextViewWithBackgroud(this, DensityUtil.dip2px(this, 88.0F), DensityUtil.dip2px(this, 36.0F), DensityUtil.dip2px(this, 2.0F), DensityUtil.dip2px(this, 2.0F), com.taobao.taorecorder.R.drawable.aliwx_sv_st_notification_recordlimit, -1, this.getResources().getColor(R.color.bghuise), 17, this.getResources().getString(com.taobao.taorecorder.R.string.imrecorder_mintimeshow_message));
            bitmap = Util.convertViewToBitmap(tempTextView);
            this.iv_notice_recordlimit.setImageBitmap(bitmap);
            this.mRecorderTimeline.setItemDrawable(com.taobao.taorecorder.R.drawable.aliwx_sv_strecorder_timeline_clip_selector);
//            this.min_capture_duration_point.setImageDrawable(new ColorDrawable(this.getResources().getColor(com.taobao.taorecorder.R.color.imstrecorder_init_color)));
        } else if (this.mType == 0) {
            tempTextView = Util.createTextViewWithBackgroud(this, DensityUtil.dip2px(this, 88.0F), DensityUtil.dip2px(this, 36.0F), DensityUtil.dip2px(this, 2.0F), DensityUtil.dip2px(this, 2.0F), com.taobao.taorecorder.R.drawable.aliwx_sv_notification_recordlimit, -1, this.getResources().getColor(R.color.bghuise), 17, this.getResources().getString(com.taobao.taorecorder.R.string.imrecorder_mintimeshow_message));
            bitmap = Util.convertViewToBitmap(tempTextView);
            this.iv_notice_recordlimit.setImageBitmap(bitmap);
        }

    }

    public boolean checkIsUnSupportFlishLight(Camera camera) {
        return Util.hasFlash(camera);
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        PermissionUtils.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private boolean checkIsUnSupportVersion() {
        return !Util.hasMediaEncodeSo(this) || SystemUtil.isSpecialMobileType() || SystemUtil.isLowPhone(this) || !MediaFileUtils.checkSDCardAvailable();
    }

    private boolean isSupportFocusModeChange() {
        return !SystemUtil.isMobileInFocusModeBlackList();
    }

    private void setVideoType() {
        if (this._maxDuration >= 9000) {
            this.mVideoType = 1;
        } else {
            this.mVideoType = 0;
        }
    }

    private void startPreview() {
        if (this.mCamera != null && this.mSurfaceAcquired) {
            try {
                this.addSurfaceCallBack();
                this.mCamera.setPreviewDisplay(this.mSurfaceHolder);
                this.mCamera.startPreview();
            } catch (Exception var2) {
                return;
            }

            Camera.Parameters cameraParams = this.mCamera.getParameters();
            if (cameraParams.getFocusMode() == "auto") {
                this.mCamera.autoFocus((Camera.AutoFocusCallback) null);
            }

            this.startCollect();
        }
    }

    private void startCollect() {
        this.initMediaRecorder();
        this.mTaoMediaRecorder.prepareCamera(this.mCamera);
        this.mTaoMediaRecorder.setOrientationHintByCameraPostion(this, this.mCamera, this.cameraPosition);

        try {
            this.mTaoMediaRecorder.prepare();
        } catch (Exception var2) {
            var2.printStackTrace();
        }

    }

    private void initMediaRecorder() {
        if (this.mTaoMediaRecorder == null) {
            this.mTaoMediaRecorder = new TaoMediaRecorder(this);
            this.mTaoMediaRecorder.setVideoSource(1);
            this.mTaoMediaRecorder.setAudioSource(0);
            this.mTaoMediaRecorder.setOutputFormat(2);
            this.mTaoMediaRecorder.setAudioEncoder(0);
            this.mTaoMediaRecorder.setVideoEncoder(2);
            this.mTaoMediaRecorder.setVideoSize(this.mPreviewWidth, this.mPreviewHeight);
            this.mTaoMediaRecorder.setQuality(this._quality);
        }

    }

    private void openCameraError() {
        if (!this.isFinishing()) {
            Toast.makeText(this, this.getString(com.taobao.taorecorder.R.string.taorecorder_camera_permission_deny), Toast.LENGTH_SHORT).show();
            this.finish();
        }

    }

    private void recordError() {
        if (!this.isFinishing()) {
            Toast.makeText(this, this.getString(com.taobao.taorecorder.R.string.taorecorder_record_fail), Toast.LENGTH_SHORT).show();
            this.finish();
        }

    }

    private boolean initCameraImpl() {
        this.mCamera = CameraHelper.openCamera(this.cameraPosition);
        if (this.mCamera == null) {
            this.openCameraError();
            return false;
        } else {
            Camera.Parameters parameters = this.mCamera.getParameters();
            this.setupPreviewSize(parameters);
            CameraHelper.setFocusArea(parameters, new Rect(-100, -100, 100, 100));
            CameraHelper.setPreviewFrameRate(parameters, 20);
            if (this.isSupportFocusModeChange()) {
                CameraHelper.setFocusMode(parameters);
            }

            CameraHelper.setCameraDisplayOrientation(this, this.cameraPosition, this.mCamera);
            this.mCamera.setParameters(parameters);
            this.mHasFlashLight = this.checkIsUnSupportFlishLight(this.mCamera);
            if (!this.mHasFlashLight) {
                this.iv_light.setVisibility(View.GONE);
            }

            return true;
        }
    }

    private boolean initCamera() {
        try {
            return this.initCameraImpl();
        } catch (Exception var2) {
            IMCommitManager.addErrorTrack("@sv", "initCamera", var2);
            return false;
        }
    }

    private void setupPreviewSize(Camera.Parameters camera_params) {
        Camera.Size valid_size = null;
        if (valid_size == null) {
            Camera.Size[] size_list = CameraHelper.choosePreviewSize(camera_params, 480, 480);
            if (size_list.length == 0) {
                valid_size = camera_params.getPreviewSize();
            } else {
                valid_size = size_list[0];
            }
        }

        this.mPreviewWidth = valid_size.width;
        this.mPreviewHeight = valid_size.height;
        camera_params.setPreviewSize(valid_size.width, valid_size.height);
    }

    private void stopPreview() {
        boolean stopSuc = true;

        try {
            if (this.mTaoMediaRecorder != null) {
                this.mTaoMediaRecorder.stop();
                this.isVideoRecording = false;
            }

            this.removeHolderAndStopPreview(true);
        } catch (Exception var3) {
            stopSuc = false;
            IMCommitManager.addErrorTrack("@sv", "stopPreview", var3);
        }

        if (!stopSuc) {
            this.openCameraError();
        }
    }

    private boolean removeHolderAndStopPreview(boolean needLock) {
        try {
            if (this.mCamera != null) {
                if (this.mSurfaceHolder != null) {
                    this.mSurfaceHolder.removeCallback(this.surfaceCallback);
                }

                this.mCamera.setPreviewCallback((Camera.PreviewCallback) null);
                this.mCamera.stopPreview();
                if (needLock) {
                    this.mCamera.lock();
                }

                this.mCamera.release();
                this.mCamera = null;
                return true;
            } else {
                return false;
            }
        } catch (Exception var3) {
            IMCommitManager.addErrorTrack("@sv", "removeHolderAndStopPreview", var3);
            return false;
        }
    }

    private String getLastOutputFile() {
        return "temp_" + this.mVideoIndex + ".mp4";
    }

    private Handler getSafeHandler() {
        if (this.mSafeHandler != null) {
            return this.mSafeHandler;
        } else {
            this.mSafeHandler = new Handler();
            return this.mSafeHandler;
        }
    }

    private void startRecord() {
        IMCommitManager.commitClick(IMCommitManager.getActivityPageName(this), "Video_Recording");
        this.btn_delete_last_clip.setVisibility(View.VISIBLE);
        this.iv_ok.setVisibility(View.VISIBLE);
        this.resetRecorderState();
        this.mRecorderTimeline.stopAnim();
        if (this.mClipManager.isReachJumpTime()) {
            this.toIMPlayRecordVideoActivity();
            this.isVideoRecording = false;
        } else {
            VideoBean vb = new VideoBean();
            vb.videoFile = this.mTaoMediaRecorder.getFileDir() + File.separator + this.getLastOutputFile();
            this.mClipManager.onRecordStarted(vb);
            this.startTime = 0L;
            if (this.mSafeHandler != null) {
                this.getSafeHandler().post(this._runnableTimer);
            }

            this.mRecorderTimeline.stopAnim();
            if (this.enableClickRecord && 1 == this.mVideoType && !this.isLongPress) {
                this.iv_Recorderbg.setBackgroundResource(com.taobao.taorecorder.R.drawable.aliwx_sv_recorder_ovalbg_stroke_pause);
            }

            this.iv_Recorderbg.startAnimation(this.scaleAnimation);
            this.iv_Recorder.startAnimation(this.alpahAnimation);
            this.mTaoMediaRecorder.setOutputFile(this.getLastOutputFile());
            this.mTaoMediaRecorder.start();
            this.isVideoRecording = true;
        }
    }

    private void stopRecord() {
        this.showIcon();
        this.btn_delete_last_clip.setEnabled(true);
        this.mClipManager.onRecordPaused();
        if (this.mSafeHandler != null) {
            this.getSafeHandler().removeCallbacks(this._runnableTimer);
        }

        if (this.iv_Recorder.isShown() && !this.mClipManager.isMaxDurationReached()) {
            this.mRecorderTimeline.startAnim();
        } else {
            this.mRecorderTimeline.stopAnim();
        }

        if (this.enableClickRecord && 1 == this.mVideoType && !this.isLongPress) {
            if (this.mType == 1) {
                this.iv_Recorder.setBackgroundResource(com.taobao.taorecorder.R.drawable.aliwx_sv_st_video_record);
            } else if (this.mType == 0) {
                this.iv_Recorder.setBackgroundResource(com.taobao.taorecorder.R.drawable.aliwx_sv_wx_video_record);
            }

            this.iv_Recorderbg.setBackgroundResource(com.taobao.taorecorder.R.drawable.aliwx_sv_recorder_ovalbg_stroke);
        }

        this.iv_Recorderbg.clearAnimation();
        this.iv_Recorder.clearAnimation();
        ++this.mVideoIndex;
        this.mTaoMediaRecorder.stop();
        if (this.mClipManager.isLastClipMinTime()) {
            this.delete_last_clip();
        }

        this.btn_delete_last_clip.setEnabled(this.mClipManager.isUnEmpty());
        this.isVideoRecording = false;
        if (this.mClipManager.isReachJumpTime()) {
            this.toIMPlayRecordVideoActivity();
        }

    }

    private void onMaxDurationReached() {
        this.isVideoRecording = false;
        this.stopRecord();
    }

    private void setRecordTime() {
        int totaltime = this.mClipManager.getDuration();
        if (totaltime >= 0 && totaltime < this._maxDuration) {
            this.mFormatBuilder.setLength(0);
            String stime = this.mFormatter.format("%d.%d 秒", new Object[]{Integer.valueOf(totaltime / 1000), Integer.valueOf(totaltime / 100 % 10)}).toString();
            this.tv_recordtime.setText(stime);
        }

    }

    public void onResume() {
        super.onResume();
        IMCommitManager.pageAppear(this);
        if (!this.mClipManager.isMaxDurationReached()) {
            this.mRecorderTimeline.startAnim();
        }

        this.setDeleteNoneSelectedContent();
        if (!this.initCamera()) {
            this.openCameraError();
        } else {
            this.startPreview();
        }
    }

    public void onPause() {
        super.onPause();
        IMCommitManager.pageDisappear(this);
        if (this.isVideoRecording) {
            this.stopRecord();
        }

        this.mRecorderTimeline.stopAnim();
        this.iv_Recorderbg.clearAnimation();
        this.iv_Recorder.clearAnimation();
        this.stopPreview();
    }

    public void onStop() {
        super.onStop();
    }

    protected void onDestroy() {
        this.abandonAudioFocus();
        if (this.alpahAnimation != null) {
            this.alpahAnimation.cancel();
            this.alpahAnimation.reset();
            this.alpahAnimation = null;
        }

        if (this.scaleAnimation != null) {
            this.scaleAnimation.cancel();
            this.scaleAnimation.reset();
            this.scaleAnimation = null;
        }

        if (this.mRecorderTimeline != null) {
            this.mRecorderTimeline.destory();
        }

        if (this.mSafeHandler != null) {
            this.getSafeHandler().removeCallbacksAndMessages((Object) null);
            this.mSafeHandler = null;
        }

        if (!this.mSuccessBroadcast) {
            this.sendErrorBroadcast();
        }

        super.onDestroy();
    }

    public void onClick(View v) {
        if (!this.isVideoRecording) {
            int id = v.getId();
            if (id == R.id.iv_back) {
                IMCommitManager.commitClick(IMCommitManager.getActivityPageName(this), "Video_Return");
                this.pressback();
            } else if (id == R.id.iv_light) {
                IMCommitManager.commitClick(IMCommitManager.getActivityPageName(this), "Video_Flash");
                this.changeLight();
            } else if (id == R.id.iv_camerarotate) {
                IMCommitManager.commitClick(IMCommitManager.getActivityPageName(this), "Video_FrontBack");
                this.rotateCamera();
            } else if (id == R.id.btn_delete_last_clip) {
                this.click_delete_last_clip();
            } else if (id == R.id.iv_ok) {
                IMCommitManager.commitClick(IMCommitManager.getActivityPageName(this), "Video_Confirm");
                if (this.mClipManager.isMinDurationReached()) {
                    this.toIMPlayRecordVideoActivity();

                } else {
                    this.toIMPlayRecordVideoActivity();
//                    this.showMinTimeNotice();
                }
            }

        }
    }

    private void showMinTimeNotice() {
        if (!this.iv_notice_recordlimit.isShown()) {
            if (this.anim_mintime_notice == null) {
                DisplayMetrics metric = new DisplayMetrics();
                this.getWindowManager().getDefaultDisplay().getMetrics(metric);
                int screenwidth = metric.widthPixels;
                int videowidth = this.iv_notice_recordlimit.getWidth();
                float _x = (float) (screenwidth * this.mClipManager.getMinDuration()) / this.mClipManager.getMaxDuration() - (float) (videowidth / 2);
                this.anim_mintime_notice = new TranslateAnimation((float) ((int) _x), (float) ((int) _x), -30.0F, 0.0F);
                this.anim_mintime_notice.setDuration(600L);
                this.anim_mintime_notice.setRepeatCount(3);
                this.anim_mintime_notice.setRepeatMode(2);
                this.anim_mintime_notice.setAnimationListener(new Animation.AnimationListener() {
                    public void onAnimationStart(Animation animation) {
                    }

                    public void onAnimationEnd(Animation animation) {
                        YWRecordVideoActivity.this.iv_notice_recordlimit.setVisibility(View.INVISIBLE);
                    }

                    public void onAnimationRepeat(Animation animation) {
                    }
                });
            }

            this.iv_notice_recordlimit.setVisibility(View.VISIBLE);
            this.iv_notice_recordlimit.startAnimation(this.anim_mintime_notice);
        }
    }

    private void changeLight() {
        Camera.Parameters parameters = this.mCamera.getParameters();
        if (CameraHelper.getFlashlightOn(parameters)) {
            CameraHelper.setFlashlightMode(parameters, false);
            this.iv_light.setImageResource(com.taobao.taorecorder.R.drawable.aliwx_sv_wx_shiny_nor);
        } else {
            CameraHelper.setFlashlightMode(parameters, true);
            if (this.mType == 1) {
                this.iv_light.setImageResource(com.taobao.taorecorder.R.drawable.aliwx_sv_st_shiny_pre);
            } else if (this.mType == 0) {
                this.iv_light.setImageResource(com.taobao.taorecorder.R.drawable.aliwx_sv_wx_shiny_pre);
            }
        }

        this.mCamera.setParameters(parameters);
    }

    private void rotateCamera() {
        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
        int cameraCount = Camera.getNumberOfCameras();

        for (int i = 0; i < cameraCount; ++i) {
            Camera.getCameraInfo(i, cameraInfo);
            if (this.cameraPosition == SystemUtil.getCameraFacingBack()) {
                if (cameraInfo.facing == SystemUtil.getCameraFacingFront()) {
                    if (!this.removeHolderAndStopPreview(false)) {
                        this.openCameraError();
                        return;
                    }

                    this.cameraPosition = SystemUtil.getCameraFacingFront();
                    if (!this.initCamera()) {
                        this.openCameraError();
                        return;
                    }

                    this.startPreview();
                    this.iv_light.setVisibility(View.INVISIBLE);
                    if (this.mType == 1) {
                        this.iv_camerarotate.setImageResource(com.taobao.taorecorder.R.drawable.aliwx_sv_st_camera_pre);
                    } else if (this.mType == 0) {
                        this.iv_camerarotate.setImageResource(com.taobao.taorecorder.R.drawable.aliwx_sv_wx_camera_pre);
                    }
                    break;
                }
            } else if (cameraInfo.facing == SystemUtil.getCameraFacingBack()) {
                if (!this.removeHolderAndStopPreview(false)) {
                    this.openCameraError();
                    return;
                }

                this.cameraPosition = SystemUtil.getCameraFacingBack();
                if (!this.initCamera()) {
                    this.openCameraError();
                    return;
                }

                this.startPreview();
                this.iv_light.setImageResource(com.taobao.taorecorder.R.drawable.aliwx_sv_wx_shiny_nor);
                if (this.mHasFlashLight) {
                    this.iv_light.setVisibility(View.VISIBLE);
                }

                this.iv_camerarotate.setImageResource(com.taobao.taorecorder.R.drawable.aliwx_sv_wx_camera_nor);
                break;
            }
        }

    }

    private void click_delete_last_clip() {
        if (this.btn_delete_last_clip.isChecked()) {
            this.mRecorderTimeline.stopAnim();
            this.mClipManager.setLastClipSelected(true);
            this.setDeleteSelectedContent();
        } else {
            IMCommitManager.commitClick(IMCommitManager.getActivityPageName(this), "Video_DeleteLast");
            this.delete_last_clip();
            this.setDeleteNoneSelectedContent();
        }

    }

    private void setDeleteSelectedContent() {
        if (this.btn_delete_last_clip != null) {
            this.btn_delete_last_clip.setText("删除");
            this.btn_delete_last_clip.setTextColor(this.getResources().getColor(com.taobao.taorecorder.R.color.imrecorder_remove_clip));
        }

    }

    private void setDeleteNoneSelectedContent() {
        if (this.btn_delete_last_clip != null) {
            this.btn_delete_last_clip.setText("回删");

            this.btn_delete_last_clip.setTextColor(this.getResources().getColor(R.color.bghuise));
        }

    }

    private void delete_last_clip() {
        this.mClipManager.removeLastClip();
        --this.mVideoIndex;
        if (this.mVideoIndex <= 0) {
            this.btn_delete_last_clip.setVisibility(View.GONE);
//            this.iv_ok.setVisibility(View.GONE);
            this.iv_Recorderbg.setBackgroundResource(com.taobao.taorecorder.R.drawable.aliwx_sv_recorder_ovalbg_stroke);
            if (this.mType == 1) {
                this.iv_Recorder.setBackgroundResource(com.taobao.taorecorder.R.drawable.aliwx_sv_strecorder_record_ovalbg);
            } else if (this.mType == 0) {
                this.iv_Recorder.setBackgroundResource(com.taobao.taorecorder.R.drawable.aliwx_sv_recorder_record_ovalbg);
            }
        }

        if (this.mClipManager.isMaxDurationReached()) {
            this.iv_Recorder.setEnabled(false);
            this.iv_Recorder.setAlpha(0.5F);
            this.iv_Recorderbg.setAlpha(0.5F);
        } else {
            this.iv_Recorder.setEnabled(true);
            this.iv_Recorder.setAlpha(1.0F);
            this.iv_Recorderbg.setAlpha(1.0F);
        }

        this.mRecorderTimeline.stopAnim();
        this.mRecorderTimeline.startAnim();
        if (this.mClipManager.isMinDurationReached()) {
            this.iv_ok.setVisibility(View.VISIBLE);
            this.iv_ok.setAlpha(1.0F);
            this.iv_ok.setTextColor(this.getResources().getColor(R.color.bghuise));
        } else {
            this.iv_ok.setVisibility(View.VISIBLE);
            this.iv_ok.setAlpha(1.0F);
            this.iv_ok.setTextColor(this.getResources().getColor(R.color.bghuise));
        }

        this.btn_delete_last_clip.setEnabled(this.mClipManager.isUnEmpty());
        if (this.mClipManager.isEmpty()) {
            this.btn_delete_last_clip.setAlpha(0.5F);
        } else {
            this.btn_delete_last_clip.setAlpha(1.0F);
        }

        this.setRecordTime();
    }

    private void toIMPlayRecordVideoActivity() {
        int totaltime = YWRecordVideoActivity.this.mClipManager.getDuration();
        if (totaltime < 3000) {
            Toast toast = Toast.makeText(YWRecordVideoActivity.this, "录制时长不能小于3秒钟", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            return;
        }

        this.showIcon();
        this.showProgressDialog();
        (new Thread(new Runnable() {
            public void run() {

                String[] input = new String[YWRecordVideoActivity.this.mVideoIndex];

                for (int tempVideoPath = 0; tempVideoPath < YWRecordVideoActivity.this.mVideoIndex; ++tempVideoPath) {
                    input[tempVideoPath] = YWRecordVideoActivity.this.mTaoMediaRecorder.getFileDir() + File.separator + "temp_" + tempVideoPath + ".mp4";
                }

                String var7 = YWRecordVideoActivity.this.mTaoMediaRecorder.getFileDir() + File.separator + "temp_output.mp4";

                FileUtils.deleteFile(var7);
                MediaEncoderMgr.mergeMp4Files(input, var7);
                YWRecordVideoActivity.this.mTaoMediaRecorder.setOutputFile("temp_output.mp4");


                String tempJpgPath = YWRecordVideoActivity.this.mTaoMediaRecorder.getJpegFile();
                String UUIDString = UUID.randomUUID().toString().replaceAll("-", "");
                final String targetVideoPath = YWRecordVideoActivity.this.mTaoMediaRecorder.getFileDir() + File.separator + UUIDString + "_output.mp4";
                final String targetJpgPath = YWRecordVideoActivity.this.mTaoMediaRecorder.getFileDir() + File.separator + UUIDString + "_output.jpg";

                FileUtils.copyFile(tempJpgPath, targetJpgPath);
                FileUtils.copyFile(var7, targetVideoPath);

//                Toast.makeText(YWRecordVideoActivity.this,"录制完成",Toast.LENGTH_SHORT).show();

                YWRecordVideoActivity.this.getSafeHandler().post(new Runnable() {
                    public void run() {
                        YWRecordVideoActivity.this.dismissProgressDialog();
                        int size1 = 0;
                        if (targetVideoPath != null && (new File(targetVideoPath)).isFile()) {
                            File file = new File(targetVideoPath);
                            size1 = (int) file.length();
                        }

                        Log.d("videoSize", "getDuration:" + YWRecordVideoActivity.this.mClipManager.getDuration() + "");

                        int totaltime = YWRecordVideoActivity.this.mClipManager.getDuration();

                        if (totaltime < 3000) {
                            Toast toast = Toast.makeText(YWRecordVideoActivity.this, "录制时长不能小于3秒钟", Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                        } else {
                            YWRecordVideoActivity.this.mFormatBuilder.setLength(0);
                            String stime = YWRecordVideoActivity.this.mFormatter.format("%d.%d 秒", new Object[]{Integer.valueOf(totaltime / 1000), Integer.valueOf(totaltime / 100 % 10)}).toString();
                            YWRecordVideoActivity.this.tv_recordtime.setText(stime);

                            Intent intent1 = new Intent(YWRecordVideoActivity.this, EditVideoActivity.class);
                            //视频路径
                            intent1.putExtra("videoPath", targetVideoPath);
                            //图片路径
                            intent1.putExtra("framePicPath", targetJpgPath);
                            //录制时间
                            intent1.putExtra("videoDuration", stime);
                            //录制大小
                            intent1.putExtra("videoSize", size1);

                            startActivity(intent1);
                            finish();
                        }


//                        Intent intent = new Intent(YWRecordVideoActivity.this, IMPlayRecordVideoActivity.class);
//                        Bundle bundle = new Bundle();
//                        BigDecimal duration = new BigDecimal(YWRecordVideoActivity.this.mClipManager.getDuration());
//                        BigDecimal ms = new BigDecimal(1000);
//                        int returnValue = duration.divide(ms, 0, 1).intValue();
//                        bundle.putInt("videoDuration", returnValue);
//                        int actualWidth = 0;
//                        int actualHeight = 0;
//                        if (targetJpgPath != null) {
//                            BitmapFactory.Options size = new BitmapFactory.Options();
//                            size.inJustDecodeBounds = true;
//                            BitmapFactory.decodeFile(targetJpgPath, size);
//                            actualWidth = size.outWidth;
//                            actualHeight = size.outHeight;
//                        }

//                        int size1 = 0;
//                        if (targetVideoPath != null && (new File(targetVideoPath)).isFile()) {
//                            File file = new File(targetVideoPath);
//                            size1 = (int) file.length();
//                        }

//                        bundle.putInt("framePicWidth", actualWidth);
//                        bundle.putInt("framePicHeight", actualHeight);
//                        bundle.putInt("videoSize", size1);
//                        IMCommitManager.commitEvent("Finish_Record", "Page_WangXin_VideoRecord", (long) YWRecordVideoActivity.this.mClipManager.getDuration(), "size", Integer.toString(size1));
//                        bundle.putCharSequence("videoPath", targetVideoPath);
//                        bundle.putCharSequence("framePicPath", targetJpgPath);
//                        bundle.putCharSequence("uploadID", YWRecordVideoActivity.this.getIntent().getStringExtra("uploadID"));
//                        bundle.putCharSequence("uploadTarget", YWRecordVideoActivity.this.getIntent().getStringExtra("uploadTarget"));

//                        if (YWRecordVideoActivity.this.mType == 1) {
//                            if (Config.isDebug()) {
//                                Log.d(YWRecordVideoActivity.this.TAG + "@sv", "Nav.from(YWRecordVideoActivity.this) to IMPlayRecordVideoActivity; http://svideo.m.taobao.com/av/imstpreview.html");
//                            }
//
////                            Nav.from(YWRecordVideoActivity.this).forResult(101).withExtras(bundle).toUri("http://svideo.m.taobao.com/av/imstpreview.html");
//                        } else {
//                            if (Config.isDebug()) {
//                                Log.d(YWRecordVideoActivity.this.TAG + "@sv", "start.from(YWRecordVideoActivity.this) to IMPlayRecordVideoActivity");
//                            }
//
//                            intent.putExtras(bundle);
//                            YWRecordVideoActivity.this.startActivityForResult(intent, 101);
//                            YWRecordVideoActivity.this.overridePendingTransition(com.taobao.taorecorder.R.anim.taorecorder_push_left_in, com.taobao.taorecorder.R.anim.taorecorder_push_left_out);
//                        }

                    }
                });
            }
        })).start();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (101 == requestCode) {
            if (-1 == resultCode) {
                this.setResult(-1, data);
                this.finish();
                return;
            }

            if (resultCode == 0 && data != null) {
                Bundle bundle = data.getExtras();
                if (bundle != null) {
                    this.mPubTitle = bundle.getString("pub_title_key");
                }
            }
        }

    }

    private void pressback() {
        if (!this.isVideoRecording) {
            if (this.mClipManager.isEmpty()) {
                this.finish();
            } else if (this.mType == 0) {
                WxAlertDialog.Builder newDialog = new WxAlertDialog.Builder(this);
                newDialog.setMessage(this.getResources().getString(com.taobao.taorecorder.R.string.imrecorder_dlg_record_quit_message)).setCancelable(false).setPositiveButton(com.taobao.taorecorder.R.string.taorecorder_dlg_record_quit_confirm, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                        FileUtils.clearTempFiles(YWRecordVideoActivity.this.mTaoMediaRecorder.getFileDir());
                        YWRecordVideoActivity.this.finish();
                    }
                }).setNegativeButton(com.taobao.taorecorder.R.string.taorecorder_dlg_record_quit_cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
                AlertDialog dialog = newDialog.create();
                dialog.show();
            } else {
                final NewDialog newDialog1 = new NewDialog(this);
                newDialog1.setLeftMsg(this.getString(com.taobao.taorecorder.R.string.taorecorder_dlg_record_quit_confirm));
                newDialog1.setRightMsg(this.getString(com.taobao.taorecorder.R.string.taorecorder_dlg_record_quit_cancel));
                newDialog1.setMsg(this.getString(com.taobao.taorecorder.R.string.imrecorder_dlg_record_quit_message));
                newDialog1.setRightMsgListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        newDialog1.dismiss();
                    }
                });
                newDialog1.setLeftMsgListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        newDialog1.dismiss();
                        FileUtils.clearTempFiles(YWRecordVideoActivity.this.mTaoMediaRecorder.getFileDir());
                        YWRecordVideoActivity.this.finish();
                    }
                });
                newDialog1.show();
            }

        }
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == 4) {
            if (this.mProgressDialogView != null && this.mProgressDialogView.isShown()) {
                return true;
            } else {
                this.pressback();
                return true;
            }
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }

    private void requestAudioFocus() {
        this.mAudioManager.requestAudioFocus(this.mAudioFocusListener, 3, 1);
    }

    private void abandonAudioFocus() {
        if (this.mAudioManager != null) {
            this.mAudioManager.abandonAudioFocus(this.mAudioFocusListener);
        }

    }

    public void onSizeChanged(View view, int w, int h, int oldw, int oldh) {
        float scale_x = (float) w / 480.0F;
        float scale_y = (float) h / 480.0F;
        if (scale_x != 0.0F && scale_y != 0.0F) {
            short rotation = 90;
            short preview_width = 640;
            short preivew_height = 480;
            short surface_width;
            short surface_height;
            switch (rotation) {
                case 90:
                case 270:
                    surface_width = preivew_height;
                    surface_height = preview_width;
                    break;
                default:
                    surface_width = preview_width;
                    surface_height = preivew_height;
            }

            FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) this.mCameraPreview.getLayoutParams();
            lp.gravity = 51;
            lp.width = (int) (scale_x * (float) surface_width);
            lp.height = (int) (scale_y * (float) surface_height);
            lp.setMargins(0, 0, 0, 0);
            this.mCameraPreview.setLayoutParams(lp);
        }
    }

    private void sendErrorBroadcast() {
        Intent intent = new Intent("com.taobao.taorecorder.action.error_action");
        intent.putExtra("errorCode", "2002");
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

    private void initProgressDialog() {
        this.mProgressDialogView = this.findViewById(com.taobao.taorecorder.R.id.view_dialog);
        int ringWidth = DensityUtil.dip2px(this, 2.0F);
        this.mProgressDrawable = new CircularProgressDrawable(-1, (float) ringWidth);
        this.mProgressView = (ImageView) this.findViewById(com.taobao.taorecorder.R.id.taorecorder_uik_circularProgress);
        this.mProgressTextView = (TextView) this.findViewById(com.taobao.taorecorder.R.id.taorecorder_uik_progressText);
        this.mProgressView.setImageDrawable(this.mProgressDrawable);
        this.mProgressTextView.setText(this.getString(com.taobao.taorecorder.R.string.taorecorder_doing));
    }

    private void showProgressDialog() {
        if (this.mProgressDialogView != null && !this.mProgressDialogView.isShown()) {
            this.mProgressDrawable.start();
            this.mProgressDialogView.setVisibility(View.VISIBLE);
        }

    }

    private void dismissProgressDialog() {
        if (this.mProgressDialogView != null && this.mProgressDialogView.isShown()) {
            this.mProgressDialogView.setVisibility(View.GONE);
            this.mProgressDrawable.stop();
        }

    }

    private void hideIcon() {
        this.iv_back.setVisibility(View.INVISIBLE);
        this.iv_light.setVisibility(View.INVISIBLE);
        this.iv_camerarotate.setVisibility(View.INVISIBLE);
        this.btn_delete_last_clip.setAlpha(0.5F);
        this.iv_ok.setAlpha(1.0F);
        this.iv_ok.setTextColor(this.getResources().getColor(R.color.bghuise));
    }

    private void showIcon() {
        this.iv_back.setVisibility(View.VISIBLE);
        this.iv_camerarotate.setVisibility(View.VISIBLE);
        this.btn_delete_last_clip.setAlpha(1.0F);
        if (this.mClipManager.isMinDurationReached()) {
            this.iv_ok.setAlpha(1.0F);
            this.iv_ok.setTextColor(this.getResources().getColor(R.color.bghuise));
        } else {
            this.iv_ok.setAlpha(1.0F);
            this.iv_ok.setTextColor(this.getResources().getColor(R.color.bghuise));
        }

        if (this.cameraPosition == SystemUtil.getCameraFacingBack()) {
            if (this.mHasFlashLight) {
                this.iv_light.setVisibility(View.VISIBLE);
            }

            this.iv_camerarotate.setImageResource(com.taobao.taorecorder.R.drawable.aliwx_sv_wx_camera_nor);
        } else {
            this.iv_light.setVisibility(View.INVISIBLE);
            if (this.mType == 1) {
                this.iv_camerarotate.setImageResource(com.taobao.taorecorder.R.drawable.aliwx_sv_st_camera_pre);
            } else if (this.mType == 0) {
                this.iv_camerarotate.setImageResource(com.taobao.taorecorder.R.drawable.aliwx_sv_wx_camera_pre);
            }
        }

    }

    private void resetRecorderState() {
        this.hideIcon();
        this.btn_delete_last_clip.setChecked(false);
        this.btn_delete_last_clip.setEnabled(false);
        this.mClipManager.setLastClipSelected(false);
    }
}

