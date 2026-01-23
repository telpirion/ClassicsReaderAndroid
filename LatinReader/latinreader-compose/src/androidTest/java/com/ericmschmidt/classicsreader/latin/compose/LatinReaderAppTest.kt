package com.ericmschmidt.classicsreader.latin.compose

import androidx.activity.ComponentActivity
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowSizeClass.Companion.calculateFromSize
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.filter
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.isSelectable
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.test.espresso.device.DeviceInteraction.Companion.setScreenOrientation
import androidx.test.espresso.device.EspressoDevice.Companion.onDevice
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.telpirion.compose.ui.ReaderApp
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import androidx.test.espresso.device.action.ScreenOrientation
import androidx.test.espresso.device.rules.ScreenOrientationRule

/**
 * Instrumented test.
 */
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

    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    @Test
    fun compactDevice_verifyUsingBottomNavigation() {
        // Set up compact window
        composeTestRule.setContent {
            ReaderApp(
                windowSizeClass = calculateFromSize(
                    size = DpSize(300.dp, 600.dp)
                )
            )
        }
        // Bottom navigation is displayed
        composeTestRule.onAllNodesWithText("Library")
            .filter(isSelectable()).assertCountEquals(2)
    }

    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    @Test
    fun deviceChangeOrientation() {
        onDevice().setScreenOrientation(ScreenOrientation.LANDSCAPE)
        composeTestRule.setContent {
            ReaderApp(
                windowSizeClass = calculateFromSize(
                    size = DpSize(300.dp, 600.dp)
                )
            )
        }
        composeTestRule.onNodeWithTag("NavRail").assertIsDisplayed()
        composeTestRule.onNodeWithTag("BottomBar").assertDoesNotExist()
    }
}