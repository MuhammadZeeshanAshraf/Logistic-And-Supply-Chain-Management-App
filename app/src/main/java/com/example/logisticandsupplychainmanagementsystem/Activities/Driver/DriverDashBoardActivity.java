package com.example.logisticandsupplychainmanagementsystem.Activities.Driver;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.logisticandsupplychainmanagementsystem.Activities.Admin.OrderStatusActivity;
import com.example.logisticandsupplychainmanagementsystem.Activities.Admin.ViewStatusActivity;
import com.example.logisticandsupplychainmanagementsystem.databinding.ActivityDriverDashBoardBinding;

public class DriverDashBoardActivity extends AppCompatActivity {

    ActivityDriverDashBoardBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDriverDashBoardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.ViewOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SendUserToActivity(new ViewStatusActivity());
            }
        });


        binding.OrderStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SendUserToActivity(new OrderStatusActivity());
            }
        });

        binding.MyProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SendUserToActivity(new DriverProfileActivity());
            }
        });

    }

    public void SendUserToActivity(Activity activity)
    {
        Intent intent = new Intent(DriverDashBoardActivity.this , activity.getClass());
        startActivity(intent);
    }
}