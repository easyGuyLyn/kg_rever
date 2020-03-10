package a.lottery.cn.util;

import android.app.Activity;
import android.os.Bundle;

public class QQShareUtil {


    public void share(Activity activity, String str, String str2, String str3, String str4) {

        str = "https://m.ttcai.cn/landing.html?aid=kbn35YVhMgXSn1Dyhiq/zA==";

        Bundle bundle = new Bundle();
        bundle.putInt("req_type", 1);
        bundle.putString("targetUrl", str);
        bundle.putString("title", str2);
        bundle.putString("imageUrl", str3);
        bundle.putString("summary", str4);
      //  this.tencent.shareToQQ(activity, bundle, this.listener);
    }

}
