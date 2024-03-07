package com.doggo.ui.screen.dogs

import androidx.lifecycle.SavedStateHandle
import com.doggo.domain.model.Dog
import com.doggo.domain.repository.DataResult
import com.doggo.domain.repository.DogRepository
import com.doggo.ui.BREED_PARAM
import com.doggo.ui.SUB_BREED_PARAM
import com.doggo.ui.screen.common.ScreenUiState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
class DogsViewModelTest {

    private val dogRepository = mock<DogRepository>()

    private val stubDogs = listOf(
        Dog("https://images.dog.ceo/breeds/hound-english/n02089973_1.jpg"),
        Dog("https://images.dog.ceo/breeds/hound-english/n02089973_1066.jpg"),
        Dog("https://images.dog.ceo/breeds/hound-english/n02089973_1748.jpg"),
    )

    @Test
    fun `given repository returns dogs, then result state with dogs`() =
        runTest {
            whenever(
                dogRepository.getRandomDogs(
                    "hound",
                    "english",
                    10
                )
            ).thenReturn(
                DataResult.Success(
                    stubDogs
                )
            )
            val viewModel = createViewModel()
            advanceUntilIdle()

            Assertions.assertThat(viewModel.uiState.value).isEqualTo(
                ScreenUiState.Result(stubDogs)
            )
        }

    @Test
    fun `given repository returns unknown error, then returns unknown error state`() =
        runTest {
            whenever(
                dogRepository.getRandomDogs(
                    "hound",
                    "english",
                    10
                )
            ).thenReturn(
                DataResult.Error(DataResult.ErrorType.UNKNOWN)
            )
            val viewModel = createViewModel()
            advanceUntilIdle()

            Assertions.assertThat(viewModel.uiState.value).isEqualTo(
                ScreenUiState.Error(ScreenUiState.Error.Type.UNKNOWN)
            )
        }

    @Test
    fun `given repository returns network error, then returns network error state`() =
        runTest {
            whenever(
                dogRepository.getRandomDogs(
                    "hound",
                    "english",
                    10
                )
            ).thenReturn(
                DataResult.Error(DataResult.ErrorType.NETWORK)
            )
            val viewModel = createViewModel()
            advanceUntilIdle()

            Assertions.assertThat(viewModel.uiState.value).isEqualTo(
                ScreenUiState.Error(ScreenUiState.Error.Type.NETWORK)
            )
        }

    @Test
    fun `given repository returns error then dog, when retry is called, then loading followed by result state with dogs `() =
        runTest {
            whenever(
                dogRepository.getRandomDogs(
                    "hound",
                    "english",
                    10
                )
            ).thenReturn(
                DataResult.Error(DataResult.ErrorType.NETWORK)
            )
            val viewModel = createViewModel()
            advanceUntilIdle()

            val uiStates = mutableListOf<ScreenUiState<List<Dog>>>()
            backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
                viewModel.uiState.toList(uiStates)
            }
            whenever(
                dogRepository.getRandomDogs(
                    "hound",
                    "english",
                    10
                )
            ).thenReturn(
                DataResult.Success(stubDogs)
            )
            viewModel.retry()
            advanceUntilIdle()

            Assertions.assertThat(uiStates).containsExactly(
                ScreenUiState.Error(ScreenUiState.Error.Type.NETWORK),
                ScreenUiState.Loading,
                ScreenUiState.Result(stubDogs)
            )
        }

    private fun TestScope.createViewModel(): DogsViewModel {
        val dispatcher = StandardTestDispatcher(testScheduler)
        val savedStateHandle =
            SavedStateHandle(
                mapOf(
                    BREED_PARAM to "hound",
                    SUB_BREED_PARAM to "english"
                )
            )
        return DogsViewModel(
            dogRepository,
            savedStateHandle,
            dispatcher
        )
    }
}