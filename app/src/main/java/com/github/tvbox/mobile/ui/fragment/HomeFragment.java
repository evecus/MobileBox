package com.github.tvbox.mobile.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.github.tvbox.mobile.R;
import com.github.tvbox.mobile.ui.activity.CollectActivity;
import com.github.tvbox.mobile.ui.activity.HistoryActivity;
import com.github.tvbox.mobile.ui.activity.SearchActivity;
import com.github.tvbox.mobile.ui.adapter.HomePageAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HomeFragment extends Fragment {

    private TabLayout tabLayout;
    private ViewPager2 viewPager;
    private final List<String> categories = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Search bar click → SearchActivity
        view.findViewById(R.id.ll_search_bar).setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), SearchActivity.class));
        });

        // Collect / History
        view.findViewById(R.id.iv_collect).setOnClickListener(v ->
                startActivity(new Intent(getActivity(), CollectActivity.class)));
        view.findViewById(R.id.iv_history).setOnClickListener(v ->
                startActivity(new Intent(getActivity(), HistoryActivity.class)));

        tabLayout = view.findViewById(R.id.tab_layout);
        viewPager = view.findViewById(R.id.view_pager);

        setupTabs();
    }

    private void setupTabs() {
        categories.clear();
        categories.addAll(Arrays.asList("主页", "热门电影", "热播剧集", "热门动漫", "热播综艺"));

        HomePageAdapter adapter = new HomePageAdapter(this, categories);
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(2);

        new TabLayoutMediator(tabLayout, viewPager, (tab, position) ->
                tab.setText(categories.get(position))
        ).attach();
    }

    public void refreshCategories(List<String> newCategories) {
        categories.clear();
        categories.addAll(newCategories);
        if (viewPager.getAdapter() != null) {
            viewPager.getAdapter().notifyDataSetChanged();
        }
        // Reattach mediator for new tabs
    }
}
