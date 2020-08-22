package com.example.logisticandsupplychainmanagementsystem.Common;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.Toast;

import com.example.logisticandsupplychainmanagementsystem.Activities.MainActivity;
import com.example.logisticandsupplychainmanagementsystem.OnBoarding.OnBoardingActivity;
import com.example.logisticandsupplychainmanagementsystem.R;
import com.example.logisticandsupplychainmanagementsystem.Utils.UtilsFunctions;
import com.example.logisticandsupplychainmanagementsystem.databinding.ActivitySplashScreenBinding;

public class SplashScreenActivity extends AppCompatActivity {

    ActivitySplashScreenBinding binding;
    Animation sideAnim, bottomAnim, anime;
    final String PREFS_NAME = "MyPrefsFile";
    SharedPreferences settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UtilsFunctions.hideStatusBar(this);
        binding = ActivitySplashScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        settings = getSharedPreferences(PREFS_NAME, 0);

        Thread thread = new Thread(){
            @Override
            public void run() {
                try{
                    sleep(3000);

                }catch (Exception e)
                {
                    e.printStackTrace();
                }
                finally {

                    SendUserToActivity();
                }

            }
        };
        thread.start();

        //Animations
        sideAnim = AnimationUtils.loadAnimation(this, R.anim.side_anim);
        bottomAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_anim);
        sideAnim.setDuration(500);
        bottomAnim.setDuration(500);

        moveIcon(binding.animationView);
        binding.appName.setAnimation(sideAnim);
        binding.description.setAnimation(bottomAnim);

    }

    private void SendUserToActivity()
    {
        if (settings.getBoolean("my_first_time", true)) {
            settings.edit().putBoolean("my_first_time", false).apply();
            Intent intent = new Intent(SplashScreenActivity.this, OnBoardingActivity.class);
            startActivity(intent);
            finish();
        }
        else
        {
            Intent intent = new Intent(SplashScreenActivity.this, LoginAsActivity.class);
            startActivity(intent);
            finish();
        }
    }

    private void moveIcon(View view) {
        int[] originalPos = new int[2];
        view.getLocationOnScreen(originalPos);
        anime = new TranslateAnimation(0, 0, 0, originalPos[1] + 100);
        anime.setDuration(1000);
        anime.setFillAfter(true);
        view.startAnimation(anime);
    }

}