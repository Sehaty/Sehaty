package com.miftah.sehaty.ui.screens.onboarding.components

import android.graphics.Color
import androidx.annotation.RawRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.miftah.sehaty.R
import com.miftah.sehaty.ui.screens.onboarding.Page
import com.miftah.sehaty.ui.screens.onboarding.pages
import com.miftah.sehaty.ui.theme.GreyText
import com.miftah.sehaty.ui.theme.SehatyTheme
import com.miftah.sehaty.ui.theme.White
import com.miftah.sehaty.ui.theme.White70
import com.miftah.sehaty.ui.theme.dimens

@Composable
fun LottieIllustration(
    @RawRes rawRes: Int = R.raw.eating_healthy
) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(rawRes))
    val progress by animateLottieCompositionAsState(composition)

    LottieAnimation(
        composition,
        progress,
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.50f)
            .padding(top = 16.dp)

    )
}

@Composable
fun OnBoardingPage(
    modifier: Modifier = Modifier,
    page: Page,
    currentPage: Int
) {
    Surface(
        color = White
    ) {
        Column(
            modifier = modifier,
            verticalArrangement = Arrangement.Center,

            ) {

//        Image(
//            modifier = Modifier
//                .fillMaxWidth()
//                .fillMaxHeight(0.40f),
//            painter = painterResource(id = page.image),
//            contentDescription = null,
//            contentScale = ContentScale.Crop
//        )

            Box() {

                LottieIllustration(
                    rawRes = page.image
                )
                PagerProgressBar(
                    pagesSize = pages.size,
                    selectedPage = currentPage,
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.TopCenter)
                        .padding(top = 16.dp)
                        .zIndex(1f)
                )
            }

            Spacer(modifier = Modifier.height(MaterialTheme.dimens.medium3))
            Text(
                text = page.title,
                modifier = Modifier.padding(horizontal = MaterialTheme.dimens.medium1),
                style = MaterialTheme.typography.displaySmall.copy(
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 20.sp
                )
            )
            Text(
                text = page.description,
                modifier = Modifier.padding(horizontal = MaterialTheme.dimens.medium1),
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.Normal,
                    fontSize = 14.sp,
                    color = GreyText.copy(alpha = 0.7f),

                )
            )
        }
    }

}

@Preview(showBackground = true)
@Composable
fun OnBoardingPagePreview() {
    SehatyTheme {
        OnBoardingPage(
            page = Page(
                title = "Lorem Ipsum is simply dummy",
                description = "Lorem Ipsum is simply dummy text of the printing and typesetting industry.",
                image = R.raw.eating_healthy,
            ),
            currentPage = 0
        )
    }
}