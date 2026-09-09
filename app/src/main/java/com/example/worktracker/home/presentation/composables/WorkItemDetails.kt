package com.example.worktracker.home.presentation.composables

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.worktracker.R
import com.example.worktracker.common.Constants
import com.example.worktracker.home.domain.model.WorkItem
import com.example.worktracker.home.presentation.utils.ShareWorkItemIdViewModel
import com.example.worktracker.home.presentation.viewmodel.get_work_item_by_id.GetWorkItemByIdViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import java.nio.file.WatchEvent

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


    var newTitle by remember { mutableStateOf<String>("") }
    var newDescription by remember { mutableStateOf<String>("") }

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
                            ShowInfo(workItem = getWorkItemByIdResponse.workItem,
                                onChangingTitle = {
                                    newTitle= it
                                },
                                onChangingDescription = {
                                    newDescription= it
                                })
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

@Composable
fun ShowInfo(workItem: WorkItem,
             onChangingTitle: (String) -> Unit,
             onChangingDescription: (String) -> Unit)
{

    var title by remember { mutableStateOf<String>(workItem.title) }
    var description by remember { mutableStateOf<String>(workItem.description) }

    Column(modifier = Modifier.fillMaxSize().padding(10.dp)) {

        //number
        Text(
            text = workItem.workItemNumber,
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold,
            color = colorResource(R.color.color_a)
        )

        Spacer(modifier = Modifier.height(15.dp))

        //title
        Text(
            text = buildAnnotatedString {
                withStyle(style = SpanStyle(color = colorResource(R.color.color_a))){
                    append(Constants.TITLE) }
                withStyle(style = SpanStyle(color = Color.Red)){
                    append(" *") }
            },
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(2.dp))
        OutlinedTextField(
            value = title,
            onValueChange = {
                title = it
                onChangingTitle(it)
                            },
            modifier = Modifier.fillMaxWidth(),
            label = { Text(text = Constants.ADD_TITLE) },
            singleLine = false,
            maxLines = 2,
            shape = RoundedCornerShape(15.dp),
        )

        Spacer(modifier = Modifier.height(15.dp))

        //description
        Text(
            text = buildAnnotatedString {
                withStyle(style = SpanStyle(color = colorResource(R.color.color_a))){
                    append(Constants.DESCRIPTION) }
                withStyle(style = SpanStyle(color = Color.Red)){
                    append(" *") }
            },
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(2.dp))
        OutlinedTextField(
            value = description,
            onValueChange = {
                description = it
                onChangingTitle(it)
            },
            modifier = Modifier.fillMaxWidth().height(100.dp),
            label = { Text(text = Constants.ADD_DESCRIPTION) },
            singleLine = false,
            maxLines = 10,
            shape = RoundedCornerShape(15.dp),
            textStyle = LocalTextStyle.current.copy(
                textAlign = TextAlign.Start
            )
        )

        Spacer(modifier = Modifier.height(15.dp))

        //work type
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = Constants.WORK_TYPE + " :",
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = colorResource(R.color.color_a)
            )

            Spacer(modifier = Modifier.width(10.dp))

            Text(
                text = workItem.workType?.name ?:"",
                fontSize = 15.sp,
                color = colorResource(R.color.color_b)
            )

        }

        Spacer(modifier = Modifier.height(15.dp))

        //priority
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = Constants.PRIORITY + " :",
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = colorResource(R.color.color_a)
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = workItem.priority?.name ?:"",
                fontSize = 15.sp,
                color = colorResource(R.color.color_b)
            )
        }

        Spacer(modifier = Modifier.height(15.dp))

        //status
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = Constants.STATUS + " :",
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = colorResource(R.color.color_a)
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = workItem.status?.name ?:"",
                fontSize = 15.sp,
                color = colorResource(R.color.color_b)
            )
        }

        Spacer(modifier = Modifier.height(15.dp))

        //assignee
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = Constants.ASSIGNEE + " :",
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = colorResource(R.color.color_a)
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = workItem.assignee?.name ?: "",
                fontSize = 15.sp,
                color = colorResource(R.color.color_b)
            )
        }

        Spacer(modifier = Modifier.height(15.dp))

        //assigner
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = Constants.ASSIGNER + " :",
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = colorResource(R.color.color_a)
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = workItem.assigner?.name ?: "",
                fontSize = 15.sp,
                color = colorResource(R.color.color_b)
            )
        }

        Spacer(modifier = Modifier.height(15.dp))
    }


}