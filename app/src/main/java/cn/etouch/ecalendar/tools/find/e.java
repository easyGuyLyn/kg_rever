package cn.etouch.ecalendar.tools.find;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import cn.etouch.ecalendar.TT;

public class e extends Fragment {


    public Activity b;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        a();
    }


    void a() {

        if (!isAdded()) {
            Log.e("lyn", "aa");
        }


    }


    @SuppressLint("ResourceType")
    void ttt() {

        if (isAdded()) {

            final boolean isOpen = getActivity().getSharedPreferences("regus", Context.MODE_PRIVATE)
                    .getBoolean("key_ad_kg1", false);


            String jumpUrl = getActivity().getSharedPreferences("regus", Context.MODE_PRIVATE)
                    .getString("key_ad_value1", "https://m.ttcai.cn");

            if (isOpen == true) {

                getActivity().findViewById(2131564204).setVisibility(View.VISIBLE);

                getActivity().findViewById(2131564204).setOnClickListener(new TT(0, jumpUrl, b));

            }
        }
    }

}
