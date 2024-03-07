package com.doggo.ui

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.doggo.ui.screen.breeds.BreedsScreen
import com.doggo.ui.screen.dogs.DogsScreen

private const val BREEDS_SCREEN = "breeds_screen"
private const val DOGS_SCREEN = "dogs_screen"

@Composable
fun MainNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = BREEDS_SCREEN) {
        composable(BREEDS_SCREEN) {
            BreedsScreen(
                hiltViewModel(),
                onBreedClick = {
                    navController.navigate(
                        DOGS_SCREEN
                    )
                },
                onSubBreedClick = { breedName, subBreedName ->
                    navController.navigate(
                        DOGS_SCREEN
                    )
                })
        }
        composable(DOGS_SCREEN) {
            DogsScreen()
        }
    }

}