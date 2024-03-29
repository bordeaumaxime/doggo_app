package com.doggo.ui.screen.dogs

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.doggo.ui.screen.dogs.component.DogsScreenInternal

/**
 * Displays a list of random dog pics from the given breed or sub breed
 */
@Composable
fun DogsScreen(viewModel: DogsViewModel, onBack: () -> Unit) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    DogsScreenInternal(
        uiState = uiState,
        onBack = onBack,
        onRetry = viewModel::retry,
        modifier = Modifier.fillMaxSize()
    )
}