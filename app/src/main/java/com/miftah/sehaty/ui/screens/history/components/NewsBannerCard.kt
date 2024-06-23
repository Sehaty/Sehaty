package com.miftah.sehaty.ui.screens.history.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.miftah.sehaty.ui.screens.common.ChipAndWarning

@Composable
fun NewsBannerCard(
    title: String,
    photo: String,
    mediaName: String,
    mediaLogo: String,
    publishDate: String,
    modifier: Modifier = Modifier,

    ) {
    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = modifier
            .width(300.dp)
            .height(180.dp)

    ) {
        Box(modifier = Modifier.fillMaxSize()) {



            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current).data(photo).crossfade(true).build(),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .align(Alignment.BottomCenter)
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(Color.Transparent, Color.Black),
                            startY = 0f,
                            endY = 300f
                        )
                    )
            )
            Column(
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Bottom,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.Bottom,

                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                    ){
                        Text(
                            text = title,
                            style = MaterialTheme.typography.labelLarge.copy(
                                fontWeight = FontWeight.Normal,
                                fontSize = 18.sp,
                                color = Color.White
                            ),
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )

                        Row(
                            horizontalArrangement = Arrangement.Start,

                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Box(
                                modifier = Modifier
                                    .width(20.dp)
                                    .height(20.dp)
                                    .clip(RoundedCornerShape(20.dp))
                            ) {
                                AsyncImage(
                                    model = ImageRequest.Builder(LocalContext.current).data(mediaLogo)
                                        .crossfade(true)
                                        .build(),
                                    contentDescription = null, contentScale = ContentScale.Crop,
                                    modifier = Modifier.fillMaxSize(),
                                )


                            }


                            Text(
                                text = mediaName,
                                style = MaterialTheme.typography.labelMedium.copy(
                                    fontWeight = FontWeight.Normal,
                                    fontSize = 13.sp,
                                    color = Color.White
                                ),
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                modifier = Modifier.padding(start = 8.dp)
                            )
                            Text(
                                text = " - $publishDate",
                                style = MaterialTheme.typography.labelMedium.copy(
                                    fontWeight = FontWeight.Normal,
                                    fontSize = 13.sp,
                                    color = Color.White
                                ),
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    }
                }
            }
        }
    }


}


@Composable
// PREVIEW
@Preview
fun NewsBannerCardPreview() {
    NewsBannerCard(
        title = "The 10 Best Foods for Your Heart",
        photo = "https://www.verywellfit.com/thmb/1Z6",
        mediaName = "Verywell Fit",
        mediaLogo = "https://www.verywellfit.com/thmb/1Z6",
        publishDate = "2022-02-02",
    )
}
