package com.doggo.ui.screen.dogs

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.doggo.domain.model.Dog
import com.doggo.domain.repository.DogRepository
import com.doggo.ui.BREED_PARAM
import com.doggo.ui.SUB_BREED_PARAM
import com.doggo.ui.screen.common.ScreenUiState
import com.doggo.ui.screen.common.toScreenUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DogsViewModel @Inject constructor(
    private val dogRepository: DogRepository,
    savedStateHandle: SavedStateHandle,
    private val defaultDispatcher: CoroutineDispatcher
) : ViewModel() {

    companion object {
        private const val DOGS_COUNT = 10
    }

    private val breedName: String = checkNotNull(savedStateHandle[BREED_PARAM])
    private val subBreedName: String? = savedStateHandle[SUB_BREED_PARAM]

    private val _uiState: MutableStateFlow<ScreenUiState<List<Dog>>> =
        MutableStateFlow(
            ScreenUiState.Loading
        )
    val uiState: StateFlow<ScreenUiState<List<Dog>>> = _uiState

    private var retrieveDogsJob: Job

    init {
        retrieveDogsJob = launchRetrieveDogs()
    }

    private fun launchRetrieveDogs(): Job {
        return viewModelScope.launch(defaultDispatcher) {
            // replace by real breed names
            println("subBreedName ${subBreedName?.isEmpty()}")
            val dogsResult =
                dogRepository.getRandomDogs(breedName, subBreedName, DOGS_COUNT)
            _uiState.update {
                dogsResult.toScreenUiState()
            }
        }
    }

    fun retry() {
        _uiState.update {
            ScreenUiState.Loading
        }
        retrieveDogsJob.cancel()
        retrieveDogsJob = launchRetrieveDogs()
    }
}