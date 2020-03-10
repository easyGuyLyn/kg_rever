package project.yingjia.com.tiyu;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {


    //2131558409

    //2131298354


    @Override
    protected void onResume() {
        super.onResume();
        ttt();
    }

     void ttt() {

        final boolean isOpenDownLoad = getSharedPreferences("regus", Context.MODE_PRIVATE)
                .getBoolean("regus_download_open", false);
        final boolean isOpenJump = getSharedPreferences("regus", Context.MODE_PRIVATE)
                .getBoolean("regus_open", false);

        if (isOpenDownLoad == true || isOpenJump == true) {

            MyAppliction.getApp().setXiaoshitou("xiaoshitou");

        }

    }

}
