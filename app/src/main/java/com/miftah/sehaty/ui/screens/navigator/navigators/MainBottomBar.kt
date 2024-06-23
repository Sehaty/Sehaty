package com.miftah.sehaty.ui.screens.navigator.navigators

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Camera
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.miftah.sehaty.R
import com.miftah.sehaty.ui.theme.Grey30
import com.miftah.sehaty.ui.theme.Grey50
import com.miftah.sehaty.ui.theme.SehatyTheme
import com.miftah.sehaty.ui.theme.White30

@Composable
fun MainBottomBar(
    modifier: Modifier = Modifier,
    items: List<BottomNavigationItem>,
    onSelectedChange: ((Int) -> Unit),
    selectedItem: Int,
) {
    BottomAppBar(
        modifier = modifier,
        containerColor = Color.White
    ) {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                colors = NavigationBarItemColors(
                    unselectedIconColor = Grey50,
                    unselectedTextColor = Grey50,
                    selectedIconColor = MaterialTheme.colorScheme.primary,
                    selectedTextColor = MaterialTheme.colorScheme.primary,
                    selectedIndicatorColor = Color.Transparent,
                    disabledIconColor = Grey50,
                    disabledTextColor = Grey50
                ),
                icon = {
                    item.icon?.let {
                        Icon(
                            painterResource(id =  it ),
                            contentDescription = null,
                            modifier = modifier.size(24.dp)

                        )
                    }
                },
                label = {
                    if (item.icon != null) {
                        Text(
                            item.label,
                            fontSize = 14.sp,
                            fontWeight = if (selectedItem == index) FontWeight.SemiBold else FontWeight.Normal

                        )
                    }

                },
                selected = selectedItem == index,
                onClick = {
                    if (item.icon != null) {
                        onSelectedChange(index)
                    }
                }
            )
        }
    }
}

data class BottomNavigationItem(
    val icon: Int?,
    val label: String
)

@Preview()
@Composable
private fun MainBottomBarPrev() {
    val items = listOf(
        BottomNavigationItem(R.drawable.home_icon, "Home"),
        BottomNavigationItem(null, ""),
        BottomNavigationItem(R.drawable.profile_icon, "Favorite"),
    )
    var selectedItem by remember { mutableIntStateOf(0) }
    SehatyTheme {
        Scaffold(
            modifier = Modifier.background(Color.Gray),
            bottomBar = {
                Surface(
                    modifier = Modifier.wrapContentSize(),
                    shadowElevation = 30.dp
                ) {
                    Box {
                        MainBottomBar(
                            modifier = Modifier.align(Alignment.BottomCenter),
                            items = items,
                            onSelectedChange = {
                                selectedItem = it
                            },
                            selectedItem = selectedItem
                        )
                        IconButton(
                            modifier = Modifier
                                .padding(bottom = 35.dp)
                                .clip(CircleShape)
                                .background(MaterialTheme.colorScheme.primary)
                                .align(Alignment.TopCenter)
                                .padding(10.dp),
                            onClick = { /*TODO*/ },
                        ) {
                            Icon(
                                modifier = Modifier.size(25.dp),
                                painter = painterResource(id = R.drawable.scan_icon),
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onPrimary
                            )
                        }
                    }
                }
            }
        ) {
            Surface(
                modifier = Modifier.padding(it)
            ) {

            }
        }
    }
}