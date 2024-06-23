package com.miftah.sehaty.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

//private val DarkColorScheme = darkColorScheme(
//    primary = Green70,
//    primaryContainer = White30,
//    secondary = Grey50,
//    secondaryContainer = White30,
//    surfaceContainer = Grey50,
//    surfaceContainerHigh = White70,
//    surfaceContainerLow = White30
//)
private val DarkColorScheme = darkColorScheme(
    primary = Green70,
    primaryContainer = White30,
    secondary = Grey50,
    secondaryContainer = White30,
    surfaceContainer = Grey50,
    surfaceContainerHigh = White70,
    surfaceContainerLow = White30,
    surface = GreyBackground
)
private val LightColorScheme = lightColorScheme(
    primary = Green70,
    primaryContainer = White30,
    secondary = Grey50,
    secondaryContainer = White30,
    surfaceContainer = Grey50,
    surfaceContainerHigh = White70,
    surfaceContainerLow = White30,
    surface = GreyBackground
)

/*
primary
om primary
secondary
on secondary
tertiary
on tertiary

container primary
container om primary
container secondary
container on secondary
container tertiary
container on tertiary

surface

surface container
surface container low
 */

/*
// Primary
val Blue70 = Color(0xFF001E71)
val Orange40 = Color(255, 159, 102)

// secondary
val Orange50 = Color(255, 95, 0)
val White50 = Color(255, 250, 230)

val White70 = Color(0xFFE6E1CF)
val White30 = Color(0xFFFFFCF0)

val Grey30 = Color(0xFF6D7075)
val Grey50 = Color(0xFF484C52)
val Grey70 = Color(0xFF323539)
 */

@Composable
fun SehatyTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }

    val typography = MediumTypography
    val appDimens = MediumDimens

    ProvideAppUtils(appDimens = appDimens) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = typography,
            content = content
        )
    }
}