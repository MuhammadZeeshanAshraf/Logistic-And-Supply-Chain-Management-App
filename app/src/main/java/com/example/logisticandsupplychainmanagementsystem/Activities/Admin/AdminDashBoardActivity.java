package com.example.logisticandsupplychainmanagementsystem.Activities.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.logisticandsupplychainmanagementsystem.R;
import com.example.logisticandsupplychainmanagementsystem.Utils.UtilsFunctions;

public class AdminDashBoardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UtilsFunctions.hideStatusBar(this);
        setContentView(R.layout.activity_admin_dash_board);
    }
}