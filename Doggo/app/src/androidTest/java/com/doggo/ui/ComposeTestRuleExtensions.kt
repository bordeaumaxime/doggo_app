package com.doggo.ui

import androidx.compose.ui.test.SemanticsNodeInteraction
import androidx.compose.ui.test.SemanticsNodeInteractionCollection
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.compose.ui.test.onAllNodesWithContentDescription
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollToIndex

fun ComposeTestRule.assertTextDisplayed(
    text: String,
): SemanticsNodeInteraction {
    return onNodeWithText(
        text,
    ).assertIsDisplayed()
}

fun ComposeTestRule.assertTextDoesNotExist(
    text: String,
) {
    return onNodeWithText(
        text,
    ).assertDoesNotExist()
}

fun ComposeTestRule.clickText(
    text: String,
): SemanticsNodeInteraction {
    return assertTextDisplayed(text).performClick()
}

fun ComposeTestRule.assertContentDescriptionDisplayed(
    contentDescription: String,
): SemanticsNodeInteraction {
    return onNodeWithContentDescription(
        contentDescription,
    ).assertIsDisplayed()
}

fun ComposeTestRule.assertContentDescriptionDisplayed(
    contentDescription: String,
    count: Int
): SemanticsNodeInteractionCollection {
    return onAllNodesWithContentDescription(
        contentDescription,
    ).assertCountEquals(count)
}

fun ComposeTestRule.clickContentDescription(
    text: String,
): SemanticsNodeInteraction {
    return assertContentDescriptionDisplayed(text).performClick()
}

fun ComposeTestRule.assertTagDisplayed(
    tag: String
): SemanticsNodeInteraction {
    return onNodeWithTag(tag).assertIsDisplayed()
}

fun ComposeTestRule.assertItemsDisplayedInList(
    listTag: String,
    itemsTag: List<String>
) {
    itemsTag.forEachIndexed { index, s ->
        println(s)
        onNodeWithTag(listTag).performScrollToIndex(index)
        onNodeWithTag(s).assertIsDisplayed()
    }
}