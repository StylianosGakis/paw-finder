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
package com.stylianosgakis.pawfinder.ui.animaldetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import com.stylianosgakis.pawfinder.data.PetFinderRepository
import com.stylianosgakis.pawfinder.di.DefaultDispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.plus
import javax.inject.Inject

@HiltViewModel
class DetailsScreenViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val petFinderRepository: PetFinderRepository,
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher,
) : ViewModel() {
    private val animalIdFlow: Flow<Long> = savedStateHandle.getLiveData<Long>("id").asFlow()

    val viewStateFlow: StateFlow<DetailsScreenViewState> = animalIdFlow
        .map { animalId: Long ->
            val animal = petFinderRepository.getAnimal(animalId)
            DetailsScreenViewState.Loaded(animal)
        }
        .stateIn(
            scope = viewModelScope + defaultDispatcher,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = DetailsScreenViewState.Loading
        )
}
