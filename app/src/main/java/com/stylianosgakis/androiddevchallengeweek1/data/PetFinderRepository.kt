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