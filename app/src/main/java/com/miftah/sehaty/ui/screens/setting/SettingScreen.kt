package com.miftah.sehaty.ui.screens.setting

import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.annotation.DrawableRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.miftah.sehaty.R
import com.miftah.sehaty.ui.screens.setting.components.SettingItem
import com.miftah.sehaty.ui.theme.SehatyTheme
import com.miftah.sehaty.ui.theme.dimens
import com.miftah.sehaty.utils.UiState

@Composable
fun SettingScreen(
    modifier: Modifier = Modifier,
    state: SettingsState,
    onEvent: (SettingsEvent) -> Unit
) {
    var loadingResult by remember {
        mutableStateOf(false)
    }

    val context = LocalContext.current
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 16.dp)
    ) {
        LazyColumn(
            modifier = Modifier
        ) {
            /*item {
                Text(
                    modifier = Modifier.padding(top = MaterialTheme.dimens.medium1),
                    text = "Settings",
                    style = MaterialTheme.typography.titleLarge
                )
                Spacer(modifier = Modifier.padding(bottom = MaterialTheme.dimens.medium1))
            }*/
            items(state.itemSettings) {
                state.getSavedJWT?.collectAsState(initial = null)?.value.let { jwt ->
                    if (jwt != null) {
                        state.checkSessionWA?.collectAsState(initial = null)?.value.let { session ->
                            when (session) {
                                is UiState.Error -> {
                                    loadingResult = true
                                }

                                UiState.Loading -> {
                                    loadingResult = false
                                }

                                is UiState.Success -> {
                                    loadingResult = true
                                    Log.d("TAG", "SettingScreen: $jwt")
                                    onEvent(SettingsEvent.ActivatedAccount(session.data))
                                }

                                null -> {
                                    loadingResult = false
                                    onEvent(SettingsEvent.CheckWaSession(jwt))
                                }
                            }
                        }
                        state.isActive?.collectAsState(initial = null)?.value.let { active ->
                            when (active) {
                                true -> {
                                    SettingItem(
                                        modifier = Modifier,
                                        titleSetting = it.title,
                                        description = state.phoneNumber,
                                        drawable = it.drawable,
                                        isActive = true
                                    )
                                }

                                false -> {
                                    SettingItem(
                                        modifier = Modifier.clickable(onClick = {
                                            val message = "start_session $jwt"
                                            context.startActivity(
                                                Intent(
                                                    Intent.ACTION_VIEW,
                                                    Uri.parse("https://api.whatsapp.com/send?phone=+6285861447367&text=$message")
                                                )
                                            )
                                        }),
                                        titleSetting = it.title,
                                        description = it.description,
                                        drawable = it.drawable,
                                        isActive = false
                                    )
                                }

                                null -> {}
                            }
                        }

                    }

                }
            }
        }
        if (!loadingResult) {
            CircularProgressIndicator(
                modifier = Modifier
                    .width(64.dp)
                    .align(Alignment.Center),
                color = MaterialTheme.colorScheme.secondary,
                trackColor = MaterialTheme.colorScheme.surfaceVariant,
            )
        }
    }

}

data class SettingData(
    val title: String,
    val description: String,
    @DrawableRes val drawable: Int
)

@Preview(showBackground = true)
@Composable
private fun SettingScreenPreview() {
    SehatyTheme {
        SettingScreen(
            state = SettingsState(),
            onEvent = {

            }
        )
    }
}