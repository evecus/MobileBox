package com.github.tvbox.mobile.ui.activity;

import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.tvbox.mobile.R;
import com.github.tvbox.mobile.ui.adapter.EpisodeAdapter;
import com.github.tvbox.mobile.ui.adapter.RouteAdapter;

public class PlayActivity extends AppCompatActivity {

    private FrameLayout flPlayerContainer;
    private ProgressBar pbLoading;
    private ImageView ivPlayPause;
    private ImageView ivFullscreen;
    private ImageView ivBack;
    private RecyclerView rvRoutes;
    private RecyclerView rvEpisodes;
    private RouteAdapter routeAdapter;
    private EpisodeAdapter episodeAdapter;

    private String vodId;
    private String vodName;
    private String sourceKey;
    private boolean isFullscreen = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.color_bg_primary));
        setContentView(R.layout.activity_play);

        vodId = getIntent().getStringExtra("id");
        vodName = getIntent().getStringExtra("name");
        sourceKey = getIntent().getStringExtra("sourceKey");

        flPlayerContainer = findViewById(R.id.fl_player_container);
        pbLoading = findViewById(R.id.pb_loading);
        ivPlayPause = findViewById(R.id.iv_play_pause);
        ivFullscreen = findViewById(R.id.iv_fullscreen);
        ivBack = findViewById(R.id.iv_back);

        ivBack.setOnClickListener(v -> {
            if (isFullscreen) exitFullscreen();
            else finish();
        });

        ivFullscreen.setOnClickListener(v -> {
            if (isFullscreen) exitFullscreen();
            else enterFullscreen();
        });

        ivPlayPause.setOnClickListener(v -> togglePlayPause());

        // Title
        TextView tvTitle = findViewById(R.id.tv_title);
        TextView tvVideoTitle = findViewById(R.id.tv_video_title);
        if (tvTitle != null) tvTitle.setText(vodName != null ? vodName : "");
        if (tvVideoTitle != null) tvVideoTitle.setText(vodName != null ? vodName : "");

        // Source name
        TextView tvSourceName = findViewById(R.id.tv_source_name);
        if (tvSourceName != null) tvSourceName.setText(sourceKey != null ? sourceKey : "");

        // Routes
        rvRoutes = findViewById(R.id.rv_routes);
        rvRoutes.setLayoutManager(new LinearLayoutManager(
                this, LinearLayoutManager.HORIZONTAL, false));
        routeAdapter = new RouteAdapter();
        rvRoutes.setAdapter(routeAdapter);

        // Episodes
        rvEpisodes = findViewById(R.id.rv_episodes);
        rvEpisodes.setLayoutManager(new GridLayoutManager(this, 5));
        episodeAdapter = new EpisodeAdapter();
        rvEpisodes.setAdapter(episodeAdapter);

        episodeAdapter.setOnEpisodeClickListener((episode, position) -> {
            episodeAdapter.setSelectedPosition(position);
            // TODO: play episode
        });

        loadVodDetail();
    }

    private void loadVodDetail() {
        pbLoading.setVisibility(View.VISIBLE);
        // TODO: ApiConfig.get().getDetail(sourceKey, vodId, callback)
        pbLoading.setVisibility(View.GONE);
    }

    private void enterFullscreen() {
        isFullscreen = true;
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        // Expand player to full screen
        ViewGroup.LayoutParams lp = flPlayerContainer.getLayoutParams();
        lp.height = ViewGroup.LayoutParams.MATCH_PARENT;
        flPlayerContainer.setLayoutParams(lp);
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_FULLSCREEN |
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }

    private void exitFullscreen() {
        isFullscreen = false;
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        // Restore player height to 210dp
        ViewGroup.LayoutParams lp = flPlayerContainer.getLayoutParams();
        lp.height = (int)(210 * getResources().getDisplayMetrics().density);
        flPlayerContainer.setLayoutParams(lp);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.color_bg_primary));
    }

    private void togglePlayPause() {
        // TODO: toggle IjkPlayer/ExoPlayer
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            enterFullscreen();
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            exitFullscreen();
        }
    }
}
