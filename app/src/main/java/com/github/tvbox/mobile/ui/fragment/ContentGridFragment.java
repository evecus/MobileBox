package com.github.tvbox.mobile.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.github.tvbox.mobile.R;
import com.github.tvbox.mobile.ui.activity.PlayActivity;
import com.github.tvbox.mobile.ui.adapter.VodCardAdapter;

public class ContentGridFragment extends Fragment {

    private static final String ARG_CATEGORY = "category";
    private static final String ARG_POSITION = "position";

    private String category;
    private int position;
    private RecyclerView rvContent;
    private SwipeRefreshLayout swipeRefresh;
    private ProgressBar progressBar;
    private VodCardAdapter adapter;

    public static ContentGridFragment newInstance(String category, int position) {
        ContentGridFragment f = new ContentGridFragment();
        Bundle args = new Bundle();
        args.putString(ARG_CATEGORY, category);
        args.putInt(ARG_POSITION, position);
        f.setArguments(args);
        return f;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            category = getArguments().getString(ARG_CATEGORY);
            position = getArguments().getInt(ARG_POSITION);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_content_grid, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        swipeRefresh = view.findViewById(R.id.swipe_refresh);
        swipeRefresh.setColorSchemeResources(R.color.color_primary);
        rvContent = view.findViewById(R.id.rv_content);
        progressBar = view.findViewById(R.id.progress_bar);

        rvContent.setLayoutManager(new GridLayoutManager(getContext(), 3));
        adapter = new VodCardAdapter();
        rvContent.setAdapter(adapter);

        adapter.setOnItemClickListener((vod, pos) -> {
            Intent intent = new Intent(getActivity(), PlayActivity.class);
            intent.putExtra("id", vod.id);
            intent.putExtra("name", vod.name);
            intent.putExtra("sourceKey", vod.sourceKey);
            startActivity(intent);
        });

        swipeRefresh.setOnRefreshListener(this::loadContent);
        loadContent();
    }

    private void loadContent() {
        progressBar.setVisibility(View.VISIBLE);
        // TODO: load content by category using ApiConfig
        progressBar.setVisibility(View.GONE);
        swipeRefresh.setRefreshing(false);
    }
}
