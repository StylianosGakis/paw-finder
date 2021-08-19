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
package com.stylianosgakis.androiddevchallengeweek1.ui

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.navArgument
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.stylianosgakis.androiddevchallengeweek1.ui.animaldetails.DetailsScreen
import com.stylianosgakis.androiddevchallengeweek1.ui.animals.AnimalsScreen

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AppScreen() {
    val navController = rememberAnimatedNavController()
    val actions = remember(navController) { NavigationActions(navController) }

    AnimatedNavHost(
        navController = navController,
        startDestination = Screen.AnimalsScreen.route,
    ) {
        addAnimalsScreen(actions)
        addAnimalDetailScreen()
    }
}

@OptIn(ExperimentalAnimationApi::class)
private fun NavGraphBuilder.addAnimalsScreen(
    actions: NavigationActions,
) {
    val animationDuration = 300
    composable(
        route = Screen.AnimalsScreen.route,
        enterTransition = { initial, _ ->
            when (initial.destination.route) {
                Screen.DetailsScreen.route -> {
                    slideInHorizontally(
                        initialOffsetX = { -300 },
                        animationSpec = tween(animationDuration),
                    ) + fadeIn(
                        animationSpec = tween(animationDuration)
                    )
                }
                else -> null
            }
        },
        exitTransition = { _, target ->
            when (target.destination.route) {
                Screen.AnimalsScreen.route -> {
                    slideOutHorizontally(
                        targetOffsetX = { -300 },
                        animationSpec = tween(animationDuration),
                    ) + fadeOut(
                        animationSpec = tween(animationDuration)
                    )
                }
                else -> null
            }
        }
    ) {
        AnimalsScreen(actions)
    }
}

@OptIn(ExperimentalAnimationApi::class)
private fun NavGraphBuilder.addAnimalDetailScreen() {
    val animationDuration = 300
    composable(
        route = Screen.DetailsScreen.route,
        arguments = listOf(
            navArgument("id") {
                type = NavType.IntType
            }
        ),
        enterTransition = { initial, _ ->
            when (initial.destination.route) {
                Screen.AnimalsScreen.route -> {
                    slideInHorizontally(
                        initialOffsetX = { 300 },
                        animationSpec = tween(animationDuration)
                    ) + fadeIn(
                        animationSpec = tween(animationDuration)
                    )
                }
                else -> null
            }
        },
        exitTransition = { _, target ->
            when (target.destination.route) {
                Screen.AnimalsScreen.route -> {
                    slideOutHorizontally(
                        targetOffsetX = { 300 },
                        animationSpec = tween(animationDuration)
                    ) + fadeOut(
                        animationSpec = tween(animationDuration)
                    )
                }
                else -> null
            }
        }
    ) {
        DetailsScreen()
    }
}
