package com.github.jameshnsears.cameraoverlay.view.common

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import timber.log.Timber

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommonTopAppBar(title: String, navController: NavController, navRoute: String) {
    TopAppBar(
        title = { Text(title, fontSize = 28.sp) },
        navigationIcon = {
            Timber.d("navRoute=%s", navRoute)

            IconButton(onClick = {
                navController.navigate(navRoute) {
                    popUpTo(CommonNavigation.SCREEN_MAIN)
                }
            }) {
                Icon(
                    imageVector = Icons.Outlined.ArrowBack,
                    contentDescription = null
                )
            }
        }
    )
}
