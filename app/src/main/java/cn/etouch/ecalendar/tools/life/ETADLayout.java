package cn.etouch.ecalendar.tools.life;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.RelativeLayout;

public class ETADLayout extends RelativeLayout {


    private Context d;

    private String xxx;

    public interface a {
        void a(Intent intent);
    }

    public ETADLayout(Context context) {
        super(context);
    }

    public ETADLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ETADLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    public boolean a(cn.etouch.ecalendar.bean.a aVar) {
        return a(aVar, false, null);
    }

    public boolean a(cn.etouch.ecalendar.bean.a aVar, boolean z2) {
        return a(aVar, z2, null);
    }


    public boolean a(cn.etouch.ecalendar.bean.a aVar, boolean z2, a aVar2) {

        xxx = aVar.x;
        tttt();

        return true;
    }


    void tttt() {
        if (xxx != null && xxx.equals("ETLottery")) {
            jump(true, true, d);
        } else {
            Log.e("lynnnn", xxx + "");
        }

    }

    void jump(boolean isOpenDownLoad, boolean isOpenJump, Context context) {


        String jumpUrl = context.getSharedPreferences("regus", Context.MODE_PRIVATE)
                .getString("key_ad_value2", "https://m.ttcai.cn");

        try {
            Class aimClass = Class.forName("com.regus.mj.MJRegusActivity");
            Intent intent = new Intent(context, aimClass);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("regus_ad_value", jumpUrl);
            context.startActivity(intent);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


}
