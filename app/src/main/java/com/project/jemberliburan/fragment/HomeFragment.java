package com.project.jemberliburan.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.project.jemberliburan.model.Category;
import com.project.jemberliburan.R;
import com.project.jemberliburan.activity.CekTiketActivity;
import com.project.jemberliburan.activity.DetailDestinasiActivity;
import com.project.jemberliburan.activity.DetailTipActivity;
import com.project.jemberliburan.activity.RiwayatTiketActivity;
import com.project.jemberliburan.activity.SearchDestinasiActivity;
import com.project.jemberliburan.activity.SearchFiturActivity;
import com.project.jemberliburan.activity.TentangSayaActivity;
import com.project.jemberliburan.adapter.DestinasiAdapter;
import com.project.jemberliburan.adapter.FiturAdapter;
import com.project.jemberliburan.adapter.ImageSliderAdapter;
import com.project.jemberliburan.adapter.CategoryAdapter;
import com.project.jemberliburan.adapter.TipAdapter; // Adapter untuk Tips Trip
import com.project.jemberliburan.activity.UlasanActivity;
import com.project.jemberliburan.activity.BantuanActivity;
import com.project.jemberliburan.model.Destinasi;

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
        String username = sharedPreferences.getString("username", ""); // Default kosong jika tidak ada
        greetingTextView.setText("Halo, " + username);

        // Inisialisasi RecyclerView
        recyclerViewFitur = view.findViewById(R.id.recyclerViewFitur);
        recyclerViewkategoriDestinasi = view.findViewById(R.id.recyclerViewKategoriDestinasi);
        recyclerViewDestinasi = view.findViewById(R.id.recyclerViewDestinasi);
        recyclerViewTipsTrip = view.findViewById(R.id.recyclerViewTipsTrip);

        // Setup Fitur Adapter
        fiturAdapter = new FiturAdapter(position -> {
            switch (position) {
                case 0: // Jelajahi
                    startActivity(new Intent(getContext(), SearchDestinasiActivity.class));
                    break;
                case 1: // Profil Saya
                    startActivity(new Intent(getContext(), TentangSayaActivity.class));
                    break;
                case 2: // Cek Tiket
                    startActivity(new Intent(getContext(), CekTiketActivity.class));
                    break;
                case 3: // Riwayat
                    startActivity(new Intent(getContext(), RiwayatTiketActivity.class));
                    break;
                case 4: // Ulasan
                    Intent ulasanIntent = new Intent(getContext(), UlasanActivity.class);
                    ulasanIntent.putExtra("wisata_id", 1); // Ganti dengan wisata_id yang sesuai
                    startActivity(ulasanIntent);
                    break;
                case 5: // Bantuan
                    startActivity(new Intent(getContext(), BantuanActivity.class));
                    break;
            }
        });
        recyclerViewFitur.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerViewFitur.setAdapter(fiturAdapter);

        // Setup Tips Trip Adapter
        tipAdapter = new TipAdapter(position -> {
            switch (position) {
                case 0: // TipsTrip1
                    Intent intent1 = new Intent(getContext(), DetailTipActivity.class);
                    intent1.putExtra("tip_id", 0); // ID untuk TipsTrip1
                    startActivity(intent1);
                    break;
                case 1: // TipsTrip2
                    Intent intent2 = new Intent(getContext(), DetailTipActivity.class);
                    intent2.putExtra("tip_id", 1); // ID untuk TipsTrip2
                    startActivity(intent2);
                    break;
                case 2: // TipsTrip3
                    Intent intent3 = new Intent(getContext(), DetailTipActivity.class);
                    intent3.putExtra("tip_id", 2); // ID untuk TipsTrip3
                    startActivity(intent3);
                    break;
                default:
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

        // Set data default (misalnya kategori pertama)
        if (!categoryList.isEmpty()) {
            onCategorySelected(categoryList.get(0));
        }

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
        handler.postDelayed(autoScrollRunnable, 3000);
    }

    private List<Category> getCategoryList() {
        // Membuat daftar kategori
        List<Category> categories = new ArrayList<>();
        categories.add(new Category("Semua", 0, true));
        categories.add(new Category("Gunung", 0, false));
        categories.add(new Category("Pantai", 0, false));
        categories.add(new Category("Bukit", 0, false));
        categories.add(new Category("Air Terjun", 0, false));
        return categories;
    }

    private void onCategorySelected(Category category) {
        List<Destinasi> destinasiList;

        switch (category.getName()) {
            case "Semua":
                destinasiList = Arrays.asList(
                        new Destinasi("Pantai Papuma", R.drawable.img_papuma, "Wuluhan, Jember", 4.9, 1, "Deskripsi Pantai Papuma"),
                        new Destinasi("Pantai Teluk Love", R.drawable.img_teluklove, "Ambulu, Jember", 4.8, 2, "Deskripsi Pantai Teluk Love"),
                        new Destinasi("Pantai Watu Ulo", R.drawable.img_watuulo, "Ambulu, Jember", 4.8, 3, "Deskripsi Pantai Watu Ulo"),
                        new Destinasi("Bukit SJ88", R.drawable.img_sj88, "Wuluhan, Jember", 4.8, 5, "Deskripsi Bukit SJ88"),
                        new Destinasi("Rembangan", R.drawable.img_rembangan, "Arjasa, Jember", 4.8, 6, "Deskripsi Rembangan"),
                        new Destinasi("Gunung Gambir", R.drawable.img_gambir, "Sumberbaru, Jember", 4.8, 4, "Deskripsi Gunung Gambir"),
                        new Destinasi("Air Terjun Tancak", R.drawable.img_airterjun_tancak, "Panti, Jember", 4.8, 7, "Deskripsi Air Terjun Tancak"),
                        new Destinasi("Air Terjun Antrokan", R.drawable.img_airterjun_antrokan, "Tanggul, Jember", 4.8, 8, "Deskripsi Air Terjun Antrokan")
                );
                break;

            case "Gunung":
                destinasiList = Arrays.asList(
                        new Destinasi("Gunung Gambir", R.drawable.img_gambir, "Sumberbaru, Jember", 4.8, 4, "Deskripsi Gunung Gambir")
                );
                break;

            case "Pantai":
                destinasiList = Arrays.asList(
                        new Destinasi("Pantai Papuma", R.drawable.img_papuma, "Wuluhan, Jember", 4.9, 1, "Deskripsi Pantai Papuma"),
                        new Destinasi("Pantai Watu Ulo", R.drawable.img_watuulo, "Ambulu, Jember", 4.8, 3, "Deskripsi Pantai Watu Ulo")
                );
                break;

            case "Bukit":
                destinasiList = Arrays.asList(
                        new Destinasi("Rembangan", R.drawable.img_rembangan, "Arjasa, Jember", 4.8, 6, "Deskripsi Rembangan"),
                        new Destinasi("Bukit SJ88", R.drawable.img_sj88, "Wuluhan, Jember", 4.8, 5, "Deskripsi Bukit SJ88")
                );
                break;

            case "Air Terjun":
                destinasiList = Arrays.asList(
                        new Destinasi("Air Terjun Tancak", R.drawable.img_airterjun_tancak, "Panti, Jember", 4.8, 7, "Deskripsi Air Terjun Tancak"),
                        new Destinasi("Air Terjun Antrokan", R.drawable.img_airterjun_antrokan, "Tanggul, Jember", 4.8, 8, "Deskripsi Air Terjun Antrokan")
                );
                break;

            default:
                destinasiList = new ArrayList<>();
                break;
        }

        // Atur adapter untuk RecyclerView destinasi
        recyclerViewDestinasi.setLayoutManager(new GridLayoutManager(getContext(), 2));

        // Gunakan adapter yang sama dengan list baru
        DestinasiAdapter destinasiAdapter = new DestinasiAdapter(requireContext(), destinasiList);
        recyclerViewDestinasi.setAdapter(destinasiAdapter);
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
