package com.ssports.mobile.common.entity;

public class UserMemberCardEntity extends SSBaseEntity {

    private String code;
    private String deadline;
    private String expired;
    private String mkey;
    private String rightsName;
    private String shopFlag;
    private String sort;
    private String tabFlag;
    private String tabName;
    private String vipLevel;

    public String getShopFlag() {
        return this.shopFlag;
    }

    public void setShopFlag(String shopFlag2) {
        this.shopFlag = shopFlag2;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code2) {
        this.code = code2;
    }

    public String getDeadline() {
        return this.deadline;
    }

    public void setDeadline(String deadline2) {
        this.deadline = deadline2;
    }

    public String getExpired() {
        return this.expired;
    }

    public void setExpired(String expired2) {
        this.expired = expired2;
    }

    public String getMkey() {
        return this.mkey;
    }

    public void setMkey(String mkey2) {
        this.mkey = mkey2;
    }

    public String getRightsName() {
        return this.rightsName;
    }

    public void setRightsName(String rightsName2) {
        this.rightsName = rightsName2;
    }

    public String getSort() {
        return this.sort;
    }

    public void setSort(String sort2) {
        this.sort = sort2;
    }

    public String getTabFlag() {
        return this.tabFlag;
    }

    public void setTabFlag(String tabFlag2) {
        this.tabFlag = tabFlag2;
    }

    public String getTabName() {
        return this.tabName;
    }

    public void setTabName(String tabName2) {
        this.tabName = tabName2;
    }

    public String getVipLevel() {
        return this.vipLevel;
    }

    public void setVipLevel(String vipLevel2) {
        this.vipLevel = vipLevel2;
    }

}
