package com.ssports.mobile.common.entity;

import android.text.TextUtils;

import java.io.Serializable;
import java.util.List;

public class UserEntity extends SSBaseEntity {

    private RetData retData;

    public static class MemberShipBean {
        private String diamond;
        private String month;
        private List<TeamBean> teams;
        private String vip;
        private String vip_plus;

        public String getVip_plus() {
            return "1";
        }

        public void setVip_plus(String vip_plus2) {
            this.vip_plus = vip_plus2;
        }

        public String getDiamond() {
            return this.diamond;
        }

        public void setDiamond(String diamond2) {
            this.diamond = diamond2;
        }

        public List<TeamBean> getTeams() {
            return this.teams;
        }

        public void setTeams(List<TeamBean> teams2) {
            this.teams = teams2;
        }

        public String getVip() {
            return "1";
        }

        public void setVip(String vip2) {
            this.vip = vip2;
        }

        public String getMonth() {
            return this.month;
        }

        public void setMonth(String month2) {
            this.month = month2;
        }

        public String toString() {
            return "MemberShipBean{diamond='" + this.diamond + '\'' + ", teams=" + this.teams + ", vip='" + this.vip + '\'' + ", month='" + this.month + '\'' + '}';
        }
    }

    public static class MembershipInfoBean {
        private String season_info;
        private String team_icon;

        public String getTeam_icon() {
            return this.team_icon;
        }

        public void setTeam_icon(String team_icon2) {
            this.team_icon = team_icon2;
        }

        public String getSeason_info() {
            return this.season_info;
        }

        public void setSeason_info(String season_info2) {
            this.season_info = season_info2;
        }
    }

    public static class RegisterTips {
        private String display;
        private String info;

        public String getDisplay() {
            return this.display;
        }

        public void setDisplay(String display2) {
            this.display = display2;
        }

        public String getInfo() {
            return this.info;
        }

        public void setInfo(String info2) {
            this.info = info2;
        }
    }

    public static class RetData implements Serializable {
        private String avatarX;
        private List<UserMemberCardEntity> card_list;
        private String email;
        private boolean hasBuyed;
        private String isMember;
        private String isVip;
        private String isVipPlus;
        private String level;
        private MemberShipBean membership;
        private MembershipInfoBean membership_info;
        private String nickName;
        private String photo_url;
        private RegisterTips register_tips;
        private String tel;
        private String userId;
        private String vip;
        private VolumeNum volume_num;

        public List<UserMemberCardEntity> getCard_list() {
            return this.card_list;
        }

        public String getEmail() {
            return this.email;
        }

        public void setEmail(String email2) {
            this.email = email2;
        }

        public void setCard_list(List<UserMemberCardEntity> card_list2) {
            this.card_list = card_list2;
        }

        public VolumeNum getVolume_num() {
            return this.volume_num;
        }

        public void setVolume_num(VolumeNum volume_num2) {
            this.volume_num = volume_num2;
        }

        public MemberShipBean getMembership() {
            return this.membership;
        }

        public void setMembership(MemberShipBean membership2) {
            this.membership = membership2;
        }

        public String getVip() {
            if (this.membership == null) {
                return "";
            }
            return this.membership.getVip();
        }

        public String getIsMember() {
            return "1";
        }

        public void setIsMember(String isMember2) {
            this.isMember = isMember2;
        }

        public String getIsVip() {
            return "1";
        }

        public void setIsVip(String isVip2) {
            this.isVip = isVip2;
        }

        public String getIsVipPlus() {
            return "1";
        }

        public void setIsVipPlus(String isVipPlus2) {
            this.isVipPlus = isVipPlus2;
        }

        public void setVip(String vip2) {
            this.vip = vip2;
        }

        public MembershipInfoBean getMembership_info() {
            return this.membership_info;
        }

        public void setMembership_info(MembershipInfoBean membership_info2) {
            this.membership_info = membership_info2;
        }

        public String getNickName() {
            return this.nickName;
        }

        public void setNickName(String nickName2) {
            this.nickName = nickName2;
        }

        public String getUserId() {
            return this.userId;
        }

        public void setUserId(String userId2) {
            this.userId = userId2;
        }

        public String getTel() {
            return this.tel;
        }

        public void setTel(String tel2) {
            this.tel = tel2;
        }

        public String getAvatarX() {
            return this.avatarX;
        }

        public void setAvatarX(String avatarX2) {
            this.avatarX = avatarX2;
        }

        public String getLevel() {
            return this.level;
        }

        public void setLevel(String level2) {
            this.level = level2;
        }

        public String getPhoto_url() {
            return this.photo_url;
        }

        public void setPhoto_url(String photo_url2) {
            this.photo_url = photo_url2;
        }

        public boolean isHasBuyed() {
            if (this.membership == null) {
                return false;
            }
            if ((this.membership.getTeams() == null || this.membership.getTeams().size() <= 0) && TextUtils.isEmpty(this.membership.getMonth()) && TextUtils.isEmpty(this.membership.getDiamond())) {
                return false;
            }
            return true;
        }

        public RegisterTips getRegister_tips() {
            return this.register_tips;
        }

        public void setRegister_tips(RegisterTips register_tips2) {
            this.register_tips = register_tips2;
        }
    }

    public static class TeamBean {
        private String deadline;
        private String teamId;
        private String teamName;

        public String getTeamName() {
            return this.teamName;
        }

        public void setTeamName(String teamName2) {
            this.teamName = teamName2;
        }

        public String getTeamId() {
            return this.teamId;
        }

        public void setTeamId(String teamId2) {
            this.teamId = teamId2;
        }

        public String getDeadline() {
            return this.deadline;
        }

        public void setDeadline(String deadline2) {
            this.deadline = deadline2;
        }

        public String toString() {
            return "TeamBean{teamName='" + this.teamName + '\'' + ", teamId='" + this.teamId + '\'' + ", deadline='" + this.deadline + '\'' + '}';
        }
    }

    public static class VolumeNum {
        private String discount;
        private String volume;

        public String getDiscount() {
            return this.discount;
        }

        public void setDiscount(String discount2) {
            this.discount = discount2;
        }

        public String getVolume() {
            return this.volume;
        }

        public void setVolume(String volume2) {
            this.volume = volume2;
        }
    }

    public RetData getRetData() {
        return this.retData;
    }

    public void setRetData(RetData retData2) {
        this.retData = retData2;
    }


}
