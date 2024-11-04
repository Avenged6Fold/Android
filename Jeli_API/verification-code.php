<?php
// File: send_verification_code.php
include 'config.php'; // Include koneksi ke database

if (isset($_POST['email'])) {
    $email = $_POST['email'];

    // Cek apakah email ada di database
    $query = $conn->prepare("SELECT * FROM users WHERE email = ?");
    $query->bind_param("s", $email);
    $query->execute();
    $result = $query->get_result();

    if ($result->num_rows > 0) {
        // Buat kode verifikasi
        $verification_code = rand(100000, 999999);

        // Simpan kode verifikasi di database
        $update_query = $conn->prepare("UPDATE users SET verification_code = ? WHERE email = ?");
        $update_query->bind_param("ss", $verification_code, $email);
        $update_query->execute();

        // Kirim kode verifikasi ke email
        $subject = "Kode Verifikasi Lupa Password";
        $message = "Kode verifikasi Anda adalah: " . $verification_code;
        $headers = "From: your_email@example.com";

        if (mail($email, $subject, $message, $headers)) {
            echo json_encode(["status" => "success", "message" => "Kode verifikasi telah dikirim ke email Anda."]);
        } else {
            echo json_encode(["status" => "error", "message" => "Gagal mengirim email."]);
        }
    } else {
        echo json_encode(["status" => "error", "message" => "Email tidak ditemukan."]);
    }
}
?>
