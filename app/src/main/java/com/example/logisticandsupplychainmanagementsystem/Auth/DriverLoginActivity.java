package com.example.logisticandsupplychainmanagementsystem.Auth;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.logisticandsupplychainmanagementsystem.Activities.Driver.DriverDashBoardActivity;
import com.example.logisticandsupplychainmanagementsystem.Utils.UtilsFunctions;
import com.example.logisticandsupplychainmanagementsystem.databinding.ActivityDriverLoginBinding;

public class DriverLoginActivity extends AppCompatActivity {

    ActivityDriverLoginBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDriverLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.DriverSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UtilsFunctions.SendUserToActivity(DriverLoginActivity.this , new DriverDashBoardActivity());
            }
        });


    }
}