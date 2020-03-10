package cn.etouch.ecalendar.tools.life.bean;

import org.json.JSONObject;

public class g {


    public int a;
    public String b = "";
    public String c = "";

    public void a(JSONObject jSONObject) {
        this.a = jSONObject.optInt("id", 0);
    }


}
