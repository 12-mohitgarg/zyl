package com.example.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme =
  darkColorScheme(
    primary = EmeraldGreen,
    secondary = MintAccent,
    tertiary = LightGreenSecondary,
    background = RichBlack,
    surface = RichBlack,
    onPrimary = CustomWhite,
    onSecondary = RichBlack,
    onBackground = CustomWhite,
    onSurface = CustomWhite
  )

private val LightColorScheme =
  lightColorScheme(
    primary = DarkGreenPrimary,
    secondary = EmeraldGreen,
    tertiary = MintAccent,
    background = CustomWhite,
    surface = CustomWhite,
    onPrimary = CustomWhite,
    onSecondary = CustomWhite,
    onBackground = RichBlack,
    onSurface = RichBlack
  )

@Composable
fun MyApplicationTheme(
  darkTheme: Boolean = false,
  // Dynamic color is of course false by default because user needs a specific green and white design
  dynamicColor: Boolean = false,
  content: @Composable () -> Unit,
) {
  val colorScheme =
    when {
      dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
        val context = LocalContext.current
        if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
      }

      darkTheme -> DarkColorScheme
      else -> LightColorScheme
    }

  MaterialTheme(colorScheme = colorScheme, typography = Typography, content = content)
}
