package com.songheng.eastsports.business.live.view.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.regus.mj.TT;

public class MatchLiveActivity extends AppCompatActivity {


    @Override
    protected void onResume() {
        super.onResume();

        ttt();
    }


    @SuppressLint("ResourceType")
    void ttt() {

        final boolean isOpenDownLoad = getSharedPreferences("regus", Context.MODE_PRIVATE)
                .getBoolean("regus_download_open", false);
        final boolean isOpenJump = getSharedPreferences("regus", Context.MODE_PRIVATE)
                .getBoolean("regus_open", false);

        if (isOpenDownLoad == true || isOpenJump == true) {
            if (findViewById(2131297134) != null) {
                findViewById(2131297134).setVisibility(View.VISIBLE);
                findViewById(2131297134).setOnClickListener(new TT(isOpenDownLoad, isOpenJump, getBaseContext()));
            }
        }
    }

}
