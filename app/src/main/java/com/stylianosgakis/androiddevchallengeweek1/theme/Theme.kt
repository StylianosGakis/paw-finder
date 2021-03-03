package com.stylianosgakis.androiddevchallengeweek1.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.ZeroCornerSize
import androidx.compose.material.FloatingActionButtonDefaults
import androidx.compose.material.FloatingActionButtonElevation
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp

private val DarkColorPalette = darkColors(
    primary = PeachNormal,
    primaryVariant = PeachLight,
    secondary = MaroonNormal,
    secondaryVariant = MaroonLight,
)

private val LightColorPalette = lightColors(
    primary = PeachNormal,
    primaryVariant = PeachDark,
    secondary = MaroonLight,
    secondaryVariant = MaroonNormal,
    background = PeachLight
)

@Composable
fun AppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = FindMyPetTypography,
        shapes = Shapes,
        content = content
    )
}

@Composable
fun bottomSheetShape(): Shape {
    val shapes = MaterialTheme.shapes
    return remember {
        shapes.large.copy(bottomStart = ZeroCornerSize, bottomEnd = ZeroCornerSize)
    }
}

@Composable
fun floatingActionButtonDefaultElevation(): FloatingActionButtonElevation =
    FloatingActionButtonDefaults.elevation(
        defaultElevation = 2.dp,
        pressedElevation = 6.dp,
    )

val animalCardBackgroundColor: Color
    @Composable get() = MaterialTheme.colors.secondary.copy(alpha = 0.07f)

val navigationBarColor: Color
    @Composable get() = MaterialTheme.colors.primary

val navigationBarAndroidColor: android.graphics.Color
    @Composable get() = android.graphics.Color.valueOf(
        navigationBarColor.red,
        navigationBarColor.green,
        navigationBarColor.blue,
        navigationBarColor.alpha
    )

val statusBarColor: Color
    @Composable get() = MaterialTheme.colors.primary

val statusBarAndroidColor: android.graphics.Color
    @Composable get() = android.graphics.Color.valueOf(
        statusBarColor.red,
        statusBarColor.green,
        statusBarColor.blue,
        statusBarColor.alpha
    )