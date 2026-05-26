package com.github.tvbox.mobile.ui.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.github.tvbox.mobile.ui.fragment.ContentGridFragment;

import java.util.List;

public class HomePageAdapter extends FragmentStateAdapter {

    private final List<String> categories;

    public HomePageAdapter(@NonNull Fragment fragment, List<String> categories) {
        super(fragment);
        this.categories = categories;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return ContentGridFragment.newInstance(categories.get(position), position);
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }
}
