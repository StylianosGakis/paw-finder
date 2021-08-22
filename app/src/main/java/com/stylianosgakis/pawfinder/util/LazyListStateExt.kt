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
package com.stylianosgakis.pawfinder.util

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState

private class ScrollInfo(
    var index: Int = 0,
    var scrollOffset: Int = 0,
)

@Composable
fun LazyListState.isScrollingForwards(): Boolean {
    val oldScrollInfo = remember(this) { ScrollInfo() }
    val scrollOffset by rememberUpdatedState(firstVisibleItemScrollOffset)
    val index by rememberUpdatedState(firstVisibleItemIndex)
    val isScrollingForwards by remember(this) {
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
    return isScrollingForwards
}

@Composable
fun LazyListState.isLastItemVisible(): Boolean {
    val isLastItemVisible by remember {
        derivedStateOf {
            layoutInfo.visibleItemsInfo.lastOrNull()?.index == layoutInfo.totalItemsCount - 1
        }
    }
    return isLastItemVisible
}