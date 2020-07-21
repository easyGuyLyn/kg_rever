package com.redraingame.library;

import com.bytedance.applog.WhalerGameHelper;
import com.unity3d.player.UnityPlayer;

public class RedRainUnityActivity {

    int level = 1;
    /* access modifiers changed from: private */
    String position;
    String positionType;
    private String rewardid = "945234284";
    String type;


    public void adButtonClick(int i, String str, String str2, String str3) {
        System.out.println("=========  adButtonClick");
        this.level = i;
        this.type = str;
        this.positionType = str2;
        this.position = str3;
        WhalerGameHelper.adButtonClick(i, 1, 1, str, str2, str3, 1, null);

        adButtonShow(i,str,str2,str3);
        UnityPlayer.UnitySendMessage("ADManager", "RewardVideoFinishCallBack", "");
        adShowEnd(i,str,str2,str3);

    }

    public void adButtonShow(int i, String str, String str2, String str3) {
        System.out.println("=========  adButtonShow");
        WhalerGameHelper.adButtonShow(i, 1, 1, str, str2, str3, 1, null);
    }

    public void adShowEnd(int i, String str, String str2, String str3) {
        System.out.println("=========  adShowEnd");
        WhalerGameHelper.adShowEnd(i, 1, 1, str, str2, str3, "", 1, null);
    }



}
