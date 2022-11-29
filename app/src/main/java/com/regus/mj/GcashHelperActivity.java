package com.regus.mj;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.hwangjr.rxbus.RxBus;
import com.hwangjr.rxbus.annotation.Subscribe;
import com.hwangjr.rxbus.annotation.Tag;
import com.regus.MyApplication;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import a.c8.cn.R;

import static com.regus.mj.ControlService.gcash_CLASS_sendmoney;

public class GcashHelperActivity extends Activity {

    public static final String start_task = "start_task";//start_task

    public static final String end_task = "end_task";//end_task

    Button b_start;
    TextView tvAllPhoneNumber;
    StartRunnable startRunnable;

    /**
     * 权限部分
     */

    String[] permissions = new String[]{
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_PHONE_STATE,
    };
    List<String> mPermissionList = new ArrayList<>();
    boolean mShowRequestPermission = true;//用户是否禁止权限

    public static LinkedList<String> phoneNumberList = new LinkedList<>();
    public static boolean isStart;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gcash);
        RxBus.get().register(this);
        initview();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkPermision();
        }

            SingleToast.showMsg("请开启无障碍权限");
            Intent intent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
            this.startActivity(intent);

    }


    private void initview() {
        b_start = findViewById(R.id.b_start);
        tvAllPhoneNumber = findViewById(R.id.tvAllPhoneNumber);

        startRunnable = new StartRunnable();

        b_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(phoneNumberList.size()==0){
                    SingleToast.showMsg("沒有号码数据源");
                    return;
                }

                isStart = !isStart;
                if(isStart){
                    b_start.setText("已经開啟");
                    SingleToast.showMsg("10秒后開始操作 請到目標界面");
                    new Handler().postDelayed(startRunnable,10000);
                }else {
                    if(startRunnable!=null){
                        new Handler().removeCallbacks(startRunnable);
                    }
                    b_start.setText("开始脚本");
                }
            }
        });
    }

    class StartRunnable implements Runnable {

        @Override
        public void run() {
            b_start.setEnabled(false);
            RxBus.get().post(start_task, "");
        }
    }

    /**
     * 权限检测
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void checkPermision() {

        try {
            mPermissionList.clear();
            for (int i = 0; i < permissions.length; i++) {
                if (checkSelfPermission(permissions[i]) != PackageManager.PERMISSION_GRANTED) {
                    mPermissionList.add(permissions[i]);
                }
            }
            /**
             * 判断是否为空
             */
            if (mPermissionList.isEmpty()) {//未授予的权限为空，表示都授予了
                afterCheckPermision();
            } else {//请求权限方法
                String[] permissions = mPermissionList.toArray(new String[mPermissionList.size()]);//将List转为数组
                requestPermissions(permissions, 1);
            }
        } catch (Exception e) {

            //某些机型在启动页申请权限会有问题  或者 有些低版本机型 不需要申请 或者没有ActivityCompat这个类
            //先进去  里面的会帮我申请好权限
            afterCheckPermision();

        }

    }


    @Subscribe(tags = {@Tag(end_task)})
    public void end_task(String s) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                writeDataToFile();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        SingleToast.showMsg("任务结束  所有号码已经过滤完");
                        b_start.setEnabled(true);
                        b_start.setText("开始脚本");
                        isStart = false;
                    }
                });
            }
        }).start();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1:
                for (int i = 0; i < grantResults.length; i++) {
                    if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                        //判断是否勾选禁止后不再询问
                        boolean showRequestPermission = shouldShowRequestPermissionRationale(permissions[i]);
                        if (showRequestPermission) {//
                            checkPermision();//重新申请权限
                            return;
                        } else {
                            mShowRequestPermission = false;//已经禁止
                        }
                    }
                }
                afterCheckPermision();
                break;
            default:
                break;
        }
    }




    private void afterCheckPermision() {

        readPhoneData();

    }



    private void writeDataToFile(){

        Log.e("lyn","writeDataToFile " + phoneNumberList.size());

        StringBuilder stringBuilder = new StringBuilder();
        for(String s : phoneNumberList){
            stringBuilder.append(s+ "\r\n");
        }

        WriteDataToTxtUtils.writeTxtToFile(stringBuilder.toString(),
                FileTool.getCacheDir(this).getAbsolutePath()+"/readphone/",
                DateTool.getTimeFromLong(DateTool.FMT_DATE_TIME,System.currentTimeMillis())+".txt");

    }



    private void readPhoneData() {

        ProgressDialog mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setMessage("短信数据读取中，请稍后...");
        mProgressDialog.show();

        new Thread(new Runnable() {
            @Override
            public void run() {
                phoneNumberList.clear();
                getFromAssets("data.txt");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tvAllPhoneNumber.setText("需要过滤的短信数量: " + phoneNumberList.size());
                        if(mProgressDialog!=null && mProgressDialog.isShowing()){
                            mProgressDialog.dismiss();
                        }
                    }
                });
            }
        }).start();

    }


    public String getFromAssets(String fileName) {
        try {
            InputStreamReader inputReader = new InputStreamReader(getResources().getAssets().open(fileName));
            BufferedReader bufReader = new BufferedReader(inputReader);
            String line = "";
            String result = "";
            while ((line = bufReader.readLine()) != null){
                phoneNumberList.add(line.trim());
                result += line;
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }


    @SuppressLint("WrongConstant")
    public static void goToGCashBankMain() {
        ComponentName componentName = new ComponentName("com.globe.gcash.android", gcash_CLASS_sendmoney);
        Intent intent = new Intent();
        intent.setComponent(componentName);
        intent.setFlags(268435456);
        MyApplication.getInstance().startActivity(intent);
    }


    @Override
    protected void onDestroy() {
        RxBus.get().unregister(this);
        super.onDestroy();
    }
}
