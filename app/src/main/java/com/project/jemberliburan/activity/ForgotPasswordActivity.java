package com.project.jemberliburan.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.project.jemberliburan.Connection.Db_Contract;
import com.project.jemberliburan.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ForgotPasswordActivity extends AppCompatActivity {

    private static final String TAG = "ForgotPasswordActivity";
    private EditText emailEditText;
    private Button sendVerificationButton;
    private boolean canSendRequest = true; // Mengatur delay pengiriman ulang

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        emailEditText = findViewById(R.id.emailEditText);
        sendVerificationButton = findViewById(R.id.sendVerificationButton);

        // Tombol kembali untuk kembali ke halaman sebelumnya
        ImageView backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> finish());

        // Tombol kirim verifikasi
        sendVerificationButton.setOnClickListener(v -> {
            String email = emailEditText.getText().toString().trim();
            if (email.isEmpty()) {
                Toast.makeText(this, "Email tidak boleh kosong", Toast.LENGTH_SHORT).show();
            } else if (!canSendRequest) {
                Toast.makeText(this, "Harap tunggu sebelum mengirim ulang kode verifikasi.", Toast.LENGTH_SHORT).show();
            } else {
                sendVerificationCode(email);
            }
        });
    }

    private void sendVerificationCode(String email) {
        // Batasi pengiriman dengan mengatur canSendRequest ke false
        canSendRequest = false;

        String url = Db_Contract.urlSendVerificationCode; // Endpoint untuk pengiriman kode
        Log.d(TAG, "Sending verification code to: " + email);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                response -> {
                    Log.d(TAG, "Server response: " + response);
                    try {
                        JSONObject jsonResponse = new JSONObject(response);
                        String status = jsonResponse.optString("status");
                        String message = jsonResponse.optString("message");

                        if ("success".equalsIgnoreCase(status)) {
                            Toast.makeText(ForgotPasswordActivity.this, message, Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(ForgotPasswordActivity.this, VerificationCodeActivity.class);
                            intent.putExtra("email", email);
                            startActivity(intent);
                        } else {
                            Toast.makeText(ForgotPasswordActivity.this, message, Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        Log.e(TAG, "JSON parsing error: " + e.getMessage());
                        Toast.makeText(ForgotPasswordActivity.this, "Kesalahan parsing data.", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> {
                    if (error.networkResponse != null && error.networkResponse.statusCode == 429) {
                        Log.w(TAG, "Too Many Requests. Waiting before retry...");
                        Toast.makeText(this, "Terlalu banyak permintaan. Menunggu 5 detik sebelum mencoba ulang.", Toast.LENGTH_SHORT).show();

                        // Tunggu 5 detik sebelum mencoba ulang
                        new android.os.Handler().postDelayed(() -> {
                            canSendRequest = true;
                            sendVerificationCode(email); // Coba ulang
                        }, 5000);
                    } else {
                        handleVolleyError(error);
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("email", email);
                return params;
            }
        };

        // Tambahkan permintaan ke antrian
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

        // Tambahkan delay 60 detik sebelum pengguna dapat mengirim ulang permintaan
        new android.os.Handler().postDelayed(() -> canSendRequest = true, 60000);
    }

    private void handleVolleyError(VolleyError error) {
        if (error.networkResponse != null) {
            // Jika ada respons dari server
            String errorData = new String(error.networkResponse.data);
            Log.e(TAG, "Error Response Code: " + error.networkResponse.statusCode);
            Log.e(TAG, "Error Data: " + errorData);
            Toast.makeText(this, "Kesalahan Server: " + errorData, Toast.LENGTH_SHORT).show();
        } else if (error.getMessage() != null) {
            // Jika tidak ada respons tetapi ada pesan error
            Log.e(TAG, "Error Message: " + error.getMessage());
            Toast.makeText(this, "Kesalahan Jaringan: " + error.getMessage(), Toast.LENGTH_SHORT).show();
        } else {
            // Jika tidak ada respons atau pesan error
            Log.e(TAG, "Unknown error occurred");
            Toast.makeText(this, "Kesalahan tidak diketahui. Periksa koneksi Anda.", Toast.LENGTH_SHORT).show();
        }

        // Tetapkan ulang canSendRequest menjadi true agar pengguna bisa mencoba lagi
        canSendRequest = true;
    }
}
