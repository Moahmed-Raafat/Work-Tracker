package com.example.worktracker.home.presentation.composables

import android.widget.Toast
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.worktracker.R
import com.example.worktracker.common.Constants
import com.example.worktracker.contributors.presentation.viewmodel.get_contributors.GetContributorsViewModel
import com.example.worktracker.home.presentation.viewmodel.GetWorkItemsViewModel
import com.example.worktracker.navigation.Screens
import com.example.worktracker.priorities.presentation.composables.ShowList
import com.example.worktracker.priorities.presentation.viewmodel.get_priorities.GetPrioritiesViewModel
import com.example.worktracker.statuses.presentation.viewmodel.get_statuses.GetStatusesViewModel
import com.example.worktracker.worktypes.presentation.viewmodel.get_worktypes.GetWorkTypesViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope
import androidx.compose.runtime.rememberCoroutineScope


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddWorkItem(navController: NavController,
                getWorkTypesViewModel: GetWorkTypesViewModel,
                getStatusesViewModel: GetStatusesViewModel,
                getPrioritiesViewModel: GetPrioritiesViewModel,
                getAssignersViewModel: GetContributorsViewModel,
                getAssigneesViewModel: GetContributorsViewModel)
{
    val context = LocalContext.current.applicationContext


    val getWorkTypesState by getWorkTypesViewModel.state.collectAsStateWithLifecycle()
    val getStatusesState by getStatusesViewModel.state.collectAsStateWithLifecycle()
    val getPrioritiesState by getPrioritiesViewModel.state.collectAsStateWithLifecycle()
    val getAssignersState by getAssignersViewModel.state.collectAsStateWithLifecycle()
    val getAssigneesState by getAssigneesViewModel.state.collectAsStateWithLifecycle()

    //todo: now when one api fails the other apis continue running. am i right?
    // the current code works like the approach i need but does the code is written in a good way or it is a work around


    LaunchedEffect(Unit) {
        getWorkTypesViewModel.loadFirstPage()
        getStatusesViewModel.loadFirstPage()
        getPrioritiesViewModel.loadFirstPage()
        getAssignersViewModel.loadFirstPage()
        getAssigneesViewModel.loadFirstPage()
    }

    //the refresh layout
    var isRefreshing by remember { mutableStateOf(false) }


    val errorMessage =
        getWorkTypesState.error
            ?: getStatusesState.error
            ?: getPrioritiesState.error
            ?: getAssignersState.error
            ?: getAssigneesState.error

    LaunchedEffect(errorMessage) {
        if (!errorMessage.isNullOrBlank()) {
            Toast.makeText(
                context,
                errorMessage,
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    val hasError =
        !getWorkTypesState.error.isNullOrBlank() ||
                !getStatusesState.error.isNullOrBlank() ||
                !getPrioritiesState.error.isNullOrBlank() ||
                !getAssignersState.error.isNullOrBlank() ||
                !getAssigneesState.error.isNullOrBlank()

    val isLoading = getWorkTypesState.isLoading ||
            getStatusesState.isLoading ||
            getPrioritiesState.isLoading ||
            getAssignersState.isLoading ||
            getAssigneesState.isLoading




    Scaffold(
        modifier = Modifier.fillMaxSize().statusBarsPadding().navigationBarsPadding(),
        topBar = {
            TopAppBar(
                modifier = Modifier.height(50.dp),
                title = {
                    Box(modifier = Modifier.fillMaxWidth().fillMaxHeight(), contentAlignment = Alignment.Center)
                    {
                        Text(
                            text = "Add Work Item",
                            fontWeight = FontWeight.Bold,
                            color = colorResource(R.color.white),
                            textAlign = TextAlign.Center
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = colorResource(R.color.color_a))
            )
        }
    )

    { innerPadding ->

        Column(
            modifier = Modifier.fillMaxSize().padding(innerPadding)
                .background(color = colorResource(R.color.background)),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally)
        {
            Box(modifier = Modifier
                .fillMaxSize()
                .padding(0.dp, 0.dp))
            {
                SwipeRefresh(
                    state = rememberSwipeRefreshState(isRefreshing),
                    onRefresh = {
                        isRefreshing = true

                        getWorkTypesViewModel.loadFirstPage()
                        getStatusesViewModel.loadFirstPage()
                        getPrioritiesViewModel.loadFirstPage()
                        getAssignersViewModel.loadFirstPage()
                        getAssigneesViewModel.loadFirstPage()

                        isRefreshing = false
                    }
                )
                {
                    val workTypesItems = getWorkTypesState.workTypesList
                    val prioritiesItems = getPrioritiesState.prioritiesList
                    val statusesItems = getStatusesState.statusesList
                    val assignersItems = getAssignersState.contributorsList
                    val assigneesItems = getAssigneesState.contributorsList

                    val canCreateWorkItem =
                        !isLoading &&
                                !hasError &&
                                workTypesItems.isNotEmpty() &&
                                prioritiesItems.isNotEmpty() &&
                                statusesItems.isNotEmpty() &&
                                assignersItems.isNotEmpty() &&
                                assigneesItems.isNotEmpty()


                    Box(modifier = Modifier.fillMaxSize())
                    {
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
                        else if (hasError) {
                            //todo give the user a button to retry the apis
                            Column(modifier = Modifier.fillMaxSize(),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center)
                            {
                                Text(
                                    text = "there is an error"
                                )
                            }
                        }
                        else if (canCreateWorkItem) {
                            //todo show the screen
                            Column(modifier = Modifier.fillMaxSize(),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center)
                            {
                                Text(
                                    text = "there is data"
                                )
                            }
                        }
                        else {
                            if(workTypesItems.isEmpty())
                            {
                                Column(modifier = Modifier.fillMaxSize(),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Center)
                                {
                                    Text(
                                        text = "no available work types",
                                        color = colorResource(R.color.color_c)
                                    )
                                    Button(
                                        onClick = {
                                            navController.navigate(Screens.WorkTypes.route)
                                        },
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = colorResource(R.color.color_a),
                                            contentColor = colorResource(R.color.white)
                                        )
                                    ) {
                                        Text(
                                            text = "go to work types screen",
                                            color = colorResource(R.color.color_c)
                                        )
                                    }
                                }
                            }
                            else if(prioritiesItems.isEmpty())
                            {
                                Column(modifier = Modifier.fillMaxSize(),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Center)
                                {
                                    Text(
                                        text = "no available priorities",
                                        color = colorResource(R.color.color_c)
                                    )
                                    Button(
                                        onClick = {
                                            navController.navigate(Screens.Priorities.route)
                                        },
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = colorResource(R.color.color_a),
                                            contentColor = colorResource(R.color.white)
                                        )
                                    ) {
                                        Text(
                                            text = "go to priorities screen",
                                            color = colorResource(R.color.color_c)
                                        )
                                    }
                                }
                            }
                            else if(statusesItems.isEmpty())
                            {
                                Column(modifier = Modifier.fillMaxSize(),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Center)
                                {
                                    Text(
                                        text = "no available statuses",
                                        color = colorResource(R.color.color_c)
                                    )
                                }
                                Button(
                                    onClick = {
                                        navController.navigate(Screens.Statuses.route)
                                    },
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = colorResource(R.color.color_a),
                                        contentColor = colorResource(R.color.white)
                                    )
                                ) {
                                    Text(
                                        text = "go to statuses screen",
                                        color = colorResource(R.color.color_c)
                                    )
                                }
                            }
                            else
                            {
                                Column(modifier = Modifier.fillMaxSize(),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Center)
                                {
                                    Text(
                                        text = "no available contributors",
                                        color = colorResource(R.color.color_c)
                                    )
                                }
                                Button(
                                    onClick = {
                                        navController.navigate(Screens.Contributors.route)
                                    },
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = colorResource(R.color.color_a),
                                        contentColor = colorResource(R.color.white)
                                    )
                                ) {
                                    Text(
                                        text = "go to contributors screen",
                                        color = colorResource(R.color.color_c)
                                    )
                                }
                            }
                        }







                    }
                }
            }

        }
    }
}

