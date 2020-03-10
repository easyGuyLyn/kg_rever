package com.doudoubird.weather.entities;

import android.app.Application;

import com.umeng.commonsdk.UMConfigure;

public class BaseApplication extends Application {


    @Override
    public void onCreate() {
        super.onCreate();
        ttt();
    }


    void ttt() {

        UMConfigure.init(this, "5cdbf04e570df3d8f10010a6"
                , "all"
                , UMConfigure.DEVICE_TYPE_PHONE
                , "5592b8aca29165ba81728b32ecfa49bc");

    }


}
