package com.example.worktracker.home.presentation.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DrawerState
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.worktracker.R
import com.example.worktracker.common.Constants
import kotlinx.coroutines.launch


@Composable
fun NavigationDrawerItem(
    navController: NavController,
    drawerState: DrawerState,
    label: String,
    icon: Int,
    route: String)
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
                contentDescription = label,
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
            navController.navigate(route) {
                launchSingleTop = true
                restoreState = true
            }
        },
    )
}

@Composable
fun SelectedFilter(
    label: String,
    value: String,
    onClear: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .border(
                    width = 0.5.dp,
                    color = colorResource(R.color.color_b)
                )
                .padding(1.dp)
                .clickable {
                    onClear()
                }
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "$label: $value",
                    fontSize = 15.sp,
                    color = colorResource(R.color.color_b)
                )

                Spacer(modifier = Modifier.width(3.dp))

                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Remove $label filter",
                    tint = colorResource(R.color.color_b),
                    modifier = Modifier.size(15.dp)
                )
            }
        }

        Spacer(modifier = Modifier.width(10.dp))
    }
}

@Composable
fun ShowFilterOption(
    label: String,
    isExpanded: Boolean,
    onClick: () -> Unit
) {
    Row(modifier = Modifier.fillMaxWidth().padding(10.dp,0.dp)
        .clickable{
            onClick()
        },
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically)
    {
        Text(
            text = label,
            color =
                if(isExpanded) colorResource(R.color.color_b)
                else  colorResource(R.color.color_a),
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold
        )
        Icon(
            imageVector =
                if(isExpanded) Icons.Default.KeyboardArrowDown
                else Icons.Default.KeyboardArrowUp,
            contentDescription = label,
            tint =
                if(isExpanded) colorResource(R.color.color_b)
                else  colorResource(R.color.color_a),
            modifier = Modifier.size(30.dp)
        )
    }
}

@Composable
fun <T> ShowFilterItems(
    items: List<T>,
    isLoading: Boolean,
    listState: LazyListState,
    key: (T) -> Any,
    itemText: (T) -> String,
    onLoadNextPage: () -> Unit,
    onItemSelected: (T) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
            .padding(horizontal = 20.dp)
            .border(
                width = 1.dp,
                color = colorResource(R.color.light_gray)
            )
            .background(colorResource(R.color.white))
            .padding(10.dp)
    ) {

        if (items.isNotEmpty()) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                state = listState
            ) {
                itemsIndexed(
                    items = items,
                    key = { _, item -> key(item) }
                ) { index, item ->

                    // Pagination trigger
                    if (index >= items.size - 3) {
                        onLoadNextPage()
                    }

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                onItemSelected(item)
                            }
                    ) {
                        Text(
                            text = itemText(item),
                            color = colorResource(R.color.color_e),
                            fontSize = 15.sp
                        )

                        Spacer(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(5.dp)
                        )
                    }
                }
            }
        }

        if (isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.1f)),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        if (items.isEmpty() && !isLoading) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = Constants.LOOKS_EMPTY_HERE,
                    color = colorResource(R.color.color_a)
                )
            }
        }
    }
}