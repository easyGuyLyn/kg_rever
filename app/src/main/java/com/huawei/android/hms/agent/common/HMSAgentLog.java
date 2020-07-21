package com.huawei.android.hms.agent.common;

import android.util.Log;

public final class HMSAgentLog {
    private static final int PRINT_STACK_COUTN = 2;
    private static final int START_STACK_INDEX = 4;
    private static IHMSAgentLogCallback logCallback;

    public interface IHMSAgentLogCallback {
        void logD(String str, String str2);

        void logE(String str, String str2);

        void logI(String str, String str2);

        void logV(String str, String str2);

        void logW(String str, String str2);
    }

    public static void setHMSAgentLogCallback(IHMSAgentLogCallback iHMSAgentLogCallback) {
        logCallback = iHMSAgentLogCallback;
    }

    public static void d(String str) {
        StringBuilder sb = new StringBuilder();
        appendStack(sb);
        sb.append(str);
        IHMSAgentLogCallback iHMSAgentLogCallback = logCallback;
        String str2 = "HMSAgent";
        if (iHMSAgentLogCallback != null) {
            iHMSAgentLogCallback.logD(str2, sb.toString());
        } else {
            Log.d(str2, sb.toString());
        }
    }

    public static void v(String str) {
        StringBuilder sb = new StringBuilder();
        appendStack(sb);
        sb.append(str);
        IHMSAgentLogCallback iHMSAgentLogCallback = logCallback;
        String str2 = "HMSAgent";
        if (iHMSAgentLogCallback != null) {
            iHMSAgentLogCallback.logV(str2, sb.toString());
        } else {
            Log.v(str2, sb.toString());
        }
    }

    public static void i(String str) {
        StringBuilder sb = new StringBuilder();
        appendStack(sb);
        sb.append(str);
        IHMSAgentLogCallback iHMSAgentLogCallback = logCallback;
        String str2 = "HMSAgent";
        if (iHMSAgentLogCallback != null) {
            iHMSAgentLogCallback.logI(str2, sb.toString());
        } else {
            Log.i(str2, sb.toString());
        }
    }

    public static void w(String str) {
        StringBuilder sb = new StringBuilder();
        appendStack(sb);
        sb.append(str);
        IHMSAgentLogCallback iHMSAgentLogCallback = logCallback;
        String str2 = "HMSAgent";
        if (iHMSAgentLogCallback != null) {
            iHMSAgentLogCallback.logW(str2, sb.toString());
        } else {
            Log.w(str2, sb.toString());
        }
    }

    public static void e(String str) {
        StringBuilder sb = new StringBuilder();
        appendStack(sb);
        sb.append(str);
        IHMSAgentLogCallback iHMSAgentLogCallback = logCallback;
        String str2 = "HMSAgent";
        if (iHMSAgentLogCallback != null) {
            iHMSAgentLogCallback.logE(str2, sb.toString());
        } else {
            Log.e(str2, sb.toString());
        }
    }

    private static void appendStack(StringBuilder sb) {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        if (stackTrace != null && stackTrace.length > 4) {
            for (int min = Math.min(stackTrace.length - 1, 6); min >= 4; min--) {
                if (stackTrace[min] != null) {
                    String fileName = stackTrace[min].getFileName();
                    if (fileName != null) {
                        int indexOf = fileName.indexOf(46);
                        if (indexOf > 0) {
                            fileName = fileName.substring(0, indexOf);
                        }
                    }
                    sb.append(fileName);
                    sb.append('(');
                    sb.append(stackTrace[min].getLineNumber());
                    sb.append(")");
                    sb.append("->");
                }
            }
            sb.append(stackTrace[4].getMethodName());
        }
        sb.append(10);
    }
}