package com.miftah.sehaty.ui.screens.onboarding

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.datastore.dataStore
import com.miftah.sehaty.R
import com.miftah.sehaty.ui.screens.navGraph.Route
import com.miftah.sehaty.ui.screens.onboarding.components.OnBoardingPage
import com.miftah.sehaty.ui.screens.onboarding.components.PagerIndicator
import com.miftah.sehaty.ui.theme.SehatyTheme
import com.miftah.sehaty.ui.theme.White
import com.miftah.sehaty.ui.theme.dimens
import com.miftah.sehaty.utils.UiState
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnBoardingScreen(
    modifier: Modifier = Modifier,
    state: OnBoardingState,
    onEvent: (OnBoardingEvent) -> Unit
) {
    Box(
        modifier = modifier
            .background(color = White)
            .padding(top = 16.dp)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            val pagerState = rememberPagerState(initialPage = 0) {
                pages.size
            }
            val buttonsState = remember {
                derivedStateOf {
                    when (pagerState.currentPage) {
                        0 -> listOf("", "Selanjutnya")
                        1 -> listOf("Kembali", "Selanjutnya")
                        2 -> listOf("Kembali", "Mulai")
                        else -> listOf("", "")
                    }
                }
            }
            HorizontalPager(state = pagerState) { index ->
                OnBoardingPage(page = pages[index], currentPage = pagerState.currentPage)

            }

            Spacer(modifier = Modifier.weight(1f))

            Row(

                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = White)
                    .padding(horizontal = MaterialTheme.dimens.small2)
                    .navigationBarsPadding(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {


                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    val scope = rememberCoroutineScope()
                    //Hide the button when the first element of the list is empty

                    if (pagerState.currentPage == 0) {
                        Spacer(modifier = Modifier.weight(1f))
                        if (buttonsState.value[1].isNotEmpty()) {
                            OnBoardingButton(
                                text = buttonsState.value[1],
                                onClick = {
                                    scope.launch {
                                        pagerState.animateScrollToPage(page = pagerState.currentPage + 1)
                                    }
                                }
                            )
                        }
                    } else {
                        if (buttonsState.value[0].isNotEmpty()) {
                            OnBoardingTextButton(
                                text = buttonsState.value[0],
                                onClick = {
                                    scope.launch {
                                        pagerState.animateScrollToPage(page = pagerState.currentPage - 1)
                                    }
                                }
                            )
                        }

                        Spacer(modifier = Modifier.weight(1f))

                        if (buttonsState.value[1].isNotEmpty()) {
                            OnBoardingButton(
                                text = buttonsState.value[1],
                                onClick = {
                                    scope.launch {
                                        if (pagerState.currentPage == 2) {
                                            onEvent(OnBoardingEvent.GenerateJWT)
                                        } else {
                                            pagerState.animateScrollToPage(page = pagerState.currentPage + 1)
                                        }
                                    }
                                }
                            )
                        }
                    }

                }
            }
            Spacer(modifier = Modifier.weight(0.5f))
        }
        LinearProgressIndicator(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter),
            color = MaterialTheme.colorScheme.onSecondaryContainer,
            trackColor = MaterialTheme.colorScheme.secondaryContainer,
        )
        state.generateJWT?.collectAsState(initial = null)?.value.let { generateResponse ->
            when (generateResponse) {
                is UiState.Error -> {
                    AlertDialog(onDismissRequest = { }, confirmButton = { }, title = {
                        Text(text = "Err")
                    }, text = {
                        Text(text = generateResponse.error)
                    })
                }

                UiState.Loading -> {
                    LinearProgressIndicator(
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.TopCenter),
                        color = MaterialTheme.colorScheme.onSecondaryContainer,
                        trackColor = MaterialTheme.colorScheme.secondaryContainer,
                    )
                }

                is UiState.Success -> {
                    onEvent(OnBoardingEvent.SaveAppJWT(generateResponse.data))
                }

                null -> {}
            }
        }
    }
}

@Composable
fun OnBoardingButton(
    text: String,
    onClick: () -> Unit,
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = Color.White
        ),
        shape = RoundedCornerShape(size = 6.dp)
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.SemiBold , fontSize = 14.sp),
            color = Color.White
        )
    }
}

@Composable
fun OnBoardingTextButton(
    text: String,
    onClick: () -> Unit,
) {
    TextButton(onClick = onClick) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Medium , fontSize = 14.sp),
            color = Color.Gray
        )
    }
}

@Preview
@Composable
private fun ButtonPreview() {
    SehatyTheme {
        OnBoardingScreen(
            modifier = Modifier,
            state = OnBoardingState(),
            onEvent = {

            }
        )
    }
}