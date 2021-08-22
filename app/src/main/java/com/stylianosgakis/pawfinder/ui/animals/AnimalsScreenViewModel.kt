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

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stylianosgakis.pawfinder.api.model.animal.Animal
import com.stylianosgakis.pawfinder.data.AnimalType
import com.stylianosgakis.pawfinder.data.PetFinderRepository
import com.stylianosgakis.pawfinder.di.DefaultDispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.plus
import javax.inject.Inject

@HiltViewModel
class AnimalsScreenViewModel @Inject constructor(
    private val petFinderRepository: PetFinderRepository,
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher,
) : ViewModel() {

    private val _animalType: MutableStateFlow<AnimalType> = MutableStateFlow(AnimalType.Cat)
    val animalType: StateFlow<AnimalType>
        get() = _animalType.asStateFlow()

    val animalListStateFlow: StateFlow<List<Animal>> = animalType
        .map { animalType: AnimalType ->
            coroutineScope {
                val getAnimalAsyncCalls: List<Deferred<List<Animal>>> = List(4) { index ->
                    async {
                        petFinderRepository.getAnimals(
                            animalType = animalType,
                            page = (index + 1)
                        )
                    }
                }
                val animalList: List<Animal> = getAnimalAsyncCalls
                    .awaitAll()
                    .flatten()
                    .distinct() // Remove potential duplicates because they break the lazyList by duplicating keys
                animalList
            }
        }
        .stateIn(
            scope = viewModelScope + defaultDispatcher, // Not sure about setting a dispatcher here
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = listOf()
        )

    fun setAnimalType(animalType: AnimalType) {
        _animalType.value = animalType
    }
}
