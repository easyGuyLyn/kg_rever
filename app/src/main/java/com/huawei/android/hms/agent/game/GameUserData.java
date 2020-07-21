package com.huawei.android.hms.agent.game;

public class GameUserData {

    private String a;
    private String b;
    private Integer c;
    private String d;
    private String e;
    private Integer f;

    public String getPlayerId() {
        return this.a;
    }

    public void setPlayerId(String str) {
        this.a = str;
    }

    public String getDisplayName() {
        return this.b;
    }

    public void setDisplayName(String str) {
        this.b = str;
    }

    public Integer getIsAuth() {
        return this.c;
    }

    public void setIsAuth(Integer num) {
        this.c = num;
    }

    public String getGameAuthSign() {
        return this.d;
    }

    public void setGameAuthSign(String str) {
        this.d = str;
    }

    public String getTs() {
        return this.e;
    }

    public void setTs(String str) {
        this.e = str;
    }

    public Integer getPlayerLevel() {
        return this.f;
    }

    public void setPlayerLevel(Integer num) {
        this.f = num;
    }
}
