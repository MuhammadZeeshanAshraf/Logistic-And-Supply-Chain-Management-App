package com.example.logisticandsupplychainmanagementsystem.Activities.User;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.example.logisticandsupplychainmanagementsystem.Auth.UserLoginActivity;
import com.example.logisticandsupplychainmanagementsystem.Fragments.CartFragment;
import com.example.logisticandsupplychainmanagementsystem.Fragments.HomeFragment;
import com.example.logisticandsupplychainmanagementsystem.Fragments.OrderFragment;
import com.example.logisticandsupplychainmanagementsystem.Fragments.ProfileFragment;
import com.example.logisticandsupplychainmanagementsystem.R;
import com.example.logisticandsupplychainmanagementsystem.Utils.UtilsFunctions;
import com.example.logisticandsupplychainmanagementsystem.databinding.ActivityHomeBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity  implements BottomNavigationView.OnNavigationItemSelectedListener {

    ActivityHomeBinding binding;
    private TextView tv_Name, tv_Email, side_navHome, side_cart, side_about, side_Profile, side_navLogout, side_Orders;
    MenuItem prevMenuItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UtilsFunctions.hideStatusBar(this);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        linkViews();
        settingListener();
        settingViewPager();
        binding.bottomNavViewPager.setOnNavigationItemSelectedListener(this);
    }

    private void linkViews() {
        tv_Name = findViewById(R.id.tv_Name);
        tv_Email = findViewById(R.id.tv_Email);
        side_navHome = findViewById(R.id.side_navHome);
        side_cart = findViewById(R.id.side_cart);
        side_about = findViewById(R.id.side_about);
        side_Profile = findViewById(R.id.side_Profile);
        side_navLogout = findViewById(R.id.side_navLogout);
        side_Orders = findViewById(R.id.side_Orders);


    }
    private void settingListener() {
        binding.bottomNavViewPager.setOnNavigationItemSelectedListener(this);
        binding.imgeViewMenu.setOnClickListener(v -> {
            hideKeyboard(HomeActivity.this);
            if (binding.drawerLayout.isDrawerOpen(Gravity.LEFT)) {
                binding.drawerLayout.closeDrawer(Gravity.LEFT);
            } else {
                binding.drawerLayout.openDrawer(Gravity.LEFT);
            }
        });


        side_navHome.setOnClickListener(v -> {
            closeDrawer();
            binding.viewPagerMain.setCurrentItem(1);
        });

        side_cart.setOnClickListener(v -> {
            closeDrawer();
            binding.viewPagerMain.setCurrentItem(0);
        });


        side_Profile.setOnClickListener(v -> {
            closeDrawer();
            binding.viewPagerMain.setCurrentItem(3);
        });

        side_about.setOnClickListener(v -> closeDrawer());

        side_navLogout.setOnClickListener(v -> {
            startActivity(new Intent(HomeActivity.this, UserLoginActivity.class));
            finish();
        });

        side_Orders.setOnClickListener(v -> {
            closeDrawer();
            binding.viewPagerMain.setCurrentItem(2);
        });

        binding.ivCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.viewPagerMain.setCurrentItem(0);
            }
        });

    }

    private void closeDrawer() {
        binding.drawerLayout.closeDrawer(GravityCompat.START);
    }


    private void settingViewPager() {
        binding.viewPagerMain.setAdapter(new ViewPagerFragmentAdapter(this));
        binding.viewPagerMain.setCurrentItem(1, false);
        binding.viewPagerMain.registerOnPageChangeCallback(pageChangeCallback);
        binding.bottomNavViewPager.getMenu().findItem(R.id.item_home).setChecked(true);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {

            case R.id.item_cart:
                binding.viewPagerMain.setCurrentItem(0);
                break;
            case R.id.item_home:
                binding.viewPagerMain.setCurrentItem(1);
                break;
            case R.id.item_orders:
                binding.viewPagerMain.setCurrentItem(2);
                break;
            case R.id.item_profile:
                binding.viewPagerMain.setCurrentItem(3);
                break;
        }
        return false;
    }


    private class ViewPagerFragmentAdapter extends FragmentStateAdapter {

        public ViewPagerFragmentAdapter(@NonNull FragmentActivity fragmentActivity) {
            super(fragmentActivity);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            switch (position) {

                case 0:
                    return new CartFragment();
                case 1:
                    return new HomeFragment();
                case 2:
                    return new OrderFragment();
                case 3:
                    return new ProfileFragment();
            }
            return new HomeFragment();
        }

        @Override
        public int getItemCount() {
            return 4;
        }
    }

    ViewPager2.OnPageChangeCallback pageChangeCallback = new ViewPager2.OnPageChangeCallback() {
        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public void onPageSelected(int position) {
            super.onPageSelected(position);
            if (prevMenuItem != null)
                prevMenuItem.setChecked(false);
            else
                binding.bottomNavViewPager.getMenu().getItem(0).setChecked(false);
            binding.bottomNavViewPager.getMenu().getItem(position).setChecked(true);
            prevMenuItem = binding.bottomNavViewPager.getMenu().getItem(position);
            switch (position) {
                case 2:
                    break;
                case 0:
                case 1:
                case 3:
                    break;
            }
        }
    };

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}