package com.github.tvbox.mobile.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.github.tvbox.mobile.R;
import com.github.tvbox.mobile.bean.LiveChannelItem;
import java.util.ArrayList;
import java.util.List;

public class LiveChannelItemAdapter extends RecyclerView.Adapter<LiveChannelItemAdapter.VH> {
    private List<LiveChannelItem> data = new ArrayList<>();
    private int selectedPos = -1;
    private OnChannelClickListener listener;

    public interface OnChannelClickListener {
        void onClick(LiveChannelItem channel, int position);
    }

    public void setOnChannelClickListener(OnChannelClickListener l) { this.listener = l; }

    public void setChannels(List<LiveChannelItem> list) {
        this.data = list != null ? list : new ArrayList<>();
        selectedPos = -1;
        notifyDataSetChanged();
    }

    public void setSelectedPosition(int pos) {
        int old = selectedPos;
        selectedPos = pos;
        if (old >= 0) notifyItemChanged(old);
        notifyItemChanged(pos);
    }

    @NonNull @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_live_channel, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH h, int pos) {
        LiveChannelItem ch = data.get(pos);
        h.tvNum.setText(String.valueOf(ch.getChannelNum()));
        h.tvName.setText(ch.getChannelName());
        h.itemView.setSelected(pos == selectedPos);
        h.tvName.setSelected(pos == selectedPos);
        h.itemView.setOnClickListener(v -> {
            setSelectedPosition(h.getAdapterPosition());
            if (listener != null) listener.onClick(data.get(h.getAdapterPosition()), h.getAdapterPosition());
        });
    }

    @Override public int getItemCount() { return data.size(); }

    static class VH extends RecyclerView.ViewHolder {
        TextView tvNum, tvName;
        VH(@NonNull View v) {
            super(v);
            tvNum = v.findViewById(R.id.tv_channel_num);
            tvName = v.findViewById(R.id.tv_channel_name);
        }
    }
}
