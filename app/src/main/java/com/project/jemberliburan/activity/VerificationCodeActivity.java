package com.project.jemberliburan.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
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

    private EditText verificationCodeEditText;
    private Button verifyCodeButton;
    private TextView resendCodeText;
    private CountDownTimer countDownTimer;
    private boolean isWaiting = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification_code);

        // Ambil data email dari Intent
        String email = getIntent().getStringExtra("email");

        // Initialize UI elements
        verificationCodeEditText = findViewById(R.id.verificationCodeEditText);
        verifyCodeButton = findViewById(R.id.verifyCodeButton);
        resendCodeText = findViewById(R.id.resendCodeText);

        // Button click listener untuk verifikasi kode
        verifyCodeButton.setOnClickListener(v -> {
            String verificationCode = verificationCodeEditText.getText().toString().trim();
            if (verificationCode.isEmpty()) {
                Toast.makeText(this, "Kode verifikasi tidak boleh kosong", Toast.LENGTH_SHORT).show();
            } else {
                verifyCode(email, verificationCode);
            }
        });

        // Listener untuk Kirim Ulang Kode
        resendCodeText.setOnClickListener(v -> {
            if (!isWaiting) {
                resendVerificationCode(email);
                startTimer();
            }
        });
    }

    /**
     * Verifikasi kode melalui server
     */
    private void verifyCode(String email, String verificationCode) {
        String url = Db_Contract.urlVerifyCode; // Endpoint verify-code.php
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
    private void resendVerificationCode(String email) {
        resendCodeText.setText("Mengirim...");
        resendCodeText.setClickable(false);

        String url = Db_Contract.urlSendVerificationCode; // Endpoint send-verification-code.php
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
