package com.doggo.ui.screen.common

import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.intl.Locale

object BreedNameFormatter {

    /**
     * Format the breed name and sub breed name to be displayed in the UI.
     * The original names are in lowercase.
     *
     * It will work as follow:
     * - breed name: "hound" sub breed name = null -> "Hound"
     * - breed name: "hound" sub breed name = "english" -> "English Hound"
     */
    fun getBreedOrSubBreedFormattedName(breedName: String, subBreedName: String?): String {
        // we should not do that in a real app,
        // API should return well formatted text in the right language
        val formattedBreedName = breedName.capitalize(Locale.current)
        return if(subBreedName == null) {
            formattedBreedName
        } else {
            val formattedSubBreedName =
                subBreedName.capitalize(Locale.current)
            "$formattedSubBreedName $formattedBreedName"
        }
    }
}