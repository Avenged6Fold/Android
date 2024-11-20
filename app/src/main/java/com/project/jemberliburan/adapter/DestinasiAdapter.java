package com.project.jemberliburan.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.project.jemberliburan.R;
import java.util.List;

public class DestinasiAdapter extends RecyclerView.Adapter<DestinasiAdapter.ViewHolder> {

    private Context context;
    private List<DestinasiItem> destinasiList;

    public DestinasiAdapter(Context context, List<DestinasiItem> destinasiList) {
        this.context = context;
        this.destinasiList = destinasiList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_destinasi, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DestinasiItem item = destinasiList.get(position);
        holder.imageView.setImageResource(item.getImageResId());
        holder.nameTextView.setText(item.getName());

        // Set klik listener untuk card
        holder.cardView.setOnClickListener(v -> {
            Intent intent = new Intent(context, item.getDestinationActivity());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return destinasiList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView nameTextView;
        CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.destinationImage);
            nameTextView = itemView.findViewById(R.id.destinationName);
            cardView = itemView.findViewById(R.id.cardView);
        }
    }

    // Model untuk setiap item
    public static class DestinasiItem {
        private String name;
        private int imageResId;
        private Class<?> destinationActivity;

        public DestinasiItem(String name, int imageResId, Class<?> destinationActivity) {
            this.name = name;
            this.imageResId = imageResId;
            this.destinationActivity = destinationActivity;
        }

        public String getName() {
            return name;
        }

        public int getImageResId() {
            return imageResId;
        }

        public Class<?> getDestinationActivity() {
            return destinationActivity;
        }
    }
}
