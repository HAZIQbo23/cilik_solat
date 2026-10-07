package com.example.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

private val LightColorScheme = lightColorScheme(
    primary = SkyBluePrimary,
    onPrimary = Color.White,
    primaryContainer = SkyBlueLight,
    onPrimaryContainer = SkyBlueDark,
    secondary = GoldPrimary,
    onSecondary = Color.White,
    secondaryContainer = GoldLight,
    onSecondaryContainer = GoldDark,
    tertiary = EmeraldPrimary,
    onTertiary = Color.White,
    tertiaryContainer = EmeraldLight,
    onTertiaryContainer = EmeraldDark,
    background = CloudBackground,
    onBackground = TextDark,
    surface = SurfacePure,
    onSurface = TextDark,
    surfaceVariant = Color(0xFFF1F5F9),
    onSurfaceVariant = TextMuted,
    error = KidPink,
    onError = Color.White
)

private val DarkColorScheme = lightColorScheme(
    primary = SkyBluePrimary,
    onPrimary = Color.White,
    secondary = GoldPrimary,
    onSecondary = Color.White,
    tertiary = EmeraldPrimary,
    onTertiary = Color.White,
    background = Color(0xFF0F172A),
    onBackground = Color.White,
    surface = Color(0xFF1E293B),
    onSurface = Color.White
)

val KidShapes = Shapes(
    small = RoundedCornerShape(12.dp),
    medium = RoundedCornerShape(20.dp),
    large = RoundedCornerShape(28.dp),
    extraLarge = RoundedCornerShape(36.dp)
)

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false, // Keep consistent cheerful branding for kids
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        shapes = KidShapes,
        content = content
    )
}
