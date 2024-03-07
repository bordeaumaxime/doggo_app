package com.doggo.ui.screen.breeds

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.doggo.ui.screen.breeds.component.BreedsScreenInternal

@Composable
fun BreedsScreen(
    viewModel: BreedsViewModel,
    onBreedClick: (String) -> Unit,
    onSubBreedClick: (String, String) -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    BreedsScreenInternal(
        uiState = uiState,
        onBreedClick = onBreedClick,
        onSubBreedClick = onSubBreedClick,
        onRetry = viewModel::retry,
        modifier = Modifier.fillMaxSize()
    )
}