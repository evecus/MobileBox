package com.github.tvbox.mobile.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.github.tvbox.mobile.R;
import com.github.tvbox.mobile.bean.VodInfo;
import java.util.ArrayList;
import java.util.List;

public class SearchResultAdapter extends RecyclerView.Adapter<SearchResultAdapter.VH> {
    private List<VodInfo> allData = new ArrayList<>();
    private List<VodInfo> filtered = new ArrayList<>();
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onClick(VodInfo vod, int position);
    }

    public void setOnItemClickListener(OnItemClickListener l) { this.listener = l; }

    public void addResult(List<VodInfo> list) {
        allData.addAll(list);
        filtered.addAll(list);
        notifyDataSetChanged();
    }

    public void filterBySource(String source) {
        filtered.clear();
        if (source == null || source.isEmpty()) {
            filtered.addAll(allData);
        } else {
            for (VodInfo v : allData) {
                if (source.equals(v.sourceKey)) filtered.add(v);
            }
        }
        notifyDataSetChanged();
    }

    @NonNull @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new VH(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_search_result, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull VH h, int pos) {
        VodInfo v = filtered.get(pos);
        h.tvTitle.setText(v.name);
        h.tvSource.setText(v.sourceKey != null ? v.sourceKey : "");
        String score = v.note;
        h.tvScore.setText((score != null && !score.isEmpty() && !score.equals("0.0"))
                ? "评分：" + score : "");
        h.tvRemark.setText(v.note != null ? v.note : "");
        Glide.with(h.itemView).load(v.pic)
                .placeholder(R.color.color_bg_secondary)
                .error(R.color.color_bg_secondary)
                .into(h.ivCover);
        h.itemView.setOnClickListener(vw -> {
            if (listener != null) listener.onClick(v, pos);
        });
    }

    @Override public int getItemCount() { return filtered.size(); }

    static class VH extends RecyclerView.ViewHolder {
        ImageView ivCover;
        TextView tvTitle, tvSource, tvScore, tvRemark;
        VH(@NonNull View v) {
            super(v);
            ivCover = v.findViewById(R.id.iv_cover);
            tvTitle = v.findViewById(R.id.tv_title);
            tvSource = v.findViewById(R.id.tv_source);
            tvScore = v.findViewById(R.id.tv_score);
            tvRemark = v.findViewById(R.id.tv_remark);
        }
    }
}
