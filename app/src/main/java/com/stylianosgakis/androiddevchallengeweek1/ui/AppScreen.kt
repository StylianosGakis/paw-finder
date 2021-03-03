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

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigate
import androidx.navigation.compose.rememberNavController
import com.stylianosgakis.androiddevchallengeweek1.api.model.animal.Animal
import com.stylianosgakis.androiddevchallengeweek1.data.AnimalType
import com.stylianosgakis.androiddevchallengeweek1.ui.animaldetails.DetailsScreen
import com.stylianosgakis.androiddevchallengeweek1.ui.animals.AnimalsScreen
import com.stylianosgakis.androiddevchallengeweek1.ui.animals.AnimalsScreenViewModel
import com.stylianosgakis.androiddevchallengeweek1.util.viewModel

@ExperimentalMaterialApi
@Composable
fun AppScreen() {
    val navController = rememberNavController()
    val actions = remember(navController) { MainActions(navController) }

    NavHost(
        navController = navController,
        startDestination = Screen.AnimalsScreen.route
    ) {
        composable(Screen.AnimalsScreen.route) { navBackStackEntry ->
            val viewModel: AnimalsScreenViewModel = navBackStackEntry.viewModel()

            val scope = rememberCoroutineScope()
            val animalList: List<Animal> by viewModel.animalList.collectAsState(scope.coroutineContext)
            val selectedAnimalType: AnimalType by viewModel.animalType.collectAsState(scope.coroutineContext)

            AnimalsScreen(
                animalList = animalList,
                selectedAnimalType = selectedAnimalType,
                setSelectedAnimalType = { animalType ->
                    viewModel.setAnimalType(animalType)
                },
                goToDetailsScreen = { animal ->
                    actions.goToDetailsScreen(animal)
                },
            )
        }
        composable(
            route = Screen.DetailsScreen.route,
        ) {
            val animal: Animal = requireNotNull(
                navController.previousBackStackEntry?.arguments?.getParcelable(Animal::class.simpleName)
            )

            DetailsScreen(
                animal = animal,
                navigateBack = actions.upPress
            )
        }
    }
}

class MainActions(navController: NavHostController) {
    val goToDetailsScreen: (Animal) -> Unit = { animal ->
        // TODO fix temporary hack as passing Parcelable objects is not supported from Compose navigation
        navController
            .currentBackStackEntry
            ?.arguments
            ?.putParcelable(animal::class.simpleName, animal)
        navController.navigate(Screen.DetailsScreen.route)
    }

    val upPress: () -> Unit = {
        navController.navigateUp()
    }
}
