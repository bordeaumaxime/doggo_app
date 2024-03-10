package com.doggo.ui

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.doggo.ui.screen.breeds.BreedsScreen
import com.doggo.ui.screen.dogs.DogsScreen

private const val BREEDS_SCREEN = "breeds_screen"

const val BREED_PARAM = "breed_param"
const val SUB_BREED_PARAM = "sub_breed_param"
private const val DOGS_SCREEN =
    "dogs_screen/{$BREED_PARAM}?$SUB_BREED_PARAM={$SUB_BREED_PARAM}"

private fun buildDogsScreenPath(
    breedName: String,
    subBreedName: String? = null
) = if (subBreedName == null) {
    "dogs_screen/$breedName"
} else {
    "dogs_screen/$breedName?$SUB_BREED_PARAM=${subBreedName}"
}

/**
 * Handles the navigation for [MainActivity].
 * There are 2 screens:
 * - [BreedsScreen]
 * - [DogsScreen]
 */
@Composable
fun MainNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = BREEDS_SCREEN) {
        baseDestination(BREEDS_SCREEN) {
            BreedsScreen(
                hiltViewModel(),
                onBreedClick = { breedName ->
                    navController.navigate(
                        buildDogsScreenPath(breedName)
                    )
                },
                onSubBreedClick = { breedName, subBreedName ->
                    navController.navigate(
                        buildDogsScreenPath(breedName, subBreedName)
                    )
                })
        }
        baseDestination(
            DOGS_SCREEN,
            arguments = listOf(
                navArgument(BREED_PARAM) { type = NavType.StringType },
                navArgument(SUB_BREED_PARAM) {
                    nullable = true
                    defaultValue = null
                    type = NavType.StringType
                })
        ) {
            DogsScreen(hiltViewModel(), onBack = navController::navigateUp)
        }
    }

}