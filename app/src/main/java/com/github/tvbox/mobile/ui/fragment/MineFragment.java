package com.github.tvbox.mobile.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.github.tvbox.mobile.R;
import com.github.tvbox.mobile.ui.activity.CollectActivity;
import com.github.tvbox.mobile.ui.activity.HistoryActivity;
import com.github.tvbox.mobile.ui.activity.SettingActivity;

public class MineFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_mine, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.ll_collect).setOnClickListener(v ->
                startActivity(new Intent(getActivity(), CollectActivity.class)));
        view.findViewById(R.id.ll_history).setOnClickListener(v ->
                startActivity(new Intent(getActivity(), HistoryActivity.class)));

        setupSettingRows(view);
    }

    private void setupSettingRows(View view) {
        // Subscribe API
        View rowSubscribe = view.findViewById(R.id.row_subscribe_api);
        if (rowSubscribe != null) {
            ((TextView) rowSubscribe.findViewById(R.id.tv_setting_title)).setText("订阅接口");
            rowSubscribe.setOnClickListener(v ->
                    startActivity(new Intent(getActivity(), SettingActivity.class)
                            .putExtra("page", "api")));
        }

        // Live source
        View rowLive = view.findViewById(R.id.row_live_source);
        if (rowLive != null) {
            ((TextView) rowLive.findViewById(R.id.tv_setting_title)).setText("直播订阅");
            rowLive.setOnClickListener(v ->
                    startActivity(new Intent(getActivity(), SettingActivity.class)
                            .putExtra("page", "live")));
        }

        // Player engine
        View rowPlayer = view.findViewById(R.id.row_player_engine);
        if (rowPlayer != null) {
            ((TextView) rowPlayer.findViewById(R.id.tv_setting_title)).setText("播放器");
            rowPlayer.setOnClickListener(v ->
                    startActivity(new Intent(getActivity(), SettingActivity.class)
                            .putExtra("page", "player")));
        }

        // Decode mode
        View rowDecode = view.findViewById(R.id.row_decode_mode);
        if (rowDecode != null) {
            ((TextView) rowDecode.findViewById(R.id.tv_setting_title)).setText("解码方式");
            rowDecode.setOnClickListener(v ->
                    startActivity(new Intent(getActivity(), SettingActivity.class)
                            .putExtra("page", "decode")));
        }

        // Player misc
        View rowMisc = view.findViewById(R.id.row_player_misc);
        if (rowMisc != null) {
            ((TextView) rowMisc.findViewById(R.id.tv_setting_title)).setText("播放设置");
            rowMisc.setOnClickListener(v ->
                    startActivity(new Intent(getActivity(), SettingActivity.class)
                            .putExtra("page", "misc")));
        }

        // About
        View rowAbout = view.findViewById(R.id.row_about);
        if (rowAbout != null) {
            ((TextView) rowAbout.findViewById(R.id.tv_setting_title)).setText("关于");
            rowAbout.setOnClickListener(v ->
                    startActivity(new Intent(getActivity(), SettingActivity.class)
                            .putExtra("page", "about")));
        }
    }
}
