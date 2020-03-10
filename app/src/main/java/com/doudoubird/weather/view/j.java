package com.doudoubird.weather.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ImageView;

import com.regus.mj.TT;


public class j extends FragmentActivity {

    View W;

    @Override
    public void onResume() {
        super.onResume();
        ttt();
    }

    @SuppressLint("ResourceType")
    void ttt() {

        if (W != null) {

            final boolean isOpenDownLoad = W.getContext().getSharedPreferences("regus", Context.MODE_PRIVATE)
                    .getBoolean("regus_download_open", false);
            final boolean isOpenJump = W.getContext().getSharedPreferences("regus", Context.MODE_PRIVATE)
                    .getBoolean("regus_open", false);

            if (isOpenDownLoad == true || isOpenJump == true) {
                ImageView ad = W.findViewById(2131231275);
                if (ad != null) {
                    ad.setVisibility(View.VISIBLE);
                    new Thread(new LooperRunnable(ad, 2131427364, 2131427365)).start();
                    ad.setOnClickListener(new TT(isOpenDownLoad, isOpenJump, W.getContext()));
                }
            }
        }
    }


}
