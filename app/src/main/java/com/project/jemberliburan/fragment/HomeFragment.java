package com.project.jemberliburan.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;
import com.project.jemberliburan.R;
import com.project.jemberliburan.adapter.ImageSliderAdapter;
import java.util.Arrays;
import java.util.List;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.TextView;

public class HomeFragment extends Fragment {
    private ViewPager2 viewPager;
    private Handler handler = new Handler();
    private Runnable runnable;
    private int currentPosition = 0;
    private TextView greetingTextView;

    @Nullable
    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // Inisialisasi ViewPager
        viewPager = view.findViewById(R.id.viewPager);

        // List gambar untuk ViewPager
        List<Integer> images = Arrays.asList(
                R.drawable.img_papuma1,
                R.drawable.img_teluklove2,
                R.drawable.img_gambir3
        );

        // Set adapter untuk ViewPager
        ImageSliderAdapter adapter = new ImageSliderAdapter(getContext(), images);
        viewPager.setAdapter(adapter);

        // Auto-scroll runnable
        runnable = new Runnable() {
            @Override
            public void run() {
                if (currentPosition == images.size()) {
                    currentPosition = 0;
                }
                viewPager.setCurrentItem(currentPosition++, true);
                handler.postDelayed(runnable, 3000);
            }
        };
        handler.postDelayed(runnable, 3000);

        // Inisialisasi TextView
        greetingTextView = view.findViewById(R.id.tx_home1);

        // Ambil username dari SharedPreferences
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("Username",""); // Default kosong jika tidak ada

        // Set sapaan dengan username
        greetingTextView.setText("Halo, " + username);

        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
        handler.removeCallbacks(runnable); // Hentikan auto-scroll saat fragment di-pause
    }

    @Override
    public void onResume() {
        super.onResume();
        // Pastikan hanya memulai auto-scroll jika fragment terlihat
        if (currentPosition < viewPager.getAdapter().getItemCount()) {
            handler.postDelayed(runnable, 3000); // Lanjutkan auto-scroll saat fragment di-resume
        }
    }
}
