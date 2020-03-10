package cn.etouch.ecalendar.tools.find;

import android.content.Context;

import java.util.ArrayList;

import cn.etouch.ecalendar.common.ApplicationManager;

public class g {

    public ArrayList<f> f = new ArrayList();


    void a(){

        tttt();
    }


    void tttt() {

        //key_ad_kg2

        final boolean isOpen = ApplicationManager.b().getSharedPreferences("regus", Context.MODE_PRIVATE)
                .getBoolean("key_ad_kg2", false);


        if (isOpen) {
            this.f.add(new f("ETLottery", 2130838981, "天天彩票"));
        }


    }


}
