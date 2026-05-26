package com.github.tvbox.mobile.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.github.tvbox.mobile.R;
import com.github.tvbox.mobile.bean.LiveChannelGroup;
import java.util.ArrayList;
import java.util.List;

public class LiveChannelGroupAdapter extends RecyclerView.Adapter<LiveChannelGroupAdapter.VH> {
    private List<LiveChannelGroup> data = new ArrayList<>();
    private int selectedPos = 0;
    private OnGroupClickListener listener;

    public interface OnGroupClickListener {
        void onClick(LiveChannelGroup group, int position);
    }

    public void setOnGroupClickListener(OnGroupClickListener l) { this.listener = l; }

    public void setData(List<LiveChannelGroup> list) {
        this.data = list;
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
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_live_group, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH h, int pos) {
        h.tv.setText(data.get(pos).getGroupName());
        h.tv.setSelected(pos == selectedPos);
        h.itemView.setOnClickListener(v -> {
            setSelectedPosition(h.getAdapterPosition());
            if (listener != null) listener.onClick(data.get(h.getAdapterPosition()), h.getAdapterPosition());
        });
    }

    @Override public int getItemCount() { return data.size(); }

    static class VH extends RecyclerView.ViewHolder {
        TextView tv;
        VH(@NonNull View v) { super(v); tv = (TextView) v; }
    }
}
