package com.github.tvbox.mobile.base;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.github.tvbox.mobile.R;

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setLightStatusBar(true);
        setContentView(getLayoutResID());
        init();
    }

    protected abstract int getLayoutResID();
    protected abstract void init();

    protected void setLightStatusBar(boolean light) {
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        int flags = window.getDecorView().getSystemUiVisibility();
        if (light) {
            flags |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.color_bg_primary));
        } else {
            flags &= ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.color_FF000000));
        }
        window.getDecorView().setSystemUiVisibility(flags);
    }

    protected void setFullscreen() {
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        window.setStatusBarColor(android.graphics.Color.TRANSPARENT);
        int flags = window.getDecorView().getSystemUiVisibility();
        flags &= ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
        window.getDecorView().setSystemUiVisibility(flags);
    }
}
