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
    private Runnable autoScrollRunnable;
    private int currentPosition = 0;
    private TextView greetingTextView;
    private List<Integer> images;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // Inisialisasi ViewPager
        viewPager = view.findViewById(R.id.viewPager);

        // List gambar untuk ViewPager
        images = Arrays.asList(
                R.drawable.img_papuma1,
                R.drawable.img_teluklove2,
                R.drawable.img_gambir3
        );

        // Set adapter untuk ViewPager
        ImageSliderAdapter adapter = new ImageSliderAdapter(requireContext(), images);
        viewPager.setAdapter(adapter);

        // Auto-scroll runnable
        setupAutoScroll();

        // Inisialisasi TextView
        greetingTextView = view.findViewById(R.id.tx_home1);

        // Ambil username dari SharedPreferences
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("login_prefs", Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("Username", ""); // Default kosong jika tidak ada

        // Set sapaan dengan username
        greetingTextView.setText("Halo, " + username);

        return view;
    }

    private void setupAutoScroll() {
        autoScrollRunnable = new Runnable() {
            @Override
            public void run() {
                if (currentPosition >= images.size()) {
                    currentPosition = 0;
                }
                viewPager.setCurrentItem(currentPosition++, true);
                handler.postDelayed(this, 3000);
            }
        };
    }

    @Override
    public void onResume() {
        super.onResume();
        // Pastikan hanya memulai auto-scroll jika fragment terlihat
        handler.postDelayed(autoScrollRunnable, 3000);
    }

    @Override
    public void onPause() {
        super.onPause();
        // Hentikan auto-scroll saat fragment di-pause
        handler.removeCallbacks(autoScrollRunnable);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // Bersihkan handler untuk mencegah memory leak
        handler.removeCallbacksAndMessages(null);
    }
}
