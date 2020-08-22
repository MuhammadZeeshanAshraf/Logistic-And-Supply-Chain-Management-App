package com.example.logisticandsupplychainmanagementsystem.Auth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.example.logisticandsupplychainmanagementsystem.Activities.Admin.AdminDashBoardActivity;
import com.example.logisticandsupplychainmanagementsystem.R;
import com.example.logisticandsupplychainmanagementsystem.Utils.UtilsFunctions;
import com.example.logisticandsupplychainmanagementsystem.databinding.ActivityAdminLoginBinding;
import com.example.logisticandsupplychainmanagementsystem.databinding.ActivityLoginAsBinding;
import com.github.ybq.android.spinkit.style.Wave;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import es.dmoral.toasty.Toasty;

public class AdminLoginActivity extends AppCompatActivity {

    ActivityAdminLoginBinding binding;
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UtilsFunctions.hideStatusBar(this);
        binding = ActivityAdminLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        init();
        setListener();
    }

    private void init()
    {
        firebaseAuth = FirebaseAuth.getInstance();
        Wave wave = new Wave();
        binding.AdminLoadingBar.setIndeterminateDrawable(wave);
        binding.AdminLoadingBar.setVisibility(View.INVISIBLE);
    }

    private void setListener() {
        binding.AdminSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CheckFields();
            }
        });
    }

    private void CheckFields() {
        try {


            final String email = binding.AdminEmail.getText().toString();
            final String password = binding.AdminPassword.getText().toString();

            if(TextUtils.isEmpty(email))
            {
                binding.AdminEmail.setError("Email Required");
                Toasty.error(AdminLoginActivity.this ,"Enter Your Email", Toasty.LENGTH_SHORT).show();

            }
            if(TextUtils.isEmpty(password))
            {
                binding.AdminPassword.setError("Password Required");
                Toasty.error(AdminLoginActivity.this ,"Enter Your Password", Toasty.LENGTH_SHORT).show();

            }
            if(!(TextUtils.isEmpty(email)))
            {
                if(!(TextUtils.isEmpty(password)))
                {
                    ConnectivityManager connManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                    NetworkInfo mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

                    ConnectivityManager cm =
                            (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

                    NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
                    boolean isConnected = activeNetwork != null &&
                            activeNetwork.isConnectedOrConnecting();


                    if (mWifi.isConnected() || isConnected) {

                        AllowUserToLogin(email, password);
                    }
                    else
                    {
                        Toasty.warning(AdminLoginActivity.this, "Check Your Internet ! Make Sure Your are Connected to Internet ", Toasty.LENGTH_SHORT).show();
                    }



                }

            }
        }catch (Exception e)
        {
            Toast.makeText(AdminLoginActivity.this, ""+ e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void AllowUserToLogin(final String email, final String password) {

        binding.AdminLoadingBar.setVisibility(View.VISIBLE);
        if(email.equals("admin@admin.com") && password.equals("123456"))
        {
            firebaseAuth.signInWithEmailAndPassword(email , password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful())
                    {
                        Toasty.success(AdminLoginActivity.this, "Admin Login Successfully" , Toast.LENGTH_SHORT).show();
                        binding.AdminLoadingBar.setVisibility(View.INVISIBLE);
                        SendUserToActivity(new AdminDashBoardActivity());
                    }else
                    {
                        binding.AdminLoadingBar.setVisibility(View.INVISIBLE);
                        Toasty.error(AdminLoginActivity.this ,"Some Problem occur while login....", Toasty.LENGTH_SHORT).show();

                    }
                }
            });

        }
        else
        {
            binding.AdminLoadingBar.setVisibility(View.INVISIBLE);
            Toasty.error(AdminLoginActivity.this ,"Your Login Credentials are Invalid", Toasty.LENGTH_SHORT).show();

        }

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    public void SendUserToActivity(Activity activity)
    {
        Intent intent = new Intent(AdminLoginActivity.this , activity.getClass());
        startActivity(intent);
        finish();
    }
}