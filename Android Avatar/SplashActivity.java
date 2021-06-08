package com.tanyadas.androidAvatar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.WindowInsets;
import android.view.WindowInsetsController;
import android.view.WindowManager;



public class SplashActivity extends AppCompatActivity {
    private static int SPLASH_SCREEN = 4000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().hide();//To hide ActionBar from specific this activity.

        /* To hide Status Bar , enable full screen */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            final WindowInsetsController controller = getWindow().getInsetsController();
            if (controller != null)
                controller.hide(WindowInsets.Type.statusBars());
        }
        else {
            //no inspection deprecation
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);

        }

        setContentView(R.layout.activity_splash);
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {

                    Intent openLogin = new Intent(SplashActivity.this, AndroidMe.class);
                    startActivity(openLogin);
                    finish();


            }
        }, SPLASH_SCREEN);
    }
}