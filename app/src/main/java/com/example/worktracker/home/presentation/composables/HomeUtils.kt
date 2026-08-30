package com.example.worktracker.home.presentation.composables

import android.net.http.SslCertificate.restoreState
import androidx.compose.foundation.layout.size
import androidx.compose.material3.DrawerState
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.worktracker.R
import com.example.worktracker.common.Constants
import com.example.worktracker.home.presentation.composables.NavigationDrawerItem
import com.example.worktracker.navigation.Screens
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch


@Composable
fun NavigationDrawerItem(
    navController: NavController,
    drawerState: DrawerState,
    label: String,
    icon: Int)
{
    val coroutineScope = rememberCoroutineScope()
    NavigationDrawerItem(
        label = {
            Text(
                text = label,
                color = colorResource(R.color.color_a),
                fontSize = 15.sp
            )
        },
        selected = false,
        icon = {
            Icon(
                //todo change icon
                painter = painterResource(icon),
                contentDescription = Constants.WORK_TYPES,
                modifier = Modifier.size(30.dp),
                tint = colorResource(R.color.color_a)
            )
        },
        onClick = {
            //closing the navigation drawer
            coroutineScope.launch {
                //drawerState.close()
            }

            //navigating to selected screen
            navController.navigate(Screens.WorkTypes.route) {
                launchSingleTop = true
                restoreState = true
            }
        },
    )
}