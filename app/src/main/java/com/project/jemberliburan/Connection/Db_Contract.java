package com.project.jemberliburan.Connection;

public class Db_Contract {

    public static String ip = "192.168.1.17"; // IP untuk emulator (localhost)

    public static final String urlRegisterActivity = "http://" + ip + "/Jeli_API/api-register.php";
    public static final String urlLoginActivity = "http://" + ip + "/Jeli_API/api-login.php";
    public static final String urlSendVerificationCode = "http://" + ip + "/Jeli_API/send-verification-code.php";
    public static final String urlResetPassword = "http://" + ip + "/Jeli_API/reset-password.php"; // Tambahkan URL reset password
    public static final String urlVerifyCode = "http://" + ip + "/Jeli_API/verify-code.php";

    public static final String urlUpdateUsers = "http://" + ip + "/Jeli_API/api-update-users.php";

}
