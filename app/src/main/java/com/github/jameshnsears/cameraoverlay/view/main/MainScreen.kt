package com.github.jameshnsears.cameraoverlay.view.main

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.HelpOutline
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.github.jameshnsears.cameraoverlay.BuildConfig
import com.github.jameshnsears.cameraoverlay.R
import com.github.jameshnsears.cameraoverlay.model.permission.PermissionArea
import com.github.jameshnsears.cameraoverlay.view.common.CommonNavigation
import com.github.jameshnsears.cameraoverlay.view.main.about.AboutDialog
import com.github.jameshnsears.cameraoverlay.view.main.permission.PermissionButtonLocation
import com.github.jameshnsears.cameraoverlay.view.main.permission.PermissionButtonOverlay
import com.github.jameshnsears.cameraoverlay.view.main.permission.PermissionButtonStorage
import com.github.jameshnsears.cameraoverlay.view.main.permission.observeAsSate
import com.github.jameshnsears.cameraoverlay.view.theme.AppTheme
import com.github.jameshnsears.cameraoverlay.viewmodel.permission.ViewModelPermission
import timber.log.Timber

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    navController: NavController,
    viewModelPermission: ViewModelPermission
) {
    AppTheme {
        Scaffold(
            topBar = { AppBar() }
        ) { padding ->
            Column(
                modifier = Modifier
                    .padding(start = 8.dp, end = 8.dp, bottom = 5.dp)
                    .fillMaxWidth()
                    .padding(padding)
                    .verticalScroll(rememberScrollState())
            ) {
                Text(
                    stringResource(R.string.tag_line),
                    modifier = Modifier
                        .padding(start = 8.dp, bottom = 4.dp),
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )

                Usage()

                Permissions(navController, viewModelPermission)

                SelectPhoto(navController, viewModelPermission)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar() {
    val infoDialogState = remember { mutableStateOf(false) }

    if (infoDialogState.value) {
        AboutDialog(infoDialogState)
    }

    TopAppBar(
        title = { Text(stringResource(R.string.app_name), fontSize = 30.sp) },
        actions = {
            IconButton(
                onClick = {
                    infoDialogState.value = true
                }
            ) {
                Icon(Icons.Outlined.Info, contentDescription = "")
            }
        }
    )
}

@Composable
fun Usage() {
    Card(
        shape = RoundedCornerShape(20.dp),
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp
        )
    ) {
        Column(
            Modifier
                .padding(10.dp)
        ) {
            Column() {
                Text(
                    stringResource(R.string.main_screen_usage),
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp
                )
            }
            Text(
                stringResource(R.string.main_screen_usage_0),
                modifier = Modifier
                    .padding(vertical = 6.dp),

                )
            Text(
                stringResource(R.string.main_screen_usage_1),
                modifier = Modifier
                    .padding(vertical = 6.dp),
            )
            Text(
                stringResource(R.string.main_screen_usage_2),
                modifier = Modifier
                    .padding(vertical = 6.dp),
            )
        }
    }
}

@Composable
fun Permissions(
    navController: NavController,
    viewModelPermission: ViewModelPermission
) {
    Card(
        shape = RoundedCornerShape(20.dp),
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp
        )
    ) {
        Column(
            Modifier
                .padding(10.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    stringResource(R.string.permissions),
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp
                )

                IconButton(
                    onClick = {
                        navController.navigate(CommonNavigation.SCREEN_PERMISSIONS) {
                            launchSingleTop = true
                        }
                    }
                ) {
                    Icon(
                        imageVector = Icons.Outlined.HelpOutline,
                        contentDescription = "?"
                    )
                }
            }

            PermissionButtons(viewModelPermission)
        }
    }
}

@Composable
fun PermissionButtons(
    viewModelPermission: ViewModelPermission
) {
    val lifeCycleState = LocalLifecycleOwner.current.lifecycle.observeAsSate()
    Timber.d("lifeCycleState=${lifeCycleState.value.name}")

    Column {
        Row(
            Modifier.padding(6.dp)
        ) {
            PermissionButtonStorage(viewModelPermission)
        }
        Row(
            Modifier.padding(6.dp)
        )  {
            PermissionButtonOverlay(viewModelPermission)
        }
        Row(
            Modifier.padding(6.dp)
        )  {
            PermissionButtonLocation(viewModelPermission)
        }
    }
}

@Composable
fun SelectPhoto(
    navController: NavController,
    viewModelPermission: ViewModelPermission
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 10.dp, top = 8.dp),
        horizontalAlignment = Alignment.End
    ) {
        OutlinedButton(
            onClick = { navController.navigate(CommonNavigation.SCREEN_SELECT_PHOTO) },
            modifier = Modifier
                .size(width = 140.dp, height = 45.dp),
            shape = RoundedCornerShape(16.dp),
            enabled = enableButtonSelectPhoto(viewModelPermission)
        ) {
            Text(
                text = stringResource(R.string.select_photo)
            )
        }
    }
}

fun enableButtonSelectPhoto(viewModelPermission: ViewModelPermission): Boolean {
    if (BuildConfig.DEBUG) {
        return true
    }

    return !(
            !viewModelPermission.permissionButtonEnabled(PermissionArea.STORAGE) &&
                    !viewModelPermission.permissionButtonEnabled(PermissionArea.OVERLAY)
            )
}

@Preview(name = "Light Theme")
@Composable
fun PreviewMainScreenPortrait() {
    val context = LocalContext.current

    MainScreen(
        rememberNavController(),
        ViewModelPermission(context)
    )
}

@Preview(
    name = "Dark Theme, Landscape",
    widthDp = 720,
    heightDp = 720,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun PreviewMainScreenLandscape() {
    val context = LocalContext.current

    MainScreen(
        rememberNavController(),
        ViewModelPermission(context)
    )
}
