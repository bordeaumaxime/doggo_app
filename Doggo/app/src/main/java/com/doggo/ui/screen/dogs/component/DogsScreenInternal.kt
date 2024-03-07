package com.doggo.ui.screen.dogs.component

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.doggo.R
import com.doggo.domain.model.Dog
import com.doggo.ui.screen.common.ScreenUiState
import com.doggo.ui.screen.common.ScreenWithStates
import com.doggo.ui.theme.DoggoTheme

@Composable
fun DogsScreenInternal(
    uiState: ScreenUiState<List<Dog>>,
    onRetry: () -> Unit,
    modifier: Modifier
) {
    ScreenWithStates(
        uiState = uiState,
        errorText = stringResource(R.string.error_dogs),
        onRetry = onRetry,
        modifier = modifier
    ) { data, resultModifier ->
        DogsResult(
            dogs = data,
            modifier = resultModifier
        )
    }
}

private class StatePreviewParameterProvider :
    PreviewParameterProvider<ScreenUiState<List<Dog>>> {
    override val values = sequenceOf(
        ScreenUiState.Loading,
        ScreenUiState.Result(
            listOf(
                Dog("https://images.dog.ceo/breeds/hound-english/n02089973_1.jpg"),
                Dog("https://images.dog.ceo/breeds/hound-english/n02089973_1066.jpg"),
                Dog("https://images.dog.ceo/breeds/hound-english/n02089973_1748.jpg"),
            )
        ),
        ScreenUiState.Error(type = ScreenUiState.Error.Type.NETWORK)
    )
}

@Preview
@Composable
private fun DogsScreenInternalPreview(
    @PreviewParameter(
        StatePreviewParameterProvider::class
    ) uiState: ScreenUiState<List<Dog>>
) {
    DoggoTheme {
        Surface {
            DogsScreenInternal(
                uiState = uiState,
                onRetry = {},
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}