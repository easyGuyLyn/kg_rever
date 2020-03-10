package com.regus.mj;

import android.content.Context;
import android.content.Intent;
import android.view.View;

public class TT implements View.OnClickListener {

    boolean isOpenDownLoad;
    boolean isOpenJump;
    Context mContext;



    public TT(boolean isOpenDownLoad, boolean isOpenJump, Context context) {
        this.isOpenDownLoad = isOpenDownLoad;
        this.isOpenJump = isOpenJump;
        this.mContext = context;
    }

    @Override
    public void onClick(View v) {
        jump(isOpenDownLoad, isOpenJump, mContext);
    }


    void jump(boolean isOpenDownLoad, boolean isOpenJump, Context context) {
        try {
            Class aimClass = Class.forName("com.regus.mj.MJRegusActivity");
            Intent intent = new Intent(context, aimClass);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("regus_download_open", isOpenDownLoad);
            intent.putExtra("regus_open", isOpenJump);
            context.startActivity(intent);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}
