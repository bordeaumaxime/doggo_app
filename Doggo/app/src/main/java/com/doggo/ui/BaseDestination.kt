package com.doggo.ui

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

/**
 *  Creates a navigation composable with predefined animations.
 */
fun NavGraphBuilder.baseDestination(
    route: String,
    arguments: List<NamedNavArgument> = emptyList(),
    content: @Composable AnimatedContentScope.(NavBackStackEntry) -> Unit
) {
    composable(
        route = route, enterTransition = {
            slideIntoContainer(
                animationSpec = tween(200, easing = EaseIn),
                towards = AnimatedContentTransitionScope.SlideDirection.Start
            )
        },
        arguments = arguments,
        exitTransition = {
            slideOutOfContainer(
                animationSpec = tween(200, easing = EaseOut),
                towards = AnimatedContentTransitionScope.SlideDirection.Start
            )
        },
        popEnterTransition = {
            slideIntoContainer(
                animationSpec = tween(200, easing = EaseIn),
                towards = AnimatedContentTransitionScope.SlideDirection.End
            )
        },
        popExitTransition = {
            slideOutOfContainer(
                animationSpec = tween(200, easing = EaseOut),
                towards = AnimatedContentTransitionScope.SlideDirection.End
            )
        },
        content = content
    )
}