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
import com.project.jemberliburan.Model.Profile;
import com.project.jemberliburan.activity.BantuanActivity;
import com.project.jemberliburan.activity.FavoritSayaActivity;
import com.project.jemberliburan.activity.LoginActivity;
import com.project.jemberliburan.activity.UlasanActivity;

import java.util.List;

public class ProfileAdapter extends RecyclerView.Adapter<ProfileAdapter.ProfileViewHolder> {

    private Context context;
    private List<Profile> profileList;

    public ProfileAdapter(Context context, List<Profile> profileList) {
        this.context = context;
        this.profileList = profileList;
    }

    @NonNull
    @Override
    public ProfileViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_profile, parent, false);
        return new ProfileViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProfileViewHolder holder, int position) {
        Profile item = profileList.get(position);

        // Set data ke ViewHolder
        holder.imageViewIcon.setImageResource(item.getIconResId());
        holder.textViewTitle.setText(item.getTitle());
        holder.imageViewAction.setImageResource(item.getActionResId());

        // Tambahkan listener untuk item
        holder.cardView.setOnClickListener(v -> {
            switch (item.getTitle()) {
                case "Favorit Saya":
                    context.startActivity(new Intent(context, FavoritSayaActivity.class));
                    break;
                case "Ulasan":
                    context.startActivity(new Intent(context, UlasanActivity.class));
                    break;
                case "Bantuan":
                    context.startActivity(new Intent(context, BantuanActivity.class));
                    break;
                case "Log Out":
                    context.startActivity(new Intent(context, LoginActivity.class));
                    break;
                default:
                    break;
            }
        });
    }

    @Override
    public int getItemCount() {
        return profileList.size();
    }

    public static class ProfileViewHolder extends RecyclerView.ViewHolder {
        ImageView imageViewIcon;
        TextView textViewTitle;
        ImageView imageViewAction;
        CardView cardView;

        public ProfileViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cardViewProfileItem);
            imageViewIcon = itemView.findViewById(R.id.imageViewIcon);
            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            imageViewAction = itemView.findViewById(R.id.imageViewAction);
        }
    }
}
