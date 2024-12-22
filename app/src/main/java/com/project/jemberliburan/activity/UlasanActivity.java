package com.project.jemberliburan.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.project.jemberliburan.R;
import com.project.jemberliburan.adapter.UlasanAdapter;
import com.project.jemberliburan.connection.Db_Contract;
import com.project.jemberliburan.connection.MySingleton;
import com.project.jemberliburan.model.Ulasan;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class UlasanActivity extends AppCompatActivity {
    private RecyclerView recyclerViewUlasan;
    private UlasanAdapter ulasanAdapter;
    private List<Ulasan> ulasanList;
    private SwipeRefreshLayout swipeRefreshLayout;
    private int wisataId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ulasan);

        recyclerViewUlasan = findViewById(R.id.recyclerViewUlasan);
        Button buttonTambahUlasan = findViewById(R.id.buttonTambahUlasan);
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);

        ulasanList = new ArrayList<>();
        ulasanAdapter = new UlasanAdapter(this, ulasanList);
        recyclerViewUlasan.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewUlasan.setAdapter(ulasanAdapter);

        // Ambil wisata_id dari intent
        wisataId = getIntent().getIntExtra("wisata_id", -1);
        if (wisataId == -1) {
            Toast.makeText(this, "ID Destinasi tidak valid", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Ambil ulasan dari server
        fetchUlasan();

        // Tambahkan aksi pada tombol tambah ulasan
        buttonTambahUlasan.setOnClickListener(v -> {
            Intent intent = new Intent(UlasanActivity.this, PostUlasanActivity.class);
            intent.putExtra("wisata_id", wisataId); // Kirim wisata_id ke PostUlasanActivity
            startActivityForResult(intent, 100);
        });

        // Tambahkan aksi untuk SwipeRefreshLayout
        swipeRefreshLayout.setOnRefreshListener(() -> {
            fetchUlasan();
            swipeRefreshLayout.setRefreshing(false);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK) {
            // Refresh ulasan setelah kembali dari PostUlasanActivity
            fetchUlasan();
        }
    }

    private void fetchUlasan() {
        String url = Db_Contract.urlGetReviews + "?wisata_id=" + wisataId; // URL endpoint

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                response -> {
                    try {
                        // Log respons JSON sebelum parsing
                        Log.d("UlasanActivity", "Full Response JSON: " + response);

                        // Gunakan GsonBuilder dengan setLenient untuk parsing JSON
                        Gson gson = new GsonBuilder().setLenient().create();
                        JsonObject jsonResponse = JsonParser.parseString(response).getAsJsonObject();
                        String status = jsonResponse.get("status").getAsString();

                        if (status.equals("success")) {
                            // Parsing data ulasan
                            Type ulasanListType = new TypeToken<List<Ulasan>>() {}.getType();
                            List<Ulasan> ulasanlist = gson.fromJson(jsonResponse.get("data"), ulasanListType);

                            // Clear list dan tambahkan data baru
                            ulasanList.clear();
                            ulasanList.addAll(ulasanlist);

                            // Log data ulasan untuk memastikan parsing berhasil
                            for (Ulasan ulasan : ulasanList) {
                                Log.d("UlasanActivity", "Parsed Ulasan: " +
                                        "Nama User: " + ulasan.getNamaUser() +
                                        ", Tanggal Ulasan: " + ulasan.getTanggalUlasan() +
                                        ", Foto Profil: " + ulasan.getFotoProfil());
                            }

                            // Refresh adapter
                            ulasanAdapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(this, jsonResponse.get("message").getAsString(), Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        Log.e("UlasanActivity", "JSON Parsing Error: " + e.getMessage());
                        Toast.makeText(this, "Kesalahan saat memproses data ulasan.", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> {
                    Log.e("UlasanActivity", "Volley Error: " + error.getMessage());
                    Toast.makeText(UlasanActivity.this, "Gagal mengambil ulasan", Toast.LENGTH_SHORT).show();
                });

        // Tambahkan request ke antrian Volley
        MySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }
}
