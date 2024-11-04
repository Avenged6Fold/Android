<?php

include 'koneksi.php';

$username = $_POST['username'];
$email = $_POST['email'];
$password = $_POST['password'];
$alamat = $_POST['alamat'];

$queryRegister = "SELECT * FROM users WHERE username = '".$username."'";

$msql = mysqli_query($koneksi, $queryRegister);

$result = mysqli_num_rows($msql);

if (!empty($username) && !empty($password) && !empty($alamat)&& !empty($email)){


    if($result == 0){
        $regis = "INSERT INTO users (username, password, alamat, email)
        VALUES ('$username', '$password', '$alamat', '$email')";

        $msqlRegis = mysqli_query($koneksi, $regis);

        echo "Daftar Berhasil";
    }else{
        echo "Username Sudah Digunakan";
    }
}else{
    echo "Semua Data Harus Di Isi Lengkap";
}