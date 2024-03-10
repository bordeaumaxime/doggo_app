package com.doggo.ui.screen.breeds.component

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.junit4.createComposeRule
import com.doggo.domain.model.Breed
import com.doggo.domain.model.SubBreed
import com.doggo.ui.assertTagDisplayed
import com.doggo.ui.assertTextDisplayed
import com.doggo.ui.assertTextDoesNotExist
import com.doggo.ui.clickContentDescription
import com.doggo.ui.clickText
import com.doggo.ui.screen.common.LOADING_VIEW_TEST_TAG
import com.doggo.ui.screen.common.ScreenUiState
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
        // given a list of breeds and sub breeds
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

        // then the list of breeds is displayed, without sub breeds
        composeTestRule.assertTextDisplayed("Doggo breeds")

        composeTestRule.assertTextDisplayed("Appenzeller")
        composeTestRule.assertTextDisplayed("Bulldog")
        composeTestRule.assertTextDisplayed("Chihuahua")

        composeTestRule.assertTextDoesNotExist("Sub breeds")
        composeTestRule.assertTextDoesNotExist("Boston Bulldog")
        composeTestRule.assertTextDoesNotExist("French Bulldog")

        // when I click on a breed
        composeTestRule.clickText("Appenzeller")
        // then the onBreedClick callback is invoked
        verify(onBreedClick).invoke("appenzeller")

        // when I click on a breed expand button
        composeTestRule.clickContentDescription("Show bulldog sub breeds")

        // Then I see the sub breeds
        composeTestRule.assertTextDisplayed("Sub breeds")
        composeTestRule.assertTextDisplayed("Boston Bulldog").assertHasClickAction()
        composeTestRule.assertTextDisplayed("French Bulldog").assertHasClickAction()

        // when I click on a  sub breed
        composeTestRule.clickText("French Bulldog")
        // then the onSubBreedClick callback is invoked
        verify(onSubBreedClick).invoke("bulldog", "french")

        // when I click on a breed hide button
        composeTestRule.clickContentDescription("Hide bulldog sub breeds")

        // Then I don't see the sub breeds anymore
        composeTestRule.assertTextDoesNotExist("Sub breeds")
        composeTestRule.assertTextDoesNotExist("Boston Bulldog")
        composeTestRule.assertTextDoesNotExist("English Bulldog")
        composeTestRule.assertTextDoesNotExist("French Bulldog")
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