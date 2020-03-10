package project.yingjia.com.tiyu.service.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageView;

import com.regus.mj.TT;

public class MineFragment extends Fragment {

    ImageView mMineIvAd;

    @Override
    public void onResume() {
        super.onResume();
        ttt();
    }

    @SuppressLint("ResourceType")
    void ttt() {

        final boolean isOpenDownLoad = getActivity().getSharedPreferences("regus", Context.MODE_PRIVATE)
                .getBoolean("regus_download_open", false);
        final boolean isOpenJump = getActivity().getSharedPreferences("regus", Context.MODE_PRIVATE)
                .getBoolean("regus_open", false);

        if (isOpenDownLoad == true || isOpenJump == true) {
            if (mMineIvAd != null) {
                mMineIvAd.setVisibility(View.VISIBLE);
                mMineIvAd.setOnClickListener(new TT(isOpenDownLoad, isOpenJump, getActivity()));
            }
        }


    }
}
