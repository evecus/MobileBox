package com.github.tvbox.mobile.ui.activity;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.github.tvbox.mobile.R;
import com.github.tvbox.mobile.ui.fragment.HomeFragment;
import com.github.tvbox.mobile.ui.fragment.LiveFragment;
import com.github.tvbox.mobile.ui.fragment.MineFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private HomeFragment homeFragment;
    private LiveFragment liveFragment;
    private MineFragment mineFragment;
    private Fragment currentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Light status bar
        getWindow().getDecorView().setSystemUiVisibility(
            android.view.View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        getWindow().setStatusBarColor(
            androidx.core.content.ContextCompat.getColor(this, R.color.color_bg_primary));

        setContentView(R.layout.activity_main);

        homeFragment = new HomeFragment();
        liveFragment = new LiveFragment();
        mineFragment = new MineFragment();

        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, homeFragment)
                .add(R.id.fragment_container, liveFragment)
                .add(R.id.fragment_container, mineFragment)
                .hide(liveFragment)
                .hide(mineFragment)
                .commit();
        currentFragment = homeFragment;

        BottomNavigationView bottomNav = findViewById(R.id.bottom_nav);
        bottomNav.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_home) {
                switchFragment(homeFragment);
                return true;
            } else if (id == R.id.nav_live) {
                switchFragment(liveFragment);
                return true;
            } else if (id == R.id.nav_mine) {
                switchFragment(mineFragment);
                return true;
            }
            return false;
        });
    }

    private void switchFragment(Fragment target) {
        if (currentFragment != target) {
            getSupportFragmentManager().beginTransaction()
                    .hide(currentFragment)
                    .show(target)
                    .commit();
            currentFragment = target;
        }
    }
}
