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
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;
import com.project.jemberliburan.R;

import com.project.jemberliburan.activity.SearchDestinasiActivity;
import com.project.jemberliburan.activity.SearchFiturActivity;
import com.project.jemberliburan.activity.TentangSayaActivity;
import com.project.jemberliburan.adapter.DestinasiAdapter;
import com.project.jemberliburan.adapter.FiturAdapter;
import com.project.jemberliburan.adapter.ImageSliderAdapter;
import com.project.jemberliburan.adapter.CategoryAdapter;
import com.project.jemberliburan.adapter.TipAdapter; // Adapter untuk Tips Trip
import com.project.jemberliburan.Model.Category;

import com.project.jemberliburan.activity.UlasanActivity;
import com.project.jemberliburan.activity.BantuanActivity;
import com.project.jemberliburan.activity.DestinasiActivity;

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
    private TipAdapter tipAdapter; // Adapter untuk Tips Trip
    private List<Category> categoryList;


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

        CardView cariFiturCardView = view.findViewById(R.id.CariFiturCardView);
        cariFiturCardView.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), SearchFiturActivity.class);

            // Kirimkan data default untuk pencarian awal, jika diperlukan
            intent.putExtra("feature_name", 0); // Contoh default
            startActivity(intent);
        });

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
                    startActivity(new Intent(getContext(), SearchDestinasiActivity.class));
                    break;
                case 1: // Profil Saya
                    startActivity(new Intent(getContext(), TentangSayaActivity.class));
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
        // Membuat daftar kategori
        List<Category> categories = new ArrayList<>();
        categories.add(new Category("topdestinasi", R.drawable.icon_topdestinasi, true));
        categories.add(new Category("Gunung", 0, false));
        categories.add(new Category("Pantai", 0, false)); // Tidak menampilkan ikon
        categories.add(new Category("Bukit", 0, false)); // Tidak menampilkan ikon
        return categories;
    }


    private void onCategorySelected(Category category) {
        // Memperbarui daftar destinasi berdasarkan kategori yang dipilih
        List<DestinasiAdapter.DestinasiItem> destinasiList;

        if ("topdestinasi".equals(category.getName())) {
            destinasiList = Arrays.asList(
                    new DestinasiAdapter.DestinasiItem("Pantai Papuma", R.drawable.img_papuma, "Jember, Jawa Timur", 4.9, DestinasiActivity.class),
                    new DestinasiAdapter.DestinasiItem("Rembangan", R.drawable.img_rembangan, "Jember, Jawa Timur", 4.8, DestinasiActivity.class),
                    new DestinasiAdapter.DestinasiItem("Gunung Gambir", R.drawable.img_gambir, "Jember, Jawa Timur", 4.8, DestinasiActivity.class)
            );
        } else if ("Gunung".equals(category.getName())) {
            destinasiList = Arrays.asList(
                    new DestinasiAdapter.DestinasiItem("Gunung Gambir", R.drawable.img_gambir, "Jember, Jawa Timur", 4.8, DestinasiActivity.class)
            );
        }else if ("Pantai".equals(category.getName())) {
            destinasiList = Arrays.asList(
                    new DestinasiAdapter.DestinasiItem("Pantai Papuma", R.drawable.img_papuma, "Jember, Jawa Timur", 4.9, DestinasiActivity.class),
                    new DestinasiAdapter.DestinasiItem("Pantai Watu Ulo", R.drawable.img_watuulo, "Jember, Jawa Timur", 4.7, DestinasiActivity.class)
            );
        } else if ("Bukit".equals(category.getName())) {
            destinasiList = Arrays.asList(
                    new DestinasiAdapter.DestinasiItem("Rembangan", R.drawable.img_rembangan, "Jember, Jawa Timur", 4.8, DestinasiActivity.class),
                    new DestinasiAdapter.DestinasiItem("Bukit SJ88", R.drawable.img_sj88, "Jember, Jawa Timur", 4.7, DestinasiActivity.class)
            );
        } else {
            // Default list jika tidak ada kategori yang cocok
            destinasiList = new ArrayList<>();
        }

        // Set adapter baru untuk RecyclerView destinasi
        DestinasiAdapter destinasiAdapter = new DestinasiAdapter(requireContext(), destinasiList);
        recyclerViewDestinasi.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerViewDestinasi.setAdapter(destinasiAdapter);

        // Animasi untuk kategori (opsional)
        animateCategoryIcon(category);
    }


    private void animateCategoryIcon(Category category) {
        // Cari posisi kategori yang sesuai
        int position = categoryList.indexOf(category);
        if (position >= 0 && position < recyclerViewkategoriDestinasi.getChildCount()) {
            // Ambil tampilan kategori di posisi tertentu
            View categoryView = recyclerViewkategoriDestinasi.getChildAt(position);

            if (categoryView != null) {
                // Reset rotasi sebelumnya
                categoryView.setRotationY(0);

                // Jalankan animasi
                categoryView.animate()
                        .rotationY(360) // Rotasi 360 derajat
                        .setDuration(500) // Durasi animasi
                        .withEndAction(() -> {
                            // Tambahkan tindakan setelah animasi selesai jika diperlukan
                        })
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
