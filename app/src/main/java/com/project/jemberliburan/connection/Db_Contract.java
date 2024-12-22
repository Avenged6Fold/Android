package com.project.jemberliburan.connection;

public class Db_Contract {

    public static String ip = "192.168.0.103"; // IP untuk emulator (localhost)

    public static final String urlRegisterActivity = "http://" + ip + "/Jeli_API/api-register.php";
    public static final String urlLoginActivity = "http://" + ip + "/Jeli_API/api-login.php";
    public static final String urlSendVerificationCode = "http://" + ip + "/Jeli_API/send-verification-code.php";
    public static final String urlResetPassword = "http://" + ip + "/Jeli_API/reset-password.php"; // Tambahkan URL reset password
    public static final String urlVerifyCode = "http://" + ip + "/Jeli_API/verify-code.php";

    public static final String urlUpdateUsers = "http://" + ip + "/Jeli_API/api-update-users.php";

    public static final String urlCekTiket = "http://" + ip + "/Jeli_API/cek-tiket.php";
    public static final String urlRiwayatTiket = "http://" + ip + "/Jeli_API/riwayat-tiket.php";

    public static final String urlAddReview = "http://" + ip + "/Jeli_API/add-review.php";     // Endpoint untuk menambahkan ulasan
    public static final String urlGetReviews = "http://" + ip + "/Jeli_API/get-reviews.php";   // Endpoint untuk mengambil ulasan

}
