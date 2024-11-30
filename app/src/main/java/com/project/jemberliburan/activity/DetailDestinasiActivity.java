package com.project.jemberliburan.activity;

import android.os.Bundle;
import android.content.Intent;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.project.jemberliburan.R;

public class DetailDestinasiActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        EdgeToEdge.enable(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_destinasi);

        // Inisialisasi Views
        ImageView imageView = findViewById(R.id.imageViewDetail);
        TextView textViewName = findViewById(R.id.textViewNameDetail);
        TextView textViewLocation = findViewById(R.id.textViewLocationDetail);
        TextView textViewRating = findViewById(R.id.textViewRatingDetail);
        TextView textViewDeskripsi = findViewById(R.id.textViewDeskripsiDetail);
        ImageView backButton = findViewById(R.id.icon_back);

        // Tambahkan logika untuk tombol kembali
        backButton.setOnClickListener(v -> finish());

        // Ambil nama dari Intent
        Intent intent = getIntent();
        String name = intent.getStringExtra("name");

        // Variabel untuk menyimpan data detail
        int imageResource = 0;
        String location = "";
        double rating = 0;
        String deskripsi = "";

        // Gunakan switch-case untuk menentukan detail
        switch (name) {
            case "Pantai Papuma":
                imageResource = R.drawable.img_papuma;
                location = "Wuluhan, Jember";
                rating = 4.9;
                deskripsi = "Pantai Papuma (Pantai Pasir Putih Malikan) terkenal dengan keindahan pasir putih dan batu karangnya yang ikonik. Terletak di Kecamatan Wuluhan, pantai ini memiliki pemandangan laut biru dengan ombak yang relatif tenang, cocok untuk wisata keluarga. Di sepanjang pantai, terdapat batuan besar yang dikenal sebagai Batu Malikan, yang konon memiliki kisah mistis. Wisatawan juga dapat menikmati panorama matahari terbit dan terbenam yang memukau, serta melihat perahu nelayan yang bersandar di pantai.";
                break;

            case "Pantai Teluk Love":
                imageResource = R.drawable.img_teluklove;
                location = "Ambulu, Jember";
                rating = 4.8;
                deskripsi = "Teluk Love adalah teluk berbentuk hati yang terlihat dari ketinggian, berlokasi di Kecamatan Ambulu, dekat Pantai Watu Ulo. Dari atas bukit yang mengelilingi teluk, pengunjung bisa melihat bentuk teluk yang menyerupai lambang cinta. Tempat ini menjadi favorit untuk wisatawan yang ingin berfoto atau menikmati pemandangan laut dari sudut pandang yang berbeda. Teluk Love sangat populer sebagai spot foto dan sering dipadati pengunjung saat matahari terbenam.";
                break;

            case "Pantai Watu Ulo":
                imageResource = R.drawable.img_watuulo;
                location = "Ambulu, Jember";
                rating = 4.7;
                deskripsi = "Pantai Watu Ulo berlokasi di Kecamatan Ambulu, berdekatan dengan Pantai Papuma. Namanya berasal dari susunan batu karang yang memanjang menyerupai ular (dalam bahasa Jawa, ulo berarti ular). Pantai ini sering dikunjungi karena pemandangan alamnya yang unik dan legendanya yang menarik. Watu Ulo juga terkenal sebagai tempat yang sering digunakan untuk berbagai festival budaya dan acara lokal, termasuk Festival Petik Laut. Di sekitar pantai, wisatawan dapat menemukan beberapa warung makan dan kios oleh-oleh khas Jember.";
                break;

            case "Gunung Gambir":
                imageResource = R.drawable.img_gambir;
                location = "Sumberbaru, Jember";
                rating = 4.8;
                deskripsi = "Terletak di Desa Gelang, Kecamatan Sumberbaru, Kebun Teh Gunung Gambir adalah destinasi wisata yang menawarkan pemandangan hamparan kebun teh hijau. Suasana pegunungan yang sejuk membuat tempat ini populer sebagai lokasi untuk bersantai, berjalan-jalan, atau sekadar berfoto dengan latar perkebunan. Selain menikmati keindahan kebun teh, pengunjung juga bisa belajar tentang proses produksi teh dan sejarah kebun teh yang telah berdiri sejak zaman kolonial Belanda.";
                break;

            case "Bukit SJ88":
                imageResource = R.drawable.img_sj88;
                location = "Wuluhan, Jember";
                rating = 4.8;
                deskripsi = "Bukit SJ88 adalah destinasi wisata di Kecamatan Sumberjambe yang menawarkan pemandangan alam yang indah dan suasana sejuk. Dari puncaknya, pengunjung bisa melihat hamparan pegunungan dan lembah, serta kebun kopi dan cengkeh yang tumbuh subur di sekitarnya. SJ88 juga memiliki spot foto yang menarik seperti gazebo, jembatan bambu, dan beberapa ornamen unik untuk selfie. Tempat ini cocok bagi para pengunjung yang ingin menikmati keindahan alam sambil bersantai.";
                break;

            case "Rembangan":
                imageResource = R.drawable.img_rembangan;
                location = "Arjasa, Jember";
                rating = 4.8;
                deskripsi = "Rembangan adalah kawasan wisata pegunungan yang berlokasi di Kecamatan Arjasa. Terkenal dengan pemandangan kota Jember dari ketinggian, Rembangan menawarkan suasana sejuk dan tenang, sehingga cocok untuk relaksasi. Di tempat ini terdapat penginapan, kolam renang, dan kebun buah yang bisa dinikmati oleh wisatawan. Rembangan juga memiliki area perkebunan kopi dan buah naga, yang menjadi daya tarik tambahan bagi pengunjung yang ingin mengenal lebih dekat produk perkebunan Jember.";
                break;

            case "Air Terjun Tancak":
                imageResource = R.drawable.img_airterjun_tancak;
                location = "Panti, Jember";
                rating = 4.8;
                deskripsi = "Air Terjun Tancak terletak di Desa Suci, Kecamatan Panti, dengan ketinggian sekitar 82 meter. Dikelilingi hutan tropis di kaki Gunung Argopuro, tempat ini menawarkan suasana segar dan alami, cocok bagi pecinta trekking dan alam. Untuk mencapainya, pengunjung harus menempuh perjalanan kaki sekitar 1–2 jam melewati jalur hutan yang menantang. Suara gemericik air dan udara yang sejuk menjadikan air terjun ini lokasi ideal untuk melepas penat dan menikmati keindahan alam.";
                break;

            case "Air Terjun Antrokan":
                imageResource = R.drawable.img_airterjun_antrokan;
                location = "Tangul, Jember";
                rating = 4.8;
                deskripsi = "Berada di Desa Manggisan, Kecamatan Tanggul, Air Terjun Antrokan memiliki ketinggian sekitar 30 meter dengan air yang jernih dan sejuk. Dikelilingi area perbukitan, sawah, dan perkebunan, tempat ini lebih mudah diakses dibanding Air Terjun Tancak, sehingga cocok untuk wisata keluarga. Pengunjung dapat menikmati suasana tenang sambil bermain air atau berfoto dengan latar pemandangan alam yang asri dan indah.";
                break;

            default:
                name = "Detail Tidak Ditemukan";
                location = "Tidak Tersedia";
                rating = 0;
                deskripsi = "Deskripsi untuk destinasi ini belum tersedia.";
                break;
        }

        // Set data ke View
        textViewName.setText(name);
        imageView.setImageResource(imageResource);
        textViewLocation.setText(location);
        textViewRating.setText("Rating: " + rating);
        textViewDeskripsi.setText(deskripsi);
    }
}
