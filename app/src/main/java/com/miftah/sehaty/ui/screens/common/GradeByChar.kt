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
import com.miftah.sehaty.ui.theme.GradeBgA
import com.miftah.sehaty.ui.theme.GradeTxtA
import com.miftah.sehaty.ui.theme.RedDark50
import com.miftah.sehaty.ui.theme.SehatyTheme

@Composable
fun GradeByChar(
    grade: String,
    backgroundColor: Color,
    textColor: Color,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.size(57.dp),
        shape = RoundedCornerShape(10.dp),
        color = backgroundColor
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = grade,
                color = textColor,
                style = MaterialTheme.typography.labelLarge.copy(
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 36.sp
                )
            )
        }
    }
}


@Preview
@Composable
private fun GradeByCharPreview() {
    SehatyTheme {
        GradeByChar(
            grade = "A",
            backgroundColor = GradeBgA,
            textColor = GradeTxtA
        )
    }
}