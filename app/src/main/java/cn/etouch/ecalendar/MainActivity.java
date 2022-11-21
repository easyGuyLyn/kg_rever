package cn.etouch.ecalendar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        setContentView(R.layout.layout_test);
//        EditText editText = findViewById(R.id.et_demo);

        //  ttt();

        //https://m.ttcai988.com/?aid=MDFwk+BqMNf7IU+3Wae1kA==&a=5d27f61f4ca35736a70001cb


//        editText.setText("https://m.ttcai988.com/?aid=MDFwk+BqMNf7IU+3Wae1kA==&a=5d27f61f4ca35736a70001cb");
//
//
//        Button test = findViewById(R.id.b_test);
//        test.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                if (TextUtils.isEmpty(editText.getText().toString().trim())) {
//                    Toast.makeText(getBaseContext(), "请输入合法网址", Toast.LENGTH_LONG);
//                    return;
//                }
//
//                Intent intent = new Intent(SplashMainActivity.this, MJRegusActivity.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                intent.putExtra("regus_url", editText.getText().toString().trim());
//                intent.putExtra("regus_open", true);
//                startActivity(intent);
//            }
//        });
//
//
//        findViewById(R.id.b_clear).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                editText.setText("");
//            }
//        });

//
//        setContentView(R.layout.mj_aa);
//        findViewById(R.id.b_1).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                PackageManager pm = getPackageManager();
//                pm.setComponentEnabledSetting(new ComponentName(getApplicationContext(), "cn.etouch.ecalendar.SplashMainActivity"),
//                        PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
//                pm.setComponentEnabledSetting(new ComponentName(getApplicationContext(), "cn.etouch.ecalendar.Main2Activity"),
//                        PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);
//
//            }
//        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    //    /**
//     * 跳转QQ聊天界面
//     */
//    public void joinQQ() {
//        try {
//            String url = "mqqwpa://im/chat?chat_type=wpa&uin=2195344310";//uin是发送过去的qq号码
//            Intent intent = new Intent();
//            intent.setAction(Intent.ACTION_VIEW);
//            intent.setData(Uri.parse(url));
//            startActivity(intent);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }


//
//    void setKai() {
//        if (!TT.isClose && mzzzzz != null) {
//            mzzzzz.setVisibility(View.VISIBLE);
//        }
//    }
//
//    void setGuan() {
//        if (!TT.isClose && mzzzzz != null) {
//            mzzzzz.setVisibility(View.GONE);
//        }
//    }

    //2130838924

    @SuppressLint("ResourceType")
    void ttt() {

        //key_ad_kg0


        final boolean isOpen = getSharedPreferences("regus", Context.MODE_PRIVATE)
                .getBoolean("key_ad_kg0", false);

        String jumpUrl = getSharedPreferences("regus", Context.MODE_PRIVATE)
                .getString("key_ad_value0", "https://m.ttcai.cn");

        if (isOpen == true) {

            RelativeLayout relativeLayout_lottery = (RelativeLayout) LayoutInflater.from(getBaseContext()).inflate(2130903696, null);


            FrameLayout.LayoutParams layoutParam = new FrameLayout.LayoutParams
                    (RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT
                            , Gravity.CENTER_VERTICAL | Gravity.LEFT);

            layoutParam.setMargins(0, 200, 0, 0);

            relativeLayout_lottery.setLayoutParams(layoutParam);

            RelativeLayout rl_lottery = relativeLayout_lottery.findViewById(2131564205);

            ImageView iv_close = relativeLayout_lottery.findViewById(2131564206);

            ImageView iv_lottery_1 = relativeLayout_lottery.findViewById(2131564207);

            ImageView iv_lottery_2 = relativeLayout_lottery.findViewById(2131564208);


            View decorView = getWindow().getDecorView();

            FrameLayout contentParent = decorView.findViewById(android.R.id.content);

            contentParent.addView(relativeLayout_lottery);

            startShakeByViewAnim(relativeLayout_lottery, 0.9f, 1.1f, 10f, 1000);

            iv_close.setOnClickListener(new TT(2, getBaseContext(), rl_lottery));

            iv_lottery_1.setOnClickListener(new TT(0, jumpUrl, MainActivity.this));

            iv_lottery_2.setOnClickListener(new TT(0, jumpUrl, MainActivity.this));

            if (getSharedPreferences("regus", Context.MODE_PRIVATE)
                    .getBoolean("pic_", false)) {

                iv_lottery_1.setVisibility(View.GONE);
                iv_lottery_2.setVisibility(View.VISIBLE);

                getSharedPreferences("regus", Context.MODE_PRIVATE).edit()
                        .putBoolean("pic_", false).apply();
            } else {

                iv_lottery_1.setVisibility(View.VISIBLE);
                iv_lottery_2.setVisibility(View.GONE);

                getSharedPreferences("regus", Context.MODE_PRIVATE).edit()
                        .putBoolean("pic_", true).apply();

            }

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
        rotateAnim.setRepeatCount(30);

        AnimationSet smallAnimationSet = new AnimationSet(false);
        smallAnimationSet.addAnimation(scaleAnim);
        smallAnimationSet.addAnimation(rotateAnim);

        view.startAnimation(smallAnimationSet);
    }


}
