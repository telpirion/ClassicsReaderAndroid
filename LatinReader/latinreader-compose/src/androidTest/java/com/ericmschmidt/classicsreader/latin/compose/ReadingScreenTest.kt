package com.ericmschmidt.classicsreader.latin.compose

import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.isDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTouchInput
import androidx.compose.ui.test.printToLog
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.device.action.ScreenOrientation
import androidx.test.espresso.device.filter.RequiresDisplay
import androidx.test.espresso.device.rules.ScreenOrientationRule
import androidx.test.espresso.device.sizeclass.HeightSizeClass
import androidx.test.espresso.device.sizeclass.WidthSizeClass
import com.telpirion.compose.MainActivity
import com.telpirion.compose.ui.BOTTOM_NAVIGATION_BAR_TAG
import com.telpirion.compose.ui.components.SUPPORTING_PANE_TAG
import com.telpirion.compose.ui.screens.READING_SCREEN_TAG
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ReadingScreenTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @get:Rule
    val screenOrientationRule: ScreenOrientationRule = ScreenOrientationRule(ScreenOrientation.PORTRAIT)

    @Before
    fun setup() {
        composeTestRule.onNodeWithTag(BOTTOM_NAVIGATION_BAR_TAG).assertIsDisplayed()
        composeTestRule.waitUntil {
            composeTestRule.onNode(
                hasText("De Bello Gallico", substring = true, ignoreCase = true))
                .isDisplayed()
        }

        val actualCaesarItem = composeTestRule.onNode(hasText("De Bello Gallico", substring = true, ignoreCase = true))
        actualCaesarItem.performClick()

        composeTestRule.waitUntil {
            composeTestRule.onNodeWithText("Read").isDisplayed()
        }
        composeTestRule.onNodeWithText("Read").performClick()

        composeTestRule.waitUntil {
            composeTestRule.onNodeWithTag(READING_SCREEN_TAG).isDisplayed()
        }
    }

    // This runs on a Pixel 9a phone in portrait
    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    @RequiresDisplay(widthSizeClass = WidthSizeClass.Companion.WidthSizeClassEnum.COMPACT,
        heightSizeClass = HeightSizeClass.Companion.HeightSizeClassEnum.EXPANDED)
    @Test
    fun compactDevicePortrait_verifySupportingPaneHeight() {
        val readingSurface = composeTestRule.onNodeWithTag(READING_SCREEN_TAG)
        readingSurface.assertIsDisplayed()

        val supportingPane = composeTestRule.onNodeWithTag(SUPPORTING_PANE_TAG)
        supportingPane.assertDoesNotExist()

        readingSurface.performClick()
        composeTestRule.waitUntil {
            composeTestRule
                .onNode(hasText("Switch", substring = true, ignoreCase = true))
                .isDisplayed()
        }

        composeTestRule.onNode(hasText("Switch", substring = true, ignoreCase = true))
            .performClick()
        composeTestRule.waitUntil {
            composeTestRule
                .onNodeWithTag(SUPPORTING_PANE_TAG).isDisplayed()
        }
        supportingPane.assertIsDisplayed()
    }
}