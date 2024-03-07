package com.doggo.ui.screen.breeds

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.doggo.ui.screen.breeds.component.BreedsScreenInternal
import com.doggo.ui.screen.breeds.component.BreedsViewModel

@Composable
fun BreedsScreen(viewModel: BreedsViewModel) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    BreedsScreenInternal(
        uiState = uiState,
        onBreedClick = {},
        onSubBreedClick = { _, _ -> },
        onRetry = { viewModel.retry() },
        modifier = Modifier.fillMaxSize()
    )
}