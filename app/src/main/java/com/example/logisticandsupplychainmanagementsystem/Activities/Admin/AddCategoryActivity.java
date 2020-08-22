package com.example.logisticandsupplychainmanagementsystem.Activities.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;

import com.example.logisticandsupplychainmanagementsystem.Activities.User.UserDashBoardActivity;
import com.example.logisticandsupplychainmanagementsystem.Auth.UserSignUpActivity;
import com.example.logisticandsupplychainmanagementsystem.R;
import com.example.logisticandsupplychainmanagementsystem.Utils.UtilsFunctions;
import com.example.logisticandsupplychainmanagementsystem.databinding.ActivityAddCategoryBinding;
import com.github.ybq.android.spinkit.style.Wave;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class AddCategoryActivity extends AppCompatActivity {
    ActivityAddCategoryBinding binding;
    DatabaseReference rootRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        binding = ActivityAddCategoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        init();
        setListener();
    }

    private void init() {
        rootRef = FirebaseDatabase.getInstance().getReference();
        Wave wave = new Wave();
        binding.LoadingBar.setIndeterminateDrawable(wave);
        binding.LoadingBar.setVisibility(View.INVISIBLE);

    }

    private void setListener() {
        binding.AddCategoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddCategoryToDatabase();
            }
        });
    }

    private void AddCategoryToDatabase() {
        try {

            final String name = binding.catName.getText().toString();
            if (TextUtils.isEmpty(name)) {
                UtilsFunctions.setError(binding.catName, "Name of Category Required");
                UtilsFunctions.showShortToastError(AddCategoryActivity.this, "Enter the name of category to be added.");
            } else {
                binding.LoadingBar.setVisibility(View.VISIBLE);
                DatabaseReference userKeyRef = rootRef.child("Categories").push();

                final String PushID = userKeyRef.getKey();
                Map MessageMap = new HashMap<>();
                MessageMap.put("ID", PushID);
                MessageMap.put("Name", name);

                rootRef.child("Categories").child(PushID).updateChildren(MessageMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if (task.isSuccessful()) {
                            UtilsFunctions.showShortToastInfo(AddCategoryActivity.this, "Category added successfully...!");
                            finish();
                        } else {
                            binding.LoadingBar.setVisibility(View.GONE);
                            UtilsFunctions.showShortToastWarning(AddCategoryActivity.this, "Some Problem happen will adding Category...!");

                        }
                    }
                });
            }
        } catch (Exception e) {
            binding.LoadingBar.setVisibility(View.GONE);
            UtilsFunctions.showShortToastWarning(AddCategoryActivity.this, "Some Problem happen will adding Category...!");
        }
    }
}