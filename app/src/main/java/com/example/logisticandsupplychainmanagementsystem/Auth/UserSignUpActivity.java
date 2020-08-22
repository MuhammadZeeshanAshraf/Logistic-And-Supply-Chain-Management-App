package com.example.logisticandsupplychainmanagementsystem.Auth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.airbnb.lottie.utils.Utils;
import com.example.logisticandsupplychainmanagementsystem.Activities.User.UserDashBoardActivity;
import com.example.logisticandsupplychainmanagementsystem.R;
import com.example.logisticandsupplychainmanagementsystem.Utils.UtilsFunctions;
import com.example.logisticandsupplychainmanagementsystem.databinding.ActivityAdminLoginBinding;
import com.example.logisticandsupplychainmanagementsystem.databinding.ActivityUserSignUpBinding;
import com.github.ybq.android.spinkit.style.Wave;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class UserSignUpActivity extends AppCompatActivity {
    ActivityUserSignUpBinding binding;
    FirebaseAuth firebaseAuth;
    DatabaseReference rootRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        binding = ActivityUserSignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        init();
        setListener();
    }

    private void init() {
        firebaseAuth = FirebaseAuth.getInstance();
        rootRef = FirebaseDatabase.getInstance().getReference();
        Wave wave = new Wave();
        binding.LoadingBar.setIndeterminateDrawable(wave);
        binding.LoadingBar.setVisibility(View.INVISIBLE);
    }

    private void setListener() {
        binding.UserSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CheckField();
            }
        });

        binding.UserSignUpGoToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserSignUpActivity.this , UserLoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void CheckField() {
        try {
            final String name = binding.UserSignUpName.getText().toString();
            final String email = binding.UserSignUpEmail.getText().toString();
            final String phone = binding.UserSignUpPhone.getText().toString();
            final String password = binding.UserSignUpPassword.getText().toString();
            final String confirm = binding.UserSignUpConfirmPassword.getText().toString();

            if (TextUtils.isEmpty(name)) {
                UtilsFunctions.setError(binding.UserSignUpName, "Username Required");
            } else if (TextUtils.isEmpty(email)) {
                UtilsFunctions.setError(binding.UserSignUpEmail, "Email Required");

            } else if (TextUtils.isEmpty(phone)) {
                UtilsFunctions.setError(binding.UserSignUpPhone, "Phone Number Required");
            } else if (TextUtils.isEmpty(password)) {
                UtilsFunctions.setError(binding.UserSignUpPassword, "Password Required");
            } else if (TextUtils.isEmpty(confirm)) {
                UtilsFunctions.setError(binding.UserSignUpConfirmPassword, "Confirm Password Required");
            } else if (!(password.equals(confirm))) {
                UtilsFunctions.setError(binding.UserSignUpConfirmPassword, "Password and Confirm Password must be same");
            } else {
                if (UtilsFunctions.isNetworkAvailable(this)) {
                    RegisterUserWithFirebase(name, email, phone, password);
                } else {
                    UtilsFunctions.showShortToastInfo(this, "Check Your Internet ! Make Sure Your are Connected to Internet ");
                }


            }
        } catch (Exception e) {
            Toast.makeText(UserSignUpActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void RegisterUserWithFirebase(String name, String email, String phone, String password) {
        try {
            binding.LoadingBar.setVisibility(View.VISIBLE);
            firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful())
                    {
                        final String currentUserID = firebaseAuth.getCurrentUser().getUid();
                        Map MessageMap = new HashMap<>();
                        MessageMap.put("ID", currentUserID);
                        MessageMap.put("Email", email);
                        MessageMap.put("Password", password);
                        MessageMap.put("Username", name);
                        MessageMap.put("PhoneNumber", phone);

                        rootRef.child("Users").child(currentUserID).setValue(MessageMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                                if(task.isSuccessful())
                                {
                                    startActivity(new Intent(UserSignUpActivity.this , UserDashBoardActivity.class));
                                    finish();
                                }else
                                {
                                    binding.LoadingBar.setVisibility(View.GONE);
                                    UtilsFunctions.showShortToastWarning(UserSignUpActivity.this,"Some Problem happen will adding user...!");

                                }
                            }
                        });

                    }else
                    {
                        binding.LoadingBar.setVisibility(View.GONE);
                        UtilsFunctions.showShortToastWarning(UserSignUpActivity.this,"Some Problem happen will adding user...!");

                    }
                }
            });




        } catch (Exception error) {
            binding.LoadingBar.setVisibility(View.GONE);
            UtilsFunctions.showShortToastWarning(UserSignUpActivity.this,"Some Problem happen will adding user...!");
        }
    }
}