package com.ericmschmidt.classicsreader.latin.compose.data

import androidx.compose.ui.input.key.Key
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.isDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performKeyInput
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.test.pressKey
import androidx.compose.ui.test.printToLog
import com.telpirion.compose.MainActivity
import com.telpirion.compose.ui.screens.READING_SCREEN_TAG
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class DictionaryTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setup() {
        composeTestRule.waitUntil {
            composeTestRule.onNode(
                hasText("De Bello Gallico", substring = true, ignoreCase = true)
            )
                .isDisplayed()
        }

        val actualCaesarItem = composeTestRule.onNode(
            hasText(
                "De Bello Gallico",
                substring = true,
                ignoreCase = true
            )
        )
        actualCaesarItem.performClick()

        composeTestRule.waitUntil {
            composeTestRule.onNodeWithText("Read").isDisplayed()
        }
        composeTestRule.onNodeWithText("Read").performClick()

        composeTestRule.waitUntil {
            composeTestRule.onNodeWithTag(READING_SCREEN_TAG).isDisplayed()
        }
    }

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun verifyPartialStemSearch() {
        val readingSurface = composeTestRule.onNodeWithTag(READING_SCREEN_TAG)
        readingSurface.assertIsDisplayed()
        readingSurface.printToLog(READING_SCREEN_TAG)

        val searchBox = composeTestRule.onNodeWithTag("SearchField")
        searchBox.performTextInput("fac-")
        searchBox.performKeyInput {
            pressKey(Key.Enter)
        }

        composeTestRule.waitUntilAtLeastOneExists(hasText("fac-"))

        composeTestRule.onAllNodes(hasText("fac-")).onFirst().printToLog("Result")
        composeTestRule.onNodeWithTag("ReadingContent").assertExists()

        composeTestRule.waitUntil(5000) {
            composeTestRule.onNode(hasText("adv.", substring = true)).isDisplayed()
        }

        composeTestRule.onNode(hasText("facile", substring = true)).isDisplayed()
    }
}