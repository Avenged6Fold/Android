package com.project.jemberliburan.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.project.jemberliburan.Model.Category;
import com.project.jemberliburan.R;
import com.project.jemberliburan.activity.DetailDestinasiActivity;
import com.project.jemberliburan.activity.SearchDestinasiActivity;
import com.project.jemberliburan.adapter.CategoryAdapter;
import com.project.jemberliburan.adapter.DestinasiAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DestinasiFragment extends Fragment {
    private RecyclerView recyclerViewKategoriDestinasi;
    private RecyclerView recyclerViewDestinasi;
    private List<Category> categoryList;
    private CategoryAdapter categoryAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_destinasi, container, false);

        // Inisialisasi RecyclerView untuk kategori destinasi
        recyclerViewKategoriDestinasi = view.findViewById(R.id.recyclerViewKategoriDestinasi);
        recyclerViewDestinasi = view.findViewById(R.id.recyclerViewDestinasi);
        CardView jelajahiDestinasiCardView = view.findViewById(R.id.JelajahiDestinasiCardView);

        jelajahiDestinasiCardView.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), SearchDestinasiActivity.class);
            startActivity(intent);
        });

        // Inisialisasi data kategori
        categoryList = getCategoryList();

        // Atur adapter untuk kategori
        categoryAdapter = new CategoryAdapter(categoryList, this::onCategorySelected);
        recyclerViewKategoriDestinasi.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerViewKategoriDestinasi.setAdapter(categoryAdapter);

        // Set data default (misalnya kategori pertama)
        if (!categoryList.isEmpty()) {
            onCategorySelected(categoryList.get(0));
        }

        return view;
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
        // Memperbarui daftar destinasi berdasarkan kategori yang dipilih
        List<DestinasiAdapter.DestinasiItem> destinasiList;

        switch (category.getName()) {
            case "Semua":
                destinasiList = Arrays.asList(
                        new DestinasiAdapter.DestinasiItem("Pantai Papuma", R.drawable.img_papuma, "Wuluhan, Jember", 4.9, DetailDestinasiActivity.class),
                        new DestinasiAdapter.DestinasiItem("Pantai Teluk Love", R.drawable.img_teluklove, "Ambulu, Jember", 4.8, DetailDestinasiActivity.class),
                        new DestinasiAdapter.DestinasiItem("Pantai Watu Ulo", R.drawable.img_watuulo, "Ambulu, Jember", 4.8, DetailDestinasiActivity.class),
                        new DestinasiAdapter.DestinasiItem("Bukit SJ88", R.drawable.img_sj88, "Wuluhan, Jember", 4.8, DetailDestinasiActivity.class),
                        new DestinasiAdapter.DestinasiItem("Rembangan", R.drawable.img_rembangan, "Arjasa, Jember", 4.8, DetailDestinasiActivity.class),
                        new DestinasiAdapter.DestinasiItem("Gunung Gambir", R.drawable.img_gambir, "Sumberbaru, jember", 4.8, DetailDestinasiActivity.class),
                        new DestinasiAdapter.DestinasiItem("Air Terjun Tancak", R.drawable.img_airterjun_tancak, "Panti, Jember", 4.8, DetailDestinasiActivity.class),
                        new DestinasiAdapter.DestinasiItem("Air Terjun Antrokan", R.drawable.img_airterjun_antrokan, "Tanggul, Jember", 4.8, DetailDestinasiActivity.class)
                );
                break;

            case "Gunung":
                destinasiList = Arrays.asList(
                        new DestinasiAdapter.DestinasiItem("Gunung Gambir", R.drawable.img_gambir, "Sumberbaru, jember", 4.8, DetailDestinasiActivity.class)
                );
                break;

            case "Pantai":
                destinasiList = Arrays.asList(
                        new DestinasiAdapter.DestinasiItem("Pantai Papuma", R.drawable.img_papuma, "Wuluhan, Jember", 4.9, DetailDestinasiActivity.class),
                        new DestinasiAdapter.DestinasiItem("Pantai Watu Ulo", R.drawable.img_watuulo, "Ambulu, Jember", 4.8, DetailDestinasiActivity.class)
                );
                break;

            case "Bukit":
                destinasiList = Arrays.asList(
                        new DestinasiAdapter.DestinasiItem("Rembangan", R.drawable.img_rembangan, "Arjasa, Jember", 4.8, DetailDestinasiActivity.class),
                        new DestinasiAdapter.DestinasiItem("Bukit SJ88", R.drawable.img_sj88, "Wuluhan, Jember", 4.8, DetailDestinasiActivity.class)
                );
                break;

            case "Air Terjun":
                destinasiList = Arrays.asList(
                        new DestinasiAdapter.DestinasiItem("Air Terjun Tancak", R.drawable.img_airterjun_tancak, "Panti, Jember", 4.8, DetailDestinasiActivity.class),
                        new DestinasiAdapter.DestinasiItem("Air Terjun Antrokan", R.drawable.img_airterjun_antrokan, "Tanggul, Jember", 4.8, DetailDestinasiActivity.class)
                );
                break;

            default:
                // Default list jika tidak ada kategori yang cocok
                destinasiList = new ArrayList<>();
                break;
        }

        // Atur adapter untuk RecyclerView destinasi
        recyclerViewDestinasi.setLayoutManager(new GridLayoutManager(getContext(), 2));

        // Tetap gunakan adapter yang sama
        DestinasiAdapter destinasiAdapter = new DestinasiAdapter(requireContext(), destinasiList);
        recyclerViewDestinasi.setAdapter(destinasiAdapter);

    }
}
