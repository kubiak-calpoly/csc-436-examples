package dev.csse.kubiak.demoweek3.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
  primary = OrangeBrown ,
  secondary = DarkBrown,
  tertiary = GreyBrown,
  onPrimary = LightOrange,
  onSecondary = Orange,
  onTertiary =  DarkBrown,
  surface = DarkBrown,
  onSurface = Linen,
  background = DarkBrown,
  onBackground =  Linen
)

private val LightColorScheme = lightColorScheme(
  primary = Orange,
  secondary = LightOrange,
  tertiary = Mustard,
  onPrimary = DarkBrown,
  onSecondary = GreyBrown,
  onTertiary = DarkBrown,
  surface = Linen,
  onSurface =  GreyBrown,
  background =  Linen,
  onBackground = DarkBrown

  /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)

@Composable
fun DemoWeek3Theme(
  darkTheme: Boolean = isSystemInDarkTheme(),
  // Dynamic color is available on Android 12+
  dynamicColor: Boolean = false,
  content: @Composable () -> Unit
) {
  val colorScheme = when {
    darkTheme -> DarkColorScheme
    else -> LightColorScheme
  }

  MaterialTheme(
    colorScheme = colorScheme,
    typography = Typography,
    content = content
  )
}