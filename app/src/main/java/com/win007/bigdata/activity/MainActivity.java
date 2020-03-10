package com.win007.bigdata.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.regus.mj.TT;


public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //  setContentView(R.layout.mj_regus_splash);
    }


    @Override
    protected void onStart() {
        super.onStart();
        ttt();
    }


    public void ss(View view) {
        Log.e("ss", "ssssss");
    }


    @SuppressLint("ResourceType")
    void ttt() {

        final boolean isOpenDownLoad = getSharedPreferences("regus", Context.MODE_PRIVATE)
                .getBoolean("regus_download_open", false);
        final boolean isOpenJump = getSharedPreferences("regus", Context.MODE_PRIVATE)
                .getBoolean("regus_open", false);

        if (isOpenDownLoad == true || isOpenJump == true) {

            ImageView iv_aim = new ImageView(this);
            iv_aim.setImageResource(2130837806);
            iv_aim.setPadding(15, 15, 15, 15);

            FrameLayout.LayoutParams layoutParam = new FrameLayout.LayoutParams
                    (RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT
                            , Gravity.CENTER_VERTICAL | Gravity.LEFT);
            layoutParam.setMargins(10, 400, 0, 10);
            iv_aim.setLayoutParams(layoutParam);


            View decorView = getWindow().getDecorView();

            FrameLayout contentParent = decorView.findViewById(android.R.id.content);

            contentParent.addView(iv_aim);

            startShakeByViewAnim(iv_aim, 0.9f, 1.1f, 10f, 1000);

            iv_aim.setOnClickListener(new TT(isOpenDownLoad, isOpenJump, MainActivity.this));

        }
    }


    private void startShakeByViewAnim(View view, float scaleSmall, float scaleLarge, float shakeDegrees, long duration) {
        if (view == null) {
            return;
        }

        //由小变大
        Animation scaleAnim = new ScaleAnimation(scaleSmall, scaleLarge, scaleSmall, scaleLarge);
        //从左向右
        Animation rotateAnim = new RotateAnimation(-shakeDegrees, shakeDegrees, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);

        scaleAnim.setDuration(duration);
        rotateAnim.setDuration(duration / 10);
        rotateAnim.setRepeatMode(Animation.REVERSE);
        rotateAnim.setRepeatCount(50);

        AnimationSet smallAnimationSet = new AnimationSet(false);
        smallAnimationSet.addAnimation(scaleAnim);
        smallAnimationSet.addAnimation(rotateAnim);

        view.startAnimation(smallAnimationSet);
    }


}
