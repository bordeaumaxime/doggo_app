package com.doggo.ui.screen.breeds

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.doggo.domain.model.Breed
import com.doggo.domain.repository.BreedRepository
import com.doggo.domain.repository.DataResult
import com.doggo.ui.screen.common.ScreenUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BreedsViewModel @Inject constructor(
    private val breedRepository: BreedRepository,
    private val defaultDispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _uiState: MutableStateFlow<ScreenUiState<List<Breed>>> =
        MutableStateFlow(
            ScreenUiState.Loading
        )
    val uiState: StateFlow<ScreenUiState<List<Breed>>> = _uiState

    private var retrieveBreedsJob: Job

    init {
        retrieveBreedsJob = launchRetrieveBreeds()
    }

    private fun launchRetrieveBreeds(): Job {
        return viewModelScope.launch(defaultDispatcher) {
            val breedsResult = breedRepository.getAllBreeds()
            val newUiState = when (breedsResult) {
                is DataResult.Error -> ScreenUiState.Error(
                    when (breedsResult.errorType) {
                        DataResult.ErrorType.NETWORK -> ScreenUiState.Error.Type.NETWORK
                        DataResult.ErrorType.UNKNOWN -> ScreenUiState.Error.Type.UNKNOWN
                    }
                )

                is DataResult.Success -> ScreenUiState.Result(breedsResult.data)
            }
            _uiState.update {
                newUiState
            }
        }
    }

    fun retry() {
        _uiState.update {
            ScreenUiState.Loading
        }
        retrieveBreedsJob.cancel()
        retrieveBreedsJob = launchRetrieveBreeds()
    }
}