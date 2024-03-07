package com.doggo.ui.screen.breeds

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.doggo.domain.model.Breed
import com.doggo.domain.model.SubBreed
import com.doggo.ui.screen.breeds.component.BreedsScreenInternal
import com.doggo.ui.screen.common.ScreenUiState

@Composable
fun BreedsScreen() {
    val uiState = ScreenUiState.Result(
        data = listOf(
            Breed("appenzeller", emptyList()),
            Breed("australian", listOf(SubBreed("shepherd"))),
            Breed(
                "bulldog",
                listOf(
                    SubBreed("boston"),
                    SubBreed("english"),
                    SubBreed("french")
                ),
            ),
            Breed("chihuahua", emptyList()),
        )
    )
    BreedsScreenInternal(
        uiState = uiState,
        onBreedClick = {},
        onSubBreedClick = { _, _ -> },
        onRetry = {},
        modifier = Modifier.fillMaxSize()
    )
}