package com.project.jemberliburan.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.project.jemberliburan.R;
import com.project.jemberliburan.adapter.WellcomeSliderAdapter;
import com.project.jemberliburan.Model.Wellcome;
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator;

import java.util.ArrayList;
import java.util.List;

public class WellcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        EdgeToEdge.enable(this);
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
        setContentView(R.layout.activity_wellcome);

        // ViewPager2 untuk slider
        ViewPager2 viewPager = findViewById(R.id.viewPager);
        DotsIndicator dotsIndicator = findViewById(R.id.dotsIndicator);

        List<Wellcome> wellcomeItems = new ArrayList<>();
        wellcomeItems.add(new Wellcome("liburan jadi makin gampang", "JELI hadir buat bikin rencana liburan kamu lebih seru dan bebas ribet. Mulai dari info destinasi, pesan tiket, sampai tips liburan, semua ada di satu aplikasi!", R.drawable.img_wellcome1, R.drawable.icon_swipe1));
        wellcomeItems.add(new Wellcome("fitur simpel, liburan jadi fleksibel", "Aplikasi JELI dirancang buat kamu yang suka hal praktis semua fitur gampang dipakai dan cocok untuk rencana dadakan maupun yang sudah terjadwal", R.drawable.img_wellcome2, R.drawable.icon_swipe1));
        wellcomeItems.add(new Wellcome("mulai liburanmu bersama", "Yuk, mulai eksplorasi perjalanan impianmu bareng JELI. Semua udah siap, tinggal klik, dan nikmati liburan asikmu!", R.drawable.img_wellcome3, R.drawable.icon_swipe2));

        WellcomeSliderAdapter adapter = new WellcomeSliderAdapter(this, wellcomeItems, viewPager);
        viewPager.setAdapter(adapter);

        dotsIndicator.setViewPager2(viewPager);

        // Atur orientasi swipe ke vertikal
        viewPager.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
    }
}
