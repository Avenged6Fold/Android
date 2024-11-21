package com.project.jemberliburan.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.project.jemberliburan.Connection.Db_Contract;
import com.project.jemberliburan.R;

import org.json.JSONException;
import org.json.JSONObject;

public class VerificationCodeActivity extends AppCompatActivity {

    private static final String TAG = "VerificationCodeActivity";
    private static final long TIMER_DURATION = 60000; // 1 menit dalam milidetik

    private EditText inputField1, inputField2, inputField3, inputField4, inputField5, inputField6;
    private Button verifyCodeButton;
    private TextView resendCodeText;
    private String email; // Simpan email untuk keperluan resend kode
    private CountDownTimer countDownTimer;
    private boolean isWaiting = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification_code);

        // Ambil email dari Intent
        email = getIntent().getStringExtra("email");

        if (email == null || email.isEmpty()) {
            Toast.makeText(this, "Email tidak valid. Harap kembali ke halaman sebelumnya.", Toast.LENGTH_SHORT).show();
            finish(); // Kembali ke halaman sebelumnya jika email tidak valid
            return;
        }

        Log.d(TAG, "Email yang diterima: " + email);

        // Inisialisasi UI
        initViews();

        // Tombol verifikasi kode
        verifyCodeButton.setOnClickListener(v -> {
            String verificationCode = collectVerificationCode();
            if (verificationCode.length() < 6) {
                Toast.makeText(this, "Kode verifikasi tidak valid", Toast.LENGTH_SHORT).show();
            } else {
                verifyCode(verificationCode);
            }
        });

        // Tombol kirim ulang kode
        resendCodeText.setOnClickListener(v -> {
            if (!isWaiting) {
                clearInputs();
                resendVerificationCode();
                startTimer();
            }
        });
    }

    /**
     * Inisialisasi semua elemen UI
     */
    private void initViews() {
        inputField1 = findViewById(R.id.inputField1);
        inputField2 = findViewById(R.id.inputField2);
        inputField3 = findViewById(R.id.inputField3);
        inputField4 = findViewById(R.id.inputField4);
        inputField5 = findViewById(R.id.inputField5);
        inputField6 = findViewById(R.id.inputField6);
        verifyCodeButton = findViewById(R.id.verifyCodeButton);
        resendCodeText = findViewById(R.id.resendCodeText);

        setupTextWatchers();
    }

    /**
     * Menggabungkan input dari semua EditText menjadi satu kode verifikasi
     */
    private String collectVerificationCode() {
        return inputField1.getText().toString().trim() +
                inputField2.getText().toString().trim() +
                inputField3.getText().toString().trim() +
                inputField4.getText().toString().trim() +
                inputField5.getText().toString().trim() +
                inputField6.getText().toString().trim();
    }

    /**
     * Membersihkan semua input EditText
     */
    private void clearInputs() {
        inputField1.setText("");
        inputField2.setText("");
        inputField3.setText("");
        inputField4.setText("");
        inputField5.setText("");
        inputField6.setText("");
        inputField1.requestFocus(); // Fokus ke input pertama
    }

    /**
     * Menavigasikan antar EditText secara otomatis saat input terisi
     */
    private void setupTextWatchers() {
        setupTextWatcher(inputField1, inputField2);
        setupTextWatcher(inputField2, inputField3);
        setupTextWatcher(inputField3, inputField4);
        setupTextWatcher(inputField4, inputField5);
        setupTextWatcher(inputField5, inputField6);
    }

    private void setupTextWatcher(EditText current, EditText next) {
        current.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 1) next.requestFocus();
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    /**
     * Verifikasi kode melalui server
     */
    private void verifyCode(String verificationCode) {
        String url = Db_Contract.urlVerifyCode; // URL untuk endpoint verify-code.php
        Log.d(TAG, "Verifying code for: " + email);

        JSONObject params = new JSONObject();
        try {
            params.put("email", email);
            params.put("verification_code", verificationCode);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST, url, params,
                response -> {
                    try {
                        boolean success = response.getBoolean("success");
                        String message = response.getString("message");

                        if (success) {
                            Toast.makeText(this, "Kode berhasil diverifikasi!", Toast.LENGTH_SHORT).show();
                            // Arahkan ke halaman reset password
                            Intent intent = new Intent(this, ResetPasswordActivity.class);
                            intent.putExtra("email", email);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        Log.e(TAG, "JSON parsing error: " + e.getMessage());
                        Toast.makeText(this, "Kesalahan parsing data.", Toast.LENGTH_SHORT).show();
                    }
                },
                this::handleVolleyError
        );

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonObjectRequest);
    }

    /**
     * Kirim ulang kode verifikasi melalui server
     */
    private void resendVerificationCode() {
        String url = Db_Contract.urlSendVerificationCode; // URL untuk endpoint send-verification-code.php
        JSONObject params = new JSONObject();
        try {
            params.put("email", email);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST, url, params,
                response -> {
                    try {
                        String message = response.getString("message");
                        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
                        resendCodeText.setText("Kode Terkirim");
                        new android.os.Handler().postDelayed(() -> resendCodeText.setText("Kirim Ulang Kode"), 3000);
                    } catch (JSONException e) {
                        Log.e(TAG, "JSON parsing error: " + e.getMessage());
                        Toast.makeText(this, "Kesalahan parsing data.", Toast.LENGTH_SHORT).show();
                    }
                },
                this::handleVolleyError
        );

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonObjectRequest);
    }

    /**
     * Timer untuk membatasi pengiriman ulang kode
     */
    private void startTimer() {
        isWaiting = true;

        countDownTimer = new CountDownTimer(TIMER_DURATION, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                long secondsLeft = millisUntilFinished / 1000;
                resendCodeText.setText("Tunggu " + secondsLeft + " detik...");
            }

            @Override
            public void onFinish() {
                isWaiting = false;
                resendCodeText.setClickable(true);
                resendCodeText.setText("Kirim Ulang Kode");
            }
        };
        countDownTimer.start();
    }

    /**
     * Tangani error dari server
     */
    private void handleVolleyError(VolleyError error) {
        if (error.networkResponse != null && error.networkResponse.data != null) {
            try {
                String errorResponse = new String(error.networkResponse.data, "UTF-8");
                Log.e(TAG, "Error Response: " + errorResponse);
                Toast.makeText(this, "Kesalahan Server: " + errorResponse, Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(this, "Kesalahan jaringan.", Toast.LENGTH_SHORT).show();
            }
        } else {
            Log.e(TAG, "Volley Error: " + error.getMessage());
            Toast.makeText(this, "Kesalahan jaringan: " + error.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }
}
