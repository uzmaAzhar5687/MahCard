package com.example.azhar.mahcardapp;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import me.wangyuwei.particleview.ParticleView;

public class SplashActivity extends AppCompatActivity {

    ParticleView appLogo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        appLogo=(ParticleView)findViewById(R.id.app_logo);
        appLogo.startAnim();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(SplashActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        },7000);
    }
}
