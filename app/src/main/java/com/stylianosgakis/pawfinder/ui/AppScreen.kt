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

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.navArgument
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.stylianosgakis.pawfinder.ui.animaldetails.DetailsScreen
import com.stylianosgakis.pawfinder.ui.animals.AnimalsScreen

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
    composable(
        route = Screen.AnimalsScreen.route,
        enterTransition = { initial, _ ->
            when (initial.destination.route) {
                Screen.DetailsScreen.route -> {
                    slideAndFadeIn(from = Direction.Left)
                }
                else -> null
            }
        },
        exitTransition = { _, target ->
            when (target.destination.route) {
                Screen.DetailsScreen.route -> {
                    slideAndFadeOut(to = Direction.Left)
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
                    slideAndFadeIn(from = Direction.Right)
                }
                else -> null
            }
        },
        exitTransition = { _, target ->
            when (target.destination.route) {
                Screen.AnimalsScreen.route -> {
                    slideAndFadeOut(to = Direction.Right)
                }
                else -> null
            }
        }
    ) {
        DetailsScreen()
    }
}
