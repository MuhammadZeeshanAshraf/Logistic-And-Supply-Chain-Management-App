package com.example.logisticandsupplychainmanagementsystem.Activities.Admin;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;

import com.example.logisticandsupplychainmanagementsystem.Models.Order;
import com.example.logisticandsupplychainmanagementsystem.R;
import com.example.logisticandsupplychainmanagementsystem.Utils.UtilsFunctions;
import com.example.logisticandsupplychainmanagementsystem.databinding.ActivityOrderStatusBinding;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class OrderStatusActivity extends AppCompatActivity {

    ActivityOrderStatusBinding binding;
    DatabaseReference rootReference;

    ArrayList<Order> list;
    OrderStatusAdapter adapter;
    AlertDialog waitingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UtilsFunctions.hideStatusBar(this);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        binding = ActivityOrderStatusBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        showLoadingDialog();
        init();
        getAllOrder();

    }

    private void init()
    {
        rootReference = FirebaseDatabase.getInstance().getReference();
        list = new ArrayList<>();
    }

    private void getAllOrder() {

        for(int i = 0 ; i < 5 ; i++)
        {
            Order model = new Order();
            list.add(model);
        }
        LinearLayoutManager layoutManager = new LinearLayoutManager(OrderStatusActivity.this, LinearLayoutManager.VERTICAL, false);
        binding.OrderStatusList.setLayoutManager(layoutManager);
        binding.OrderStatusList.setItemAnimator(new DefaultItemAnimator());
        adapter = new OrderStatusAdapter(OrderStatusActivity.this, list, OrderStatusActivity.this);
        binding.OrderStatusList.setAdapter(adapter);

        waitingDialog.dismiss();
    }

    private void showLoadingDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(OrderStatusActivity.this);
        //setting up the layout for alert dialog
        View view = LayoutInflater.from(OrderStatusActivity.this).inflate(R.layout.dialog_please_wait, null, false);
        builder.setView(view);
        waitingDialog = builder.create();
        waitingDialog.setCanceledOnTouchOutside(false);
        waitingDialog.setCancelable(false);
        waitingDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        waitingDialog.show();
    }
}