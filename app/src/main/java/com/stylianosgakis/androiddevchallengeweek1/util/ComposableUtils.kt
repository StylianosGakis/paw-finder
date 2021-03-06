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
package com.stylianosgakis.androiddevchallengeweek1.util

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material.BottomSheetState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import dev.chrisbanes.accompanist.insets.LocalWindowInsets

private class ScrollInfo(
    var index: Int = 0,
    var scrollOffset: Int = 0,
)

@Composable
fun LazyListState.isScrollingForwardsAsState(): State<Boolean> {
    val oldScrollInfo = remember(this) { ScrollInfo() }
    val scrollOffset by rememberUpdatedState(firstVisibleItemScrollOffset)
    val index by rememberUpdatedState(firstVisibleItemIndex)
    return remember(this) {
        derivedStateOf {
            val isForward = when {
                oldScrollInfo.index != index -> oldScrollInfo.index < index
                else -> oldScrollInfo.scrollOffset < scrollOffset
            }
            oldScrollInfo.scrollOffset = scrollOffset
            oldScrollInfo.index = index
            return@derivedStateOf isForward
        }
    }
}

@Composable
fun systemNavigationBarHeightDpAsState(): State<Dp> {
    val density = LocalDensity.current
    val insets = LocalWindowInsets.current.navigationBars
    return remember(density, insets) {
        derivedStateOf { with(density) { insets.bottom.toDp() } }
    }
}

@ExperimentalMaterialApi
@Composable
@Stable
fun BottomSheetState.swipeProgressAsState(totalDragDistance: Float): State<Float> {
    val bottomSheetOffset by this.offset
    return remember(this, totalDragDistance) {
        derivedStateOf {
            when {
                bottomSheetOffset.isNaN() || totalDragDistance <= 0 -> 0f
                else -> 1f - ((bottomSheetOffset) / totalDragDistance).coerceIn(0f, 1f)
            }
        }
    }
}

fun DrawScope.fillDrawScope(
    color: Color,
) {
    this.drawRect(
        color = color,
        size = Size(
            width = this.size.width,
            height = this.size.height,
        )
    )
}
