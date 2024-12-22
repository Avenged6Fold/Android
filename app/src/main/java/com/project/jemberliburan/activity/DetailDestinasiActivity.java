package com.project.jemberliburan.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.project.jemberliburan.R;

public class DetailDestinasiActivity extends AppCompatActivity {

    private boolean isFavorited = false; // Status favorit
    private SharedPreferences sharedPreferences;
    private String name;
    private int imageResId;
    private String address;
    private Double rating;
    private int wisataId;
    private String deskripsi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detail_destinasi);

        // Inisialisasi Views
        ImageView imgFavoritSaya = findViewById(R.id.img_favoritsaya);
        ImageView imageView = findViewById(R.id.imageViewDetail);
        TextView textViewName = findViewById(R.id.textViewNameDetail);
        TextView textViewLocation = findViewById(R.id.textViewLocationDetail);
        TextView textViewRating = findViewById(R.id.textViewRatingDetail);
        TextView textViewDeskripsi = findViewById(R.id.textViewDeskripsiDetail);
        ImageView backButton = findViewById(R.id.icon_back);

        // Tambahkan tombol ulasan
        Button buttonLihatUlasan = findViewById(R.id.buttonLihatUlasan);
        Button buttonTambahUlasanDetail = findViewById(R.id.buttonTambahUlasanDetail);

        // SharedPreferences untuk menyimpan status favorit
        sharedPreferences = getSharedPreferences("Favorites", MODE_PRIVATE);

        // Ambil data dari Intent
        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        imageResId = intent.getIntExtra("imageResId", R.drawable.img_default);
        address = intent.getStringExtra("address");
        rating = intent.getDoubleExtra("rating", 0.0);
        wisataId = intent.getIntExtra("wisata_id", -1);
        deskripsi = intent.getStringExtra("deskripsi");

        if (deskripsi == null || deskripsi.isEmpty()) {
            deskripsi = "Deskripsi untuk destinasi ini belum tersedia.";
        }

        // Set data ke Views
        textViewName.setText(name);
        imageView.setImageResource(imageResId);
        textViewLocation.setText(address);
        textViewRating.setText("Rating : " + rating);
        textViewDeskripsi.setText(deskripsi);

        // Periksa status favorit
        isFavorited = sharedPreferences.contains(name);
        updateFavoriteIcon(imgFavoritSaya);

        // Klik listener untuk ikon favorit
        imgFavoritSaya.setOnClickListener(v -> {
            if (isFavorited) {
                removeFromFavorites(name);
            } else {
                saveToFavorites(name, imageResId, address, rating);
            }
            isFavorited = !isFavorited;
            updateFavoriteIcon(imgFavoritSaya);

            String message = isFavorited ? "Ditambahkan ke Favorit" : "Dihapus dari Favorit";
            Toast.makeText(DetailDestinasiActivity.this, message, Toast.LENGTH_SHORT).show();
        });

        // Klik listener untuk tombol kembali
        backButton.setOnClickListener(v -> finish());

        // Klik listener untuk tombol lihat ulasan
        buttonLihatUlasan.setOnClickListener(v -> {
            Intent ulasanIntent = new Intent(DetailDestinasiActivity.this, UlasanActivity.class);
            ulasanIntent.putExtra("wisata_id", wisataId);
            startActivity(ulasanIntent);
        });

        // Klik listener untuk tombol tambah ulasan
        buttonTambahUlasanDetail.setOnClickListener(v -> {
            Intent postUlasanIntent = new Intent(DetailDestinasiActivity.this, PostUlasanActivity.class);
            postUlasanIntent.putExtra("wisata_id", wisataId);
            startActivity(postUlasanIntent);
        });
    }

    private void saveToFavorites(String name, int imageResId, String address, double rating) {
        Gson gson = new Gson();
        FavoriteItem favorite = new FavoriteItem(name, imageResId, address, rating);

        String json = gson.toJson(favorite);
        sharedPreferences.edit().putString(name, json).apply();

        Log.d("DetailDestinasiActivity", "Favorit disimpan: " + json);
    }

    private void removeFromFavorites(String name) {
        sharedPreferences.edit().remove(name).apply();
    }

    private void updateFavoriteIcon(ImageView imgFavoritSaya) {
        int color = isFavorited ? getResources().getColor(R.color.primary_colour) : getResources().getColor(R.color.font_colour);
        imgFavoritSaya.setColorFilter(color);
    }

    // Kelas untuk menyimpan data favorit
    private static class FavoriteItem {
        String name;
        int imageResId;
        String address;
        double rating;

        public FavoriteItem(String name, int imageResId, String address, double rating) {
            this.name = name;
            this.imageResId = imageResId;
            this.address = address;
            this.rating = rating;
        }
    }
}
