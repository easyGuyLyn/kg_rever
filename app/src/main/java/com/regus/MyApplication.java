package com.regus;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.util.Log;

import com.regus.mj.ServiceManagerWraper;

import swu.xl.linkgame.Util.EncryptionUtil;

public class MyApplication extends Application {
    public static Handler handler = new Handler();
    @Override
    protected void attachBaseContext(Context base) {
        //ServiceManagerWraper.hookPMS(base);
        super.attachBaseContext(base);
    }

    private static MyApplication instance;


    public static MyApplication getInstance() {
        return instance;
    }



    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
      //  Utils.init(this);
    }


}
