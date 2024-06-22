package com.miftah.sehaty.ui.screens.history.components

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.miftah.sehaty.ui.screens.common.ChipAndWarning
import com.miftah.sehaty.ui.screens.common.GradeByChar
import com.miftah.sehaty.ui.screens.common.ItemChipWarning
import com.miftah.sehaty.ui.screens.common.SimpleScoreData
import com.miftah.sehaty.ui.screens.common.SimpleScoreNutrient
import com.miftah.sehaty.ui.screens.common.setSimpleScore
import com.miftah.sehaty.ui.theme.SehatyTheme

@Composable
fun HistoriesCard(
    modifier: Modifier = Modifier,
    urlImage: String,
    itemsChip: List<ChipAndWarning>,
    simpleScoreData: SimpleScoreData,
    productName: String
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Max),
        shape = RoundedCornerShape(16.dp),
        shadowElevation = 2.dp
    ) {
        Row(
            modifier = modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                modifier = Modifier
                    .size(70.dp)
                    .clip(RoundedCornerShape(10.dp)),
                model = urlImage,
                contentDescription = null,
                contentScale = ContentScale.Crop,
            )
            Column(
                modifier = Modifier
                    .padding(horizontal = 10.dp)
                    .weight(2f)
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        modifier = Modifier.weight(1f),
                        text = productName,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1,
                        style = MaterialTheme.typography.labelMedium.copy(
                            fontSize = 18.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState())
                        .padding(top = 8.dp)
                ) {
                    if (itemsChip.isNotEmpty()) {
                        itemsChip.forEach {
                            ItemChipWarning(
                                modifier = Modifier.padding(end = 8.dp),
                                itemChip = it
                            )
                        }
                    }
                }
            }
            GradeByChar(
                modifier = Modifier
            )
        }
    }
}

@Preview()
@Composable
private fun HistoriesCardPreview() {
    SehatyTheme {
        Surface(
            modifier = Modifier
        ) {
            HistoriesCard(
                modifier = Modifier.fillMaxWidth(),
                urlImage = "https://tabris.com/wp-content/uploads/2021/06/jetpack-compose-icon_RGB.png",
                itemsChip = listOf(
                    ChipAndWarning(
                        title = "Gula",
                        containerColor = Color.Red,
                        titleColor = Color.White,
                    ),
                ),
                simpleScoreData = setSimpleScore("A"),
                productName = "Yoga Ganteng"
            )
        }
    }
}