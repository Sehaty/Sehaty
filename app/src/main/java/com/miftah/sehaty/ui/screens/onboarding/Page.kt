package com.miftah.sehaty.ui.screens.onboarding

import androidx.annotation.RawRes
import com.miftah.sehaty.R

data class Page(
    val title: String,
    val description: String,
    @RawRes val image: Int,
)

val pages = listOf(
    Page(
        title = "Temukan Nilai Gizi Secara Instan",
        description = "Pindai Makanan Anda dan Dapatkan Skor Nutrisi Akurat dalam Hitungan Detik. Kita menggunakan perhitungan Nutriscore yang sudah diterapkan di Eropa",
        image = R.raw.onboarding_1
    ),
    Page(
        title = "Integrasi WhatsApp yang Mulus",
        description = "Upload Photo Nutrisi Langsung di WhatsApp Anda. Kami akan memberikan Skor Nutrisi dan Saran Makanan Sehat dalam Hitungan Detik.",
        image = R.raw.onboarding_2
    ),
    Page(
        title = "Sinkronisasi Data Otomatis",
        description = "Setiap kali Anda mengunggah foto di WhatsApp, riwayatnya akan otomatis masuk ke aplikasi ini, dan sebaliknya. Semua data Anda tersimpan dengan aman dan mudah diakses.",
        image = R.raw.onboarding_3
    ),
)