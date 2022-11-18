package com.regus.mj;

import android.app.Activity;
import android.util.Log;

public class setDataUtilActivity extends Activity {


    @Override
    protected void onResume() {
        super.onResume();

        Log.e("zzz",""+ System.currentTimeMillis());
        Log.e("zzz1",""+DateTool.getLongFromTime(DateTool.FMT_DATE,"2022-11-27"));
        setData();

    }

    private void setData(){

        if(System.currentTimeMillis()>1669478400000L){
            Log.e("zzz2",""+ System.currentTimeMillis());





        }


    }

}
