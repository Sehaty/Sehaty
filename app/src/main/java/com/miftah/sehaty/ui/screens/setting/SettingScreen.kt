package com.miftah.sehaty.ui.screens.setting

import androidx.annotation.DrawableRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.miftah.sehaty.R
import com.miftah.sehaty.ui.screens.setting.components.SettingItem
import com.miftah.sehaty.ui.theme.SehatyTheme
import com.miftah.sehaty.ui.theme.dimens

@Composable
fun SettingScreen(
    modifier: Modifier = Modifier,
    itemSettings: List<SettingData>,
    clickable: () -> Unit,
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        item {
            Text(
                modifier = Modifier.padding(top = MaterialTheme.dimens.medium1),
                text = "Settings",
                style = MaterialTheme.typography.titleLarge
            )
            Spacer(modifier = Modifier.padding(bottom = MaterialTheme.dimens.medium1))
        }
        items(itemSettings) {
            SettingItem(
                modifier = Modifier.clickable(onClick = clickable),
                titleSetting = it.title,
                description = it.description,
                drawable = it.drawable
            )
            Text("Testing")
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
            itemSettings = listOf(
                SettingData(
                    title = "WhatsApp",
                    description = "Connect to WhatsApp",
                    drawable = R.drawable.whatsapp_icon
                )
            )
        ) {

        }
    }
}