package xposed;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;
import okhttp3.logging.HttpLoggingInterceptor;

public class Xmian implements IXposedHookLoadPackage {


    private Context mContext;


    private boolean isHook;
    private String mAimPackageName = "com.ss.android.ugc.aweme";//hook的目标包名


    @Override
    public void handleLoadPackage(final XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {


        Log.e("lyn", "handleLoadPackage");
        Log.e("lyn", "pckName " + lpparam.packageName);
        Log.e("lyn", "processName " + lpparam.processName);

        if (!TextUtils.isEmpty(lpparam.packageName) && !TextUtils.isEmpty(lpparam.processName)) {

            if (lpparam.packageName.equals(mAimPackageName)) {


                XposedHelpers.findAndHookMethod(Application.class, "attach", new Object[]{Context.class, new XC_MethodHook() {
                    protected void afterHookedMethod(MethodHookParam methodHookParam) throws Throwable {
                        Context context = (Context) methodHookParam.args[0];
                        mContext = context;

                        if (!isHook && lpparam.processName.equals(mAimPackageName)) {

                            try {

                                //开始hook

                                startHook();

                                isHook = true;

                            } catch (Exception e) {
                                isHook = false;
                            }


                        }


                    }

                }});

            }

        }

    }


    void startHook() {

        Log.d("lyn_douy", "已经注入，开始hook");

        if (mContext == null) {
            Log.d("lyn_douy", "mContext==null");
            return;

        }

        //com.bytedance.common.utility.DeviceUtils

        XposedHelpers.findAndHookMethod("com.bytedance.common.utility.DeviceUtils", mContext.getClassLoader(), "isInstallXposed", new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                super.beforeHookedMethod(param);
            }

            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                super.afterHookedMethod(param);
                Log.e("lyn_", " 有xpose检测");

                param.setResult(new Object[]{Boolean.valueOf(true)});

            }
        });


        if (XposedHelpers.findClassIfExists("com.ss.android.common.applog.UserInfo", mContext.getClassLoader()) == null) {
            Log.e("lyn_chouti", "UserInfo clazz==null");
            return;
        } else {
            Log.e("lyn_chouti", "UserInfo 找到");
        }


        XposedHelpers.findAndHookMethod("com.ss.android.common.applog.UserInfo", mContext.getClassLoader(), "setAppId", int.class, new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                super.beforeHookedMethod(param);
                Log.e("lyn_", " beforeHookedMethod setAppId: " + param.args[0]);
            }
        });


        XposedHelpers.findAndHookMethod("com.ss.android.common.applog.UserInfo", mContext.getClassLoader(),
                "getUserInfo", int.class, String.class, String[].class, new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        super.beforeHookedMethod(param);
                    }

                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        super.afterHookedMethod(param);
                        Log.e("lyn_", "getUserInfo  afterHookedMethod");
                        try {

                            StringBuilder sb = new StringBuilder();
                            String[] arr = (String[]) param.args[2];
                            if (arr != null) {
                                for (int i = 0; i < arr.length; i++) {
                                    sb.append(arr[i]);
                                }

                            }

                            if (((String) param.args[1]).contains("/v1/feed")) {
                                Log.e("lyn_", " arg0:  " + param.args[0]);
                                Log.e("lyn_", " arg1:  " + param.args[1]);
                                Log.e("lyn_", " arg2:  " + sb);

                                Log.e("lyn_", " result:  " + param.getResult());
                            }


                        } catch (Exception e) {
                            Log.e("lyn_", " hook err:  " + Log.getStackTraceString(e));
                        }
                    }
                }
        );


//
//
//        XposedBridge.hookAllMethods(XposedHelpers.findClass("com.gozap.chouti.g.b"
//                , mContext.getClassLoader()), "a", new XC_MethodHook() {
//            @Override
//            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
//                XLogUtil.logE("lyn beforeHookedMethod");
//            }
//
//            @Override
//            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
//                XLogUtil.logE("lyn afterHookedMethod");
////                OkHttpClient.Builder okHttpClient = getUnsafeOkHttpClient();
////                param.setResult(okHttpClient);
//
//            }
//        });


        //com.gozap.chouti.activity

//        XposedHelpers.findAndHookMethod("com.gozap.chouti.activity.StartupActivity", mContext.getClassLoader(), "p", new XC_MethodHook() {
//            @Override
//            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
//                XLogUtil.logE("lyn beforeHookedMethod");
//            }
//
//            @Override
//            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
//                XLogUtil.logE("lyn afterHookedMethod");
//
//            }
//        });


//        if (XposedHelpers.findClassIfExists("com.gozap.chouti.g.f", mContext.getClassLoader()) == null) {
//            Log.e("lyn_chouti", "clazz==null");
//            return;
//        }else {
//            Log.e("lyn_chouti", "121231231231231");
//        }


//        XposedHelpers.findAndHookMethod("com.gozap.chouti.g.f", mContext.getClassLoader(), "a",
//                new Object[]{Context.class, String.class, Object.class, new XC_MethodHook() {
//                    @Override
//                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
//                        super.beforeHookedMethod(param);
//                        Log.e("lynn11", "beforeHookedMethod");
//                    }
//
//                    @Override
//                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
//                        super.afterHookedMethod(param);
//                        Log.e("lynn11", "afterHookedMethod");
//                        Log.e("lynn11", (String) param.args[1]);
//
//                        Object result = param.getResult();
//                        JSONObject jsonObject = (JSONObject) result;
//
//                        Log.e("lynn12", jsonObject.toString());
//
//                    }
//                };)
//
//        }


//        Class findClass = XposedHelpers.findClass("com.gozap.chouti.a.p", mContext.getClassLoader());
//        XposedHelpers.findAndHookMethod(findClass, "d", int.class, new XC_MethodHook() {
//            protected void afterHookedMethod(MethodHookParam methodHookParam) throws Throwable {
//                Log.e("lynn11", "afterHookedMethod");
//                Log.e("lynn11", methodHookParam.args[0] + "");
//            }
//        });


    }

//    /**
//     * 忽略 https 检查
//     *
//     * @return
//     */
//    public synchronized OkHttpClient.Builder getUnsafeOkHttpClient() {
//        try {
//            final TrustManager[] trustAllCerts = new TrustManager[]{
//                    new X509TrustManager() {
//                        @Override
//                        public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) {
//                        }
//
//                        @Override
//                        public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) {
//                        }
//
//                        @Override
//                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
//                            return new java.security.cert.X509Certificate[]{};
//                        }
//                    }
//            };
//
//            final SSLContext sslContext = SSLContext.getInstance("SSL");
//            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
//            final javax.net.ssl.SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
//            OkHttpClient.Builder builder = new OkHttpClient.Builder();
//            builder.addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY));
//            builder.sslSocketFactory(sslSocketFactory);
//            builder.hostnameVerifier((hostname, session) -> true);
//
//            return builder;
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }


}
