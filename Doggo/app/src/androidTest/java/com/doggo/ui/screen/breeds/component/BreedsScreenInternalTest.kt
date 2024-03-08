package com.doggo.ui.screen.breeds.component

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.junit4.createComposeRule
import com.doggo.domain.model.Breed
import com.doggo.domain.model.SubBreed
import com.doggo.ui.assertTagDisplayed
import com.doggo.ui.assertTextDisplayed
import com.doggo.ui.clickText
import com.doggo.ui.screen.common.LOADING_VIEW_TEST_TAG
import com.doggo.ui.screen.common.ScreenUiState
import com.doggo.ui.screen.dogs.component.DogsScreenInternal
import com.doggo.ui.theme.DoggoTheme
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

/**
 * Tests that the [BreedsScreenInternal] displays the right views according to the state passed to it
 * and calls the right callbacks when the user does actions.
 */
class BreedsScreenInternalTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val onBreedClick: (String) -> Unit = mock()
    private val onSubBreedClick: (String, String) -> Unit = mock()
    private val onRetry: () -> Unit = mock()

    @Test
    fun testLoadingStateContent() {
        setContent(ScreenUiState.Loading)

        composeTestRule.assertTextDisplayed("Doggo breeds")
        composeTestRule.assertTagDisplayed(LOADING_VIEW_TEST_TAG)
    }

    @Test
    fun testResultStateContent() {
        setContent(
            ScreenUiState.Result(
                listOf(
                    Breed("appenzeller", emptyList()),
                    Breed(
                        "bulldog",
                        listOf(
                            SubBreed("boston"),
                            SubBreed("french")
                        ),
                    ),
                    Breed("chihuahua", emptyList()),
                )
            )
        )

        composeTestRule.assertTextDisplayed("Doggo breeds")

        composeTestRule.assertTextDisplayed("Appenzeller")
        composeTestRule.assertTextDisplayed("Bulldog")
        composeTestRule.assertTextDisplayed("Boston Bulldog")
        composeTestRule.assertTextDisplayed("French Bulldog")
        composeTestRule.assertTextDisplayed("Chihuahua")

        composeTestRule.clickText("Appenzeller")
        verify(onBreedClick).invoke("appenzeller")

        composeTestRule.clickText("French Bulldog")
        verify(onSubBreedClick).invoke("bulldog", "french")
    }

    @Test
    fun testErrorStateContent() {
        setContent(ScreenUiState.Error(ScreenUiState.Error.Type.NETWORK))

        composeTestRule.assertTextDisplayed("Doggo breeds")
        composeTestRule.assertTextDisplayed("Could not retrieve breeds :(")
        composeTestRule.assertTextDisplayed("A network error occurred")
        composeTestRule.assertTextDisplayed("Retry")

        composeTestRule.clickText("Retry")
        verify(onRetry).invoke()
    }

    private fun setContent(uiState: ScreenUiState<List<Breed>>) {
        composeTestRule.setContent {
            DoggoTheme {
                Surface {
                    BreedsScreenInternal(
                        uiState = uiState,
                        onBreedClick = onBreedClick,
                        onSubBreedClick = onSubBreedClick,
                        onRetry = onRetry,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }
    }
}