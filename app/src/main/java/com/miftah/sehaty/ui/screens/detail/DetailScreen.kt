package com.miftah.sehaty.ui.screens.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.miftah.sehaty.core.dummyFoodAfterScan
import com.miftah.sehaty.domain.model.FoodAfterScan
import com.miftah.sehaty.ui.screens.common.ButtonPrimary
import com.miftah.sehaty.ui.screens.common.ChipAndWarning
import com.miftah.sehaty.ui.screens.common.GradeNutrient
import com.miftah.sehaty.ui.screens.common.ItemChipWarning
import com.miftah.sehaty.ui.screens.common.SegmentedButtonItem
import com.miftah.sehaty.ui.screens.common.SegmentedButtons
import com.miftah.sehaty.ui.screens.common.SegmentedButtonsDefaults
import com.miftah.sehaty.ui.theme.BlueLight50
import com.miftah.sehaty.ui.theme.BlueMid50
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
import com.miftah.sehaty.ui.theme.Grey70
import com.miftah.sehaty.ui.theme.SehatyTheme
import com.miftah.sehaty.ui.theme.Green70
import com.miftah.sehaty.ui.theme.GreenChipSurface
import com.miftah.sehaty.ui.theme.GreenChipText
import com.miftah.sehaty.ui.theme.GreenLight50
import com.miftah.sehaty.ui.theme.GreyText
import com.miftah.sehaty.ui.theme.OrangeMid50
import com.miftah.sehaty.ui.theme.PurpleMid50
import com.miftah.sehaty.ui.theme.Red30
import com.miftah.sehaty.ui.theme.RedChipSurface
import com.miftah.sehaty.ui.theme.RedChipText
import com.miftah.sehaty.ui.theme.RedDark50
import com.miftah.sehaty.ui.theme.Yellow30
import com.miftah.sehaty.utils.UiState

@Composable
fun DetailScreen(
    modifier: Modifier = Modifier,
    state: DetailState,
    isFromHistory: Boolean,
    onEvent: (DetailEvent) -> Unit,
    backToHistory: () -> Unit
) {
    val configuration = LocalConfiguration.current

    val screenWidth = configuration.screenWidthDp.dp
    val screenHeight = configuration.screenHeightDp.dp

    var selectedItem by remember { mutableIntStateOf(0) }



    Scaffold(
        bottomBar = {
            if (isFromHistory) {
                ButtonPrimary(
                    modifier = modifier
                        .fillMaxWidth()
                        .height(60.dp)
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    title = "Simpan",
                    textColor = MaterialTheme.colorScheme.onPrimary,
                    containerColor = MaterialTheme.colorScheme.primary
                ) {
                    if (state.isActive) {
                        onEvent(DetailEvent.SaveToCloud)
                    } else {
                        onEvent(DetailEvent.SaveToLocal)
                    }
                }
            }
        }
    ) { innerPadding ->
        state.foodAfterScan?.let {
            Box(
                modifier = Modifier.padding(innerPadding)
            ) {
                Column(
                    modifier = Modifier.verticalScroll(rememberScrollState())
                ) {
                    ProductImage(
                        modifier = Modifier
                            .fillMaxWidth()
                            .size((screenHeight / 4)), // Membuat kotak persegi (2:1 aspect ratio)
                        urlImage = it.productPhoto,
                    )
                    NutrientDetailSection(
                        modifier = Modifier,
                        titleItem = it.productName ?: "",
                        piece = it.portionSize.toString(),
                        items = it.warnings.map { item ->
                            ChipAndWarning(
                                title = item,
                                containerColor = RedChipSurface,
                                titleColor = RedChipText
                            )
                        } + it.positiveFeedback.map { item ->
                            ChipAndWarning(
                                title = item,
                                containerColor = GreenChipSurface,
                                titleColor = GreenChipText
                            )
                        }
                    )
                    Card(
                        shape = RoundedCornerShape(8.dp),
                        colors =  CardDefaults.cardColors(
                            containerColor = Color.White,
                        ),
                        elevation =  CardDefaults.cardElevation(
                            defaultElevation = 1.dp,
                        ),
                        modifier = modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                    ) {
                        Column(
                            verticalArrangement = Arrangement.spacedBy(16.dp),
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(16.dp)


                        ) {
                            Text(
                                text = "Skor Nutrisi",
                                style = MaterialTheme.typography.labelLarge.copy(
                                    fontSize = 18.sp
                                )
                            )
                            NutrientsSummarySection(
                                modifier = Modifier,
                                scoreResult = it.grade
                            )

                        }
                    }
                   Card(
                          colors =  CardDefaults.cardColors(
                            containerColor = Color.Transparent,
                          ),

                          modifier = modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                   ) {
                       CustomSegmentedButtons(
                           selectedItem = selectedItem,
                           onItemSelected = { selectedItem = it },
                           portionSize = 50
                       )
                     }
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 16.dp)
                    ) {
//                        Text(
//                            modifier = Modifier.padding(vertical = 8.dp),
//                            text = "Detail Nutrisi",
//                            style = MaterialTheme.typography.labelLarge.copy(
//                                fontSize = 18.sp
//                            ),
//                        )

                        if (selectedItem == 0) {
                            state.dataNutrientPercentage?.let { it1 ->
                                NutritionProportion(
                                    modifier = Modifier.padding(top = 8.dp),
                                    foodAfterScan = it,
                                    nutrientPercentage = it1
                                )
                            }
                        } else {
                            Text("100g")
                        }

                    }
                    Spacer(modifier = Modifier.padding(50.dp))
                }
                IconButton(
                    modifier = Modifier
                        .padding(16.dp)
                        .align(Alignment.TopStart)
                        .clip(CircleShape)
                        .background(Color.Black.copy(alpha = 0.5f))
                        .size(32.dp),
                    onClick = backToHistory
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = null,
                        tint = Color.White
                    )
                }

                state.saveFoodAfterScan?.collectAsState(initial = null)?.value.let {
                    when (it) {
                        is UiState.Error -> {
                            AlertDialog(
                                dismissButton = {
                                    TextButton(
                                        onClick = { },
                                        modifier = Modifier.padding(8.dp),
                                    ) {
                                        Text("Dismiss")
                                    }
                                },
                                onDismissRequest = {

                                },
                                confirmButton = {
                                    TextButton(
                                        onClick = { },
                                        modifier = Modifier.padding(8.dp),
                                    ) {
                                        Text("Confirm")
                                    }
                                },
                                title = { Text(text = "Err") },
                                text = { Text(text = it.error) }
                            )
                        }

                        UiState.Loading -> {
                            CircularProgressIndicator(
                                modifier = Modifier
                                    .align(Alignment.Center)
                            )
                        }

                        is UiState.Success -> {
                            backToHistory()
                        }

                        null -> {}
                    }
                }
            }

        }

    }
}

data class NutrientPercentage(
//    val cholesterol: Float = 0f,
    val totalFat : Float = 0f,
    val totalCarbs: Float = 0f,
    val dietaryFiber: Float = 0f,
    val protein: Float = 0f,
    val sodium: Float = 0f,
    val sugars: Float = 0f
)

/*
@Composable
fun NutrientsSection(modifier: Modifier, foodAfterScan: FoodAfterScan) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        */
/*NutrientCard(
            modifier = Modifier.fillMaxWidth(),
            nutrientTitle = "Cholesterol",
            nutrientValue = if (foodAfterScan.cholesterol == null) "" else foodAfterScan.cholesterol.toString(),
            description = "Its good for you",
            containerColor = Color.Blue.copy(
                alpha = 0.9f
            ),
            iconVector = Icons.Default.Fastfood,
            titleColor = Color.Unspecified
        )
        NutrientCard(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            nutrientTitle = "Karbon",
            nutrientValue = foodAfterScan.totalCarbs.toString(),
            description = "Its good for you",
            containerColor = Color.Red.copy(
                alpha = 0.9f
            ),
            iconVector = Icons.Default.LightMode,
            titleColor = Color.Unspecified
        )
        NutrientCard(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            nutrientTitle = "Serat",
            nutrientValue = foodAfterScan.dietaryFiber.toString(),
            description = "Its good for you",
            containerColor = Color.Green.copy(
                alpha = 0.9f
            ),
            iconVector = Icons.Default.LightMode,
            titleColor = Color.Unspecified
        )
        NutrientCard(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            nutrientTitle = "Protein",
            nutrientValue = foodAfterScan.protein.toString(),
            description = "Its good for you",
            containerColor = Color.Yellow.copy(
                alpha = 0.9f
            ),
            iconVector = Icons.Default.LightMode,
            titleColor = Color.Unspecified
        )
        NutrientCard(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            nutrientTitle = "Gram",
            nutrientValue = foodAfterScan.sodium.toString(),
            description = "Its good for you",
            containerColor = Color.Magenta.copy(
                alpha = 0.9f
            ),
            iconVector = Icons.Default.LightMode,
            titleColor = Color.Unspecified
        )
        NutrientCard(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            nutrientTitle = "Gula",
            nutrientValue = foodAfterScan.sugars.toString(),
            description = "Its good for you",
            containerColor = Color.Cyan.copy(
                alpha = 0.9f
            ),
            iconVector = Icons.Default.LightMode,
            titleColor = Color.Unspecified
        )*//*
    }
}
*/

@Composable
fun ProductImage(modifier: Modifier = Modifier, urlImage: String) {
    Box(
        modifier = modifier
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(urlImage)
                .crossfade(true)
                .build(),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize() // Mengisi seluruh kotak
        )
    }
}

@Composable
fun NutrientDetailSection(
    modifier: Modifier = Modifier,
    titleItem: String,
    piece: String,
    items: List<ChipAndWarning>
) {
    Card(

        shape = RoundedCornerShape(8.dp),
        colors =  CardDefaults.cardColors(
            containerColor = Color.White,
        ),
        elevation =  CardDefaults.cardElevation(
            defaultElevation = 1.dp,
        ),
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)

    ){
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()


            ) {
                Text(
                    titleItem,
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                )
                Text(
                    text = piece + "g",

                    style = MaterialTheme.typography.labelLarge.copy(
                        color = Grey70,


                        fontWeight = FontWeight.Light
                    )
                )
            }

            LazyRow(
                modifier = Modifier.padding(end = 16.dp, top = 16.dp),
            ) {
                item {
                    if (items.isNotEmpty()) {
                        items.forEach {
                            ItemChipWarning(
                                modifier = Modifier.padding(end = 4.dp),
                                itemChip = it
                            )
                        }
                    }

                }
            }
        }
    }
}


@Composable
fun NutrientsSummarySection(
    modifier: Modifier = Modifier,
    scoreResult: String
) {
    val result = scoreDesc(scoreResult)
    Column(
        modifier = modifier
            .fillMaxWidth(),

    ) {

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                GradeNutrient(
                    modifier = Modifier
                        .padding(end = 8.dp),
                    fontSize = 50,
                    indicatorSize = 100,
                    percentage = result.progress,
                    strokeWidth = 8,
                    indicatorColor = result.color,
                    score = result.grade
                )
                Text(

                    textAlign = TextAlign.Start,
                    text = result.desc,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontSize = 13.sp,
                        color =  Color(0xff282727),
                        fontWeight = FontWeight.Normal
                    )
                )

        }
    }
}

/*
data class NutrientPercentage(
    val cholesterol: Float = 0f,
    val totalCarbs: Float = 0f,
    val dietaryFiber: Float = 0f,
    val protein: Float = 0f,
    val sodium: Float = 0f,
    val sugars: Float = 0f
)
 */

@Composable
fun NutritionProportion(
    modifier: Modifier = Modifier,
    nutrientPercentage: NutrientPercentage,
    foodAfterScan: FoodAfterScan
) {
    Surface(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier.zIndex(10f),
        color = Color.White,
        shadowElevation = 1.dp,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                ) {
                    Row {
                        Text(
                            modifier = Modifier.weight(1f),
                            text = "Lemak",
                            style = MaterialTheme.typography.labelMedium.copy(
                                fontWeight = FontWeight.SemiBold
                            )
                        )
                        Text(
                            modifier = Modifier,
                            text = (foodAfterScan.totalFat ).toString() + "gr"
                        )
                    }
                    LinearProgressIndicator(
                        modifier = Modifier
                            .fillMaxWidth()
                            .size(16.dp)
                            .padding(top = 8.dp),
                        progress = { nutrientPercentage.totalFat },
                        color = OrangeMid50,
                        strokeCap = StrokeCap.Round
                    )
                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                ) {
                    Row {
                        Text(
                            modifier = Modifier.weight(1f),
                            text = "Karbo",
                            style = MaterialTheme.typography.labelMedium.copy(
                                fontWeight = FontWeight.SemiBold
                            )
                        )
                        Text(
                            modifier = Modifier,
                            text = foodAfterScan.totalCarbs.toString() + "gr"
                        )
                    }
                    LinearProgressIndicator(
                        modifier = Modifier
                            .fillMaxWidth()
                            .size(16.dp)
                            .padding(top = 8.dp),
                        progress = { nutrientPercentage.totalCarbs },
                        color = RedDark50,
                        strokeCap = StrokeCap.Round
                    )
                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                ) {
                    Row {
                        Text(
                            modifier = Modifier.weight(1f),
                            text = "Serat",
                            style = MaterialTheme.typography.labelMedium.copy(
                                fontWeight = FontWeight.SemiBold
                            )
                        )
                        Text(
                            modifier = Modifier,
                            text = foodAfterScan.dietaryFiber.toString() + "gr"
                        )
                    }
                    LinearProgressIndicator(
                        modifier = Modifier
                            .fillMaxWidth()
                            .size(16.dp)
                            .padding(top = 8.dp),
                        progress = { nutrientPercentage.dietaryFiber },
                        color = GreenLight50,
                        strokeCap = StrokeCap.Round
                    )
                }
            }
            Spacer(modifier = Modifier.padding(6.dp))
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                ) {
                    Row {
                        Text(
                            modifier = Modifier.weight(1f),
                            text = "Protein",
                            style = MaterialTheme.typography.labelMedium.copy(
                                fontWeight = FontWeight.SemiBold
                            )
                        )
                        Text(
                            modifier = Modifier,
                            text = foodAfterScan.protein.toString() + "gr"
                        )
                    }
                    LinearProgressIndicator(
                        modifier = Modifier
                            .fillMaxWidth()
                            .size(16.dp)
                            .padding(top = 8.dp),
                        progress = { nutrientPercentage.protein },
                        color = BlueMid50,
                        strokeCap = StrokeCap.Round
                    )
                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                ) {
                    Row {
                        Text(
                            modifier = Modifier.weight(1f),
                            text = "Garam",
                            style = MaterialTheme.typography.labelMedium.copy(
                                fontWeight = FontWeight.SemiBold
                            )
                        )
                        Text(
                            modifier = Modifier,
                            text = foodAfterScan.sodium.toString() + "gr"
                        )
                    }
                    LinearProgressIndicator(
                        modifier = Modifier
                            .fillMaxWidth()
                            .size(16.dp)
                            .padding(top = 8.dp),
                        progress = { nutrientPercentage.sodium },
                        color = BlueLight50,
                        strokeCap = StrokeCap.Round
                    )
                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                ) {
                    Row {
                        Text(
                            modifier = Modifier.weight(1f),
                            text = "Gula",
                            style = MaterialTheme.typography.labelMedium.copy(
                                fontWeight = FontWeight.SemiBold
                            )
                        )
                        Text(
                            modifier = Modifier,
                            text = foodAfterScan.sugars.toString() + "gr"
                        )
                    }
                    LinearProgressIndicator(
                        modifier = Modifier
                            .fillMaxWidth()
                            .size(16.dp)
                            .padding(top = 8.dp),
                        progress = { nutrientPercentage.sugars },
                        color = PurpleMid50,
                        strokeCap = StrokeCap.Round
                    )
                }
            }
        }
    }
}

fun scoreDesc(result: String): ScoreDescItem {
    return when (result) {
        "A" -> ScoreDescItem(
            desc = "Sangat sehat, pilihan terbaik untuk dikonsumsi. Produk ini kaya akan nutrisi yang bermanfaat dan rendah kalori, gula, garam, dan lemak jenuh.",
            color =  GradeTxtA,
            grade = "A",
            progress = 1f
        )

        "B" -> ScoreDescItem(
            desc = "Sehat, pilihan baik untuk dikonsumsi. Produk ini memiliki sedikit lebih banyak kalori, gula, garam, atau lemak jenuh tetapi masih merupakan pilihan yang sehat.",
            color = GradeTxtB,
            grade = "B",
            progress = 0.8f
        )

        "C" -> ScoreDescItem(
            desc = "Cukup sehat, boleh dikonsumsi secara moderat. Produk ini memiliki keseimbangan antara nutrisi yang bermanfaat dan yang kurang diinginkan.",
            color = GradeTxtC,
            grade = "C",
            progress = 0.6f
        )

        "D" -> ScoreDescItem(
            desc = "Kurang sehat, batasi konsumsi. Produk ini lebih tinggi kalori, gula, garam, atau lemak jenuh dan sebaiknya dikonsumsi secara terbatas.",
            color = GradeTxtD,
            grade = "D",
            progress = 0.4f
        )

        "E" -> ScoreDescItem(
            desc = "Tidak sehat, hindari konsumsi jika memungkinkan. Produk ini sangat tinggi kalori, gula, garam, atau lemak jenuh dan sebaiknya dikonsumsi sesedikit mungkin.",
            color = GradeTxtE,
            grade = "E",
            progress = 0.2f
        )

        else -> ScoreDescItem(
            desc = "Unknown",
            color = Color.Gray,
            grade = "?",
            progress = 0.0f
        )
    }
}

data class NutrientValue(
    val icon: Icons,
    val containerColor: Color,
    val titleColor: Color,
)

@Composable
fun CustomSegmentedButtons(
    selectedItem: Int,
    onItemSelected: (Int) -> Unit,
    portionSize: Int, // Assuming `portionSize` is provided
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .padding(vertical = 16.dp)
            .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(8.dp))
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        CustomSegmentedButtonItem(
            selected = selectedItem == 0,
            onClick = { onItemSelected(0) },
            label = "Nutrisi dalam $portionSize g"
        )
        CustomSegmentedButtonItem(
            selected = selectedItem == 1,
            onClick = { onItemSelected(1) },
            label = "Nutrisi dalam 100g"
        )
    }
}

@Composable
fun CustomSegmentedButtonItem(
    selected: Boolean,
    onClick: () -> Unit,
    label: String
) {
    Box(
        modifier = Modifier

            .clip(RoundedCornerShape(8.dp))
            .background(
                if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.background
            )
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 8.dp)

    ) {
        Text(
            text = label,
            color = if (selected) MaterialTheme.colorScheme.onPrimary else GreyText,
            style = MaterialTheme.typography.bodyMedium.copy(
                fontSize = 14.sp

            )
        )
    }
}

data class ScoreDescItem(
    val desc: String,
    val color: Color,
    val grade: String,
    val progress: Float
)

@Preview(showBackground = true)
@Composable
private fun DetailScreenPreview() {
    SehatyTheme {
        Scaffold(
            modifier = Modifier
                .fillMaxSize()
        ) { innerPadding ->
            DetailScreen(
                modifier = Modifier.padding(innerPadding),
                onEvent = {

                },
                state = DetailState(
                    foodAfterScan = dummyFoodAfterScan()
                ),
                isFromHistory = false
            ) {

            }
        }
    }
}