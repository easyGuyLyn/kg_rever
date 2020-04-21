package org.cocos2dx.lib;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.widget.Toast;

public class Cocos2dxGLSurfaceView extends GLSurfaceView {



    private long time1 = 0L;

    public Cocos2dxGLSurfaceView(Context context) {
        super(context);
    }

    public Cocos2dxGLSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            long time2 = System.currentTimeMillis();
            if (time2 - time1 > 2000) {
                time1 = time2;
                Toast.makeText(getContext(), "再按一次退出应用", Toast.LENGTH_LONG);
            } else {
                //退出应用程序
                android.os.Process.killProcess(android.os.Process.myPid());
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


}
