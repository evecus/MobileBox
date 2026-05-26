package com.github.tvbox.mobile.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.tvbox.mobile.R;
import com.github.tvbox.mobile.ui.adapter.LiveChannelGroupAdapter;
import com.github.tvbox.mobile.ui.adapter.LiveChannelItemAdapter;

public class LiveFragment extends Fragment {

    private RecyclerView rvGroups;
    private RecyclerView rvChannels;
    private LiveChannelGroupAdapter groupAdapter;
    private LiveChannelItemAdapter channelAdapter;
    private View playerContainer;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_live, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        playerContainer = view.findViewById(R.id.fl_live_player);

        rvGroups = view.findViewById(R.id.rv_live_groups);
        rvGroups.setLayoutManager(new LinearLayoutManager(getContext()));
        groupAdapter = new LiveChannelGroupAdapter();
        rvGroups.setAdapter(groupAdapter);

        rvChannels = view.findViewById(R.id.rv_live_channels);
        rvChannels.setLayoutManager(new LinearLayoutManager(getContext()));
        channelAdapter = new LiveChannelItemAdapter();
        rvChannels.setAdapter(channelAdapter);

        // Fullscreen toggle
        view.findViewById(R.id.iv_live_fullscreen).setOnClickListener(v -> enterFullscreen());

        groupAdapter.setOnGroupClickListener((group, position) -> {
            groupAdapter.setSelectedPosition(position);
            channelAdapter.setChannels(group.getChannelList());
        });

        channelAdapter.setOnChannelClickListener((channel, position) -> {
            channelAdapter.setSelectedPosition(position);
            // TODO: play channel
        });
    }

    private void enterFullscreen() {
        // Switch to landscape fullscreen player
        // TODO: implement fullscreen player activity
    }
}
