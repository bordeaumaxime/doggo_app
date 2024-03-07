package com.doggo.ui

import androidx.compose.runtime.Composable
import com.doggo.ui.screen.breeds.BreedsScreen
import com.doggo.ui.screen.breeds.component.BreedsViewModel

@Composable
fun MainNavigation(viewModel: BreedsViewModel) {
    BreedsScreen(viewModel)
}