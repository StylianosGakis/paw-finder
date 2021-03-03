/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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

private val LightColorPalette = lightColors(
    primary = PeachNormal,
    primaryVariant = PeachDark,
    secondary = MaroonLight,
    secondaryVariant = MaroonNormal,
    background = PeachLight
)

private val DarkColorPalette = darkColors(
    primary = PeachNormal,
    primaryVariant = PeachLight,
    secondary = MaroonNormal,
    secondaryVariant = MaroonLight,
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
