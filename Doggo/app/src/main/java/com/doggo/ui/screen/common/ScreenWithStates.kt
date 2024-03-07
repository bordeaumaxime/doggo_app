package com.doggo.ui.screen.common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.doggo.ui.theme.DoggoTheme

@Composable
fun <T: Any> ScreenWithStates(
    uiState: ScreenUiState<T>,
    errorText: String,
    onRetry: () -> Unit,
    modifier: Modifier,
    resultContent: @Composable (T, Modifier) -> Unit,
) {
    when (uiState) {
        is ScreenUiState.Error -> ErrorView(
            text = errorText,
            errorType = uiState.type,
            onRetry = onRetry,
            modifier = modifier
        )

        ScreenUiState.Loading -> LoadingView(modifier = modifier)
        is ScreenUiState.Result -> resultContent(uiState.data, modifier)
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
                uiState = uiState,
                errorText = "Could not retrieve data",
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