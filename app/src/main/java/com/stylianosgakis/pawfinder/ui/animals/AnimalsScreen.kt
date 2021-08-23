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
package com.stylianosgakis.pawfinder.ui.animals

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.insets.navigationBarsPadding
import com.stylianosgakis.pawfinder.api.animal.model.Animal
import com.stylianosgakis.pawfinder.components.AnimalCard
import com.stylianosgakis.pawfinder.components.LoadingScreen
import com.stylianosgakis.pawfinder.data.AnimalType
import com.stylianosgakis.pawfinder.theme.AppTheme
import com.stylianosgakis.pawfinder.theme.bottomSheetShape
import com.stylianosgakis.pawfinder.ui.FindMyPetAppBar
import com.stylianosgakis.pawfinder.ui.NavigationActions
import com.stylianosgakis.pawfinder.util.isLastItemVisible
import com.stylianosgakis.pawfinder.util.isScrollingForwards
import com.stylianosgakis.pawfinder.util.previewAnimal
import kotlinx.coroutines.launch

@Composable
fun AnimalsScreen(actions: NavigationActions) {
    val viewModel: AnimalsScreenViewModel = hiltViewModel()

    val selectedAnimalType by viewModel.animalType.collectAsState()
    val animalList by viewModel.animalListStateFlow.collectAsState()

    AnimalsScreen(
        animalList = animalList,
        selectedAnimalType = selectedAnimalType,
        setSelectedAnimalType = { animalType ->
            viewModel.setAnimalType(animalType)
        },
        goToDetailsScreen = { animalId: Long ->
            actions.goToDetailsScreen(animalId)
        },
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun AnimalsScreen(
    animalList: List<Animal>,
    selectedAnimalType: AnimalType,
    setSelectedAnimalType: (AnimalType) -> Unit,
    goToDetailsScreen: (id: Long) -> Unit,
) {
    val composableCoroutineScope = rememberCoroutineScope()

    val lazyListState: LazyListState = rememberLazyListState()
    val isScrollingForwards = lazyListState.isScrollingForwards()
    val isLastItemVisible = lazyListState.isLastItemVisible()

    Scaffold(
        topBar = { FindMyPetAppBar() },
        scaffoldState = rememberScaffoldState(),
    ) { paddingValues ->
        val modalSheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
        ModalBottomSheetLayout(
            sheetState = modalSheetState,
            sheetContent = {
                AnimalTypePickerSheet(
                    selectedAnimalType = selectedAnimalType,
                    setSelectedAnimalType = { animalType ->
                        setSelectedAnimalType(animalType)
                        composableCoroutineScope.launch {
                            modalSheetState.hide()
                            lazyListState.animateScrollToItem(0)
                        }
                    },
                )
            },
            sheetShape = bottomSheetShape(),
            sheetBackgroundColor = MaterialTheme.colors.primary,
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
        ) {
            Box(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
            ) {
                var fabHeightPx by remember { mutableStateOf(0) }
                val fabHeightDp = with(LocalDensity.current) {
                    fabHeightPx.toDp()
                }

                if (animalList.isEmpty()) {
                    LoadingScreen()
                }

                LazyColumn(
                    state = lazyListState,
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    contentPadding = PaddingValues(top = 16.dp, bottom = fabHeightDp),
                    modifier = Modifier.padding(horizontal = 16.dp)
                ) {
                    itemsIndexed(
                        items = animalList,
                        key = { _, animal -> animal.id }
                    ) { index: Int, animal: Animal ->
                        AnimalCard(
                            cardIndex = index,
                            animal = animal,
                            goToDetailsScreen = { goToDetailsScreen(animal.id) },
                        )
                    }
                }

                val shouldFilterButtonShow by derivedStateOf {
                    !modalSheetState.isVisible &&
                        (animalList.isEmpty() || isScrollingForwards || isLastItemVisible)
                }
                FilterAnimalTypeButton(
                    onClick = {
                        composableCoroutineScope.launch {
                            modalSheetState.show()
                        }
                    },
                    visible = shouldFilterButtonShow,
                    modifier = Modifier
                        .onSizeChanged { intSize ->
                            fabHeightPx = intSize.height
                        }
                        .align(Alignment.BottomCenter)
                        .navigationBarsPadding(),
                )
            }
        }
    }
}

@Preview
@Composable
fun AnimalsScreenPreview() {
    val animal = previewAnimal
    AppTheme {
        AnimalsScreen(
            animalList = List(6) {
                return@List animal.copy(id = animal.id + it)
            },
            selectedAnimalType = AnimalType.valueOf(animal.type),
            setSelectedAnimalType = {},
            goToDetailsScreen = {}
        )
    }
}
