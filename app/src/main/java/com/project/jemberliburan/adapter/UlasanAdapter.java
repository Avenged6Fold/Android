package com.project.jemberliburan.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.project.jemberliburan.R;
import com.project.jemberliburan.model.Ulasan;

import java.util.List;

public class UlasanAdapter extends RecyclerView.Adapter<UlasanAdapter.ViewHolder> {
    private final Context context;
    private final List<Ulasan> ulasanList;

    public UlasanAdapter(Context context, List<Ulasan> ulasanList) {
        this.context = context;
        this.ulasanList = ulasanList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_ulasan, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Ulasan ulasan = ulasanList.get(position);

        // Log data ulasan
        Log.d("UlasanAdapter", "Nama User: " + ulasan.getNamaUser());
        Log.d("UlasanAdapter", "Tanggal Ulasan: " + ulasan.getTanggalUlasan());
        Log.d("UlasanAdapter", "Foto Profil URL: " + ulasan.getFotoProfil());

        // Set nama pengguna (nama_user)
        holder.userName.setText(ulasan.getNamaUser() != null ? ulasan.getNamaUser() : "Pengguna Tidak Dikenal");

        // Set rating dengan simbol bintang
        holder.rating.setText("\u2B50 " + ulasan.getRating());

        // Set komentar
        holder.comment.setText(ulasan.getKomentar());

        // Set tanggal ulasan
        holder.date.setText(ulasan.getTanggalUlasan() != null ? ulasan.getTanggalUlasan() : "Tanggal Tidak Tersedia");

        // Set foto profil
        if (ulasan.getFotoProfil() != null && !ulasan.getFotoProfil().isEmpty()) {
            Log.d("UlasanAdapter", "Final Foto Profil URL: " + ulasan.getFotoProfil());

            Glide.with(context)
                    .load(ulasan.getFotoProfil())
                    .placeholder(R.drawable.placeholder_profile)
                    .error(R.drawable.placeholder_profile)
                    .circleCrop()
                    .into(holder.profileImage);
        } else {
            Log.d("UlasanAdapter", "Menggunakan gambar profil default");
            holder.profileImage.setImageResource(R.drawable.placeholder_profile);
        }
    }

    @Override
    public int getItemCount() {
        return ulasanList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView userName, rating, comment, date;
        CardView cardView;
        ImageView profileImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cardviewUlasan);
            userName = itemView.findViewById(R.id.textViewUsername);
            rating = itemView.findViewById(R.id.textViewRating);
            comment = itemView.findViewById(R.id.textViewComment);
            date = itemView.findViewById(R.id.textViewDate);
            profileImage = itemView.findViewById(R.id.imageViewProfile);
        }
    }
}
