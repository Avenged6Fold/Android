package com.project.jemberliburan.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.project.jemberliburan.R;
import com.project.jemberliburan.adapter.ProfileAdapter;
import com.project.jemberliburan.Model.Profile;

import java.util.ArrayList;
import java.util.List;

public class ProfileFragment extends Fragment {

    private RecyclerView recyclerViewProfile;
    private ProfileAdapter profileAdapter;
    private List<Profile> profileList;
    private TextView usernameTextView, emailTextView; // TextViews untuk menampilkan username dan email

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Menghubungkan fragment dengan layout XML
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        // Inisialisasi RecyclerView
        recyclerViewProfile = view.findViewById(R.id.recyclerViewProfile);
        recyclerViewProfile.setLayoutManager(new LinearLayoutManager(getContext()));

        // Inisialisasi daftar data profil
        profileList = new ArrayList<>();
        profileList.add(new Profile(R.drawable.icon_favoritprofile, "Favorit Saya", R.drawable.icon_actionprofile));
        profileList.add(new Profile(R.drawable.icon_ulasanprofile, "Ulasan", R.drawable.icon_actionprofile));
        profileList.add(new Profile(R.drawable.icon_bantuanprofile, "Bantuan", R.drawable.icon_actionprofile));
        profileList.add(new Profile(R.drawable.icon_logoutprofile, "Log Out", R.drawable.icon_actionprofile));

        // Menghubungkan adapter dengan RecyclerView
        profileAdapter = new ProfileAdapter(requireContext(), profileList);
        recyclerViewProfile.setAdapter(profileAdapter);

        // Ambil username dan email dari SharedPreferences
        usernameTextView = view.findViewById(R.id.tx_username);
        emailTextView = view.findViewById(R.id.tx_email);

        SharedPreferences preferences = requireContext().getSharedPreferences("login_prefs", getContext().MODE_PRIVATE);
        String username = preferences.getString("Username", "Username Tidak Ditemukan");
        String email = preferences.getString("Email", "Email Tidak Ditemukan");

        // Set username dan email ke TextView
        usernameTextView.setText(username);
        emailTextView.setText(email);

        return view;
    }
}
