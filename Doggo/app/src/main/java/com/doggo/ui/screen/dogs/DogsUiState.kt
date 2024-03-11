package com.doggo.ui.screen.dogs

import com.doggo.domain.model.Dog
import com.doggo.ui.screen.common.ScreenUiState

data class DogsUiState(
    val breedName: String,
    val subBreedName: String?,
    val screenUiState: ScreenUiState<List<Dog>>
)
