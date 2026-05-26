package com.github.tvbox.mobile.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.tvbox.mobile.R;
import com.github.tvbox.mobile.bean.VodInfo;
import com.github.tvbox.mobile.cache.RoomDataManger;
import com.github.tvbox.mobile.cache.VodCollect;
import com.github.tvbox.mobile.ui.adapter.VodCardAdapter;

import java.util.ArrayList;
import java.util.List;

public class CollectActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.color_bg_primary));
        setContentView(R.layout.activity_list_page);

        ((TextView) findViewById(R.id.tv_page_title)).setText("我的收藏");
        findViewById(R.id.iv_back).setOnClickListener(v -> finish());

        RecyclerView rv = findViewById(R.id.rv_list);
        rv.setLayoutManager(new GridLayoutManager(this, 3));
        VodCardAdapter adapter = new VodCardAdapter();
        rv.setAdapter(adapter);

        adapter.setOnItemClickListener((vod, pos) ->
                startActivity(new Intent(this, PlayActivity.class)
                        .putExtra("id", vod.id)
                        .putExtra("name", vod.name)
                        .putExtra("sourceKey", vod.sourceKey)));

        new Thread(() -> {
            List<VodCollect> collects = RoomDataManger.getAllVodCollect();
            List<VodInfo> list = new ArrayList<>();
            if (collects != null) {
                for (VodCollect c : collects) {
                    VodInfo v = new VodInfo();
                    v.id = c.vodId;
                    v.name = c.name;
                    v.pic = c.pic;
                    v.sourceKey = c.sourceKey;
                    list.add(v);
                }
            }
            runOnUiThread(() -> {
                adapter.setData(list);
                findViewById(R.id.ll_empty).setVisibility(
                        list.isEmpty() ? View.VISIBLE : View.GONE);
            });
        }).start();
    }
}
