package com.project.jemberliburan.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;
import com.project.jemberliburan.R;
import com.project.jemberliburan.adapter.ImageSliderAdapter;
import com.project.jemberliburan.adapter.CategoryAdapter;
import com.project.jemberliburan.adapter.ImageAdapter;
import com.project.jemberliburan.adapter.TipAdapter; // Adapter untuk Tips Trip
import com.project.jemberliburan.Model.Category;
import com.project.jemberliburan.Model.Image;
import com.project.jemberliburan.Model.Tip; // Model untuk Tips Trip
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HomeFragment extends Fragment {
    private ViewPager2 viewPager;
    private Handler handler = new Handler();
    private Runnable autoScrollRunnable;
    private int currentPosition = 0;
    private TextView greetingTextView;
    private List<Integer> images;

    private RecyclerView recyclerViewDestinasi;
    private RecyclerView recyclerFavoritanda;
    private RecyclerView recyclerViewTipsTrip; // RecyclerView untuk Tips Trip
    private CategoryAdapter categoryAdapter;
    private ImageAdapter imageAdapter;
    private TipAdapter tipAdapter; // Adapter untuk Tips Trip
    private List<Category> categoryList;
    private List<Image> imageList;
    private List<Tip> tipList; // Daftar Tips Trip

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

        // Setup auto-scroll
        setupAutoScroll();

        // Inisialisasi TextView sapaan
        greetingTextView = view.findViewById(R.id.tx_home1);
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("login_prefs", Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("Username", ""); // Default kosong jika tidak ada
        greetingTextView.setText("Halo, " + username);

        // Inisialisasi RecyclerView
        recyclerViewDestinasi = view.findViewById(R.id.recyclerdestinasi);
        recyclerFavoritanda = view.findViewById(R.id.recyclerViewFavoritanda);
        recyclerViewTipsTrip = view.findViewById(R.id.recyclerViewTipsTrip);

        // Set up kategori RecyclerView
        categoryList = getCategoryList();
        categoryAdapter = new CategoryAdapter(categoryList, this::onCategorySelected);
        recyclerViewDestinasi.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerViewDestinasi.setAdapter(categoryAdapter);

        // Set up gambar RecyclerView
        imageList = new ArrayList<>(); // Kosongkan gambar awal
        imageAdapter = new ImageAdapter(imageList);
        recyclerFavoritanda.setLayoutManager(new GridLayoutManager(getContext(), 2));
        recyclerFavoritanda.setAdapter(imageAdapter);

        // Set up RecyclerView untuk Tips Trip
        tipList = getTipList();
        tipAdapter = new TipAdapter(getContext(), tipList, tip -> {
            // Aksi ketika item Tips Trip di-klik
        });
        recyclerViewTipsTrip.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerViewTipsTrip.setAdapter(tipAdapter);

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

    private List<Category> getCategoryList() {
        return Arrays.asList(
                new Category("Gunung", R.drawable.icon_destinasi),
                new Category("Pantai", R.drawable.icon_home),
                new Category("Bukit", R.drawable.icon_tiket)
        );
    }

    private List<Tip> getTipList() {
        return Arrays.asList(
                new Tip("Tips Liburan Hemat & Praktis", "Tips Liburan", R.drawable.tip1),
                new Tip("Prepare Barang Bawaanmu", "Tips Packing", R.drawable.tip2),
                new Tip("Trip Cerdas di Setiap Musim", "Tips Musim", R.drawable.tip3)
        );
    }

    private void onCategorySelected(Category category) {
        // Memperbarui daftar gambar berdasarkan kategori yang dipilih
        imageList.clear();
        switch (category.getName()) {
            case "Gunung":
                imageList.add(new Image(R.drawable.icon_home));
                imageList.add(new Image(R.drawable.icon_tiket));
                animateCategoryIcon(category);
                break;
            case "Pantai":
                imageList.add(new Image(R.drawable.icon_porfile));
                imageList.add(new Image(R.drawable.icon_destinasi));
                break;
            case "Bukit":
                imageList.add(new Image(R.drawable.icon_jeli));
                imageList.add(new Image(R.drawable.icon_tiket));
                break;
        }
        imageAdapter.notifyDataSetChanged();
    }

    private void animateCategoryIcon(Category category) {
        if ("Gunung".equals(category.getName())) {
            View categoryView = recyclerViewDestinasi.getChildAt(0); // Ambil kategori pertama
            if (categoryView != null) {
                categoryView.animate().rotationY(360).setDuration(500).start();
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        handler.postDelayed(autoScrollRunnable, 3000);
    }

    @Override
    public void onPause() {
        super.onPause();
        handler.removeCallbacks(autoScrollRunnable);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        handler.removeCallbacksAndMessages(null);
    }
}
