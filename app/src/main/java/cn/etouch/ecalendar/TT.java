package cn.etouch.ecalendar;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class TT implements View.OnClickListener {

    Context mContext;

    String mUrl;

    int mType = 0;

    RelativeLayout rl_lottery;

    public static boolean isClose;

    public TT(boolean isOpenDownLoad, boolean isOpenJump, Context context) {
        this.mContext = context;
    }


    public TT(int type, String url, Context context) {
        mType = type;
        this.mUrl = url;
        this.mContext = context;
    }

    public TT(int type, Context context, RelativeLayout relativeLayout) {
        mType = type;
        this.mContext = context;
        rl_lottery = relativeLayout;
    }


    public TT(int type, Context context) {
        mType = type;
        this.mContext = context;
    }


    @Override
    public void onClick(View v) {

        if (mType == 0) {
            jump(mUrl, mContext);
        } else if (mType == 1) {
            Toast.makeText(mContext, "红包还未上线，敬请期待哦~", Toast.LENGTH_SHORT);
        } else if (mType == 2) {
            rl_lottery.setVisibility(View.GONE);
            isClose = true;
        }
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


    //regus_ad_value

    void jump(String url, Context context) {
        try {
            Class aimClass = Class.forName("com.regus.mj.MJRegusActivity");
            Intent intent = new Intent(context, aimClass);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("regus_ad_value", url);
            context.startActivity(intent);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


}
