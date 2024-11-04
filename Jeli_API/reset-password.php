<?php
// File: reset_password.php
include 'config.php'; // Include koneksi ke database

if (isset($_POST['email']) && isset($_POST['code']) && isset($_POST['new_password'])) {
    $email = $_POST['email'];
    $verification_code = $_POST['code'];
    $new_password = password_hash($_POST['new_password'], PASSWORD_DEFAULT);

    // Cek apakah email dan kode verifikasi sesuai
    $query = $conn->prepare("SELECT * FROM users WHERE email = ? AND verification_code = ?");
    $query->bind_param("ss", $email, $verification_code);
    $query->execute();
    $result = $query->get_result();

    if ($result->num_rows > 0) {
        // Update password baru
        $update_query = $conn->prepare("UPDATE users SET password = ?, verification_code = NULL WHERE email = ?");
        $update_query->bind_param("ss", $new_password, $email);
        if ($update_query->execute()) {
            echo json_encode(["status" => "success", "message" => "Password berhasil direset."]);
        } else {
            echo json_encode(["status" => "error", "message" => "Gagal mereset password."]);
        }
    } else {
        echo json_encode(["status" => "error", "message" => "Kode verifikasi salah atau email tidak valid."]);
    }
}
?>
