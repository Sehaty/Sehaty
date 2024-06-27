package com.miftah.sehaty.ui.screens.detail.components
import androidx.annotation.RawRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.google.accompanist.pager.*
import com.miftah.sehaty.R
import kotlinx.coroutines.launch

@Composable
fun LottieIllustration(
    @RawRes rawRes: Int = R.raw.step_2_tutorial
) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(rawRes))
    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = LottieConstants.IterateForever
    )
    LottieAnimation(
        composition,
        progress,
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.50f)


    )
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun TutorialPopup(onDismiss: () -> Unit) {
    val pagerState = rememberPagerState()
    val scope = rememberCoroutineScope()

    Dialog(onDismissRequest = { onDismiss() }) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = Color.White,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                      // Padding top to make room for the close button
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top
                ) {
                    HorizontalPager(
                        count = 3,
                        state = pagerState
                    ) { page ->

                        TutorialPage(page)
                    }
                    HorizontalPagerIndicator(
                        pagerState = pagerState,
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(16.dp)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        if (pagerState.currentPage > 0) {
                            TextButton(onClick = { scope.launch { pagerState.animateScrollToPage(pagerState.currentPage - 1) } }) {
                                Text("Kembali")
                            }
                        }

                        if (pagerState.currentPage < pagerState.pageCount - 1) {
                            TextButton(onClick = { scope.launch { pagerState.animateScrollToPage(pagerState.currentPage + 1) } }) {
                                Text("Lanjut")
                            }
                        }

                        if (pagerState.currentPage == pagerState.pageCount - 1) {
                            TextButton(onClick = onDismiss) {
                                Text( "Coba Sekarang")
                            }
                        }
                    }
                }

//                IconButton(
//                    onClick = onDismiss,
//                    modifier = Modifier
//                        .align(Alignment.TopEnd)
//                        .padding(8.dp)
//                ) {
//                    Icon(Icons.Filled.Close, contentDescription = "Close")
//                }
            }
        }
    }
}

@Composable
fun TutorialPage(page: Int) {
    val tips = listOf(
        "Pilih product yang ingin di cek  dan pastikan  focus photo kandungan nutrisinya agar hasil lebih maximal.",
        "Setelah itu, kami rekomendafsikan untuk memasukan nama product untuk memudahkan anda di riwayat pemindaian.",
        "Tunggu sebentar, hasil akan muncul dalam beberapa detik."
    )
    Column(

    ){
        LottieIllustration(
            rawRes = when(page){
                0 -> R.raw.step_1_tutorial
                1 -> R.raw.step_2_tutorial
                else -> R.raw.testing
            }
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(32.dp)
        ) {
            Text(
                text = tips[page],
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center,
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }

}