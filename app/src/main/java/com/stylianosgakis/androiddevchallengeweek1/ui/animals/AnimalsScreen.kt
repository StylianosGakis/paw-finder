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
package com.stylianosgakis.androiddevchallengeweek1.ui.animals

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.BiasAlignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.insets.navigationBarsPadding
import com.stylianosgakis.androiddevchallengeweek1.api.model.animal.Animal
import com.stylianosgakis.androiddevchallengeweek1.components.AnimalCard
import com.stylianosgakis.androiddevchallengeweek1.data.AnimalType
import com.stylianosgakis.androiddevchallengeweek1.theme.AppTheme
import com.stylianosgakis.androiddevchallengeweek1.theme.bottomSheetShape
import com.stylianosgakis.androiddevchallengeweek1.ui.FindMyPetAppBar
import com.stylianosgakis.androiddevchallengeweek1.util.isScrollingForwardsAsState
import com.stylianosgakis.androiddevchallengeweek1.util.previewAnimal
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AnimalsScreen(
    animalList: List<Animal>,
    selectedAnimalType: AnimalType,
    setSelectedAnimalType: (AnimalType) -> Unit,
    goToDetailsScreen: (id: Int) -> Unit,
) {
    val lazyListState = rememberLazyListState()
    val isScrollingForwards by lazyListState.isScrollingForwardsAsState()
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = { FindMyPetAppBar() },
        scaffoldState = scaffoldState,
    ) { paddingValues ->
        val modalSheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
        ModalBottomSheetLayout(
            sheetState = modalSheetState,
            sheetContent = {
                AnimalTypePickerSheet(
                    selectedAnimalType = selectedAnimalType,
                    setSelectedAnimalType = { animalType ->
                        scope.launch {
                            modalSheetState.hide()
                        }
                        setSelectedAnimalType(animalType)
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
                var fabHeight by remember { mutableStateOf(0) }

                if (animalList.isEmpty()) {
                    CircularProgressIndicator(
                        modifier = Modifier.align(
                            BiasAlignment(verticalBias = -0.4f, horizontalBias = 0f)
                        )

                    )
                }

                LazyColumn(
                    state = lazyListState,
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(horizontal = 16.dp)
                ) {
                    item {
                        Spacer(modifier = Modifier)
                    }
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
                    item {
                        Spacer(
                            modifier = Modifier
                                .height(fabHeight.dp) // TODO make the space follow the fab size properly
                        )
                    }
                }

                FilterAnimalTypeButton(
                    onClick = {
                        scope.launch {
                            modalSheetState.show()
                        }
                    },
                    visible = !modalSheetState.isVisible &&
                        (animalList.isEmpty() || isScrollingForwards),
                    modifier = Modifier
                        .onSizeChanged {
                            fabHeight = it.height
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
