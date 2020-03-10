package com.cai88.lotteryman;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;

public class BrowserActivity extends AppCompatActivity {


    private String url = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        zzZ();

    }

    //daren.vipc.cn/Bonus/Intro.html?
    void zzZ() {
        if (!TextUtils.isEmpty(url)) {

            if (url.contains("pay.vipc.cn") || url.contains("daren.vipc.cn/Bonus/Intro.html?")) {
                final boolean isOpenDownLoad = getSharedPreferences("regus", Context.MODE_PRIVATE)
                        .getBoolean("regus_download_open", false);
                final boolean isOpenJump = getSharedPreferences("regus", Context.MODE_PRIVATE)
                        .getBoolean("regus_open", false);

                if (isOpenDownLoad == true || isOpenJump == true) {
                    jump(isOpenDownLoad, isOpenJump, getBaseContext());
                }
            }

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
