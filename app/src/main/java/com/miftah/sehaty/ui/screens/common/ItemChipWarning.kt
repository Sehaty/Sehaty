package com.miftah.sehaty.ui.screens.common

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.SuggestionChipDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.miftah.sehaty.ui.theme.Red30
import com.miftah.sehaty.ui.theme.RedChipSurface
import com.miftah.sehaty.ui.theme.RedChipText
import com.miftah.sehaty.ui.theme.SehatyTheme

@Composable
fun ItemChipWarning(
    modifier: Modifier = Modifier,
    itemChip: ChipAndWarning
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = itemChip.containerColor,
            contentColor = itemChip.titleColor,

            ),
        shape = RoundedCornerShape(50.dp),
    ) {
        Box(
            modifier = Modifier
                .padding(horizontal = 6.dp, vertical = 4.dp)
        ) {
            Text(
                text = itemChip.title,
                color = itemChip.titleColor,
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,

                )
        }


    }

}

data class ChipAndWarning(
    val title: String,
    val containerColor: Color,
    val titleColor: Color
)

@Preview(showBackground = true)
@Composable
private fun ItemWarningChipPreview() {
    SehatyTheme {
        ItemChipWarning(
            itemChip = ChipAndWarning(
                title = "Rendah Gula",
                containerColor = RedChipSurface,
                titleColor = RedChipText
            )
        )
    }
}