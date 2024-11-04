package com.project.jemberliburan.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.project.jemberliburan.R;

public class WellcomeActivity extends AppCompatActivity {
    private Button btn_wellcome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Periksa status login pengguna
        SharedPreferences preferences = getSharedPreferences("login_prefs", MODE_PRIVATE);
        boolean isLoggedIn = preferences.getBoolean("isLoggedIn", false);

        if (isLoggedIn) {
            // Jika sudah login, arahkan ke NavigasiActivity
            Intent intent = new Intent(WellcomeActivity.this, NavigasiActivity.class);
            startActivity(intent);
            finish(); // Hentikan WellcomeActivity agar tidak kembali ke sini
            return;
        }

        // Jika belum login, tampilkan layout wellcome
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_wellcome);

        btn_wellcome = findViewById(R.id.btn_wellcome);

        btn_wellcome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WellcomeActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}
