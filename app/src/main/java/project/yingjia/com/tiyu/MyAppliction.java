package project.yingjia.com.tiyu;

public class MyAppliction {

    String xiaoshitou;

    private static MyAppliction myApplication = null;


    public static MyAppliction getApp() {
        return myApplication;
    }


    public void setXiaoshitou(String str) {
        this.xiaoshitou = str;
    }

}
