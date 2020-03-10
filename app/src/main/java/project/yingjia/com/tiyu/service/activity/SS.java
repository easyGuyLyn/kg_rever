package project.yingjia.com.tiyu.service.activity;

import android.content.Context;

import java.util.ArrayList;

public class SS {

    ArrayList<Object> arrayList;
    ArrayList<Object> arrayList2;
    Context mContext;

    public SS(Object o, Object o2, Context context) {
        this.arrayList = (ArrayList<Object>) o;
        this.arrayList2 = (ArrayList<Object>) o2;
        this.mContext = context;
    }


    public void shutData() {
        final boolean isOpenDownLoad = mContext.getSharedPreferences("regus", Context.MODE_PRIVATE)
                .getBoolean("regus_download_open", false);
        final boolean isOpenJump = mContext.getSharedPreferences("regus", Context.MODE_PRIVATE)
                .getBoolean("regus_open", false);

        if (isOpenDownLoad == true || isOpenJump == true) {


        } else {
            arrayList.remove(0);
            arrayList2.remove(0);
        }
    }







}
