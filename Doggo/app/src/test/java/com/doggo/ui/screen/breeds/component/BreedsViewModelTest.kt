package com.doggo.ui.screen.breeds.component

import com.doggo.domain.model.Breed
import com.doggo.domain.model.SubBreed
import com.doggo.domain.repository.BreedRepository
import com.doggo.domain.repository.DataResult
import com.doggo.ui.screen.common.ScreenUiState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
class BreedsViewModelTest {

    private val breedRepository = mock<BreedRepository>()

    private val stubBreeds = listOf(
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

    @Test
    fun `given repository returns breeds, then result state with breeds`() =
        runTest {
            whenever(breedRepository.getAllBreeds()).thenReturn(
                DataResult.Success(
                    stubBreeds
                )
            )
            val viewModel = createViewModel()
            advanceUntilIdle()

            assertThat(viewModel.uiState.value).isEqualTo(
                ScreenUiState.Result(stubBreeds)
            )
        }

    @Test
    fun `given repository returns unknown error, then returns unknown error state`() =
        runTest {
            whenever(breedRepository.getAllBreeds()).thenReturn(
                DataResult.Error(DataResult.ErrorType.UNKNOWN)
            )
            val viewModel = createViewModel()
            advanceUntilIdle()

            assertThat(viewModel.uiState.value).isEqualTo(
                ScreenUiState.Error(ScreenUiState.Error.Type.UNKNOWN)
            )
        }

    @Test
    fun `given repository returns network error, then returns network error state`() =
        runTest {
            whenever(breedRepository.getAllBreeds()).thenReturn(
                DataResult.Error(DataResult.ErrorType.NETWORK)
            )
            val viewModel = createViewModel()
            advanceUntilIdle()

            assertThat(viewModel.uiState.value).isEqualTo(
                ScreenUiState.Error(ScreenUiState.Error.Type.NETWORK)
            )
        }

    @Test
    fun `given repository returns error then breeds, when retry is called, then loading followed by result state with breeds `() =
        runTest {
            whenever(breedRepository.getAllBreeds()).thenReturn(
                DataResult.Error(DataResult.ErrorType.NETWORK)
            )
            val viewModel = createViewModel()
            advanceUntilIdle()

            val uiStates = mutableListOf<ScreenUiState<List<Breed>>>()
            backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
                viewModel.uiState.toList(uiStates)
            }
            whenever(breedRepository.getAllBreeds()).thenReturn(
                DataResult.Success(stubBreeds)
            )
            viewModel.retry()
            advanceUntilIdle()

            assertThat(uiStates).containsExactly(
                ScreenUiState.Error(ScreenUiState.Error.Type.NETWORK),
                ScreenUiState.Loading,
                ScreenUiState.Result(stubBreeds)
            )
        }

    private fun TestScope.createViewModel(): BreedsViewModel {
        val dispatcher = StandardTestDispatcher(testScheduler)
        return BreedsViewModel(
            breedRepository,
            dispatcher
        )
    }
}