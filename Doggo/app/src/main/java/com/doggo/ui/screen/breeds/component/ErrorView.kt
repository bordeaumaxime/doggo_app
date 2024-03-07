package com.doggo.ui.screen.breeds.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import com.doggo.R
import com.doggo.ui.screen.common.ScreenUiState
import com.doggo.ui.theme.DoggoTheme

@Composable
fun ErrorView(
    text: String,
    errorType: ScreenUiState.Error.Type,
    onRetry: () -> Unit,
    modifier: Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = text)
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = when (errorType) {
                ScreenUiState.Error.Type.NETWORK -> stringResource(R.string.network_error)
                ScreenUiState.Error.Type.UNKNOWN -> stringResource(R.string.unknown_error)
            }
        )
        Spacer(modifier = Modifier.height(32.dp))
        Button(onClick = onRetry) {
            Text(text = stringResource(R.string.retry))
        }
    }
}

private class ErrorTypeParameterProvider :
    PreviewParameterProvider<ScreenUiState.Error.Type> {
    override val values = ScreenUiState.Error.Type.entries.asSequence()
}

@PreviewLightDark
@Composable
private fun ErrorViewPreview(
    @PreviewParameter(
        ErrorTypeParameterProvider::class
    ) errorType: ScreenUiState.Error.Type
) {
    DoggoTheme {
        Surface {
            ErrorView(
                text = "Could not retrieve data.",
                errorType = errorType,
                onRetry = {},
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}