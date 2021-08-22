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
package com.stylianosgakis.pawfinder.data

import com.stylianosgakis.pawfinder.api.PetFinderApi
import com.stylianosgakis.pawfinder.api.model.animal.Animal
import com.stylianosgakis.pawfinder.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PetFinderRepository @Inject constructor(
    private val petFinderApi: PetFinderApi,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) {
    suspend fun getAnimals(
        animalType: AnimalType,
        page: Int,
    ): List<Animal> = withContext(ioDispatcher) {
        return@withContext petFinderApi.searchAnimals(
            animalType.type,
            page
        ).animals
            // Filter not 'nice' names for better looking UI
            .filterNot { animal ->
                animal.name.contains(
                    //language=RegExp
                    Regex("[^a-zA-Z ]")
                )
            }
            // Filter only ones with multiple pictures to showcase feature in details screen
            .filter {
                it.photos.size > 1
            }
            .sortedByDescending { animal -> animal.id }
    }

    suspend fun getAnimal(
        id: Int
    ): Animal = withContext(ioDispatcher) {
        petFinderApi.searchAnimal(id).animal
    }
}
