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

import com.example.logisticandsupplychainmanagementsystem.Adapters.ViewOrderAdapter;
import com.example.logisticandsupplychainmanagementsystem.Models.Order;
import com.example.logisticandsupplychainmanagementsystem.R;
import com.example.logisticandsupplychainmanagementsystem.Utils.UtilsFunctions;
import com.example.logisticandsupplychainmanagementsystem.databinding.ActivityViewStatusBinding;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ViewStatusActivity extends AppCompatActivity {

    ActivityViewStatusBinding binding;
    DatabaseReference rootReference;

    ArrayList<Order> list;
    ViewOrderAdapter adapter;
    AlertDialog waitingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UtilsFunctions.hideStatusBar(this);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        binding = ActivityViewStatusBinding.inflate(getLayoutInflater());
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
        LinearLayoutManager layoutManager = new LinearLayoutManager(ViewStatusActivity.this, LinearLayoutManager.VERTICAL, false);
        binding.OrderList.setLayoutManager(layoutManager);
        binding.OrderList.setItemAnimator(new DefaultItemAnimator());
        adapter = new ViewOrderAdapter(ViewStatusActivity.this, list, ViewStatusActivity.this);
        binding.OrderList.setAdapter(adapter);

        waitingDialog.dismiss();
    }

    private void showLoadingDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(ViewStatusActivity.this);
        //setting up the layout for alert dialog
        View view = LayoutInflater.from(ViewStatusActivity.this).inflate(R.layout.dialog_please_wait, null, false);
        builder.setView(view);
        waitingDialog = builder.create();
        waitingDialog.setCanceledOnTouchOutside(false);
        waitingDialog.setCancelable(false);
        waitingDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        waitingDialog.show();
    }
}