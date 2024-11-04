package com.project.jemberliburan.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.project.jemberliburan.Model.Tip;
import com.project.jemberliburan.R;


import java.util.List;

public class TipAdapter extends RecyclerView.Adapter<TipAdapter.ViewHolder> {

    private Context context;
    private List<Tip> tips;

    public TipAdapter(Context context, List<Tip> tips) {
        this.context = context;
        this.tips = tips;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_item_tip, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Tip tip = tips.get(position);
        holder.title.setText(tip.getTitle());
        holder.description.setText(tip.getDescription());
        holder.image.setImageResource(tip.getImageResourceId());
    }

    @Override
    public int getItemCount() {
        return tips.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, description;
        ImageView image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tv_tip_title);
            description = itemView.findViewById(R.id.tv_tip_description);
            image = itemView.findViewById(R.id.image_tip);
        }
    }
}
