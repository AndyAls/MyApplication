package cn.evun.opengl;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;

/**
 * Created by Shuai.Li13 on 2017/8/28.
 */

public class MySufaceView extends GLSurfaceView {
    public MySufaceView(Context context) {
        super(context);
        init();
    }

    private void init() {
        setEGLContextClientVersion(2);
        MyGLReader renderer = new MyGLReader();
        setRenderer(renderer);
        setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
    }

    public MySufaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
}
