package com.project.jemberliburan.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.project.jemberliburan.R;
import com.project.jemberliburan.Model.Category;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {
    private List<Category> categoryList;
    private OnCategoryClickListener listener;

    // Interface untuk mendeteksi klik pada kategori
    public interface OnCategoryClickListener {
        void onCategoryClick(Category category);
    }

    // Constructor adapter
    public CategoryAdapter(List<Category> categoryList, OnCategoryClickListener listener) {
        this.categoryList = categoryList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate layout item_category.xml
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category, parent, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        Category category = categoryList.get(position);

        if ("topdestinasi".equals(category.getName())) {
            // Hanya tampilkan ikon tanpa nama
            holder.categoryIcon.setVisibility(View.VISIBLE);
            holder.categoryIcon.setImageResource(category.getIconResId());
            holder.categoryName.setVisibility(View.GONE); // Sembunyikan nama
        } else {
            // Tampilkan ikon dan nama sesuai properti
            if (category.isShowIcon()) {
                holder.categoryIcon.setVisibility(View.VISIBLE);
                holder.categoryIcon.setImageResource(category.getIconResId());
            } else {
                holder.categoryIcon.setVisibility(View.GONE);
            }
            holder.categoryName.setVisibility(View.VISIBLE);
            holder.categoryName.setText(category.getName());
        }

        // Listener untuk item klik
        holder.itemView.setOnClickListener(v -> listener.onCategoryClick(category));
    }


    @Override
    public int getItemCount() {
        // Kembalikan jumlah kategori dalam list
        return categoryList.size();
    }

    // ViewHolder untuk memegang referensi komponen UI
    public static class CategoryViewHolder extends RecyclerView.ViewHolder {
        TextView categoryName;
        ImageView categoryIcon;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryName = itemView.findViewById(R.id.categoryName); // Pastikan id sesuai dengan XML
            categoryIcon = itemView.findViewById(R.id.categoryIcon); // Pastikan id sesuai dengan XML
        }
    }
}
