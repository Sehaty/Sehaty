package com.miftah.sehaty.ui.screens.common

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.SuggestionChipDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.miftah.sehaty.ui.theme.Red30
import com.miftah.sehaty.ui.theme.SehatyTheme

@Composable
fun ItemChipWarning(
    modifier: Modifier = Modifier,
    itemChip: ChipAndWarning
) {
    AssistChip(
        shape = RoundedCornerShape(70),
        modifier = modifier,
        enabled = false,
        onClick = {},
        label = {
            Text(
                modifier = Modifier.padding(4.dp),
                text = itemChip.title,
                style = MaterialTheme.typography.labelSmall.copy(
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    fontSize = 12.sp
                )
            )
        },
        border = BorderStroke(
            3.dp,
            itemChip.containerColor
        ),
        colors = AssistChipDefaults.assistChipColors().copy(
            disabledLabelColor = itemChip.containerColor,
            disabledContainerColor = itemChip.containerColor
        )
    )
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
                title = "Gula",
                containerColor = Red30,
                titleColor = Color.White
            )
        )
    }
}