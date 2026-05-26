package com.github.tvbox.mobile.base;

import android.app.Application;
import android.content.Context;

import androidx.multidex.MultiDex;

import com.github.tvbox.mobile.util.HawkConfig;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.model.HttpHeaders;
import com.orhanobut.hawk.Hawk;
import com.blankj.utilcode.util.Utils;

import me.jessyan.autosize.AutoSize;
import me.jessyan.autosize.AutoSizeConfig;
import me.jessyan.autosize.unit.Subunits;

import okhttp3.OkHttpClient;

import java.util.concurrent.TimeUnit;

public class App extends Application {

    private static App instance;

    public static App getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        // Utils
        Utils.init(this);

        // Hawk prefs
        Hawk.init(this).build();

        // AutoSize: design width 360dp
        AutoSizeConfig.getInstance()
                .setDesignWidthInDp(360)
                .setDesignHeightInDp(780)
                .getUnitsManager()
                .setSupportDP(true)
                .setSupportSP(true);

        // OkGo
        initOkGo();
    }

    private void initOkGo() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .connectTimeout(15, TimeUnit.SECONDS)
                .build();

        OkGo.getInstance()
                .init(this)
                .setOkHttpClient(okHttpClient)
                .setCacheMode(CacheMode.NO_CACHE)
                .setRetryCount(0);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
