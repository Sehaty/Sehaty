package com.miftah.sehaty.ui.screens.onboarding.components
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.geometry.CornerRadius

@Composable
fun PagerProgressBar(
    modifier: Modifier = Modifier,
    pagesSize: Int,
    selectedPage: Int,
    selectedColor: Color = MaterialTheme.colorScheme.primary,
    unselectedColor: Color = MaterialTheme.colorScheme.onPrimary,
) {
    val progress = if (selectedPage == 0) 0.25f else selectedPage / (pagesSize - 1).toFloat()

    Canvas(modifier = modifier.height(8.dp).fillMaxWidth()) {
        val canvasWidth = size.width
        val canvasHeight = size.height

        // Draw unselected progress
        drawRoundRect(
            color = unselectedColor,
            topLeft = Offset(0f, 0f),
            size = Size(canvasWidth, canvasHeight),
            cornerRadius = CornerRadius(4.dp.toPx(), 4.dp.toPx())
        )

        // Draw selected progress
        drawRoundRect(
            color = selectedColor,
            topLeft = Offset(0f, 0f),
            size = Size(canvasWidth * progress, canvasHeight),
            cornerRadius = CornerRadius(4.dp.toPx(), 4.dp.toPx())
        )
    }
}
