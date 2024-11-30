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
import com.project.jemberliburan.activity.DetailDestinasiActivity;

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

    public void updateList(List<DestinasiItem> newList) {
        this.destinasiList = newList;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DestinasiItem item = destinasiList.get(position);
        holder.imageView.setImageResource(item.getImageResId());
        holder.nameTextView.setText(item.getName());
        holder.addressTextView.setText(item.getAddress()); // Set alamat
        holder.ratingTextView.setText("⭐ " + item.getRating()); // Set rating

        // Set klik listener untuk card
        holder.cardView.setOnClickListener(v -> {
            Intent intent = new Intent(context, DetailDestinasiActivity.class);
            intent.putExtra("name", item.getName()); // Hanya mengirim nama destinasi
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
        TextView addressTextView;  // Tambahkan referensi untuk alamat
        TextView ratingTextView;   // Tambahkan referensi untuk rating


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.destinasiImage);
            nameTextView = itemView.findViewById(R.id.destinasiName);
            addressTextView = itemView.findViewById(R.id.destinasiAddress); // Inisialisasi
            ratingTextView = itemView.findViewById(R.id.destinasiRating);   // Inisialisasi
            cardView = itemView.findViewById(R.id.cardview);
        }
    }

    // Model untuk setiap item
    public static class DestinasiItem {
        private String name;
        private int imageResId;
        private String address;  // Tambahkan properti alamat
        private double rating;   // Tambahkan properti rating


        public DestinasiItem(String name, int imageResId, String address, double rating, Class<?> destinationActivity) {
            this.name = name;
            this.imageResId = imageResId;
            this.address = address;
            this.rating = rating;
        }

        public String getName() {
            return name;
        }

        public int getImageResId() {
            return imageResId;
        }

        public String getAddress() {
            return address;
        }

        public double getRating() {
            return rating;
        }

    }
}
