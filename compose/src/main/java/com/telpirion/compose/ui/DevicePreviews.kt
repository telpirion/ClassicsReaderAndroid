package com.telpirion.compose.ui

import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview

/**
 * Multipreview annotation that represents various device sizes. Add this annotation to a composable
 * to render various devices.
 */
@Preview(name = "phone", device = Devices.PHONE, showBackground = true)
@Preview(name = "foldable", device = Devices.FOLDABLE, showBackground = true)
@Preview(name = "tablet", device = Devices.TABLET, showBackground = true)
@Preview(name = "desktop", device = Devices.DESKTOP, showBackground = true)
annotation class DevicePreviews