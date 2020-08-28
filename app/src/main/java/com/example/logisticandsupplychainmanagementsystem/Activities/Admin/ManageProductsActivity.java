package com.example.logisticandsupplychainmanagementsystem.Activities.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;

import com.example.logisticandsupplychainmanagementsystem.Adapters.MangeProductAdapter;
import com.example.logisticandsupplychainmanagementsystem.Models.Product;
import com.example.logisticandsupplychainmanagementsystem.R;
import com.example.logisticandsupplychainmanagementsystem.databinding.ActivityManageProductsBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ManageProductsActivity extends AppCompatActivity {

    ActivityManageProductsBinding binding;
    DatabaseReference rootReference;
    ArrayList<Product> list;
    MangeProductAdapter adapter;
    AlertDialog waitingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        binding = ActivityManageProductsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        showLoadingDialog();
        init();
        getAllProducts();

    }

    
    private void init() {
        rootReference = FirebaseDatabase.getInstance().getReference();
        list = new ArrayList<>();
    }

    private void getAllProducts() {
        rootReference.child("Products").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    final String key = snapshot.getKey();

                    if (key != null) {
                        rootReference.child("Products").child(key).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                for (DataSnapshot snaps : dataSnapshot.getChildren()) {
                                    final String secondKey = snaps.getKey();

                                    if (secondKey != null) {
                                        rootReference.child("Products").child(key).child(secondKey).addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                                String name = dataSnapshot.child("Name").getValue().toString();
                                                String description = dataSnapshot.child("Description").getValue().toString();
                                                String cost = dataSnapshot.child("Cost").getValue().toString();
                                                String time = dataSnapshot.child("Time").getValue().toString();
                                                String uri = dataSnapshot.child("Uri").getValue().toString();
                                                String id = dataSnapshot.child("ID").getValue().toString();
                                                String category = dataSnapshot.child("Category").getValue().toString();
                                                String stock = dataSnapshot.child("Stock").getValue().toString();

                                                Product model = new Product(id, name, category, cost, time, stock, description, uri);
                                                list.add(model);

                                                LinearLayoutManager layoutManager = new LinearLayoutManager(ManageProductsActivity.this, LinearLayoutManager.VERTICAL, false);
                                                binding.ProductList.setLayoutManager(layoutManager);
                                                binding.ProductList.setItemAnimator(new DefaultItemAnimator());
                                                adapter = new MangeProductAdapter(ManageProductsActivity.this, list, ManageProductsActivity.this);
                                                binding.ProductList.setAdapter(adapter);
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                            }
                                        });
                                    }
                                }

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }

                }

                waitingDialog.dismiss();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void showLoadingDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(ManageProductsActivity.this);
        //setting up the layout for alert dialog
        View view = LayoutInflater.from(ManageProductsActivity.this).inflate(R.layout.dialog_please_wait, null, false);
        builder.setView(view);
        waitingDialog = builder.create();
        waitingDialog.setCanceledOnTouchOutside(false);
        waitingDialog.setCancelable(false);
        waitingDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        waitingDialog.show();
    }
}