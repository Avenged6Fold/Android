package com.project.jemberliburan.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import com.project.jemberliburan.R;
import com.project.jemberliburan.activity.LoginActivity;

public class ProfileFragment extends Fragment {

    private Switch themeSwitch;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Inisialisasi elemen-elemen tampilan
        TextView txtAccountInfo = view.findViewById(R.id.txt_account_info);
        Button btnOrderHistory = view.findViewById(R.id.btn_order_history);
        Button btnLogout = view.findViewById(R.id.btn_logout);
        themeSwitch = view.findViewById(R.id.switch_theme);

        // Mengambil data akun (contoh)
        txtAccountInfo.setText("Nama Pengguna: John Doe\nEmail: johndoe@example.com");

        // Set onclick listener untuk riwayat pesanan
        btnOrderHistory.setOnClickListener(v -> {
            // Tambahkan intent untuk membuka Activity Riwayat Pesanan
        });

        // Set onclick listener untuk tombol logout
        btnLogout.setOnClickListener(v -> {
            // Hapus status login dari SharedPreferences
            SharedPreferences preferences = requireActivity().getSharedPreferences("login_prefs", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean("isLoggedIn", false);
            editor.apply();

            // Kembali ke LoginActivity
            Intent intent = new Intent(requireActivity(), LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        });

        // Set theme switcher
        themeSwitch.setChecked(AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES);
        themeSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }
            SharedPreferences preferences = requireActivity().getSharedPreferences("theme_prefs", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean("isDarkTheme", isChecked);
            editor.apply();
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        SharedPreferences preferences = requireActivity().getSharedPreferences("theme_prefs", Context.MODE_PRIVATE);
        themeSwitch.setChecked(preferences.getBoolean("isDarkTheme", false));
    }
}
