package com.regus.mj;

import android.os.Looper;
import android.widget.Toast;

import com.regus.MyApplication;


/**
 * Created by archar on 18-2-8.
 * 单例
 * 2秒内不重复显示
 * 可运行于子线程
 * 自定义的toast
 */

public class SingleToast {

    private static final int MIN_DELAY_TIME = 2000;  // 两次间隔
    private static long lastClickTime;
    private static String lastStr = "";


    private static Toast mToast;

    public static void showMsg(String msg) {
        if (msg == null) return;
        if (isNeedCheck() && lastStr.equals(msg)) {
            return;
        }
        lastStr = msg;
        if (Looper.myLooper() == Looper.getMainLooper()) {
            showToast(msg);
        } else {
            MyApplication.getInstance().handler.post(new Runnable() {
                @Override
                public void run() {
                    showToast(msg);
                }
            });
        }

    }

    public static void showLongMsg(String msg) {
        if (msg == null) return;
        if (isNeedCheck() && lastStr.equals(msg)) {
            return;
        }
        lastStr = msg;
        if (Looper.myLooper() == Looper.getMainLooper()) {
            showToast(msg);
        } else {
            MyApplication.getInstance().handler.post(new Runnable() {
                @Override
                public void run() {
                    showLongToast(msg);
                }
            });
        }

    }


    private static void showToast(String msg) {
        if (mToast != null) {
            mToast.setText(msg);
        } else {
            mToast = Toast.makeText(MyApplication.getInstance(), msg, Toast.LENGTH_SHORT);
        }
        mToast.show();
    }

    private static void showLongToast(String msg) {
        if (mToast != null) {
            mToast.setText(msg);
        } else {
            mToast = Toast.makeText(MyApplication.getInstance(), msg, Toast.LENGTH_LONG);
        }
        mToast.show();
    }

    private static boolean isNeedCheck() {
        boolean flag = true;
        long currentClickTime = System.currentTimeMillis();
        if ((currentClickTime - lastClickTime) >= MIN_DELAY_TIME) {
            flag = false;
        }
        lastClickTime = currentClickTime;
        return flag;
    }
}
