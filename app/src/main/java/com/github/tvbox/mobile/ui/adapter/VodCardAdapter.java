package com.github.tvbox.mobile.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.github.tvbox.mobile.R;
import com.github.tvbox.mobile.bean.VodInfo;

import java.util.ArrayList;
import java.util.List;

public class VodCardAdapter extends RecyclerView.Adapter<VodCardAdapter.VH> {

    private List<VodInfo> data = new ArrayList<>();
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onClick(VodInfo vod, int position);
    }

    public void setOnItemClickListener(OnItemClickListener l) {
        this.listener = l;
    }

    public void setData(List<VodInfo> list) {
        this.data = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_vod_card, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        VodInfo vod = data.get(position);
        holder.tvTitle.setText(vod.name);

        String score = vod.note;
        if (score != null && !score.isEmpty()) {
            holder.tvScore.setVisibility(View.VISIBLE);
            holder.tvScore.setText(score);
        } else {
            holder.tvScore.setVisibility(View.GONE);
        }

        int radius = (int)(12 * holder.itemView.getContext().getResources().getDisplayMetrics().density);
        Glide.with(holder.itemView)
                .load(vod.pic)
                .apply(new RequestOptions()
                        .placeholder(R.color.color_bg_secondary)
                        .error(R.color.color_bg_secondary)
                        .transform(new RoundedCorners(radius)))
                .into(holder.ivPoster);

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) listener.onClick(vod, holder.getAdapterPosition());
        });
    }

    @Override
    public int getItemCount() { return data.size(); }

    static class VH extends RecyclerView.ViewHolder {
        ImageView ivPoster;
        TextView tvTitle;
        TextView tvScore;
        VH(@NonNull View itemView) {
            super(itemView);
            ivPoster = itemView.findViewById(R.id.iv_poster);
            tvTitle = itemView.findViewById(R.id.tv_title_overlay);
            tvScore = itemView.findViewById(R.id.tv_score);
        }
    }
}
