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
package com.stylianosgakis.pawfinder.components

import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

private operator fun Color.inc(): Color {
    fun Float.increaseColorByLittle(): Float =
        (this + 0.02f).coerceAtMost(1f)

    return when {
        red != 1f -> this.copy(red = red.increaseColorByLittle())
        green != 1f -> this.copy(green = green.increaseColorByLittle())
        blue != 1f -> this.copy(blue = blue.increaseColorByLittle())
        else -> Color(red = 0f, green = 0f, blue = 0f)
    }
}

fun Modifier.indicateRecompositions(width: Dp = 20.dp): Modifier = composed {
    var color by remember { mutableStateOf(Color(red = 255, green = 0, blue = 0, alpha = 128)) }

    SideEffect {
        color++
    }

    this.then(
        Modifier
            .drawWithContent {
                drawContent()
                val topLeftOffset = Offset(width.toPx() / 2, width.toPx() / 2)
                drawRect(
                    color = color,
                    style = Stroke(width = width.toPx()),
                    topLeft = topLeftOffset,
                    // Size adjustments to make it be *inside* the composable instead of stroking along the edges
                    size = Size(
                        width = size.width - (topLeftOffset.x * 2),
                        height = size.height - (topLeftOffset.y * 2),
                    )
                )
            }
    )
}
