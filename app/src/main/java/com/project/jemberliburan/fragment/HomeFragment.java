package com.project.jemberliburan.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;
import com.project.jemberliburan.Model.Fitur;
import com.project.jemberliburan.R;
import com.project.jemberliburan.activity.BantuanActivity;
import com.project.jemberliburan.activity.UlasanActivity;
import com.project.jemberliburan.adapter.DestinasiAdapter;
import com.project.jemberliburan.adapter.FiturAdapter;
import com.project.jemberliburan.adapter.ImageSliderAdapter;
import com.project.jemberliburan.adapter.CategoryAdapter;
import com.project.jemberliburan.adapter.ImageAdapter;
import com.project.jemberliburan.adapter.TipAdapter; // Adapter untuk Tips Trip
import com.project.jemberliburan.Model.Category;
import com.project.jemberliburan.Model.Destinasi;
import com.project.jemberliburan.Model.Tip; // Model untuk Tips Trip
import com.project.jemberliburan.detail.DetailDestinasiActivity;

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
    private RecyclerView recyclerViewFitur;
    private RecyclerView recyclerViewkategoriDestinasi;
    private RecyclerView recyclerViewDestinasi;
    private RecyclerView recyclerViewTipsTrip; // RecyclerView untuk Tips Trip
    private FiturAdapter fiturAdapter;
    private CategoryAdapter categoryAdapter;
    private ImageAdapter imageAdapter;
    private TipAdapter tipAdapter; // Adapter untuk Tips Trip
    private List<Fitur> fiturList;
    private List<Category> categoryList;
    private List<Destinasi> imageList;
    private List<Tip> tipList; // Daftar Tips Trip

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // Inisialisasi ViewPager
        viewPager = view.findViewById(R.id.viewPager);

        // List gambar untuk ViewPager
        images = Arrays.asList(
                R.drawable.img_papuma,
                R.drawable.img_teluklove,
                R.drawable.img_gambir
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
        recyclerViewFitur = view.findViewById(R.id.recyclerViewFitur);
        recyclerViewkategoriDestinasi = view.findViewById(R.id.recyclerViewKategoriDestinasi);
        recyclerViewDestinasi = view.findViewById(R.id.recyclerViewDestinasi);
        recyclerViewTipsTrip = view.findViewById(R.id.recyclerViewTipsTrip);

        fiturAdapter = new FiturAdapter(position -> {
            switch (position) {
                case 0: // Jelajahi
                    startActivity(new Intent(getContext(), DestinasiFragment.class));
                    break;
                case 1: // Profil Saya
                    startActivity(new Intent(getContext(), ProfileFragment.class));
                    break;
                case 2: // Cek Tiket
                    startActivity(new Intent(getContext(), TiketFragment.class));
                    break;
                case 3: // Riwayat
                    startActivity(new Intent(getContext(), TiketFragment.class));
                    break;
                case 4: // Ulasan
                    startActivity(new Intent(getContext(), UlasanActivity.class));
                    break;
                case 5: // Bantuan
                    startActivity(new Intent(getContext(), BantuanActivity.class));
                    break;
            }
        });
        recyclerViewFitur.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerViewFitur.setAdapter(fiturAdapter);

        tipAdapter = new TipAdapter(position -> {
            switch (position) {
                case 0: // TipsTrip1
                    startActivity(new Intent(getContext(), UlasanActivity.class));
                    break;
                case 1: // TipsTrip2
                    startActivity(new Intent(getContext(), UlasanActivity.class));
                    break;
                case 2: // TipsTrip3
                    startActivity(new Intent(getContext(), UlasanActivity.class));
                    break;

            }
        });
        recyclerViewTipsTrip.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerViewTipsTrip.setAdapter(tipAdapter);

        // Set up kategori RecyclerView
        categoryList = getCategoryList();
        categoryAdapter = new CategoryAdapter(categoryList, this::onCategorySelected);
        recyclerViewkategoriDestinasi.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerViewkategoriDestinasi.setAdapter(categoryAdapter);


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


    private void onCategorySelected(Category category) {
        // Memperbarui daftar destinasi berdasarkan kategori yang dipilih
        List<DestinasiAdapter.DestinasiItem> destinasiList;

        if ("Gunung".equals(category.getName())) {
            destinasiList = Arrays.asList(
                    new DestinasiAdapter.DestinasiItem("Gunung Gambir", R.drawable.img_gambir, "Jember, Jawa Timur", 4.6, DetailDestinasiActivity.class)
            );
        } else if ("Pantai".equals(category.getName())) {
            destinasiList = Arrays.asList(
                    new DestinasiAdapter.DestinasiItem("Pantai Papuma", R.drawable.img_papuma, "Jember, Jawa Timur", 4.9, DetailDestinasiActivity.class),
                    new DestinasiAdapter.DestinasiItem("Pantai Watu Ulo", R.drawable.img_watuulo, "Jember, Jawa Timur", 4.7, DetailDestinasiActivity.class)
            );
        } else if ("Bukit".equals(category.getName())) {
            destinasiList = Arrays.asList(
                    new DestinasiAdapter.DestinasiItem("Rembangan", R.drawable.img_rembangan, "Jember, Jawa Timur", 4.8, DetailDestinasiActivity.class),
                    new DestinasiAdapter.DestinasiItem("Bukit SJ88", R.drawable.img_sj88, "Jember, Jawa Timur", 4.7, DetailDestinasiActivity.class)
            );
        } else {
            // Default list jika tidak ada kategori yang cocok
            destinasiList = new ArrayList<>();
        }

        // Set adapter baru untuk RecyclerView destinasi
        DestinasiAdapter destinasiAdapter = new DestinasiAdapter(requireContext(), destinasiList);
        recyclerViewDestinasi.setLayoutManager(new GridLayoutManager(requireContext(), 2));
        recyclerViewDestinasi.setAdapter(destinasiAdapter);

        // Animasi untuk kategori (opsional)
        animateCategoryIcon(category);
    }


    private void animateCategoryIcon(Category category) {
        // Temukan posisi kategori berdasarkan nama
        int position = categoryList.indexOf(category);
        if (position >= 0 && position < recyclerViewkategoriDestinasi.getChildCount()) {
            // Ambil tampilan kategori yang sesuai
            View categoryView = recyclerViewkategoriDestinasi.getChildAt(position);

            // Periksa jika tampilan tidak null
            if (categoryView != null) {
                // Reset properti rotasi
                categoryView.setRotationY(0);

                // Jalankan animasi rotasi
                categoryView.animate()
                        .rotationY(360)  // Rotasi 360 derajat
                        .setDuration(500) // Durasi animasi 500ms
                        .start();
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
