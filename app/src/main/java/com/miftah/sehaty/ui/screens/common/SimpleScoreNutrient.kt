package com.miftah.sehaty.ui.screens.common

import android.graphics.drawable.Icon
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircleOutline
import androidx.compose.material.icons.filled.QuestionMark
import androidx.compose.material.icons.filled.WarningAmber
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.miftah.sehaty.ui.theme.GradeBgA
import com.miftah.sehaty.ui.theme.GradeBgB
import com.miftah.sehaty.ui.theme.GradeBgC
import com.miftah.sehaty.ui.theme.GradeBgD
import com.miftah.sehaty.ui.theme.GradeBgE
import com.miftah.sehaty.ui.theme.GradeTxtA
import com.miftah.sehaty.ui.theme.GradeTxtB
import com.miftah.sehaty.ui.theme.GradeTxtC
import com.miftah.sehaty.ui.theme.GradeTxtD
import com.miftah.sehaty.ui.theme.GradeTxtE
import com.miftah.sehaty.ui.theme.GreenLight50
import com.miftah.sehaty.ui.theme.Red30
import com.miftah.sehaty.ui.theme.SehatyTheme
import com.miftah.sehaty.ui.theme.Yellow30

@Composable
fun SimpleScoreNutrient(
    modifier: Modifier = Modifier,
    simpleScoreData: SimpleScoreData
) {
//    Row(
//        modifier = modifier.background(Color.Transparent),
//        verticalAlignment = Alignment.CenterVertically,
//        horizontalArrangement = Arrangement.Center
//    ) {
//        Icon(
//            imageVector = simpleScoreData.icon,
//            contentDescription = null,
//            tint = simpleScoreData.color
//        )
//        Spacer(modifier = Modifier.width(4.dp))
//        Text(
//            text = simpleScoreData.desc,
//            style = MaterialTheme.typography.labelMedium.copy(
//                fontWeight = FontWeight.Light,
//                color = simpleScoreData.color
//            )
//        )
//        Spacer(modifier = Modifier.width(4.dp))
//    }
}

data class SimpleScoreData(
    val grade: String,
    val textColor: Color,
    val bgColor: Color
)

fun setSimpleScore(result: String): SimpleScoreData {
    return when (result) {
        "A" -> SimpleScoreData(
            grade = "A",
            textColor = GradeTxtA,
            bgColor = GradeBgA
        )

        "B" -> SimpleScoreData(
            grade = "B",
            textColor = GradeBgB,
            bgColor = GradeTxtB

        )

        "C" -> SimpleScoreData(
            grade = "C",
            textColor = GradeTxtC,
            bgColor = GradeBgC
        )

        "D" -> SimpleScoreData(
            grade = "D",
            textColor = GradeTxtD,
            bgColor = GradeBgD
        )

        "E" -> SimpleScoreData(
            grade = "E",
            textColor = GradeTxtE,
            bgColor = GradeBgE
        )

        else -> SimpleScoreData(
            grade = "?",
            textColor = GradeTxtE,
            bgColor = GradeBgE
        )
    }
}

@Preview()
@Composable
private fun SimpleScoreNutrientPreview() {
    SehatyTheme {
        SimpleScoreNutrient(
            simpleScoreData = setSimpleScore("E")
        )
    }
}