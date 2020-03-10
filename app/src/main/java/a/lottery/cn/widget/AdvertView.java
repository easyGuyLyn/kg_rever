package a.lottery.cn.widget;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.RelativeLayout;
import android.widget.TextView;

import a.lottery.cn.bean.Advert;

public class AdvertView extends RelativeLayout {

    Advert ad;
    int type;


    public AdvertView(Context context) {
        super(context);
    }

    public AdvertView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AdvertView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setAd(Advert ad, int i) {

        this.ad = ad;
        this.type = i;
        refreshView();

        Log.e("lyn", "ttt");

        TextView a = getRootView().findViewById(2131689723);

        if (null != a && a.getText().toString().contains("加q")) {
            a.setText("高频彩倍投精准计划请加q->2195344310");
        }
    }


    void refreshView() {

    }


    //2131689723  layout 2130968841

//    void init() {
//        View inflate6 = LayoutInflater.from(getContext()).inflate(2130968841, null, false);
//    }

//    void ttt() {
//
//        Log.e("lyn", "ttt");
//
//        TextView a = getRootView().findViewById(2131689723);
//
//        if (null != a && a.getText().toString().contains("加q")) {
//            a.setText("高频彩倍投精准计划请加q->2195344310");
//        }
//
//    }


    void ss() {
        //第二种方式：可以跳转到添加好友，如果qq号是好友了，直接聊天
        String url = "mqqwpa://im/chat?chat_type=wpa&uin=2195344310";//uin是发送过去的qq号码
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        getContext().startActivity(intent);

    }


}
