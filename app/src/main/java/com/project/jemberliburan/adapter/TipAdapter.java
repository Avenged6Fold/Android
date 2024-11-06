package com.project.jemberliburan.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.project.jemberliburan.R;
import com.project.jemberliburan.Model.Tip;
import java.util.List;

public class TipAdapter extends RecyclerView.Adapter<TipAdapter.TipViewHolder> {

    private Context context;
    private List<Tip> tips;
    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(Tip tip);
    }

    public TipAdapter(Context context, List<Tip> tips, OnItemClickListener onItemClickListener) {
        this.context = context;
        this.tips = tips;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public TipViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_tip, parent, false);
        return new TipViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TipViewHolder holder, int position) {
        Tip tip = tips.get(position);
        holder.titleTextView.setText(tip.getTitle());
        holder.descriptionTextView.setText(tip.getDescription());
        holder.imageView.setImageResource(tip.getImageResId());

        holder.itemView.setOnClickListener(v -> onItemClickListener.onItemClick(tip));
    }

    @Override
    public int getItemCount() {
        return tips.size();
    }

    static class TipViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView;
        TextView descriptionTextView;
        ImageView imageView;

        public TipViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.tipTitle);
            descriptionTextView = itemView.findViewById(R.id.tipDescription);
            imageView = itemView.findViewById(R.id.tipImage);
        }
    }
}
