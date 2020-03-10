package com.ssports.mobile.video.matchvideomodule.live.activity;

import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class LiveNoFootballVideoActivity extends AppCompatActivity {


    String videoUrl = null;


    JSONObject matchDetailJson_a = null;
    JSONObject urlKeyJson_a = null;


    public void setMtachData(String matchDetailStr, String matchLiveKeyStr) {

        //    new FuzhiUtil().fuzhu1(matchDetailStr, matchLiveKeyStr, matchDetailStr_lyn, matchLiveKeyStr_lyn);

        JSONObject matchDetailJson = null;
        JSONObject urlKeyJson = null;

        matchDetailJson_a = JSON.parseObject(matchDetailStr);

        urlKeyJson_a = JSON.parseObject(matchLiveKeyStr);


        matchDetailJson_a = matchDetailJson;
        urlKeyJson_a = urlKeyJson;

    }


//    void ttt(String a, String b) {
//        matchDetailStr_lyn = a;
//        matchLiveKeyStr_lyn = b;
//    }





    void setAdClickInfo(){
        a();
    }



    void a() {

        if (matchDetailJson_a != null) {

            Log.e("lyn", matchDetailJson_a.toString());

            if (matchDetailJson_a.getJSONObject("retData").getJSONObject("security") != null) {

                if (matchDetailJson_a.getJSONObject("retData").getJSONObject("security").getJSONObject("en_room_1") != null) {

                    if (matchDetailJson_a.getJSONObject("retData")
                            .getJSONObject("security").getJSONObject("en_room_1").containsKey("line_2_1080")) {
                        String line_2_1080 = matchDetailJson_a.getJSONObject("retData")
                                .getJSONObject("security").getJSONObject("en_room_1").getString("line_2_1080");

                        if (!TextUtils.isEmpty(line_2_1080)) {

                            videoUrl = line_2_1080;

                            Log.e("lyn  video urlllll", videoUrl);

                        }
                    }
                }
            }
        }


    }


    void setta() {


    }


    public void preparePlay() {

    }

}
