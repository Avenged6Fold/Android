package com.project.jemberliburan.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.project.jemberliburan.R;
import com.project.jemberliburan.fragment.HomeFragment;

public class TentangSayaActivity extends AppCompatActivity {

    private ImageView icon_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_tentang_saya);

        // Inisialisasi komponen
        ImageView backButton = findViewById(R.id.icon_back);

        // Tambahkan listener untuk icon_back
        backButton.setOnClickListener(v -> finish());
    }
}
