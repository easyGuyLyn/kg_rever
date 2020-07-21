package com.neotv.http.retrofit;

import okhttp3.logging.HttpLoggingInterceptor;

public class HttpMethodUtils {


    void addLog(){

        okhttp3.OkHttpClient.Builder builder = new okhttp3.OkHttpClient.Builder();
        builder.addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY));

    }


}
