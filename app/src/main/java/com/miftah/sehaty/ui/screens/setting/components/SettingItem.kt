package com.miftah.sehaty.ui.screens.setting.components

import android.graphics.drawable.Icon
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowCircleRight
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.miftah.sehaty.R
import com.miftah.sehaty.ui.theme.SehatyTheme
import com.miftah.sehaty.ui.theme.White

@Composable
fun SettingItem(
    modifier: Modifier = Modifier,
    titleSetting: String,
    description: String,
    @DrawableRes drawable: Int,
    isActive: Boolean
) {
    ElevatedCard(
        colors = CardDefaults.elevatedCardColors(
            containerColor = White
        ),
        modifier = modifier.fillMaxWidth(),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                modifier = Modifier.size(70.dp).padding(8.dp),
                painter = painterResource(id = drawable),
                contentDescription = null
            )
            Column(
                modifier = Modifier.weight(2f)
            ) {
                Text(
                    modifier = Modifier.padding(bottom = 4.dp),
                    text = titleSetting,
                    style = MaterialTheme.typography.labelLarge
                )
                Text(
                    text = description,
                    style = MaterialTheme.typography.labelMedium.copy(
                        fontWeight = FontWeight.Light
                    )
                )
            }
            if (isActive) {
                Icon(
                    modifier = Modifier.padding(end = 8.dp),
                    imageVector = Icons.Default.CheckCircle,
                    contentDescription = null
                )
            } else {
                Icon(
                    modifier = Modifier.padding(end = 8.dp),
                    imageVector = Icons.Default.ArrowCircleRight,
                    contentDescription = null
                )
            }

        }
    }
}

@Preview
@Composable
private fun SettingItemPreview() {
    SehatyTheme {
        SettingItem(
            titleSetting = "WhatsApp",
            description = "081902849834",
            drawable = R.drawable.whatsapp_ic,
            isActive = false
        )
    }
}