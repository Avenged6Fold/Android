<?php

$hostName = "localhost";
$userName = "root";
$password = "";
$dbName = "tiketproject";

$koneksi = mysqli_connect($hostName, $userName, $password, $dbName);

if (!$koneksi){
    echo "koneksi Gagal";
}