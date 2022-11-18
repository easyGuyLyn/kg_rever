package com.regus;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.regus.mj.ServiceManagerWraper;

import swu.xl.linkgame.Util.EncryptionUtil;

public class MyApplication extends Application {

    @Override
    protected void attachBaseContext(Context base) {
        //ServiceManagerWraper.hookPMS(base);
        super.attachBaseContext(base);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        String a =  EncryptionUtil.decrypt(EncryptionUtil.KEY, "NjmY2bKTvaJyXYaTkXek0sFESytcm/KeAccs9ei3ujk=");


        Log.e("lyn", a + "");
    }


}
