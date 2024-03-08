package com.doggo.ui.screen.breeds.component

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.doggo.R
import com.doggo.domain.model.Breed
import com.doggo.domain.model.SubBreed
import com.doggo.ui.screen.common.ScreenUiState
import com.doggo.ui.screen.common.ScreenWithStates
import com.doggo.ui.theme.DoggoTheme

@Composable
fun BreedsScreenInternal(
    uiState: ScreenUiState<List<Breed>>,
    onBreedClick: (String) -> Unit,
    onSubBreedClick: (String, String) -> Unit,
    onRetry: () -> Unit,
    modifier: Modifier
) {
    ScreenWithStates(
        title = stringResource(R.string.breeds_title),
        uiState = uiState,
        errorText = stringResource(R.string.error_breeds),
        onRetry = onRetry,
        modifier = modifier
    ) { data, resultModifier ->
        BreedsResult(
            breeds = data,
            onBreedClick = onBreedClick,
            onSubBreedClick = onSubBreedClick,
            modifier = resultModifier
        )
    }
}

private class StatePreviewParameterProvider :
    PreviewParameterProvider<ScreenUiState<List<Breed>>> {
    override val values = sequenceOf(
        ScreenUiState.Loading,
        ScreenUiState.Result(
            listOf(
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
        ),
        ScreenUiState.Error(type = ScreenUiState.Error.Type.NETWORK)
    )
}

@Preview
@Composable
private fun BreedsScreenInternalPreview(
    @PreviewParameter(
        StatePreviewParameterProvider::class
    ) uiState: ScreenUiState<List<Breed>>
) {
    DoggoTheme {
        Surface {
            BreedsScreenInternal(
                uiState = uiState,
                onBreedClick = {},
                onSubBreedClick = { _, _ -> },
                onRetry = {},
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}