package com.doggo.ui.screen.dogs.component

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import com.doggo.R
import com.doggo.domain.model.Dog
import com.doggo.ui.screen.common.BreedNameFormatter.getBreedOrSubBreedFormattedName
import com.doggo.ui.screen.common.ScreenUiState
import com.doggo.ui.screen.common.ScreenWithStates
import com.doggo.ui.screen.dogs.DogsUiState
import com.doggo.ui.theme.DoggoTheme

@Composable
fun DogsScreenInternal(
    uiState: DogsUiState,
    onBack: () -> Unit,
    onRetry: () -> Unit,
    modifier: Modifier
) {
    val breedOrSubBreed =
        getBreedOrSubBreedFormattedName(uiState.breedName, uiState.subBreedName)
    ScreenWithStates(
        title = stringResource(R.string.dogs_title, breedOrSubBreed),
        uiState = uiState.screenUiState,
        errorText = stringResource(R.string.error_dogs),
        onBack = onBack,
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
    PreviewParameterProvider<DogsUiState> {
    override val values = sequenceOf(
        DogsUiState(
            breedName = "hound",
            subBreedName = null,
            ScreenUiState.Loading
        ),
        DogsUiState(
            breedName = "hound",
            subBreedName = "english",
            ScreenUiState.Loading
        ),
        DogsUiState(
            breedName = "hound", subBreedName = null, ScreenUiState.Result(
                listOf(
                    Dog("https://images.dog.ceo/breeds/hound-english/n02089973_1.jpg"),
                    Dog("https://images.dog.ceo/breeds/hound-english/n02089973_1066.jpg"),
                    Dog("https://images.dog.ceo/breeds/hound-english/n02089973_1748.jpg"),
                )
            )
        ),
        DogsUiState(
            breedName = "hound",
            subBreedName = null,
            ScreenUiState.Error(type = ScreenUiState.Error.Type.NETWORK)
        )
    )
}

@PreviewLightDark
@PreviewScreenSizes
@Composable
private fun DogsScreenInternalPreview(
    @PreviewParameter(
        StatePreviewParameterProvider::class
    ) uiState: DogsUiState
) {
    DoggoTheme {
        Surface {
            DogsScreenInternal(
                uiState = uiState,
                onBack = {},
                onRetry = {},
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}