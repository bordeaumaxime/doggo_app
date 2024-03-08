package com.doggo.ui

import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.SemanticsNodeInteraction
import androidx.compose.ui.test.SemanticsNodeInteractionCollection
import androidx.compose.ui.test.assertAll
import androidx.compose.ui.test.assertAny
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.invokeGlobalAssertions
import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.compose.ui.test.onAllNodesWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick

fun ComposeTestRule.assertTextDisplayed(
    text: String,
): SemanticsNodeInteraction {
    return onNodeWithText(
        text,
    ).assertIsDisplayed()
}

fun ComposeTestRule.clickText(
    text: String,
): SemanticsNodeInteraction {
    return onNodeWithText(
        text,
    ).assertIsDisplayed().performClick()
}

fun ComposeTestRule.assertContentDescriptionDisplayed(
    contentDescription: String,
    count: Int
): SemanticsNodeInteractionCollection {
    return onAllNodesWithContentDescription(
        contentDescription,
    ).assertCountEquals(count)
}

fun ComposeTestRule.assertTagDisplayed(
    tag: String
): SemanticsNodeInteraction {
    return onNodeWithTag(tag).assertIsDisplayed()
}