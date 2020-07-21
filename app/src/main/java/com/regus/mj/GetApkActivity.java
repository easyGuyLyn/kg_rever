package com.regus.mj;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

public class GetApkActivity extends AppCompatActivity {


    private static final String BACKUP_PATH = "/sdcard/backup1/";
    private static final String APK = ".apk";
    private PackageManager pm;
    private List<ResolveInfo> mApps = new ArrayList<>();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Copy所有已安装APP的.apk文件到BACKUP_PATH目录下
        queryApps();
        //Copy指定包名APP的.apk文件到BACKUP_PATH目录下
        copyApk("QQ", getApk("com.tencent.tmgp.fasfdj"));

    }

    //查询已安装的APP
    private void queryApps() {
        pm = this.getPackageManager();
        Intent intent = new Intent(Intent.ACTION_MAIN, null);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        List<ResolveInfo> resolveInfos = pm.queryIntentActivities(intent, 0);
        if (resolveInfos != null && resolveInfos.size() > 0) {
            for (int i = 0; i < resolveInfos.size(); i++) {
                mApps.add(resolveInfos.get(i));
            }
        }
        if (mApps.size() > 0 && mApps != null) {
            for (int i = 0; i < mApps.size(); i++) {
                getApk(mApps.get(i).activityInfo.packageName);
            }
        }
    }

    private void copyApk(String name, String path) {
        String dest = BACKUP_PATH + name + APK;
        //path:app程序源文件路径  dest:新的存储路径  name:app名称
        new Thread(new CopyRunnable(path, dest, name)).start();
    }

    private String getApk(String packageName) {
        String appDir = null;
        try {
            //通过包名获取程序源文件路径
            appDir = getPackageManager().getApplicationInfo(packageName, 0).sourceDir;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return appDir;
    }


    /**
     * 将程序源文件Copy到指定目录
     */
    private class CopyRunnable implements Runnable {
        private String source;
        private String dest;
        private String key;

        public CopyRunnable(String source, String dest, String key) {
            this.source = source;
            this.dest = dest;
            this.key = key;
        }

        @SuppressLint("StringFormatInvalid")
        @Override
        public void run() {
            // TODO Auto-generated method stub
            try {
                int length = 1024 * 1024;
                if (!new File(BACKUP_PATH).exists()) {
                    boolean mk = new File(BACKUP_PATH).mkdirs();
                    if (mk) {
                        System.out.println("true");
                    }
                }

                File fDest = new File(dest);
                if (fDest.exists()) {
                    fDest.delete();
                }
                fDest.createNewFile();
                FileInputStream in = new FileInputStream(new File(source));
                FileOutputStream out = new FileOutputStream(fDest);
                FileChannel inC = in.getChannel();
                FileChannel outC = out.getChannel();
                int i = 0;
                while (true) {
                    if (inC.position() == inC.size()) {
                        inC.close();
                        outC.close();
                        //成功
                        Log.e("TAG", "成功");
                        break;
                    }
                    if ((inC.size() - inC.position()) < 1024 * 1024) {
                        length = (int) (inC.size() - inC.position());
                    } else {
                        length = 1024 * 1024;
                    }
                    inC.transferTo(inC.position(), length, outC);
                    inC.position(inC.position() + length);
                    i++;
                    Log.e("TAG", "进度" + i);
                }
            } catch (Exception e) {
                // TODO: handle exception
                Log.e("TAG", e.toString());
            }
        }
    }


}
