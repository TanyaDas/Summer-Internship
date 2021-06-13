package com.tanyadas.wedmist.shareMemories;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.WindowInsets;
import android.view.WindowInsetsController;
import android.view.WindowManager;

public class SplashScreen extends AppCompatActivity {
    private static int SPLASH_SCREEN = 2000;
    SharedPreferences prefs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        setContentView(R.layout.activity_splash_screen);
        prefs = getSharedPreferences("SignIn", MODE_PRIVATE);
        checkIfUserIsLoggedIn();
        boolean isLoggedIn= prefs.getBoolean("isLoggedIn", false);

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable()
        {
            @Override
            public void run() {
                if(isLoggedIn){
                    Intent openUserProfile = new Intent(SplashScreen.this, Home.class);
                    startActivity(openUserProfile);
                    finish();
                    return;
                }
                else
                {
                    Intent openLogin = new Intent(SplashScreen.this, SignIn.class);
                    startActivity(openLogin);
                    finish();
                }
            }
        }, SPLASH_SCREEN);
    }
    private void checkIfUserIsLoggedIn() {
        SharedPreferences.Editor editor = prefs.edit();
        editor.remove("message");
        editor.commit();
    }
}