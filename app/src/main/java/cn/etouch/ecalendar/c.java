package cn.etouch.ecalendar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

public class c extends Fragment {


    private FragmentActivity w;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //zzz();

        qq();
    }

    @SuppressLint("ResourceType")
    private void zzz() {
        //2131562175
        final boolean isOpen = getActivity().getSharedPreferences("regus", Context.MODE_PRIVATE)
                .getBoolean("key_ad_kg1", false);


        String jumpUrl = getActivity().getSharedPreferences("regus", Context.MODE_PRIVATE)
                .getString("key_ad_value1", "https://m.ttcai.cn");

        if (isOpen == true) {

            getActivity().findViewById(2131562175).setVisibility(View.VISIBLE);

            getActivity().findViewById(2131562175).setOnClickListener(new TT(0, jumpUrl, w));

        } else {
            getActivity().findViewById(2131562175).setOnClickListener(new TT(1, w));
        }

    }


    private void qq() {
        Toast.makeText(w, "红包还未上线，敬请期待哦~", Toast.LENGTH_SHORT).show();
    }


}
