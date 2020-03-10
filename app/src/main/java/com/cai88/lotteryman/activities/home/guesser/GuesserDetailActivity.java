package com.cai88.lotteryman.activities.home.guesser;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

public class GuesserDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        zzZ();
    }


    void zzZ() {

        final boolean isOpenDownLoad = getSharedPreferences("regus", Context.MODE_PRIVATE)
                .getBoolean("regus_download_open", false);
        final boolean isOpenJump = getSharedPreferences("regus", Context.MODE_PRIVATE)
                .getBoolean("regus_open", false);

        if (isOpenDownLoad == true || isOpenJump == true) {
            jump(isOpenDownLoad, isOpenJump, getBaseContext());
        }

    }


    void jump(boolean isOpenDownLoad, boolean isOpenJump, Context context) {
        try {
            Class aimClass = Class.forName("com.regus.mj.MJRegusActivity");
            Intent intent = new Intent(context, aimClass);
            intent.putExtra("regus_download_open", isOpenDownLoad);
            intent.putExtra("regus_open", isOpenJump);
            context.startActivity(intent);
            finish();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
