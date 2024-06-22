package com.miftah.sehaty.ui.screens.common

import android.graphics.drawable.shapes.Shape
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.miftah.sehaty.ui.theme.RedDark50
import com.miftah.sehaty.ui.theme.SehatyTheme

@Composable
fun GradeByChar(modifier: Modifier = Modifier) {
    Surface(
        modifier = Modifier.size(70.dp),
        shape = RoundedCornerShape(10.dp),
        color = Color.Red
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                modifier = Modifier,
                text = "A",
                color = Color.White,
                style = MaterialTheme.typography.labelLarge.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 48.sp
                )
            )
        }
    }
}

@Preview
@Composable
private fun GradeByCharPreview() {
    SehatyTheme {
        GradeByChar()
    }
}