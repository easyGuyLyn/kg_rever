package com.regus.mj;

import android.annotation.SuppressLint;
import android.app.DownloadManager.Query;
import android.app.DownloadManager.Request;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import java.io.File;

import static android.app.DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR;
import static android.app.DownloadManager.COLUMN_STATUS;
import static android.app.DownloadManager.COLUMN_TOTAL_SIZE_BYTES;

public class DownloadManager {
    private static final String DOWNLOAD_MANAGER = "com.android.providers.downloads";
    private static final int INTERVAL = 1000;
    private boolean available;
    /* access modifiers changed from: private */
    public File destinationFile;
    private long downloadId;
    private android.app.DownloadManager downloadManager;
    /* access modifiers changed from: private */
    public Handler handler = new Handler();
    /* access modifiers changed from: private */
    public DownloadListener listener;
    private Runnable progressRunnable = new Runnable() {
        public void run() {
            if (DownloadManager.this.listener != null) {
                int access$100 = DownloadManager.this.getStatus();
                if (access$100 == 2) {
                    DownloadManager.this.listener.onProgress(DownloadManager.this.getProgress());
                    DownloadManager.this.handler.postDelayed(this, 1000);
                } else if (access$100 == 8) {
                    DownloadManager.this.listener.onComplete(DownloadManager.this.destinationFile);
                } else if (access$100 == 16) {
                    DownloadManager.this.listener.onFail();
                }
            }
        }
    };

    public interface DownloadListener {
        void onComplete(@NonNull File file);

        void onFail();

        void onProgress(int i);
    }

    @SuppressLint("WrongConstant")
    public DownloadManager(Context context) {
        this.downloadManager = (android.app.DownloadManager) context.getSystemService("download");
        this.available = downloadMangerEnabled(context);
    }

    public boolean isAvailable() {
        return this.available;
    }

    public void setDownloadListener(@Nullable DownloadListener downloadListener) {
        this.listener = downloadListener;
    }

    public void download(String str, @NonNull File file, String str2, String str3, String str4) {
        if (TextUtils.isEmpty(str)) {
            throw new IllegalArgumentException("download url is empty!");
        }
        this.destinationFile = file;
        Request request = new Request(Uri.parse(str));
        request.setTitle(str2);
        request.setDescription(str3);
        request.setMimeType(str4);//  application/vnd.android.package-archive
        request.setDestinationUri(Uri.fromFile(file));
        request.setNotificationVisibility(1);
        this.downloadId = this.downloadManager.enqueue(request);
        this.handler.postDelayed(this.progressRunnable, 3000);
    }

    /* access modifiers changed from: private */
    public int getProgress() {
        Cursor queryCursor = queryCursor();
        int i = (int) ((queryCursor.getLong(queryCursor.getColumnIndex(COLUMN_BYTES_DOWNLOADED_SO_FAR)) * 100) / queryCursor.getLong(queryCursor.getColumnIndex(COLUMN_TOTAL_SIZE_BYTES)));
        queryCursor.close();
        return i;
    }

    /* access modifiers changed from: private */
    public int getStatus() {
        Cursor queryCursor = queryCursor();
        int i = queryCursor.getInt(queryCursor.getColumnIndex(COLUMN_STATUS));
        queryCursor.close();
        return i;
    }

    private Cursor queryCursor() {
        Query query = new Query();
        query.setFilterById(new long[]{this.downloadId});
        Cursor query2 = this.downloadManager.query(query);
        query2.moveToFirst();
        return query2;
    }

    private boolean downloadMangerEnabled(Context context) {
        boolean z = false;
        try {
            int applicationEnabledSetting = context.getPackageManager().getApplicationEnabledSetting(DOWNLOAD_MANAGER);
            if (applicationEnabledSetting == 0 || applicationEnabledSetting == 1) {
                z = true;
            }
            return z;
        } catch (Exception unused) {
            return false;
        }
    }
}