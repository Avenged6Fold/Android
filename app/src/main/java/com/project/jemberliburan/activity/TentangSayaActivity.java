package com.project.jemberliburan.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.project.jemberliburan.R;
import com.project.jemberliburan.fragment.HomeFragment;

public class TentangSayaActivity extends AppCompatActivity {

    private ImageView icon_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tentang_saya);

        // Inisialisasi komponen
        icon_back = findViewById(R.id.icon_back);

        // Tambahkan listener untuk icon_back
        icon_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Pindah ke halaman utama (MainActivity atau halaman lain)
                Intent intent = new Intent(TentangSayaActivity.this, HomeFragment.class);
                startActivity(intent);
                finish(); // Opsional, jika Anda ingin menutup aktivitas saat ini
            }
        });
    }
}
