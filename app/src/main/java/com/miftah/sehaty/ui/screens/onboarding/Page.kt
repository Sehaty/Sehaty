package com.miftah.sehaty.ui.screens.onboarding

import androidx.annotation.DrawableRes
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
        image = R.raw.product_hunt
    ),
    Page(
        title = "Integrasi WhatsApp yang Mulus",
        description = "Upload Photo Nutrisi Langsung di WhatsApp Anda. Kami akan memberikan Skor Nutrisi dan Saran Makanan Sehat dalam Hitungan Detik.",
        image = R.raw.chat_entrance
    ),
    Page(
        title = "Mari Mulai Hidup Sehat Anda",
        description = "Terdapat riset yang menunjukkan bahwa banyak umur 20-35 yang sudah harus cuci darah karena mengkomsumsi makanan yang tinggi gula",
        image = R.raw.eating_healthy
    ),
)