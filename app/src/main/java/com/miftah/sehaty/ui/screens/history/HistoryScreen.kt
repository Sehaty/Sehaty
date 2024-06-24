package com.miftah.sehaty.ui.screens.history

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.miftah.sehaty.core.data.local.entity.HistoryScannedEntity
import com.miftah.sehaty.domain.model.HistoryScanned
import com.miftah.sehaty.ui.screens.common.ChipAndWarning
import com.miftah.sehaty.ui.screens.history.components.HistoryCard
import com.miftah.sehaty.ui.theme.SehatyTheme
import com.miftah.sehaty.utils.AppUtility.fromStringToList
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.miftah.sehaty.domain.model.convertToHistoryScanned
import com.miftah.sehaty.ui.screens.common.setSimpleScore
import com.miftah.sehaty.ui.screens.history.components.HistoriesCard
import com.miftah.sehaty.ui.screens.history.components.NewsBannerCard
import com.miftah.sehaty.ui.theme.GreyText
import com.miftah.sehaty.ui.theme.RedChipSurface
import com.miftah.sehaty.ui.theme.RedChipText
import eu.bambooapps.material3.pullrefresh.PullRefreshIndicator
import eu.bambooapps.material3.pullrefresh.pullRefresh
import eu.bambooapps.material3.pullrefresh.rememberPullRefreshState

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun HistoryScreen(
    modifier: Modifier = Modifier,
    state: SearchState,
    event: (HistoryEvent) -> Unit,
    navigateToDetail: (HistoryScanned) -> Unit
) {
    val refreshScope = rememberCoroutineScope()

    val isRefreshing by remember {
        mutableStateOf(false)
    }

    val historyItemsEntity = state.scanHistory?.collectAsLazyPagingItems()

    val refreshing = rememberPullRefreshState(
        refreshing = isRefreshing,
        onRefresh = {
            historyItemsEntity?.refresh()
        }
    )
    val dummyNewsList = listOf(
        NewsData(
            "Gula dalam Botol Biang Kerok Diabetes pada Anak Muda",
            "https://tegar.s3.ap-southeast-2.amazonaws.com/banner_1.webp",
            "Liputan 6",
            "https://tegar.s3.ap-southeast-2.amazonaws.com/logo-liputan-6.jpeg",
            "21 Mar 2024"
        ),
        NewsData(
            "Dampak Negatif Konsumsi Gula Berlebih pada Remaja\n",
            "https://tegar.s3.ap-southeast-2.amazonaws.com/Dampak-Negatif-Konsumsi-Gula-Berlebih-pada-Remaja.jpg",
            "Kemendikbud",
            "https://tegar.s3.ap-southeast-2.amazonaws.com/logo_kemendikbud.png",
            "22 Jun 2024"
        ),
        NewsData(
            "Title 3",
            "https://via.placeholder.com/150",
            "Media 3",
            "https://via.placeholder.com/24",
            "2024-06-21"
        )
    )
    LaunchedEffect(state.searchQuery) {
        event(HistoryEvent.SearchNews)
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 16.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            LazyRow(
                modifier = modifier
                    .pullRefresh(refreshing),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                items(dummyNewsList) { news ->
                    NewsBannerCard(
                        title = news.title,
                        photo = news.photo,
                        mediaName = news.mediaName,
                        mediaLogo = news.mediaLogo,
                        publishDate = news.publishDate
                    )
                }
            }


            Text(
                text = "Riwayat Pemindaian",
                style = MaterialTheme.typography.labelLarge.copy(
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xff1B1919),
                    fontSize = 16.sp
                )
            )
            if (historyItemsEntity == null || historyItemsEntity.itemCount == 0) {
                Box(
                    modifier = modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = " Yuk mulai pemindaian produk pertama kamu sekarang!",
                        style = MaterialTheme.typography.labelLarge.copy(
                            color = GreyText,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Normal,
                            textAlign = TextAlign.Center
                        )
                    )
                }
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = modifier
                        .pullRefresh(refreshing)
                        .fillMaxSize(),

                    ) {

                    items(count = historyItemsEntity?.itemCount ?: 0) {
                        historyItemsEntity?.get(it)?.let { history ->
                            HistoriesCard(
                                modifier = Modifier
                                    .clickable {
                                        navigateToDetail(history.convertToHistoryScanned())
                                    },
                                urlImage = history.productPhoto,
                                productName = history.productName,
                                itemsChip = fromStringToList(history.warnings).map { text ->
                                    ChipAndWarning(text, RedChipSurface, RedChipText)
                                },
                                simpleScoreData = setSimpleScore(history.grade)
                            )
                        }
                    }
                }
            }
        }



        PullRefreshIndicator(
            refreshing = isRefreshing,
            state = refreshing,
            modifier = Modifier
                .align(Alignment.TopCenter)
        )
    }

    if (historyItemsEntity?.itemCount == 0 || historyItemsEntity == null || historyItemsEntity.loadState.hasError) {
        /*Box(
            modifier = modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {
            LazyColumn(
                modifier = modifier
                    .pullRefresh(refreshing)
                    .fillMaxSize(),
                contentPadding = PaddingValues(vertical = 8.dp),
            ) {
                item {
                    SearchBarSehatySecondary(
                        modifier = Modifier
                            .padding(
                                bottom = MaterialTheme.dimens.small2
                            )
                            .fillMaxWidth(),
                        query = state.searchQuery
                    ) {
                        event(HistoryEvent.SearchNews)
                    }
                }
            }

            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (historyItemsEntity?.loadState?.hasError == true) {
                    Text(text = "Something wrong like your life")
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "Pull to refresh")
                } else {
                    Text(text = "Its empty like your life")
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "Pull to refresh")
                }

            }

            PullRefreshIndicator(
                refreshing = isRefreshing,
                state = refreshing,
                modifier = Modifier
                    .align(Alignment.TopCenter)
            )
        }*/
    } else {
        /*Box(
            modifier = modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {
            LazyColumn(
                modifier = modifier
                    .pullRefresh(refreshing)
                    .fillMaxSize(),
                contentPadding = PaddingValues(vertical = 8.dp),
            ) {
                stickyHeader {
                    SearchBarSehatySecondary(
                        modifier = Modifier
                            .padding(
                                bottom = MaterialTheme.dimens.small2
                            )
                            .fillMaxWidth(),
                        query = state.searchQuery
                    ) {
                        event(HistoryEvent.SearchNews)
                    }
                }
                items(count = historyItemsEntity.itemCount) {
                    historyItemsEntity[it].let { history ->
                        history?.let {
                            HistoriesCard(
                                modifier = Modifier
                                    .padding(bottom = 8.dp)
                                    .clickable {
                                        navigateToDetail(history.convertToHistoryScanned())
                                    },
                                urlImage = history.productPhoto,
                                productName = history.productName,
                                itemsChip = fromStringToList(history.warnings).map { text ->
                                    ChipAndWarning(text, Color.Red, Color.White)
                                },
                                simpleScoreData = setSimpleScore(history.grade)
                            )
                        }
                    }
                }
            }

            PullRefreshIndicator(
                refreshing = isRefreshing,
                state = refreshing,
                modifier = Modifier
                    .align(Alignment.TopCenter)
            )
        }*/
    }


}

data class NewsData(
    val title: String,
    val photo: String,
    val mediaName: String,
    val mediaLogo: String,
    val publishDate: String
)

@Composable
fun SearchHistoryItemsSection(
    modifier: Modifier = Modifier,
    historyItemsEntity: LazyPagingItems<HistoryScannedEntity>?,
    onClick: (Int) -> Unit
) {
    if (historyItemsEntity?.itemCount == 0 || historyItemsEntity == null) {
        HistoryEmptyScreen(modifier = modifier)
    } else {
        LazyColumn(
            modifier = modifier.fillMaxSize(),
            contentPadding = PaddingValues(vertical = 8.dp),
        ) {
            items(count = historyItemsEntity.itemCount) {
                historyItemsEntity[it].let { history ->
                    if (history != null) {
                        HistoryCard(
                            modifier = Modifier
                                .clickable {
                                    onClick(history.id)
                                },
                            urlImage = history.productPhoto,
                            itemName = history.productName,
                            itemsChip = fromStringToList(history.warnings).map { text ->
                                ChipAndWarning(text, Color.Red, Color.White)
                            }
                        )
                    }
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun HistoryScreenPreview() {
    val query by remember { mutableStateOf("") }
    SehatyTheme {
        HistoryScreen(
            state = SearchState(),
            event = {

            },
            navigateToDetail = {

            }
        )
    }
}