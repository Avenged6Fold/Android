package com.project.jemberliburan.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.project.jemberliburan.R;
import com.project.jemberliburan.fragment.HomeFragment;

public class TentangSayaActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;

    private ImageView icon_back, imgProfile;
    private Button btnSave;
    private EditText etEmail, etUsername, etPhone, etGender, etAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tentang_saya);

        // Inisialisasi komponen
        icon_back = findViewById(R.id.icon_back);
        imgProfile = findViewById(R.id.imgProfile);
        btnSave = findViewById(R.id.btn_register);
        etEmail = findViewById(R.id.etx_email);
        etUsername = findViewById(R.id.etx_username);
        etPhone = findViewById(R.id.etx_notelpon);
        etGender = findViewById(R.id.etx_jeniskelamin);
        etAddress = findViewById(R.id.etx_alamat);

        // Listener untuk tombol kembali
        icon_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TentangSayaActivity.this, HomeFragment.class);
                startActivity(intent);
                finish();
            }
        });

        // Listener untuk gambar profil (unggah gambar)
        imgProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Buka galeri untuk memilih gambar
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, PICK_IMAGE_REQUEST);
            }
        });

        // Listener untuk tombol simpan
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Validasi data
                if (etEmail.getText().toString().isEmpty() ||
                        etUsername.getText().toString().isEmpty() ||
                        etPhone.getText().toString().isEmpty() ||
                        etGender.getText().toString().isEmpty() ||
                        etAddress.getText().toString().isEmpty()) {

                    Toast.makeText(TentangSayaActivity.this, "Harap lengkapi semua data!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(TentangSayaActivity.this, "Data berhasil disimpan!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            Uri imageUri = data.getData();
            if (imageUri != null) {
                imgProfile.setImageURI(imageUri); // Tampilkan gambar yang dipilih
            }
        }
    }
}
