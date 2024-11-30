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

import java.util.ArrayList;
import java.util.List;

public class SearchDestinasiActivity extends AppCompatActivity {

    private EditText searchInput;
    private RecyclerView recyclerViewSearchResults;
    private List<DestinasiAdapter.DestinasiItem> destinasiList;
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

        // Data dummy destinasi
        destinasiList = new ArrayList<>();
        destinasiList.add(new DestinasiAdapter.DestinasiItem("Pantai Papuma", R.drawable.img_papuma, "Wuluhan, Jember", 4.9, null));
        destinasiList.add(new DestinasiAdapter.DestinasiItem("Pantai Teluk Love", R.drawable.img_teluklove, "Ambulu, Jember", 4.8, null));
        destinasiList.add(new DestinasiAdapter.DestinasiItem("Pantai Watu Ulo", R.drawable.img_watuulo, "Ambulu, Jember", 4.8, null));
        destinasiList.add(new DestinasiAdapter.DestinasiItem("Bukit SJ88", R.drawable.img_sj88, "Wuluhan, Jember", 4.8, null));
        destinasiList.add(new DestinasiAdapter.DestinasiItem("Rembangan", R.drawable.img_rembangan, "Arjasa, Jember", 4.8, null));
        destinasiList.add(new DestinasiAdapter.DestinasiItem("Gunung Gambir", R.drawable.img_gambir, "Sumberbaru, Jember", 4.8, null));
        destinasiList.add(new DestinasiAdapter.DestinasiItem("Air Terjun Tancak", R.drawable.img_airterjun_tancak, "Panti, Jember", 4.8, null));
        destinasiList.add(new DestinasiAdapter.DestinasiItem("Air Terjun Antrokan", R.drawable.img_airterjun_antrokan, "Tanggul, Jember", 4.8, null));

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
        List<DestinasiAdapter.DestinasiItem> filteredList = new ArrayList<>();
        for (DestinasiAdapter.DestinasiItem item : destinasiList) {
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
