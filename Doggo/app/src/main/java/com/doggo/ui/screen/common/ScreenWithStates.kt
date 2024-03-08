package com.doggo.ui.screen.common

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.doggo.R
import com.doggo.ui.theme.DoggoTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T : Any> ScreenWithStates(
    title: String,
    uiState: ScreenUiState<T>,
    errorText: String,
    onBack: (() -> Unit)? = null,
    onRetry: () -> Unit,
    modifier: Modifier,
    resultContent: @Composable (T, Modifier) -> Unit,
) {
    Scaffold(topBar = {
        TopAppBar(
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.primary,
                titleContentColor = MaterialTheme.colorScheme.onPrimary,
            ),
            title = {
                Text(title)
            },
            navigationIcon = {
                if (onBack != null) {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.go_back),
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                }
            },
        )
    }, modifier = modifier) { paddingValues ->
        AnimatedContent(
            targetState = uiState,
            label = "ScreenWithStatesAnim"
        ) { targetState ->
            when (targetState) {
                is ScreenUiState.Error -> ErrorView(
                    text = errorText,
                    errorType = targetState.type,
                    onRetry = onRetry,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                )

                ScreenUiState.Loading -> LoadingView(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                )

                is ScreenUiState.Result -> resultContent(
                    targetState.data,
                    Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                )
            }
        }
    }

}

private class StatePreviewParameterProvider :
    PreviewParameterProvider<ScreenUiState<String>> {
    override val values = sequenceOf(
        ScreenUiState.Loading,
        ScreenUiState.Result("Content example"),
        ScreenUiState.Error(type = ScreenUiState.Error.Type.NETWORK)
    )
}

@Preview
@Composable
private fun ScreenWithStatesPreview(
    @PreviewParameter(
        StatePreviewParameterProvider::class
    ) uiState: ScreenUiState<String>
) {
    DoggoTheme {
        Surface {
            ScreenWithStates(
                title = "Title",
                uiState = uiState,
                errorText = "Could not retrieve data",
                onBack = {},
                onRetry = {},
                modifier = Modifier.fillMaxSize()
            ) { data, modifier ->
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = modifier
                ) {
                    Text(text = data)
                }
            }
        }
    }
}