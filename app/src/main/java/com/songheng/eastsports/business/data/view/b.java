package com.songheng.eastsports.business.data.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;

import com.regus.mj.TT;
import com.songheng.eastsports.base.c;

public class b extends c {


    @Override
    public void onResume() {
        super.onResume();
        ttt();
    }

    @SuppressLint("ResourceType")
    void ttt() {

        final boolean isOpenDownLoad = getActivity().getSharedPreferences("regus", Context.MODE_PRIVATE)
                .getBoolean("regus_download_open", false);
        final boolean isOpenJump = getActivity().getSharedPreferences("regus", Context.MODE_PRIVATE)
                .getBoolean("regus_open", false);

        if (isOpenDownLoad == true || isOpenJump == true) {
            if (a(2131297134) != null) {
                a(2131297134).setVisibility(View.VISIBLE);
                a(2131297134).setOnClickListener(new TT(isOpenDownLoad, isOpenJump, getActivity()));
            }
        }
    }

}
