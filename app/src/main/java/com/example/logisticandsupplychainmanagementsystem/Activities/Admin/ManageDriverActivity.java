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

import com.example.logisticandsupplychainmanagementsystem.Models.Driver;
import com.example.logisticandsupplychainmanagementsystem.R;
import com.example.logisticandsupplychainmanagementsystem.Utils.UtilsFunctions;
import com.example.logisticandsupplychainmanagementsystem.databinding.ActivityManageDriverBinding;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ManageDriverActivity extends AppCompatActivity {

    ActivityManageDriverBinding binding;
    DatabaseReference rootReference;
    ArrayList<Driver> list;
    MangeDriverAdapter adapter;
    AlertDialog waitingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UtilsFunctions.hideStatusBar(this);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        binding = ActivityManageDriverBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        showLoadingDialog();
        init();
        getAllDrivers();
    }


    private void init() {
        rootReference = FirebaseDatabase.getInstance().getReference();
        list = new ArrayList<>();
    }

    private void getAllDrivers() {

        for(int i = 0 ; i < 5 ; i++)
        {
            Driver model = new Driver();
            list.add(model);
        }
        LinearLayoutManager layoutManager = new LinearLayoutManager(ManageDriverActivity.this, LinearLayoutManager.VERTICAL, false);
        binding.DriverList.setLayoutManager(layoutManager);
        binding.DriverList.setItemAnimator(new DefaultItemAnimator());
        adapter = new MangeDriverAdapter(ManageDriverActivity.this, list, ManageDriverActivity.this);
        binding.DriverList.setAdapter(adapter);

        waitingDialog.dismiss();
    }

    private void showLoadingDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(ManageDriverActivity.this);
        //setting up the layout for alert dialog
        View view = LayoutInflater.from(ManageDriverActivity.this).inflate(R.layout.dialog_please_wait, null, false);
        builder.setView(view);
        waitingDialog = builder.create();
        waitingDialog.setCanceledOnTouchOutside(false);
        waitingDialog.setCancelable(false);
        waitingDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        waitingDialog.show();
    }

}