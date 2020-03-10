package com.netease.lottery.b;

import okhttp3.logging.HttpLoggingInterceptor;

public class c {


    void addLog( okhttp3.OkHttpClient.Builder builder){

        builder.addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY));

    }

}
