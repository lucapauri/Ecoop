package it.polito.did.gameskeleton.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColors(
    primary = Grey3,
    primaryVariant = Grey2,
    secondary = Green
)

private val LightColorPalette = lightColors(
    primary = Grey,
    primaryVariant = Grey3,
    secondary = Green,


    background = com.example.greenapplication.ui.theme.Green,
    surface = com.example.greenapplication.ui.theme.Grey,
    /*onPrimary = Green,
    onSecondary = Color.Black,
    onBackground = com.example.greenapplication.ui.theme.Grey2,
    onSurface = Color.Black,
    */
)

@Composable
fun GameSkeletonTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}