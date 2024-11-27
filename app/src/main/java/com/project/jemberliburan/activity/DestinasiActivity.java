package com.project.jemberliburan.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.project.jemberliburan.R;

public class DestinasiActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_destinasi);

        // Inisialisasi view
        ImageView imageView = findViewById(R.id.detailImage);
        TextView nameTextView = findViewById(R.id.detailName);
        TextView descriptionTextView = findViewById(R.id.detailDescription);

        // Ambil data dari Intent
        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        int imageResId = intent.getIntExtra("imageResId", -1);
        String description = intent.getStringExtra("description");

        // Tampilkan data
        nameTextView.setText(name);
        imageView.setImageResource(imageResId);
        descriptionTextView.setText(description);
    }
}
