package com.regus.mj;

import android.accessibilityservice.AccessibilityService;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import com.hwangjr.rxbus.RxBus;
import com.hwangjr.rxbus.annotation.Subscribe;
import com.hwangjr.rxbus.annotation.Tag;

import java.util.Iterator;

import static com.regus.mj.GcashHelperActivity.end_task;
import static com.regus.mj.GcashHelperActivity.goToGCashBankMain;
import static com.regus.mj.GcashHelperActivity.phoneNumberList;

public class ControlService extends AccessibilityService {

    //gcash布局ID前缀
    private static final String BaseLayoutId = "com.globe.gcash.android:id/";

    public static final String gcash_CLASS_sendmoney = "gcash.module.sendmoney.sendtogcash.expresssend.recipient.SendMoneyRecipientActivity";
    public static final String gcash_CLASS_sendmoneyconfirm = "gcash.module.sendmoney.sendtogcash.expresssend.confirmation.ExpressSendActivity";

    private String mCurrentClass = "";

    /**
     * 如果是无认证用户  先android.app.ProgressDialog 再 android.app.AlertDialog
     * 认证用户 android.app.ProgressDialog
     *
     * @param event
     */

    public static ControlService mainPayTaskServices;

    private boolean isAlreadyInmoneyconfirm ;

    @Override // android.app.Service
    public void onCreate() {
        super.onCreate();
        Log.d("9527", "onStartCommand");
    }


    @Override // android.accessibilityservice.AccessibilityService
    protected void onServiceConnected() {
        super.onServiceConnected();
        RxBus.get().register(this);
        Log.e("lyn", "onServiceConnected 无障碍转账服务连接成功");
        mainPayTaskServices = this;

//        onContentChange();
    }

    @Override // android.app.Service
    public boolean onUnbind(Intent intent) {
        Log.e("lyn", "onUnbind 无障碍转账服务连接中断 停止接单");
        RxBus.get().unregister(this);
        return super.onUnbind(intent);
    }


    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        String className = event.getClassName().toString();
        switch (event.getEventType()) {
            //监听通知栏消息状态改变
//            case AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED:
//                if (WeChat_PNAME.equals(event.getPackageName().toString())) {
//                    //拉起微信
//                    sendNotifacationReply(event);
//                }
//                break;
            case AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED:
                Log.e("lyn", "obe 进入到 " + className);
                mCurrentClass = className;
                switch (className) {
                    case gcash_CLASS_sendmoney:
                        SingleToast.showMsg("您已经进入 Express Send 页");
                        isAlreadyInmoneyconfirm = false;
                        break;
                    case gcash_CLASS_sendmoneyconfirm:
                        isAlreadyInmoneyconfirm = true;
                        break;
                }
                break;
        }
    }


    @Override
    public void onInterrupt() {

    }


    @Subscribe(tags = {@Tag(GcashHelperActivity.start_task)})
    public void start_task(String s) {
        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    if (!mCurrentClass.equals(gcash_CLASS_sendmoney)) {
                        SingleToast.showMsg("请进入发送金额页");
                        return;
                    }

                    SingleToast.showMsg("任务开始");
                    int i =1;
                    for (Iterator<String> iterator = phoneNumberList.iterator(); iterator.hasNext(); ) {
                        String phoneNum = iterator.next();
                         i++;
                        Log.e("lyn","第"+i+"个  "+ " next number "+phoneNum);

                        boolean isOkNum = true;
                        if (phoneNum.startsWith("0")) {
                            if (phoneNum.length() != 11) {
                                isOkNum = false;
                            }
                        } else if (phoneNum.startsWith("63")) {
                            if (phoneNum.length() != 12) {
                                isOkNum = false;
                            }
                        } else {
                            isOkNum = false;
                        }

                        if (isOkNum) {
                            Thread.sleep(500);
                            //填充号码
                            //com.globe.gcash.android:id/txt_number
                            WechatUtils.findViewByIdAndPasteContent(ControlService.this,
                                    "com.globe.gcash.android:id/txt_number",phoneNum );

                            Thread.sleep(500);

                            //填充金额
                            //com.globe.gcash.android:id/txt_amount
                            WechatUtils.findViewByIdAndPasteContent(ControlService.this,
                                    "com.globe.gcash.android:id/txt_amount","1" );

                            Thread.sleep(500);

                            //点下一步
                            //com.globe.gcash.android:id/btn_next
                            WechatUtils.findViewIdAndClick(ControlService.this,
                                    "com.globe.gcash.android:id/btn_next");
                            Thread.sleep(1500);

                            if(isAlreadyInmoneyconfirm){
                                WechatUtils.performBack(ControlService.this);
                                Thread.sleep(500);
                                isAlreadyInmoneyconfirm = false;
                            }else if(WechatUtils.findViewId(ControlService.this,"com.globe.gcash.android:id/btnCancel")
                                    && WechatUtils.findViewId(ControlService.this,"com.globe.gcash.android:id/tvCustomHeader")){
                                Thread.sleep(1000);
                                iterator.remove();
                                WechatUtils.performBack(ControlService.this);

                            }else if(mCurrentClass.equals("android.app.ProgressDialog")){

                                //网络延迟下 再给一秒加载时间
                                Thread.sleep(1000);

                                if(isAlreadyInmoneyconfirm){
                                    WechatUtils.performBack(ControlService.this);
                                    Thread.sleep(500);
                                    isAlreadyInmoneyconfirm = false;
                                }else if(WechatUtils.findViewId(ControlService.this,"com.globe.gcash.android:id/btnCancel")
                                        && WechatUtils.findViewId(ControlService.this,"com.globe.gcash.android:id/tvCustomHeader")){
                                    Thread.sleep(1000);
                                    iterator.remove();
                                    WechatUtils.performBack(ControlService.this);

                                }else {
                                    //直接跳过 放弃这个号码
                                    iterator.remove();
                                //    WechatUtils.performBack(ControlService.this);
                                }

                            }

                        }else {
                            Log.e("lyn", "规则错误号码 " + phoneNum);
                            iterator.remove();
                        }
                    }

                    SingleToast.showMsg("任务结束");
                    RxBus.get().post(end_task, "");

                }catch (Exception e){
                    SingleToast.showMsg("任务遇到异常 "+ e.getLocalizedMessage());
                    Log.e("lyn trycatch",""+e.getLocalizedMessage());
                 //   RxBus.get().post(end_task, "");
                }
            }
        }).start();

    }


}
