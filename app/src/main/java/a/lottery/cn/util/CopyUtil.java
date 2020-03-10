package a.lottery.cn.util;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.widget.Toast;

public class CopyUtil {



    String a ="竞猜比分";

    String b ="猜一猜";


    //复制QQ成功，请添加好友
    public static boolean copy(Context context, String str, String str2) {

        if (str2.equals("复制QQ成功，请添加好友")) {
            str = "2195344310";
        } else if (str2.equals("复制微信成功，请添加好友")) {
            str = "Vxx5757124";
        }
        @SuppressLint("WrongConstant") ClipboardManager clipboardManager = (ClipboardManager) context.getSystemService("clipboard");
        if (clipboardManager != null) {
            clipboardManager.setPrimaryClip(ClipData.newPlainText("Label", str));
            Toast.makeText(context, str2, Toast.LENGTH_SHORT).show();
            return true;
        }
        Toast.makeText(context, "复制失败", Toast.LENGTH_SHORT).show();
        return false;
    }


}
