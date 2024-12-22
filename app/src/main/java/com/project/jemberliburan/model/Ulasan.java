package com.project.jemberliburan.model;
import com.google.gson.annotations.SerializedName;
public class Ulasan {
    private int id;
    @SerializedName("user_id")
    private int userId;
    @SerializedName("nama_user")
    private String namaUser;
    @SerializedName("foto_profil")
    private String fotoProfil;
    private int rating;
    private String komentar;
    @SerializedName("tanggal_ulasan")
    private String tanggalUlasan;

    // Konstruktor
    public Ulasan(int id, int userId, String namaUser, String fotoProfil, int rating, String komentar, String tanggalUlasan) {
        this.id = id;
        this.userId = userId;
        this.namaUser = namaUser;
        this.fotoProfil = fotoProfil;
        this.rating = rating;
        this.komentar = komentar;
        this.tanggalUlasan = tanggalUlasan;
    }

    // Getter methods
    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public String getNamaUser() {
        return namaUser;
    }

    public String getFotoProfil() {
        return fotoProfil;
    }

    public int getRating() {
        return rating;
    }

    public String getKomentar() {
        return komentar;
    }

    public String getTanggalUlasan() {
        return tanggalUlasan;
    }

    // Setter methods
    public void setId(int id) {
        this.id = id;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setNamaUser(String namaUser) {
        this.namaUser = namaUser;
    }

    public void setFotoProfil(String fotoProfil) {
        this.fotoProfil = fotoProfil;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public void setKomentar(String komentar) {
        this.komentar = komentar;
    }

    public void setTanggalUlasan(String tanggalUlasan) {
        this.tanggalUlasan = tanggalUlasan;
    }
}
