package com.project.jemberliburan.activity;

import android.os.Bundle;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.util.Log;
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

        // SharedPreferences untuk menyimpan status favorit
        sharedPreferences = getSharedPreferences("Favorites", MODE_PRIVATE);

        // Ambil data dari Intent
        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        imageResId = getIntent().getIntExtra("imageResId", R.drawable.img_default);
        address = getIntent().getStringExtra("address");
        rating = getIntent().getDoubleExtra("rating", 0.0);
        String deskripsi = "Deskripsi untuk destinasi ini belum tersedia.";

        // Gunakan switch-case untuk menentukan detail
        switch (name) {
            case "Pantai Papuma":
                imageResId = R.drawable.img_papuma;
                address = "Wuluhan, Jember";
                rating = 4.9;
                deskripsi = "Pantai Papuma (Pantai Pasir Putih Malikan) terkenal dengan keindahan pasir putih dan batu karangnya yang ikonik...";
                break;

            case "Pantai Teluk Love":
                imageResId = R.drawable.img_teluklove;
                address = "Ambulu, Jember";
                rating = 4.8;
                deskripsi = "Teluk Love adalah teluk berbentuk hati yang terlihat dari ketinggian...";
                break;

            case "Pantai Watu Ulo":
                imageResId = R.drawable.img_watuulo;
                address = "Ambulu, Jember";
                rating = 4.7;
                deskripsi = "Pantai Watu Ulo berlokasi di Kecamatan Ambulu...";
                break;

            case "Gunung Gambir":
                imageResId = R.drawable.img_gambir;
                address = "Sumberbaru, Jember";
                rating = 4.8;
                deskripsi = "Terletak di Desa Gelang, Kecamatan Sumberbaru...";
                break;

            case "Bukit SJ88":
                imageResId = R.drawable.img_sj88;
                address = "Wuluhan, Jember";
                rating = 4.8;
                deskripsi = "Bukit SJ88 adalah destinasi wisata di Kecamatan Sumberjambe...";
                break;

            case "Rembangan":
                imageResId = R.drawable.img_rembangan;
                address = "Arjasa, Jember";
                rating = 4.8;
                deskripsi = "Rembangan adalah kawasan wisata pegunungan yang berlokasi di Kecamatan Arjasa...";
                break;

            case "Air Terjun Tancak":
                imageResId = R.drawable.img_airterjun_tancak;
                address = "Panti, Jember";
                rating = 4.8;
                deskripsi = "Air Terjun Tancak terletak di Desa Suci, Kecamatan Panti...";
                break;

            case "Air Terjun Antrokan":
                imageResId = R.drawable.img_airterjun_antrokan;
                address = "Tanggul, Jember";
                rating = 4.8;
                deskripsi = "Berada di Desa Manggisan, Kecamatan Tanggul...";
                break;
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
        int imageResId; // Ubah dari imageResource ke imageResId
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
