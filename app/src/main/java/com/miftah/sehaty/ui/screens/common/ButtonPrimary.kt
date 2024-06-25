package com.miftah.sehaty.ui.screens.common

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.miftah.sehaty.ui.theme.SehatyTheme

@Composable
fun ButtonPrimary(
    modifier: Modifier = Modifier,
    enabled : Boolean = true,
    style: TextStyle = MaterialTheme.typography.labelLarge.copy(
        color = MaterialTheme.colorScheme.onPrimary,
    ),
    containerColor: Color = MaterialTheme.colorScheme.primary,
    title: String,
    onAction: () -> Unit
) {
    Button(
        enabled = enabled,
        modifier = modifier,
        onClick = onAction,
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors().copy(
            containerColor = containerColor
        )
    ) {
        Text(
            text = title,
            style = style
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ButtonPrimaryPreview() {
    SehatyTheme {
        ButtonPrimary(
            modifier = Modifier.fillMaxWidth().height(60.dp),
            enabled = true,
            title = "testing",
            containerColor = MaterialTheme.colorScheme.secondary,
            style = MaterialTheme.typography.labelLarge.copy(
                color = MaterialTheme.colorScheme.onSecondary,
            ),
        ) {

        }
    }
}