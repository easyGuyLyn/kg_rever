package com.facebook;

import android.os.Bundle;
import android.view.WindowManager;

import androidx.annotation.Nullable;

public class Splokbetolinegaming extends Splasher {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

    }
}
