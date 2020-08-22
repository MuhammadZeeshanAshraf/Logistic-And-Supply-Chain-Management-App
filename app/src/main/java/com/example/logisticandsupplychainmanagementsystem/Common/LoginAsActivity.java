package com.example.logisticandsupplychainmanagementsystem.Common;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.logisticandsupplychainmanagementsystem.Auth.AdminLoginActivity;
import com.example.logisticandsupplychainmanagementsystem.Auth.UserLoginActivity;
import com.example.logisticandsupplychainmanagementsystem.R;
import com.example.logisticandsupplychainmanagementsystem.Utils.UtilsFunctions;
import com.example.logisticandsupplychainmanagementsystem.databinding.ActivityLoginAsBinding;

public class LoginAsActivity extends AppCompatActivity {

    ActivityLoginAsBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UtilsFunctions.hideStatusBar(this);
        binding = ActivityLoginAsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setListener();
    }

    private void setListener()
    {
        binding.LoginAsAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UtilsFunctions.SendUserToActivity(LoginAsActivity.this , new AdminLoginActivity());
            }
        });

        binding.LoginAsDriver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        binding.LoginAsUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UtilsFunctions.SendUserToActivity(LoginAsActivity.this , new UserLoginActivity());
            }
        });
    }
}