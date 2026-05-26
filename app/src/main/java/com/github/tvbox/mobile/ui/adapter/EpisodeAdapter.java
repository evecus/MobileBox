package com.github.tvbox.mobile.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.github.tvbox.mobile.R;
import java.util.ArrayList;
import java.util.List;

public class EpisodeAdapter extends RecyclerView.Adapter<EpisodeAdapter.VH> {
    private List<String> episodes = new ArrayList<>();
    private int selectedPos = 0;
    private OnEpisodeClickListener listener;

    public interface OnEpisodeClickListener {
        void onClick(String episode, int position);
    }

    public void setOnEpisodeClickListener(OnEpisodeClickListener l) { this.listener = l; }

    public void setEpisodes(List<String> list) {
        this.episodes = list;
        notifyDataSetChanged();
    }

    public void setSelectedPosition(int pos) {
        int old = selectedPos;
        selectedPos = pos;
        notifyItemChanged(old);
        notifyItemChanged(pos);
    }

    @NonNull @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new VH(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_episode, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull VH h, int pos) {
        h.tv.setText(episodes.get(pos));
        h.tv.setSelected(pos == selectedPos);
        h.itemView.setOnClickListener(v -> {
            setSelectedPosition(h.getAdapterPosition());
            if (listener != null) listener.onClick(episodes.get(h.getAdapterPosition()), h.getAdapterPosition());
        });
    }

    @Override public int getItemCount() { return episodes.size(); }

    static class VH extends RecyclerView.ViewHolder {
        TextView tv;
        VH(@NonNull View v) { super(v); tv = (TextView) v; }
    }
}
