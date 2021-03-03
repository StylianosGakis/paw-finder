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
