package com.example.worktracker.home.presentation.composables

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.worktracker.R
import com.example.worktracker.common.Constants
import com.example.worktracker.home.presentation.utils.ShareWorkItemIdViewModel
import com.example.worktracker.home.presentation.viewmodel.get_work_item_by_id.GetWorkItemByIdViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorkItemDetails(navController: NavController,
                    getWorkItemByIdViewModel: GetWorkItemByIdViewModel,
                    shareWorkItemIdViewModel: ShareWorkItemIdViewModel)
{

    val workItemId= shareWorkItemIdViewModel.workItemId

    val context = LocalContext.current.applicationContext
    val getWorkItemDetailsState by getWorkItemByIdViewModel.getWorkItemByIdState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        workItemId?.let{
            getWorkItemByIdViewModel.getWorkItemById(workItemId)
        }
    }

    var isRefreshing by remember { mutableStateOf(false) }

    LaunchedEffect(getWorkItemDetailsState.error)
    {
        if(!getWorkItemDetailsState.error.isNullOrBlank())
        {
            Toast.makeText(context, getWorkItemDetailsState.error, Toast.LENGTH_SHORT).show()
        }
    }


    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding(),
        topBar = {
            TopAppBar(
                modifier = Modifier.height(50.dp),
                title = {
                    Box(modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(), contentAlignment = Alignment.Center)
                    {
                        Text(
                            text = Constants.WORK_ITEM_DETAILS,
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
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
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
                        workItemId?.let{
                            getWorkItemByIdViewModel.getWorkItemById(workItemId)
                        }
                    }
                )
                {
                    val getWorkItemByIdResponse= getWorkItemDetailsState.getWorkItemByIdResponse

                    Box(modifier = Modifier.fillMaxSize())
                    {
                        // Show the data
                        if (getWorkItemByIdResponse != null)
                        {
                            //todo Show the data
                            Toast.makeText(context,getWorkItemByIdResponse.workItem.workItemNumber,Toast.LENGTH_SHORT).show()
                        }

                        // Show loading
                        if (getWorkItemDetailsState.isLoading)
                        {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(Color.Black.copy(alpha = 0.1f)),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator()
                            }
                        }

                        // Show empty state (when not loading and empty)
                        if (getWorkItemByIdResponse == null && !getWorkItemDetailsState.isLoading) {
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
                        isRefreshing = false
                    }
                }
            }

        }
    }

}