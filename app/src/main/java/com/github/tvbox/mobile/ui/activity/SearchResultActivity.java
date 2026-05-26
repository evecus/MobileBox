package com.github.tvbox.mobile.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.tvbox.mobile.R;
import com.github.tvbox.mobile.ui.adapter.SearchResultAdapter;
import com.github.tvbox.mobile.ui.adapter.SourceFilterAdapter;

public class SearchResultActivity extends AppCompatActivity {

    private String keyword;
    private RecyclerView rvSources;
    private RecyclerView rvResults;
    private ProgressBar progressBar;
    private View flState;
    private SearchResultAdapter resultAdapter;
    private SourceFilterAdapter sourceAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.color_bg_primary));
        setContentView(R.layout.activity_search_result);

        keyword = getIntent().getStringExtra("keyword");

        TextView tvKeyword = findViewById(R.id.tv_keyword);
        tvKeyword.setText(keyword);

        findViewById(R.id.iv_back).setOnClickListener(v -> finish());
        findViewById(R.id.iv_search).setOnClickListener(v -> {
            // Re-open search activity
            startActivity(new Intent(this, SearchActivity.class));
            finish();
        });

        rvSources = findViewById(R.id.rv_sources);
        rvSources.setLayoutManager(new LinearLayoutManager(this));
        sourceAdapter = new SourceFilterAdapter();
        rvSources.setAdapter(sourceAdapter);

        rvResults = findViewById(R.id.rv_results);
        rvResults.setLayoutManager(new LinearLayoutManager(this));
        resultAdapter = new SearchResultAdapter();
        rvResults.setAdapter(resultAdapter);
        flState = findViewById(R.id.fl_state);

        resultAdapter.setOnItemClickListener((vod, position) -> {
            Intent intent = new Intent(this, PlayActivity.class);
            intent.putExtra("id", vod.id);
            intent.putExtra("name", vod.name);
            intent.putExtra("sourceKey", vod.sourceKey);
            startActivity(intent);
        });

        sourceAdapter.setOnSourceClickListener((source, position) -> {
            sourceAdapter.setSelectedPosition(position);
            resultAdapter.filterBySource(source);
        });

        startSearch();
    }

    private void startSearch() {
        flState.setVisibility(View.VISIBLE);
        // TODO: call ApiConfig to search across all sources
        // ApiConfig.get().searchContent(keyword, callback)
        // For now, show empty state
        flState.setVisibility(View.GONE);
    }
}
