package com.stylianosgakis.androiddevchallengeweek1.ui.animals

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stylianosgakis.androiddevchallengeweek1.api.model.animal.Animal
import com.stylianosgakis.androiddevchallengeweek1.data.AnimalType
import com.stylianosgakis.androiddevchallengeweek1.data.PetFinderRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AnimalsScreenViewModel @Inject constructor(
    private val petFinderRepository: PetFinderRepository,
) : ViewModel() {

    var loadSelectedOptionsJob: Job? = null

    private val _animalList = MutableStateFlow<List<Animal>>(listOf())
    val animalList: StateFlow<List<Animal>>
        get() = _animalList.asStateFlow()

    private val _animalType: MutableStateFlow<AnimalType> = MutableStateFlow(AnimalType.Cat)
    val animalType: StateFlow<AnimalType>
        get() = _animalType.asStateFlow()

    init {
        _animalType.onEach {
            loadSelectedOption()
        }.launchIn(viewModelScope)
    }

    fun setAnimalType(animalType: AnimalType) {
        _animalList.value = listOf()
        _animalType.value = animalType
    }

    private fun loadSelectedOption() {
        loadSelectedOptionsJob?.cancel() // TODO check if this is the correct way to stop the old query if I press multiple things before the previous one finishes
        loadSelectedOptionsJob = viewModelScope.launch {
            //TODO Add page handling. For now just load some to be able to scroll freely
            val getAnimalAsyncCalls = List(4) { index ->
                async {
                    petFinderRepository.getAnimals(
                        animalType = _animalType.value,
                        page = (index + 1)
                    )
                }
            }
            val animalList: List<Animal> = getAnimalAsyncCalls
                .awaitAll()
                .flatten()
            _animalList.value = animalList
        }
    }
}