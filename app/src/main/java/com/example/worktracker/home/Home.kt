package com.example.worktracker.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.worktracker.R
import com.example.worktracker.common.Constants
import com.example.worktracker.navigation.Screens
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Home(navController: NavController)
{
    val context = LocalContext.current.applicationContext

    //navigation drawer
    val coroutineScope = rememberCoroutineScope()
    val drawerState = rememberDrawerState( initialValue = DrawerValue.Closed )

    //navigation drawer
    ModalNavigationDrawer(
        drawerState =drawerState,
        gesturesEnabled = true,
        drawerContent = {
            ModalDrawerSheet {
                Box(modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .background(colorResource(R.color.white)),
                )
                {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center) {

                        Image(
                            painter = painterResource(id = R.drawable.app_icon),
                            contentDescription = "",
                            modifier = Modifier.height(70.dp).width(70.dp)
                        )
                        Spacer(modifier = Modifier.fillMaxWidth().height(10.dp))
                        Text(
                            text = Constants.BUG_TRACKER,
                            color = colorResource(R.color.primary_text_color),
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp)
                    }
                }

                Spacer(modifier = Modifier.fillMaxWidth().height(20.dp))

                //contributors
                NavigationDrawerItem(

                    label = { Text( text = Constants.CONTRIBUTORS , color = colorResource(R.color.primary_text_color), fontSize = 15.sp)},
                    selected = false,
                    icon = {
                        Icon(
                            painter = painterResource(R.drawable.contributor) ,
                            contentDescription= Constants.CONTRIBUTORS,
                            modifier = Modifier.size(30.dp)) },
                    onClick= {
                        //closing the navigation drawer
                        coroutineScope.launch{
                            drawerState.close()
                        }

                        //navigating to selected screen
                        navController.navigate(Screens.Contributors.route){
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                )

                //statuses
                NavigationDrawerItem(

                    label = { Text( text = Constants.STATUSES , color = colorResource(R.color.primary_text_color), fontSize = 15.sp)},
                    selected = false,
                    icon = {Icon(painter = painterResource(R.drawable.status),
                        contentDescription= Constants.STATUSES,
                        modifier = Modifier.size(30.dp)) },
                    onClick= {
                        //closing the navigation drawer
                        coroutineScope.launch{
                            drawerState.close()
                        }

                        //navigating to selected screen
                        navController.navigate(Screens.Statuses.route){
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                )

                //priorities
                NavigationDrawerItem(

                    label = {
                        Text(text = Constants.PRIORITIES , color = colorResource(R.color.primary_text_color), fontSize = 15.sp)
                            },
                    selected = false,
                    icon = {Icon(painter = painterResource(R.drawable.priorities),
                        contentDescription= Constants.PRIORITIES,
                        modifier = Modifier.size(30.dp)) },
                    onClick= {
                        //closing the navigation drawer
                        coroutineScope.launch{
                            drawerState.close()
                        }

                        //navigating to selected screen
                        navController.navigate(Screens.Priorities.route){
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                )


            }

        })
    {
        Scaffold(
            modifier = Modifier.fillMaxSize().statusBarsPadding().navigationBarsPadding(),
            topBar = {
                //val coroutineScope = rememberCoroutineScope()
                TopAppBar(
                    modifier = Modifier.height(50.dp),
                    title = {
                        Box(modifier = Modifier.fillMaxWidth().fillMaxHeight().padding(0.dp,0.dp,10.dp,0.dp),
                            contentAlignment = Alignment.CenterEnd)
                        {
                            Text(
                                text = Constants.DASHBOARD,
                                fontWeight = FontWeight.Bold,
                                color = colorResource(R.color.primary_text_color)
                            )
                        }
                    },
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                        containerColor = colorResource(R.color.top_bar),
                        titleContentColor = colorResource(R.color.primary_text_color),
                        navigationIconContentColor = Color.White
                    ),
                    navigationIcon = {
                        IconButton(onClick = {
                            coroutineScope.launch {
                                drawerState.open()
                            }
                        })
                        {
                            Icon(imageVector = Icons.Rounded.Menu,
                                contentDescription = "",
                                tint = colorResource(R.color.primary_text_color))
                        }
                    },
                    actions = {
                        /*IconButton(onClick = { *//*Do Something*//* })
                        {
                            Icon(imageVector = Icons.Filled.Share,
                                contentDescription = "share",
                                tint = Color.White)
                        }
                        IconButton(onClick = { *//*Do Something*//* })
                        {
                            Icon(imageVector = Icons.Filled.Settings,
                                contentDescription = "settings",
                                tint = Color.White)
                        }*/
                    }
                )
            }
        )

        { innerPadding ->

            Column(
                modifier = Modifier.fillMaxSize().padding(innerPadding),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally)
            {
                Text(text = "bugs list")
            }

        }
    }
}

