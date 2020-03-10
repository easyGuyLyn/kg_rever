package com.regus;

import android.app.Application;
import android.content.Context;

import com.regus.mj.ServiceManagerWraper;

public class MyApplication extends Application{
	
	@Override
	protected void attachBaseContext(Context base) {
		ServiceManagerWraper.hookPMS(base);
		super.attachBaseContext(base);
	}

	@Override
	public void onCreate() {
		super.onCreate();
	}


}
