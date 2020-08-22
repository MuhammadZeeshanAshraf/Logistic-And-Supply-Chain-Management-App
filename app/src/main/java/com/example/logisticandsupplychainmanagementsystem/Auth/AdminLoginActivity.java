package com.example.logisticandsupplychainmanagementsystem.Auth;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.logisticandsupplychainmanagementsystem.Activities.Admin.AdminDashBoardActivity;
import com.example.logisticandsupplychainmanagementsystem.R;
import com.example.logisticandsupplychainmanagementsystem.databinding.ActivityAdminLoginBinding;
import com.example.logisticandsupplychainmanagementsystem.databinding.ActivityLoginAsBinding;

public class AdminLoginActivity extends AppCompatActivity {

    ActivityAdminLoginBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdminLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setListener();
    }

    private void setListener() {
        binding.AdminSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SendUserToActivity(new AdminDashBoardActivity());

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    public void SendUserToActivity(Activity activity)
    {
        Intent intent = new Intent(AdminLoginActivity.this , activity.getClass());
        startActivity(intent);
        finish();
    }
}