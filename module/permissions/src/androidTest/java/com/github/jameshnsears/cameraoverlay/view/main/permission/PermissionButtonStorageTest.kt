package com.github.jameshnsears.cameraoverlay.view.main.permission

import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.github.jameshnsears.cameraoverlay.permissions.R
import com.github.jameshnsears.cameraoverlay.viewmodel.permission.ViewModelPermission
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class PermissionButtonStorageTest : PermissionButtonHelper() {
    private var buttonTextStorage: String = context.resources.getString(
        R.string.permissions_files_and_media
    )

    @Test
    fun allow() {
        composeTestRule.setContent {
            PermissionButtonStorage(ViewModelPermission(composeTestRule.activity.application))
        }

        composeTestRule.onNodeWithText(buttonTextStorage).performClick()

        denyPermissionInDialog()

        composeTestRule.onNodeWithText(buttonTextStorage).assertIsEnabled()

        composeTestRule.onNodeWithText(buttonTextStorage).performClick()

        pressButton(text = "Permissions")

        pressButton(text = "Storage")

        grantPermissionInDialogStorage()

        pressBack()
        pressBack()
        pressBack()

        composeTestRule.onNodeWithText(buttonTextStorage).assertIsNotEnabled()
    }
}
