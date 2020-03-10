package com.ssports.mobile.common.entity;

import java.io.Serializable;

public class SSBaseEntity implements Serializable {

    private static final String MUTEXLOGOUT = "-6";
    private String requsetAction;
    private String resCode;
    private String resMessage;
    private long serverTime;
    private String userToken;

    public enum SmsType {
        RESGISTER("register"),
        LOGINBYCODE("loginByCode"),
        RESETPASSWORD("resetPassword");

        private String value;

        private SmsType(String value2) {
            this.value = value2;
        }

        public String getValue() {
            return this.value;
        }
    }

    public boolean isOK() {
        if (this.resCode == null) {
            return true;
        }
        return this.resCode.equals("200");
    }

    public String getResCode() {
        return this.resCode;
    }

    public void setResCode(String resCode2) {
        this.resCode = resCode2;
    }

    public String getResMessage() {
        return this.resMessage;
    }

    public void setResMessage(String resMessage2) {

    }

    public String getRequestAction() {
        return this.requsetAction;
    }

    public void setRequestAction(String requestAction) {
        this.requsetAction = requestAction;
    }

    public String getUserToken() {
        return this.userToken;
    }

    public void setUserToken(String userToken2) {
        this.userToken = userToken2;
    }

    public long getServerTime() {
        return this.serverTime;
    }

    public void setServerTime(long serverTime2) {
        this.serverTime = serverTime2;
    }

}
