package com.bytedance.applog;


import android.text.TextUtils;
import java.util.HashMap;
import java.util.Map.Entry;
import org.json.JSONException;
import org.json.JSONObject;

public class WhalerGameHelper {
    public static final String GT_AD_BUTTON_CLICK = "gt_ad_button_click";
    public static final String GT_AD_BUTTON_SHOW = "gt_ad_button_show";
    public static final String GT_AD_REQUEST = "gt_ad_request";
    public static final String GT_AD_SEND = "gt_ad_send";
    public static final String GT_AD_SHOW = "gt_ad_show";
    public static final String GT_AD_SHOW_END = "gt_ad_show_end";
    public static final String GT_END_PLAY = "gt_end_play";
    public static final String GT_INIT_INFO = "gt_init_info";
    public static final String GT_LEVELUP = "gt_levelup";
    public static final String GT_SCENE_LEVUP = "gt_scene_levup";
    public static final String GT_START_PLAY = "gt_start_play";
    public static final String PURCHASE = "purchase";

    public enum Result {
        UNCOMPLETED("uncompleted"),
        SUCCESS("success"),
        FAIL("fail");

        final String gameResult;

        private Result(String str) {
            this.gameResult = str;
        }
    }

    public static void adRequest(int i, int i2, int i3, String str, int i4, HashMap<String, Object> hashMap) {
        try {
            JSONObject jSONObject = new JSONObject();
            fillOtherParams(hashMap, jSONObject);
            fillCommonParams(i, i2, i3, jSONObject);
            jSONObject.put("ad_type", str);
            jSONObject.put("code_id", i4);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private static void fillOtherParams(HashMap<String, Object> hashMap, JSONObject jSONObject) {
        if (hashMap != null && !hashMap.isEmpty()) {
            for (Entry entry : hashMap.entrySet()) {
                if (!TextUtils.isEmpty((CharSequence) entry.getKey())) {
                    try {
                        jSONObject.put((String) entry.getKey(), entry.getValue());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public static void adSend(int i, int i2, int i3, String str, int i4, String str2, HashMap<String, Object> hashMap) {
        try {
            JSONObject jSONObject = new JSONObject();
            fillOtherParams(hashMap, jSONObject);
            fillCommonParams(i, i2, i3, jSONObject);
            jSONObject.put("ad_type", str);
            jSONObject.put("code_id", i4);
            jSONObject.put("ad_code", str2);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static void adButtonShow(int i, int i2, int i3, String str, String str2, String str3, int i4, HashMap<String, Object> hashMap) {
        try {
            JSONObject jSONObject = new JSONObject();
            fillOtherParams(hashMap, jSONObject);
            fillCommonParams(i, i2, i3, jSONObject);
            jSONObject.put("ad_type", str);
            jSONObject.put("ad_position_type", str2);
            jSONObject.put("ad_position", str3);
            jSONObject.put("code_id", i4);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static void adButtonClick(int i, int i2, int i3, String str, String str2, String str3, int i4, HashMap<String, Object> hashMap) {
        try {
            JSONObject jSONObject = new JSONObject();
            fillOtherParams(hashMap, jSONObject);
            fillCommonParams(i, i2, i3, jSONObject);
            jSONObject.put("ad_type", str);
            jSONObject.put("ad_position_type", str2);
            jSONObject.put("ad_position", str3);
            jSONObject.put("code_id", i4);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static void adShow(int i, int i2, int i3, String str, String str2, String str3, int i4, String str4, HashMap<String, Object> hashMap) {
        try {
            JSONObject jSONObject = new JSONObject();
            fillOtherParams(hashMap, jSONObject);
            fillCommonParams(i, i2, i3, jSONObject);
            jSONObject.put("ad_type", str);
            jSONObject.put("ad_position_type", str2);
            jSONObject.put("ad_position", str3);
            jSONObject.put("code_id", i4);
            jSONObject.put("ad_code", str4);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static void adShowEnd(int i, int i2, int i3, String str, String str2, String str3, String str4, int i4, HashMap<String, Object> hashMap) {
        try {
            JSONObject jSONObject = new JSONObject();
            fillOtherParams(hashMap, jSONObject);
            fillCommonParams(i, i2, i3, jSONObject);
            jSONObject.put("ad_type", str);
            jSONObject.put("ad_position_type", str2);
            jSONObject.put("ad_position", str3);
            jSONObject.put("result", str4);
            jSONObject.put("code_id", i4);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static void levelUp(int i, int i2, int i3, int i4, String str, int i5, HashMap<String, Object> hashMap) {
        try {
            JSONObject jSONObject = new JSONObject();
            fillOtherParams(hashMap, jSONObject);
            fillCommonParams(i, i2, i3, jSONObject);
            jSONObject.put("get_exp", i4);
            jSONObject.put("method", str);
            jSONObject.put("aflev", i5);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static void sceneLevUp(int i, int i2, int i3, int i4, String str, int i5, int i6, HashMap<String, Object> hashMap) {
        try {
            JSONObject jSONObject = new JSONObject();
            fillOtherParams(hashMap, jSONObject);
            fillCommonParams(i, i2, i3, jSONObject);
            jSONObject.put("get_exp", i4);
            jSONObject.put("method", str);
            jSONObject.put("scene_aflev", i5);
            jSONObject.put("reset", i6);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static void startPlay(int i, int i2, int i3, String str, int i4, String str2, HashMap<String, Object> hashMap) {
        try {
            JSONObject jSONObject = new JSONObject();
            fillOtherParams(hashMap, jSONObject);
            fillCommonParams(i, i2, i3, jSONObject);
            jSONObject.put("ectype_type", str);
            jSONObject.put("ectype_id", i4);
            jSONObject.put("ectype_name", str2);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static void endPlay(int i, int i2, int i3, String str, int i4, String str2, Result result, int i5, int i6, int i7, boolean z, int i8, String str3, HashMap<String, Object> hashMap) {
        try {
            JSONObject jSONObject = new JSONObject();
            fillOtherParams(hashMap, jSONObject);
            fillCommonParams(i, i2, i3, jSONObject);
            jSONObject.put("ectype_type", str);
            jSONObject.put("ectype_id", i4);
            jSONObject.put("ectype_name", str2);
            jSONObject.put("result", result.gameResult);
            jSONObject.put("score", i5);
            jSONObject.put("duration", i6);
            jSONObject.put("kill_num", i7);
            jSONObject.put("passed", z ? "yes" : "no");
            jSONObject.put("percentage", i8);
            jSONObject.put("rank", str3);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static void purchase(int i, int i2, int i3, String str, String str2, String str3, int i4, String str4, String str5, String str6, int i5, HashMap<String, Object> hashMap) {
        try {
            JSONObject jSONObject = new JSONObject();
            fillOtherParams(hashMap, jSONObject);
            fillCommonParams(i, i2, i3, jSONObject);
            jSONObject.put("content_type", str);
            jSONObject.put("content_name", str2);
            jSONObject.put("content_num", i4);
            jSONObject.put("content_id", str3);
            jSONObject.put("payment_channel", str4);
            jSONObject.put("currency", str5);
            jSONObject.put("is_success", str6);
            jSONObject.put("currency_amount", i5);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static void gameInitInfo(int i, int i2, int i3, String str, int i4, String str2, HashMap<String, Object> hashMap) {
        try {
            JSONObject jSONObject = new JSONObject();
            fillOtherParams(hashMap, jSONObject);
            fillCommonParams(i, i2, i3, jSONObject);
            jSONObject.put("coin_type", str);
            jSONObject.put("coin_left", i4);
            jSONObject.put("role_id", str2);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static void gameInitInfo(int i, int i2, int i3, String str, int i4, HashMap<String, Object> hashMap) {
        try {
            JSONObject jSONObject = new JSONObject();
            fillOtherParams(hashMap, jSONObject);
            fillCommonParams(i, i2, i3, jSONObject);
            jSONObject.put("coin_type", str);
            jSONObject.put("coin_left", i4);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private static void fillCommonParams(int i, int i2, int i3, JSONObject jSONObject) {
    }
}