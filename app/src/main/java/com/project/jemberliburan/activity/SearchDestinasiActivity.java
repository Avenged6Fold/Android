package com.project.jemberliburan.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.project.jemberliburan.R;
import com.project.jemberliburan.adapter.DestinasiAdapter;
import com.project.jemberliburan.model.Destinasi;

import java.util.ArrayList;
import java.util.List;

public class SearchDestinasiActivity extends AppCompatActivity {

    private EditText searchInput;
    private RecyclerView recyclerViewSearchResults;
    private List<Destinasi> destinasiList;
    private DestinasiAdapter destinasiAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_search_destinasi);

        // Inisialisasi elemen layout
        searchInput = findViewById(R.id.searchInput);
        recyclerViewSearchResults = findViewById(R.id.recyclerViewSearchResults);
        ImageView backButton = findViewById(R.id.icon_back);

        // Tombol kembali
        backButton.setOnClickListener(v -> finish());

        // Data dummy destinasi dengan wisataId yang valid
        destinasiList = new ArrayList<>();
        destinasiList.add(new Destinasi("Pantai Papuma", R.drawable.img_papuma, "Wuluhan, Jember", 4.9, 1, "Deskripsi Pantai Papuma"));
        destinasiList.add(new Destinasi("Pantai Teluk Love", R.drawable.img_teluklove, "Ambulu, Jember", 4.8, 2, "Deskripsi Pantai Teluk Love"));
        destinasiList.add(new Destinasi("Pantai Watu Ulo", R.drawable.img_watuulo, "Ambulu, Jember", 4.8, 3, "Deskripsi Pantai Watu Ulo"));
        destinasiList.add(new Destinasi("Gunung Gambir", R.drawable.img_gambir, "Sumberbaru, Jember", 4.8, 4, "Deskripsi Gunung Gambir"));
        destinasiList.add(new Destinasi("Bukit SJ88", R.drawable.img_sj88, "Wuluhan, Jember", 4.8, 5, "Deskripsi Bukit SJ88"));
        destinasiList.add(new Destinasi("Rembangan", R.drawable.img_rembangan, "Arjasa, Jember", 4.8, 6, "Deskripsi Rembangan"));
        destinasiList.add(new Destinasi("Air Terjun Tancak", R.drawable.img_airterjun_tancak, "Panti, Jember", 4.8, 7, "Deskripsi Air Terjun Tancak"));
        destinasiList.add(new Destinasi("Air Terjun Antrokan", R.drawable.img_airterjun_antrokan, "Tanggul, Jember", 4.8, 8, "Deskripsi Air Terjun Antrokan"));

        // Setup RecyclerView dengan GridLayout
        recyclerViewSearchResults.setLayoutManager(new GridLayoutManager(this, 2));
        destinasiAdapter = new DestinasiAdapter(this, destinasiList);
        recyclerViewSearchResults.setAdapter(destinasiAdapter);

        // Tambahkan listener untuk pencarian
        searchInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Tidak diperlukan
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterDestinasi(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Tidak diperlukan
            }
        });
    }

    private void filterDestinasi(String query) {
        List<Destinasi> filteredList = new ArrayList<>();
        for (Destinasi item : destinasiList) {
            if (item.getName().toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(item);
            }
        }

        // Update adapter dengan hasil filter
        destinasiAdapter.updateList(filteredList);

        // Tampilkan pesan jika tidak ada hasil
        if (filteredList.isEmpty() && !query.isEmpty()) {
            Toast.makeText(this, "Destinasi tidak ditemukan", Toast.LENGTH_SHORT).show();
        }
    }
}
