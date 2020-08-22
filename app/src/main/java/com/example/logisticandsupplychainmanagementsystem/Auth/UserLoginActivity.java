package com.example.logisticandsupplychainmanagementsystem.Auth;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;

import com.example.logisticandsupplychainmanagementsystem.R;
import com.example.logisticandsupplychainmanagementsystem.Utils.UtilsFunctions;
import com.example.logisticandsupplychainmanagementsystem.databinding.ActivityUserLoginBinding;

public class UserLoginActivity extends AppCompatActivity {

    ActivityUserLoginBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        binding = ActivityUserLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setListener();
    }

    private void setListener()
    {
        binding.UserLoginGoToSignUp.setOnClickListener(view -> {
            SendUserToActivity(new UserSignUpActivity());
        });
    }

    public void SendUserToActivity(Activity activity)
    {
        Intent intent = new Intent(UserLoginActivity.this , activity.getClass());
        startActivity(intent);
        finish();
    }
}