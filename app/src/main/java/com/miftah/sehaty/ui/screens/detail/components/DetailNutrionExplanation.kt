package com.miftah.sehaty.ui.screens.detail.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.miftah.sehaty.ui.screens.common.GradeByChar
import com.miftah.sehaty.ui.theme.GradeBgA
import com.miftah.sehaty.ui.theme.GradeBgB
import com.miftah.sehaty.ui.theme.GradeBgC
import com.miftah.sehaty.ui.theme.GradeBgD
import com.miftah.sehaty.ui.theme.GradeBgE
import com.miftah.sehaty.ui.theme.GradeTxtA
import com.miftah.sehaty.ui.theme.GradeTxtB
import com.miftah.sehaty.ui.theme.GradeTxtC
import com.miftah.sehaty.ui.theme.GradeTxtD
import com.miftah.sehaty.ui.theme.GradeTxtE

@Composable
fun MinimalDialog(onDismissRequest: () -> Unit) {
    Dialog(onDismissRequest = { onDismissRequest() }) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.9f),
            colors = CardDefaults.cardColors(
                containerColor = Color.White,
            ),
            shape = RoundedCornerShape(16.dp),
        ) {
            Column(
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Top,
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Penjelasan Nutriscore",
                        style = MaterialTheme.typography.labelMedium,
                        modifier = Modifier
                            .padding(16.dp)
                            .weight(1f)
                    )
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close",
                        modifier = Modifier
                            .padding(16.dp)
                            .clickable { onDismissRequest() }
                    )
                }

                HorizontalDivider()
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    GradeByChar(
                        grade = "A",
                        backgroundColor =  GradeBgA,
                        textColor = GradeTxtA
                    )
                    GradeByChar(
                        grade = "B",
                        backgroundColor =  GradeBgB,
                        textColor = GradeTxtB
                    )
                    GradeByChar(
                        grade = "C",
                        backgroundColor =  GradeBgC,
                        textColor = GradeTxtC
                    )
                    GradeByChar(
                        grade = "D",
                        backgroundColor =  GradeBgD,
                        textColor = GradeTxtD
                    )
                    GradeByChar(
                        grade = "E",
                        backgroundColor =  GradeBgE,
                        textColor = GradeTxtE
                    )

                }

                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    item{
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp)
                        ) {
                            Text(
                                text = "Apa itu Nutriscore?",
                                style = MaterialTheme.typography.labelMedium
                            )
                            Text(
                                text =  "Nutriscore adalah sistem penilaian yang digunakan untuk menilai kualitas nutrisi dari suatu produk makanan. Nutriscore memberikan nilai berdasarkan kandungan nutrisi yang terdapat pada produk makanan. Perhitungan ini sudah digunakan di europa dan beberapa negara asean ",
                                style = MaterialTheme.typography.bodyMedium,
                                modifier = Modifier.padding(top = 4.dp)
                            )
                        }
                    }
                    item {
                        NutrientGrade(
                            grade = "A",
                            description =  "Sangat sehat, pilihan terbaik untuk dikonsumsi. Produk ini kaya akan nutrisi yang bermanfaat dan rendah kalori, gula, garam, dan lemak jenuh"
                        )
                    }
                    item {
                        NutrientGrade(
                            grade = "B",
                            description = "Sehat, pilihan yang baik untuk dikonsumsi. Produk ini memiliki nutrisi yang baik dan rendah kalori, gula, garam, dan lemak jenuh"
                        )
                    }
                    item {
                        NutrientGrade(
                            grade = "C",
                            description = "Cukup sehat, pilihan yang cukup baik untuk dikonsumsi. Produk ini memiliki nutrisi yang cukup dan sedang dalam kalori, gula, garam, dan lemak jenuh"
                        )
                    }
                    item {
                        NutrientGrade(
                            grade = "D",
                            description = "Kurang sehat, pilihan yang kurang baik untuk dikonsumsi. Produk ini memiliki nutrisi yang kurang dan tinggi dalam kalori, gula, garam, dan lemak jenuh"
                        )
                    }
                    item {
                        NutrientGrade(
                            grade = "E",
                            description = "Tidak sehat, pilihan yang buruk untuk dikonsumsi. Produk ini memiliki nutrisi yang buruk dan sangat tinggi dalam kalori, gula, garam, dan lemak jenuh"
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun NutrientGrade(grade: String, description: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Text(
            text = "Grade $grade",
            style = MaterialTheme.typography.labelMedium
        )
        Text(
            text = description,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}