package swu.xl.linkgame.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.DownloadListener;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;
import androidx.fragment.app.FragmentActivity;
import java.net.URISyntaxException;

/* loaded from: classes.dex */
public class WebViewActivity extends FragmentActivity {
    WebView mWebView;
    private String url;
    private String TAG = "WebViewActivity";
    WebSettings webSettings = null;

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        requestWindowFeature(1);
        getWindow().setFlags(1024, 1024);
        WebView webView = new WebView(this);
        this.mWebView = webView;
        setContentView(webView);
        initView();
    }

    @SuppressLint("WrongConstant")
    private void initGoActivity() {
        Toast.makeText(this, "网络异常，请稍后再试试", 0).show();
    }

    private void initView() {
        String stringExtra = getIntent().getStringExtra("wap_url");
        this.url = stringExtra;
        WebSettings settings = this.mWebView.getSettings();
        this.webSettings = settings;
        settings.setJavaScriptEnabled(true);
        this.webSettings.setUseWideViewPort(true);
        this.webSettings.setSupportZoom(true);
        this.webSettings.setBuiltInZoomControls(true);
        this.webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        this.webSettings.setLoadWithOverviewMode(true);
        this.webSettings.setRenderPriority(WebSettings.RenderPriority.HIGH);
        this.webSettings.setDomStorageEnabled(true);
        this.webSettings.setDatabaseEnabled(true);
        String absolutePath = getCacheDir().getAbsolutePath();
        String str = this.TAG;
        Log.e(str, "cacheDirPath=" + absolutePath);
        this.webSettings.setDatabasePath(absolutePath);
        this.webSettings.setAppCachePath(absolutePath);
        this.webSettings.setAppCacheEnabled(true);



        webSettings.setDomStorageEnabled(true);        //设置支持DomStorage
        //图片先不加载最后再加载
        if (Build.VERSION.SDK_INT >= 19) {
            webSettings.setLoadsImagesAutomatically(true);
        } else {
            webSettings.setLoadsImagesAutomatically(false);
        }
        webSettings.setAppCacheEnabled(true);          // 启用缓存
        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
        //修改硬件加速导致页面渲染闪烁问题
        // mWebview.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        mWebView.requestFocus();
        mWebView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        /**
         * MIXED_CONTENT_ALWAYS_ALLOW：允许从任何来源加载内容，即使起源是不安全的；
         * MIXED_CONTENT_NEVER_ALLOW：不允许Https加载Http的内容，即不允许从安全的起源去加载一个不安全的资源；
         * MIXED_CONTENT_COMPATIBILITY_MODE：当涉及到混合式内容时，WebView 会尝试去兼容最新Web浏览器的风格。
         **/
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mWebView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }


        initWebView();
        this.mWebView.loadUrl(stringExtra);
    }

    private void initWebView() {
        this.mWebView.setWebViewClient(new WebViewClient() { // from class: swu.xl.linkgame.Activity.WebViewActivity.1
            @SuppressLint("WrongConstant")
            @Override // android.webkit.WebViewClient
            public boolean shouldOverrideUrlLoading(WebView webView, String str) {
                try {
                    if (str.startsWith("intent://")) {
                        try {
                            Intent parseUri = Intent.parseUri(str, 1);
                            parseUri.addCategory("android.intent.category.BROWSABLE");
                            parseUri.setComponent(null);
                            if (Build.VERSION.SDK_INT >= 15) {
                                parseUri.setSelector(null);
                            }
                            if (WebViewActivity.this.getPackageManager().queryIntentActivities(parseUri, 0).size() > 0) {
                                WebViewActivity.this.startActivityIfNeeded(parseUri, -1);
                            }
                            return true;
                        } catch (URISyntaxException e) {
                            e.printStackTrace();
                        }
                    }
                    if (!str.startsWith("http")) {
                        try {
                            Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(str));
                            intent.setFlags(805306368);
                            WebViewActivity.this.startActivity(intent);
                        } catch (Exception e2) {
                            e2.printStackTrace();
                            Toast.makeText(WebViewActivity.this, "您所打开的第三方App未安装", 0).show();
                        }
                        return true;
                    }
                } catch (Exception e3) {
                    e3.printStackTrace();
                }
                return super.shouldOverrideUrlLoading(webView, str);
            }


            @Override
            public void onReceivedSslError(WebView webView, SslErrorHandler sslErrorHandler, SslError sslError) {
                sslErrorHandler.proceed();
                super.onReceivedSslError(webView, sslErrorHandler, sslError);
            }

            @Override // android.webkit.WebViewClient
            public void onReceivedError(WebView webView, int i, String str, String str2) {
                String str3 = WebViewActivity.this.TAG;
                Log.d(str3, "onReceivedError: " + str2);
                WebViewActivity.this.showErrorPage();
            }

            @Override // android.webkit.WebViewClient
            public void onPageFinished(WebView webView, String str) {
                if (str.contains("blank")) {
                    WebViewActivity.this.showErrorPage();
                }
            }
        });
        this.mWebView.setWebChromeClient(new WebChromeClient() { // from class: swu.xl.linkgame.Activity.WebViewActivity.2
            @Override // android.webkit.WebChromeClient
            public void onHideCustomView() {
                if (WebViewActivity.this.mWebView != null) {
                    ((ViewGroup) WebViewActivity.this.mWebView.getParent()).removeView(WebViewActivity.this.mWebView);
                }
            }

            @Override // android.webkit.WebChromeClient
            public void onReceivedTitle(WebView webView, String str) {
                super.onReceivedTitle(webView, str);
                String str2 = WebViewActivity.this.TAG;
                Log.d(str2, "onReceivedTitle: " + str);
            }
        });
        this.mWebView.setDownloadListener(new DownloadListener() { // from class: swu.xl.linkgame.Activity.WebViewActivity.3
            @Override // android.webkit.DownloadListener
            public void onDownloadStart(String str, String str2, String str3, String str4, long j) {
                Intent intent = new Intent("android.intent.action.VIEW");
                intent.addCategory("android.intent.category.BROWSABLE");
                intent.setData(Uri.parse(str));
                WebViewActivity.this.startActivity(intent);
            }
        });
    }

    protected void showErrorPage() {
        initGoActivity();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        Log.d(this.TAG, "onDestroy: ");
        if (this.mWebView != null) {
            Log.d(this.TAG, "onDestroy: mWebView");
            ViewGroup viewGroup = (ViewGroup) this.mWebView.getParent();
            if (viewGroup != null) {
                viewGroup.removeView(this.mWebView);
            }
            this.mWebView.removeAllViews();
            this.mWebView.destroy();
        }
    }

    @Override // android.app.Activity, android.view.KeyEvent.Callback
    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        if (i == 4 && this.mWebView.canGoBack()) {
            this.mWebView.goBack();
            return true;
        }
        return super.onKeyDown(i, keyEvent);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        Log.d(this.TAG, "onActivityResult: ===========");
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onResume() {
        super.onResume();
    }
}