package com.project.jemberliburan.activity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.project.jemberliburan.Connection.Db_Contract;
import com.project.jemberliburan.R;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ResetPasswordActivity extends AppCompatActivity {

    private static final String TAG = "ResetPasswordActivity";
    private EditText emailEditText, verificationCodeEditText, newPasswordEditText;
    private Button resetPasswordButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        // Inisialisasi elemen UI
        emailEditText = findViewById(R.id.emailEditText);
        verificationCodeEditText = findViewById(R.id.verificationCodeEditText);
        newPasswordEditText = findViewById(R.id.newPasswordEditText);
        resetPasswordButton = findViewById(R.id.resetPasswordButton);

        // Mengatur email dari Intent jika tersedia
        String email = getIntent().getStringExtra("email");
        if (email != null) {
            emailEditText.setText(email);
        }

        // Tombol untuk reset password
        resetPasswordButton.setOnClickListener(v -> {
            String userEmail = emailEditText.getText().toString().trim();
            String verificationCode = verificationCodeEditText.getText().toString().trim();
            String newPassword = newPasswordEditText.getText().toString().trim();

            // Validasi input
            if (userEmail.isEmpty() || verificationCode.isEmpty() || newPassword.isEmpty()) {
                Toast.makeText(this, "Semua bidang harus diisi", Toast.LENGTH_SHORT).show();
            } else if (newPassword.length() < 6) {
                Toast.makeText(this, "Kata sandi harus memiliki minimal 6 karakter", Toast.LENGTH_SHORT).show();
            } else {
                resetPassword(userEmail, verificationCode, newPassword);
            }
        });
    }

    /**
     * Metode untuk mengirim permintaan reset password ke server
     */
    private void resetPassword(String email, String code, String password) {
        String url = Db_Contract.urlResetPassword;
        Log.d(TAG, "Sending reset password request for: " + email);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                response -> {
                    Log.d(TAG, "Server response: " + response);
                    try {
                        JSONObject jsonResponse = new JSONObject(response);
                        String status = jsonResponse.optString("status");
                        String message = jsonResponse.optString("message");

                        if ("success".equalsIgnoreCase(status)) {
                            Toast.makeText(ResetPasswordActivity.this, "Kata sandi berhasil diubah!", Toast.LENGTH_SHORT).show();
                            finish(); // Menutup aktivitas setelah berhasil reset password
                        } else {
                            Toast.makeText(ResetPasswordActivity.this, message, Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        Log.e(TAG, "JSON parsing error: " + e.getMessage());
                        Toast.makeText(ResetPasswordActivity.this, "Kesalahan parsing data dari server", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> {
                    if (error.networkResponse != null && error.networkResponse.data != null) {
                        try {
                            String errorData = new String(error.networkResponse.data);
                            JSONObject errorJson = new JSONObject(errorData);
                            String message = errorJson.optString("message", "Terjadi kesalahan.");
                            Log.e(TAG, "Error Response: " + message);
                            Toast.makeText(ResetPasswordActivity.this, message, Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            Log.e(TAG, "Error parsing error response: " + e.getMessage());
                            Toast.makeText(ResetPasswordActivity.this, "Kesalahan jaringan.", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Log.e(TAG, "Error: " + error.getMessage());
                        Toast.makeText(ResetPasswordActivity.this, "Kesalahan jaringan: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                // Parameter yang dikirim ke server
                Map<String, String> params = new HashMap<>();
                params.put("email", email);
                params.put("code", code);
                params.put("password", password);
                return params;
            }
        };

        // Tambahkan permintaan ke antrean Volley
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
