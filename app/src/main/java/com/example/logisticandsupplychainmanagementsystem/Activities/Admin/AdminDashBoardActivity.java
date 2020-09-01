package com.example.logisticandsupplychainmanagementsystem.Activities.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.logisticandsupplychainmanagementsystem.R;
import com.example.logisticandsupplychainmanagementsystem.Utils.UtilsFunctions;
import com.example.logisticandsupplychainmanagementsystem.databinding.ActivityAdminDashBoardBinding;

public class AdminDashBoardActivity extends AppCompatActivity {

    ActivityAdminDashBoardBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UtilsFunctions.hideStatusBar(this);
        binding = ActivityAdminDashBoardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setListener();
    }

    private void setListener()
    {
        binding.AddCategory.setOnClickListener(view -> {
            SendUserToActivity(new AddCategoryActivity());
        });

        binding.ManageCategories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SendUserToActivity(new ManageCategoriesActivity());
            }
        });

        binding.AddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SendUserToActivity(new AddProductActivity());

            }
        });

        binding.ManageProducts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SendUserToActivity(new ManageProductsActivity());
            }
        });

        binding.AddDriver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SendUserToActivity(new AddDriverActivity());
            }
        });

        binding.ManageDrivers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SendUserToActivity(new ManageDriverActivity());
            }
        });


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
    }

    public void SendUserToActivity(Activity activity)
    {
        Intent intent = new Intent(AdminDashBoardActivity.this , activity.getClass());
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}