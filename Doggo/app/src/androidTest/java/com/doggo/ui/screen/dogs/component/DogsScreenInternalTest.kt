package com.doggo.ui.screen.dogs.component

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.printToString
import com.doggo.domain.model.Dog
import com.doggo.ui.assertContentDescriptionDisplayed
import com.doggo.ui.assertTagDisplayed
import com.doggo.ui.assertTextDisplayed
import com.doggo.ui.clickText
import com.doggo.ui.screen.common.LOADING_VIEW_TEST_TAG
import com.doggo.ui.screen.common.ScreenUiState
import com.doggo.ui.theme.DoggoTheme
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

/**
 * Tests that the [DogsScreenInternal] displays the right views according to the state passed to it
 * and calls the right callbacks when the user does actions.
 */
class DogsScreenInternalTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val onRetry: () -> Unit = mock()

    @Test
    fun testLoadingStateContent() {
        setContent(ScreenUiState.Loading)

        composeTestRule.assertTagDisplayed(LOADING_VIEW_TEST_TAG)
    }

    @Test
    fun testResultStateContent() {
        setContent(
            ScreenUiState.Result(
                listOf(
                    Dog("https://images.dog.ceo/breeds/hound-english/n02089973_1.jpg"),
                    Dog("https://images.dog.ceo/breeds/hound-english/n02089973_1066.jpg"),
                    Dog("https://images.dog.ceo/breeds/hound-english/n02089973_1748.jpg"),
                )
            )
        )

        println(composeTestRule.onRoot().printToString())
        composeTestRule.assertContentDescriptionDisplayed("Image of a dog", 3)
    }

    @Test
    fun testErrorStateContent() {
        setContent(ScreenUiState.Error(ScreenUiState.Error.Type.NETWORK))

        composeTestRule.assertTextDisplayed("Could not retrieve dogs :(")
        composeTestRule.assertTextDisplayed("A network error occurred")
        composeTestRule.assertTextDisplayed("Retry")

        composeTestRule.clickText("Retry")
        verify(onRetry).invoke()
    }

    private fun setContent(uiState: ScreenUiState<List<Dog>>) {
        composeTestRule.setContent {
            DoggoTheme {
                Surface {
                    DogsScreenInternal(
                        uiState = uiState,
                        onRetry = onRetry,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }
    }
}