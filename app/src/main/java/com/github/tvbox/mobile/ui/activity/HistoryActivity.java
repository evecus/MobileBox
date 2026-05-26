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
import com.github.tvbox.mobile.ui.adapter.VodCardAdapter;

import java.util.ArrayList;
import java.util.List;

public class HistoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.color_bg_primary));
        setContentView(R.layout.activity_list_page);

        ((TextView) findViewById(R.id.tv_page_title)).setText("观看历史");
        findViewById(R.id.iv_back).setOnClickListener(v -> finish());

        RecyclerView rv = findViewById(R.id.rv_list);
        rv.setLayoutManager(new GridLayoutManager(this, 3));
        VodCardAdapter adapter = new VodCardAdapter();
        rv.setAdapter(adapter);

        adapter.setOnItemClickListener((vod, pos) -> {
            Intent intent = new Intent(this, PlayActivity.class);
            intent.putExtra("id", vod.id);
            intent.putExtra("name", vod.name);
            intent.putExtra("sourceKey", vod.sourceKey);
            startActivity(intent);
        });

        new Thread(() -> {
            List<VodInfo> list = RoomDataManger.getAllVodRecord(100);
            if (list == null) list = new ArrayList<>();
            final List<VodInfo> finalList = list;
            runOnUiThread(() -> {
                adapter.setData(finalList);
                findViewById(R.id.ll_empty).setVisibility(
                        finalList.isEmpty() ? View.VISIBLE : View.GONE);
            });
        }).start();
    }
}
