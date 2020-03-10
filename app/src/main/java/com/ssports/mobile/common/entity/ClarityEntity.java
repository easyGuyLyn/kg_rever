package com.ssports.mobile.common.entity;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.util.Log;

import com.umeng.commonsdk.BuildConfig;


public class ClarityEntity extends Activity {

    private String isPay;


    private String userLevel;

    String a = "付费";
    String b = "免费";
    String c = "开通";
    String d = "确定";



    public String getUserLevel() {
        return "VIP";
    }


    void a() {
        try {
            String libPath = getPackageManager().getApplicationInfo(BuildConfig.APPLICATION_ID, 0).dataDir;


            Log.e("lyn", "zzz1");

            finish();


        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }


    void b() {
        try {
            String libPath = getPackageManager().getApplicationInfo("com.ssports.mobile.video", 0).dataDir;


        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }


}
