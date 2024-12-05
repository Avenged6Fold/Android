package com.project.jemberliburan.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.project.jemberliburan.Model.Wellcome;
import com.project.jemberliburan.R;
import com.project.jemberliburan.activity.LoginActivity;

import java.util.List;

public class WellcomeSliderAdapter extends RecyclerView.Adapter<WellcomeSliderAdapter.WellcomeViewHolder> {

    private Context context;
    private List<Wellcome> wellcomeItems;
    private ViewPager2 viewPager; // Tambahkan referensi ViewPager2

    public WellcomeSliderAdapter(Context context, List<Wellcome> welcomeItems, ViewPager2 viewPager) {
        this.context = context;
        this.wellcomeItems = welcomeItems;
        this.viewPager = viewPager; // Inisialisasi ViewPager2
    }

    @NonNull
    @Override
    public WellcomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_wellcome, parent, false);
        return new WellcomeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WellcomeViewHolder holder, int position) {
        Wellcome item = wellcomeItems.get(position);
        holder.titleTextView.setText(item.getTitle());
        holder.descriptionTextView.setText(item.getDescription());
        holder.mainImageView.setImageResource(item.getImageResource());
        holder.swipeImageView.setImageResource(item.getSwipeImageResource());

        // Tambahkan aksi untuk swipeImageView
        holder.swipeImageView.setOnClickListener(v -> {
            if (position < wellcomeItems.size() - 1) {
                // Berpindah ke item berikutnya
                if (holder.swipeImageView.getDrawable().getConstantState()
                        == context.getResources().getDrawable(R.drawable.icon_swipe1).getConstantState()) {
                    viewPager.setCurrentItem(position + 1); // Gunakan referensi ViewPager2
                }
            } else if (holder.swipeImageView.getDrawable().getConstantState()
                    == context.getResources().getDrawable(R.drawable.icon_swipe2).getConstantState()) {
                // Berpindah ke LoginActivity
                Intent intent = new Intent(context, LoginActivity.class);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return wellcomeItems.size();
    }

    public static class WellcomeViewHolder extends RecyclerView.ViewHolder {

        TextView titleTextView, descriptionTextView;
        ImageView mainImageView, swipeImageView;

        public WellcomeViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.titleTextView);
            descriptionTextView = itemView.findViewById(R.id.descriptionTextView);
            mainImageView = itemView.findViewById(R.id.mainImageView);
            swipeImageView = itemView.findViewById(R.id.swipeImageView);
        }
    }
}
