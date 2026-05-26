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

public class SourceFilterAdapter extends RecyclerView.Adapter<SourceFilterAdapter.VH> {
    private List<String> sources = new ArrayList<>();
    private int selectedPos = 0;
    private OnSourceClickListener listener;

    public interface OnSourceClickListener {
        void onClick(String source, int position);
    }

    public void setOnSourceClickListener(OnSourceClickListener l) { this.listener = l; }

    public void setSources(List<String> list) {
        sources.clear();
        sources.add("全部显示");
        sources.addAll(list);
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
                .inflate(R.layout.item_source_filter, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull VH h, int pos) {
        h.tv.setText(sources.get(pos));
        h.tv.setSelected(pos == selectedPos);
        h.itemView.setOnClickListener(v -> {
            setSelectedPosition(h.getAdapterPosition());
            if (listener != null) {
                String src = pos == 0 ? null : sources.get(h.getAdapterPosition());
                listener.onClick(src, h.getAdapterPosition());
            }
        });
    }

    @Override public int getItemCount() { return sources.size(); }

    static class VH extends RecyclerView.ViewHolder {
        TextView tv;
        VH(@NonNull View v) { super(v); tv = (TextView) v; }
    }
}
