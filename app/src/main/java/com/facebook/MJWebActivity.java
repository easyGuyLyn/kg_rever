package com.facebook;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.webkit.CookieManager;
import android.webkit.WebSettings;
import android.webkit.WebView;

import androidx.annotation.Nullable;

import a.c8.cn.R;


public class MJWebActivity extends Activity {

    private WebView b;
    //页面布局的id
    int f5;
    int l0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
       // setContentView(R.layout.activity_mj_local);
        f5 = gli(getApplicationContext(), "activity_mj_local");
        l0 = ggi(getApplicationContext(), "mainWebView");
        setContentView(f5);

        if (Build.VERSION.SDK_INT < 19) {// lower api
            View v = this.getWindow().getDecorView();
            v.setSystemUiVisibility(View.GONE);
        } else {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_FULLSCREEN);
        }

        b = findViewById(l0);

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

        b.loadUrl("file:///android_asset/index.html");

    }


    public static int gli(Context paramContext, String paramString) {
        return paramContext.getResources().getIdentifier(paramString, "layout",
                paramContext.getPackageName());
    }

    public static int ggi(Context paramContext, String paramString) {
        return paramContext.getResources().getIdentifier(paramString, "id", paramContext.getPackageName());
    }

}
