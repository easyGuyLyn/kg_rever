package com.dongqiudi.news;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        jump();
    }


    void jump(){
            try {
                Class aimClass = Class.forName("com.regus.mj.MJRegusActivity");
                Intent intent = new Intent(this, aimClass);
             //   intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                  startActivity(intent);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
    }


}
