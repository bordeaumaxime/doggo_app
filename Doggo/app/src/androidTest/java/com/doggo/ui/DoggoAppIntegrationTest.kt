package com.doggo.ui

import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.doggo.data.network.DogCeoApiService
import com.doggo.data.network.model.BreedsApiResponse
import com.doggo.data.network.model.DogsApiResponse
import com.doggo.di.NetworkModule
import dagger.hilt.android.testing.BindValue
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Tests that the user can display the list of breeds, then when they click
 * on a breed, they can see random dog pictures for that breed.
 *
 * Here we mock the [DogCeoApiService] to have consistent results and avoid the test being too flaky.
 */
@HiltAndroidTest
@UninstallModules(NetworkModule::class)
class DoggoAppIntegrationTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    private val breedsResponse = Response.success(
        BreedsApiResponse(
            breeds = mapOf(
                "appenzeller" to emptyList(),
                "bulldog" to listOf("boston", "english", "french"),
                "chihuahua" to emptyList(),
            )
        )
    )

    private val randomDogsForBreedResponse = Response.success(
        DogsApiResponse(
            imageUrls = listOf(
                "https://images.dog.ceo/breeds/hound-english/n02089973_1.jpg",
                "https://images.dog.ceo/breeds/hound-english/n02089973_1066.jpg",
                "https://images.dog.ceo/breeds/hound-english/n02089973_1748.jpg",
            )
        )
    )

    private val randomDogsForSubBreedResponse = Response.success(
        DogsApiResponse(
            imageUrls = listOf(
                "https://images.dog.ceo/breeds/hound-english/n02089973_1.jpg",
            )
        )
    )

    @BindValue
    @JvmField
    val dogCeoApiService: DogCeoApiService = mock {
        onBlocking { getAllBreeds() } doReturn breedsResponse
        onBlocking {
            getRandomDogs(
                "bulldog",
                10
            )
        } doReturn randomDogsForBreedResponse
        onBlocking {
            getRandomDogs(
                "bulldog",
                "french",
                10
            )
        } doReturn randomDogsForSubBreedResponse
    }

    @Test
    fun testBreedList() {
        // given app is launched
        // then we see the breed list items that are clickable, and sub breeds are not visible
        composeTestRule.assertTextDisplayed("Doggo breeds")
        composeTestRule.assertTextDisplayed("Appenzeller").assertHasClickAction()
        composeTestRule.assertTextDisplayed("Bulldog").assertHasClickAction()
        composeTestRule.assertTextDisplayed("Chihuahua").assertHasClickAction()

        composeTestRule.assertTextDoesNotExist("Sub breeds")
        composeTestRule.assertTextDoesNotExist("Boston Bulldog")
        composeTestRule.assertTextDoesNotExist("English Bulldog")
        composeTestRule.assertTextDoesNotExist("French Bulldog")

        // when I click on a breed expand button
        composeTestRule.clickContentDescription("Show bulldog sub breeds")

        // Then I see the sub breeds
        composeTestRule.assertTextDisplayed("Sub breeds")
        composeTestRule.assertTextDisplayed("Boston Bulldog").assertHasClickAction()
        composeTestRule.assertTextDisplayed("English Bulldog").assertHasClickAction()
        composeTestRule.assertTextDisplayed("French Bulldog").assertHasClickAction()

        // when I click on a breed hide button
        composeTestRule.clickContentDescription("Hide bulldog sub breeds")

        // Then I don't see the sub breeds anymore
        composeTestRule.assertTextDoesNotExist("Sub breeds")
        composeTestRule.assertTextDoesNotExist("Boston Bulldog")
        composeTestRule.assertTextDoesNotExist("English Bulldog")
        composeTestRule.assertTextDoesNotExist("French Bulldog")
    }

    @Test
    fun testNavigateToRandomDogsForBreed() {
        // given app is launched
        // when I click on a breed
        composeTestRule.onNodeWithText("Bulldog").performClick()
        // then I see a list of random dog pictures
        composeTestRule.assertTextDisplayed("Doggo pics")
        composeTestRule.assertContentDescriptionDisplayed("Image of a dog", 3)
    }

    @Test
    fun testNavigateToRandomDogsForSubBreed() {
        // given app is launched
        // when I click on a sub breed
        composeTestRule.clickContentDescription("Show bulldog sub breeds")
        composeTestRule.onNodeWithText("French Bulldog").performClick()

        // then I see a list of random dog pictures
        composeTestRule.assertTextDisplayed("Doggo pics")
        composeTestRule.assertContentDescriptionDisplayed("Image of a dog", 1)
    }

    @Test
    fun testNavigateBack() {
        // given app is launched
        // when I click on a sub breed
        composeTestRule.clickContentDescription("Show bulldog sub breeds")
        composeTestRule.onNodeWithText("French Bulldog").performClick()
        // then I see a list of random dog pictures
        composeTestRule.assertTextDisplayed("Doggo pics")
        // when I click "back"
        composeTestRule.clickContentDescription("Go back")
        // then I see the list of breeds again
        composeTestRule.assertTextDisplayed("Doggo breeds")
    }
}