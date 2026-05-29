package com.ericmschmidt.classicsreader.latin.compose

import androidx.activity.ComponentActivity
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowSizeClass.Companion.calculateFromSize
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.getUnclippedBoundsInRoot
import androidx.compose.ui.test.isDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.width
import androidx.test.espresso.device.DeviceInteraction.Companion.setScreenOrientation
import androidx.test.espresso.device.EspressoDevice.Companion.onDevice
import androidx.test.espresso.device.action.ScreenOrientation
import androidx.test.espresso.device.rules.ScreenOrientationRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.telpirion.compose.ui.ReaderApp
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test.
 */
@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@RunWith(AndroidJUnit4::class)
class LatinReaderAppTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @get:Rule
    val screenOrientationRule: ScreenOrientationRule = ScreenOrientationRule(ScreenOrientation.PORTRAIT)

    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.ericmschmidt.classicsreader.latin.compose", appContext.packageName)
    }

    @Test
    fun changeDeviceOrientation() {
        onDevice().setScreenOrientation(ScreenOrientation.LANDSCAPE)
        composeTestRule.setContent {
            ReaderApp(
                windowSizeClass = calculateFromSize(
                    size = DpSize(300.dp, 600.dp)
                )
            )
        }
        composeTestRule.onNodeWithTag("NavRail").assertIsDisplayed()
    }

    @Test
    fun stretchAppToFullWidthInLandscape() {
        onDevice().setScreenOrientation(ScreenOrientation.LANDSCAPE)
        composeTestRule.setContent {
            ReaderApp(
                windowSizeClass = calculateFromSize(
                    size = DpSize(300.dp,
                        600.dp)
                )
            )
        }
        val expectedWidth = composeTestRule.activity.resources.configuration.screenWidthDp.toFloat()
        val actualWidth = composeTestRule.onRoot().getUnclippedBoundsInRoot().width.value
        assertEquals(expectedWidth, actualWidth, 1.0f)
    }

    @Test
    fun verifyItemsAndButtonsDoNotStretch() {
        // 1. Launch App in Portrait
        composeTestRule.setContent {
            ReaderApp(
                windowSizeClass = calculateFromSize(
                    size = DpSize(300.dp, 600.dp)
                )
            )
        }

        // 2. Measure length of items in Library screen
        // Find "De Bello Gallico" item
        val listItemNode = composeTestRule.onAllNodesWithText("De Bello Gallico").onFirst()
        listItemNode.assertIsDisplayed()
        // 3. Measure length of buttons on details pane
        listItemNode.performClick()

        val buttonNode = composeTestRule.onAllNodesWithText("Read").onFirst()
        buttonNode.assertIsDisplayed()
        val buttonWidthPortrait = buttonNode.getUnclippedBoundsInRoot().width.value

        // 4. Change orientation to landscape
        onDevice().setScreenOrientation(ScreenOrientation.LANDSCAPE)
        composeTestRule.setContent {
            ReaderApp(
                windowSizeClass = calculateFromSize(
                    size = DpSize(600.dp, 300.dp)
                )
            )
        }

        // 5. Measure length of items in Library screen
        // App restarts, so we are at Library List.
        val listItemNodeLandscape = composeTestRule.onAllNodesWithText("De Bello Gallico").onFirst()
        listItemNodeLandscape.assertIsDisplayed()
        val listItemWidthLandscape = listItemNodeLandscape.getUnclippedBoundsInRoot().width.value

        // 6. Measure length of buttons on details pane
        listItemNodeLandscape.performClick()
        val buttonNodeLandscape = composeTestRule.onAllNodesWithText("Read").onFirst()
        buttonNodeLandscape.assertIsDisplayed()
        val buttonWidthLandscape = buttonNodeLandscape.getUnclippedBoundsInRoot().width.value

        // 7. Verify items and buttons haven't stretched beyond reasonable tolerance
        // Button should be fixed width (120dp)
        assertEquals("Button width should be constant", buttonWidthPortrait, buttonWidthLandscape, 5.0f)
        assertEquals("Button width should be approx 120dp", 120.0f, buttonWidthLandscape, 10.0f)

        // List items should not fill the entire width in landscape
        val screenWidth = composeTestRule.onRoot().getUnclippedBoundsInRoot().width.value
        listItemNodeLandscape.assertExists()
        assert(listItemWidthLandscape < screenWidth)
    }

    @Test
    fun verifyTextContinuityAfterOrientationChange() {
        composeTestRule.setContent {
            ReaderApp(
                windowSizeClass = calculateFromSize(
                    size = DpSize(300.dp, 600.dp)
                )
            )
        }

        val dictionarySearchField = composeTestRule.onNodeWithTag("SearchField")
        dictionarySearchField.assertIsDisplayed()

        dictionarySearchField.performTextInput("facio")

        onDevice().setScreenOrientation(ScreenOrientation.LANDSCAPE)
        composeTestRule.setContent {
            ReaderApp(
                windowSizeClass = calculateFromSize(
                    size = DpSize(600.dp, 300.dp)
                )
            )
        }

        composeTestRule.waitUntil {
            composeTestRule.onNodeWithTag("SearchField").isDisplayed()
        }
        dictionarySearchField.assertTextContains("facio")
    }
}