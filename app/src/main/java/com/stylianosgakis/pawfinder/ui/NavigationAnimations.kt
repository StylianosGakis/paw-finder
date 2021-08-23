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
package com.stylianosgakis.pawfinder.ui

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally

enum class Direction {
    Right, Left
}

@OptIn(ExperimentalAnimationApi::class)
fun slideAndFadeIn(from: Direction, animationDuration: Int = 300): EnterTransition =
    slideInHorizontally(
        initialOffsetX = {
            when (from) {
                Direction.Right -> 300
                Direction.Left -> -300
            }
        },
        animationSpec = tween(animationDuration)
    ) + fadeIn(
        animationSpec = tween(animationDuration)
    )

@OptIn(ExperimentalAnimationApi::class)
fun slideAndFadeOut(to: Direction, animationDuration: Int = 300): ExitTransition =
    slideOutHorizontally(
        targetOffsetX = {
            when (to) {
                Direction.Right -> 300
                Direction.Left -> -300
            }
        },
        animationSpec = tween(animationDuration)
    ) + fadeOut(
        animationSpec = tween(animationDuration)
    )
