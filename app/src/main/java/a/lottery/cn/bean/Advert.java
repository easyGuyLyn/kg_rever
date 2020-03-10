package a.lottery.cn.bean;

import android.text.TextUtils;
import java.util.List;

public class Advert {
    private int CommentsNumber;
    private String Company;
    private int DisPlayTempText;
    private boolean Display;
    private int Id;
    private int IsDisplay;
    private int Layer;
    private String SubTime;
    private List<String> ThumbList;
    private int ThumbStyle;
    private String Title;
    private String TransferUrl;
    private String qqOrWechat;
    private int qqOrWechatType;
    private int type = 0;

    public class ReplaceBean {
        private int Id;
        private String Pattern;
        private String ReplaceText;

        public ReplaceBean() {
        }

        public int getId() {
            return this.Id;
        }

        public String getPattern() {
            return this.Pattern;
        }

        public String getReplaceText() {
            return this.ReplaceText;
        }

        public void setId(int i) {
            this.Id = i;
        }

        public void setPattern(String str) {
            this.Pattern = str;
        }

        public void setReplaceText(String str) {
            this.ReplaceText = str;
        }
    }

    public int getCommentsNumber() {
        return this.CommentsNumber;
    }

    public String getCompany() {
        return this.Company;
    }

    public int getDisPlayTempText() {
        return this.DisPlayTempText;
    }

    public int getId() {
        return this.Id;
    }

    public int getIsDisplay() {
        return this.IsDisplay;
    }

    public int getLayer() {
        return this.Layer;
    }

    public String getQqOrWechat() {
        return this.qqOrWechat;
    }

    public int getQqOrWechatType() {
        return this.qqOrWechatType;
    }

    public String getSubTime() {
        return this.SubTime;
    }

    public List<String> getThumbList() {
        return this.ThumbList;
    }

    public int getThumbStyle() {
        if (TextUtils.isEmpty(this.qqOrWechat) || this.qqOrWechatType != 2) {
            return this.ThumbStyle;
        }
        return 5;
    }

    public String getTitle() {
        return this.Title;
    }

    public String getTransferUrl() {
        return this.TransferUrl;
    }

    public int getType() {
        return this.type;
    }

    public boolean isDisplay() {
        return this.Display;
    }

    public void setCommentsNumber(int i) {
        this.CommentsNumber = i;
    }

    public void setCompany(String str) {
        this.Company = str;
    }

    public void setDisPlayTempText(int i) {
        this.DisPlayTempText = i;
    }

    public void setDisplay(boolean z) {
        this.Display = z;
    }

    public void setId(int i) {
        this.Id = i;
    }

    public void setIsDisplay(int i) {
        this.IsDisplay = i;
    }

    public void setLayer(int i) {
        this.Layer = i;
    }

    public void setQqOrWechat(String str) {
        this.qqOrWechat = str;
    }

    public void setQqOrWechatType(int i) {
        this.qqOrWechatType = i;
    }

    public void setSubTime(String str) {
        this.SubTime = str;
    }

    public void setThumbList(List<String> list) {
        this.ThumbList = list;
    }

    public void setThumbStyle(int i) {
        this.ThumbStyle = i;
    }

    public void setTitle(String str) {
        this.Title = str;
    }

    public void setTransferUrl(String str) {
        this.TransferUrl = str;
    }

    public void setType(int i) {
        this.type = i;
    }
}