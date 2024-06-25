package com.miftah.sehaty.ui.screens.detail.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.miftah.sehaty.ui.theme.Green70
import com.miftah.sehaty.ui.theme.Grey70
import com.miftah.sehaty.ui.theme.GreyBackground

@Composable
fun MealCountSelection(
    selectedMealCount: Int,
    onMealCountSelected: (Int) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    val mealCounts = (1..10).toList()

    Box(
        modifier = Modifier
            .padding(16.dp)
            .background(GreyBackground)
    ) {
        Text(
            text = "Jumlah Makan: $selectedMealCount",
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expanded = true }
                .padding(16.dp),
            style = MaterialTheme.typography.bodyMedium.copy(
                fontSize = 14.sp,

                )
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            mealCounts.forEach { count ->
                DropdownMenuItem(
                    text = {
                        Text(
                            text = count.toString(),
                            color = if (count == selectedMealCount) Color.White else Color.Black
                        )
                    },
                    modifier = Modifier.background(
                        if (count == selectedMealCount) MaterialTheme.colorScheme.primary else GreyBackground
                    ),
                    onClick = {
                        onMealCountSelected(count)
                        expanded = false
                    }
                )
            }
        }
    }
}
