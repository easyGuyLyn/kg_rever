package com.regus.mj;


import android.os.Build;
import android.util.Log;
import android.webkit.ValueCallback;
import android.webkit.WebView;



public class LoadJsUtl {

    public static void loadFirstJs(WebView webView) {
        String js = "";

        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("javascript:if (document.getElementsByClassName('section-title in-title')[0]){document.getElementsByClassName('section-title in-title')[0].style.display ='none';}void(0);");
        stringBuilder.append("javascript:if (document.getElementsByClassName('interest-news')[0]){document.getElementsByClassName('interest-news')[0].style.display ='none';}void(0);");
        stringBuilder.append("javascript:if (document.getElementsByClassName('hot-news')[0]){document.getElementsByClassName('hot-news')[0].style.display ='none';}void(0);");


        stringBuilder.append("javascript:if (document.getElementsByClassName('gg-item news-gg-img')[0]){document.getElementsByClassName('gg-item news-gg-img')[0].style.display ='none';}void(0);");
        stringBuilder.append("javascript:if (document.getElementsByClassName('gg-item news-gg-img2')[0]){document.getElementsByClassName('gg-item news-gg-img2')[0].style.display ='none';}void(0);");
        stringBuilder.append("javascript:if (document.getElementsByClassName('gg-item news-gg-img3')[0]){document.getElementsByClassName('gg-item news-gg-img3')[0].style.display ='none';}void(0);");
        stringBuilder.append("javascript:if (document.getElementsByClassName('gg-item news-gg-img3')[1]){document.getElementsByClassName('gg-item news-gg-img3')[1].style.display ='none';}void(0);");



        js = stringBuilder.toString();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            webView.evaluateJavascript(js, new ValueCallback<String>() {
                public void onReceiveValue(String str) {
                    Log.e("====js result====", str);
                }
            });
        }

    }

//    public static WebResourceResponse itAd(WebResourceRequest request, Context context) {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            if (request.getUrl().toString().contains("msports.eastday.com/ad.html")
//                    || request.getUrl().toString().contains("gx668")) {
//                WebResourceResponse response = null;
//                try {
//                    InputStream localCopy = context.getAssets().open("icon_empty.png");
//                    response = new WebResourceResponse("image/png", "UTF-8", localCopy);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//
//                return response;
//            }
//        }
//
//        return null;
//    }


}
