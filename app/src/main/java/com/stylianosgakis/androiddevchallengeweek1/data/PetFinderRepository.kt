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
package com.stylianosgakis.androiddevchallengeweek1.data

import com.stylianosgakis.androiddevchallengeweek1.api.PetFinderApi
import com.stylianosgakis.androiddevchallengeweek1.api.model.animal.Animal
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PetFinderRepository(
    private val petFinderApi: PetFinderApi,
) {
    suspend fun getAnimals(
        animalType: AnimalType,
        page: Int,
    ): List<Animal> = withContext(Dispatchers.IO) {
        return@withContext petFinderApi.searchAnimals(
            animalType.type,
            page
        ).animals
            // Filter animals without pictures for better looking UI
            .filter { animal -> animal.photos.isNotEmpty() }
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
}
