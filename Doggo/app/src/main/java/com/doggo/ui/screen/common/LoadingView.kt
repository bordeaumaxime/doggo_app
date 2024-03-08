package com.doggo.ui.screen.common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.doggo.ui.theme.DoggoTheme

@Composable
fun LoadingView(modifier: Modifier) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .width(64.dp)
                .testTag(LOADING_VIEW_TEST_TAG),
        )
    }
}

const val LOADING_VIEW_TEST_TAG = "LoadingView"

@PreviewLightDark
@Composable
private fun LoadingViewPreview() {
    DoggoTheme {
        Surface {
            LoadingView(modifier = Modifier.fillMaxSize())
        }
    }
}