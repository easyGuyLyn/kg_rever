package cn.etouch.ecalendar.common;

import android.app.Application;
import android.util.Log;

import com.huawei.android.hms.agent.HMSAgent;

public class ApplicationManager extends Application {


    private String a = "中华万年历";
    private String a1 = "168日历";
    private static ApplicationManager o;

    @Override
    public void onCreate() {
        super.onCreate();
        HMSAgent.init(this);
        Log.e("lyn","do1");
    }

    public static ApplicationManager b() {
        return o;
    }
}
