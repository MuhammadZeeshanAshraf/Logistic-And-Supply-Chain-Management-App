package com.example.logisticandsupplychainmanagementsystem.Activities.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;

import com.example.logisticandsupplychainmanagementsystem.Adapters.ManageCategoryAdapter;
import com.example.logisticandsupplychainmanagementsystem.Models.Category;
import com.example.logisticandsupplychainmanagementsystem.R;
import com.example.logisticandsupplychainmanagementsystem.databinding.ActivityManageCategoriesBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ManageCategoriesActivity extends AppCompatActivity {

    ActivityManageCategoriesBinding binding;
    DatabaseReference rootReference;
    ArrayList<Category> list;
    Context context;
    ManageCategoryAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityManageCategoriesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        init();
        RetriveDataFromDatabase();
    }

    private void init() {
        context = ManageCategoriesActivity.this;
        rootReference = FirebaseDatabase.getInstance().getReference();
        list = new ArrayList<>();
    }

    private void RetriveDataFromDatabase() {
        rootReference.child("Categories").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    final String key = snapshot.getKey();

                    if (key != null) {
                        rootReference.child("Categories").child(key).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                String name = dataSnapshot.child("Name").getValue().toString();



                                Category model = new Category(key , name);
                                list.add(model);

                                LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);

                                binding.CategoryList.setLayoutManager(layoutManager);
                                binding.CategoryList.setItemAnimator(new DefaultItemAnimator());

                                adapter = new ManageCategoryAdapter(context, list, ManageCategoriesActivity.this);

                                binding.CategoryList.setAdapter(adapter);


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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}