package com.example.worktracker.home.presentation.composables

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.worktracker.R
import com.example.worktracker.common.Constants
import com.example.worktracker.contributors.presentation.composables.UploadImageFromCamera
import com.example.worktracker.contributors.presentation.composables.UploadImageFromGallery
import com.example.worktracker.contributors.presentation.viewmodel.get_contributors.GetContributorsViewModel
import com.example.worktracker.home.domain.model.WorkItem
import com.example.worktracker.home.presentation.utils.ShareWorkItemIdViewModel
import com.example.worktracker.home.presentation.viewmodel.get_work_item_by_id.GetWorkItemByIdViewModel
import com.example.worktracker.priorities.presentation.viewmodel.get_priorities.GetPrioritiesViewModel
import com.example.worktracker.statuses.presentation.viewmodel.get_statuses.GetStatusesViewModel
import com.example.worktracker.worktypes.presentation.viewmodel.get_worktypes.GetWorkTypesViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import java.nio.file.WatchEvent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorkItemDetails(navController: NavController,
                    getWorkItemByIdViewModel: GetWorkItemByIdViewModel,
                    shareWorkItemIdViewModel: ShareWorkItemIdViewModel,
                    getWorkTypesViewModel: GetWorkTypesViewModel,
                    getStatusesViewModel: GetStatusesViewModel,
                    getPrioritiesViewModel: GetPrioritiesViewModel,
                    getAssignersViewModel: GetContributorsViewModel,
                    getAssigneesViewModel: GetContributorsViewModel)
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
                            ShowInfo(
                                workItem = getWorkItemByIdResponse.workItem,
                                onChangingTitle = {
                                    newTitle= it
                                },
                                onChangingDescription = {
                                    newDescription= it
                                },
                                getWorkTypesViewModel = getWorkTypesViewModel,
                                getStatusesViewModel = getStatusesViewModel,
                                getPrioritiesViewModel = getPrioritiesViewModel,
                                getAssignersViewModel = getAssignersViewModel,
                                getAssigneesViewModel = getAssigneesViewModel)
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
             onChangingDescription: (String) -> Unit,
             getWorkTypesViewModel: GetWorkTypesViewModel,
             getStatusesViewModel: GetStatusesViewModel,
             getPrioritiesViewModel: GetPrioritiesViewModel,
             getAssignersViewModel: GetContributorsViewModel,
             getAssigneesViewModel: GetContributorsViewModel)
{

    var title by remember { mutableStateOf<String>(workItem.title) }
    var description by remember { mutableStateOf<String>(workItem.description) }

    Column(modifier = Modifier.fillMaxSize().padding(10.dp)) {

        //number
        Text(
            text = workItem.workItemNumber,
            fontSize = 25.sp,
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
                onChangingTitle(it) },
            modifier = Modifier.fillMaxWidth(),
            label = { Text(text = Constants.ADD_TITLE) },
            singleLine = false,
            maxLines = 2,
            shape = RoundedCornerShape(15.dp),
        )

        Spacer(modifier = Modifier.height(10.dp))

        //work type and priority
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly)
        {
            Card(modifier = Modifier.size(130.dp)
                .padding(10.dp).weight(1f)
                .clickable {

                },
                shape = RoundedCornerShape(20.dp),
                //elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
                colors = CardDefaults.cardColors(
                    containerColor = colorResource(id = R.color.white)
                ))
            {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxSize())
                {
                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = Constants.WORK_TYPE,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = colorResource(R.color.color_a)
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = workItem.workType?.name ?:"",
                        fontSize = 15.sp,
                        color = colorResource(R.color.color_b)
                    )

                    Row(modifier = Modifier.fillMaxSize().padding(0.dp, 0.dp,10.dp,10.dp),
                        horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.Bottom)
                    {
                        Icon(
                            Icons.Rounded.Edit,
                            contentDescription = Constants.EDIT_CONTRIBUTOR,
                            modifier = Modifier
                                .size(20.dp)
                                .clickable {
                                    //todo allow user to re-select
                                },
                            tint = colorResource(R.color.color_d)
                        )
                    }
                }
            }

            Card(modifier = Modifier.size(130.dp)
                .padding(10.dp).weight(1f)
                .clickable {

                },
                shape = RoundedCornerShape(20.dp),
                //elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
                colors = CardDefaults.cardColors(
                    containerColor = colorResource(id = R.color.white)
                ))
            {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxSize())
                {
                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = Constants.PRIORITY,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = colorResource(R.color.color_a)
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = workItem.priority?.name ?:"",
                        fontSize = 15.sp,
                        color = colorResource(R.color.color_b)
                    )
                    Row(modifier = Modifier.fillMaxSize().padding(0.dp, 0.dp,10.dp,10.dp),
                        horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.Bottom)
                    {
                        Icon(
                            Icons.Rounded.Edit,
                            contentDescription = Constants.EDIT_CONTRIBUTOR,
                            modifier = Modifier
                                .size(20.dp)
                                .clickable {
                                    //todo allow user to re-select
                                },
                            tint = colorResource(R.color.color_d)
                        )
                    }
                }
            }
        }

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

        //status
        Row(modifier = Modifier.fillMaxWidth())
        {
            Card(modifier = Modifier.size(130.dp)
                .padding(10.dp).weight(1f)
                .clickable {

                },
                shape = RoundedCornerShape(20.dp),
                //elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
                colors = CardDefaults.cardColors(
                    containerColor = colorResource(id = R.color.white)
                ))
            {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxSize())
                {
                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = Constants.STATUS,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = colorResource(R.color.color_a)
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = workItem.status?.name ?:"",
                        fontSize = 15.sp,
                        color = colorResource(R.color.color_b)
                    )

                    Row(modifier = Modifier.fillMaxSize().padding(0.dp, 0.dp,10.dp,10.dp),
                        horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.Bottom)
                    {
                        Icon(
                            Icons.Rounded.Edit,
                            contentDescription = Constants.EDIT_CONTRIBUTOR,
                            modifier = Modifier
                                .size(20.dp)
                                .clickable {
                                    //todo allow user to re-select
                                },
                            tint = colorResource(R.color.color_d)
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        //assignee and assigner
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly)
        {
            Card(modifier = Modifier.size(130.dp)
                .padding(10.dp).weight(1f)
                .clickable {

                },
                shape = RoundedCornerShape(20.dp),
                //elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
                colors = CardDefaults.cardColors(
                    containerColor = colorResource(id = R.color.white)
                ))
            {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxSize())
                {
                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = Constants.ASSIGNEE,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = colorResource(R.color.color_a)
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = workItem.assignee?.name ?: "",
                        fontSize = 15.sp,
                        color = colorResource(R.color.color_b)
                    )

                    Row(modifier = Modifier.fillMaxSize().padding(0.dp, 0.dp,10.dp,10.dp),
                        horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.Bottom)
                    {
                        Icon(
                            Icons.Rounded.Edit,
                            contentDescription = Constants.EDIT_CONTRIBUTOR,
                            modifier = Modifier
                                .size(20.dp)
                                .clickable {
                                    //todo allow user to re-select
                                },
                            tint = colorResource(R.color.color_d)
                        )
                    }
                }
            }

            Card(modifier = Modifier.size(130.dp)
                .padding(10.dp).weight(1f)
                .clickable {

                },
                shape = RoundedCornerShape(20.dp),
                //elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
                colors = CardDefaults.cardColors(
                    containerColor = colorResource(id = R.color.white)
                ))
            {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxSize())
                {
                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = Constants.ASSIGNER,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = colorResource(R.color.color_a)
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = workItem.assigner?.name ?: "",
                        fontSize = 15.sp,
                        color = colorResource(R.color.color_b)
                    )
                    Row(modifier = Modifier.fillMaxSize().padding(0.dp, 0.dp,10.dp,10.dp),
                        horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.Bottom)
                    {
                        Icon(
                            Icons.Rounded.Edit,
                            contentDescription = Constants.EDIT_CONTRIBUTOR,
                            modifier = Modifier
                                .size(20.dp)
                                .clickable {
                                    //todo allow user to re-select
                                },
                            tint = colorResource(R.color.color_d)
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(15.dp))

        //comments
    }
}

@SuppressLint("UseKtx")
@Composable
fun UpdateWorkType(
    onDismiss: () -> Unit,
    isUploading: Boolean,
    buttonState: Boolean,
    onIsUploadingChange: (Boolean) -> Unit,
)
{
    var name by remember { mutableStateOf("") }

    //image button state to prevent multiple clicks
    var imageButtonState by remember { mutableStateOf(buttonState) }

    var showImageUploaderGallery by remember { mutableStateOf(false) }
    var showImageUploaderCamera by remember { mutableStateOf(false) }
    var uploadedImageUrl by remember { mutableStateOf<String?>(null) }

    Dialog(
        onDismissRequest = {
            if (!isUploading) {
                onDismiss()
            }
        }
    ) {
        Box(
            modifier = Modifier.fillMaxWidth()
        ) {

            Card(
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = colorResource(R.color.white)
                )
            ) {
                Column(
                    modifier = Modifier
                        .padding(20.dp)
                        .fillMaxWidth()
                ) {

                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center)
                    {
                        Text(
                            text = Constants.ADD_CONTRIBUTOR,
                            style = MaterialTheme.typography.titleLarge,
                            color = colorResource(R.color.color_a),
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    //buttons
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    )
                    {
                        Button(
                            onClick = {
                                onAddContributor(name.trim(), uploadedImageUrl?.trim())
                            },
                            enabled = name.trim().isNotEmpty(),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = colorResource(R.color.color_a),
                                contentColor = colorResource(R.color.white)
                            )
                        ) {
                            Text(Constants.ADD)
                        }

                        Spacer(modifier = Modifier.width(8.dp))

                        TextButton(onClick = onDismiss) {
                            Text(Constants.CANCEL, color = colorResource(R.color.color_c))
                        }
                    }




                    Spacer(modifier = Modifier.height(20.dp))

                }
            }

            if (isUploading) {
                Box(
                    modifier = Modifier
                        .matchParentSize()
                        .background(Color.Black.copy(alpha = 0.15f)),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
        }
    }

    if (showImageUploaderGallery)
    {
        UploadImageFromGallery(
            onUploadStarted = {
                onIsUploadingChange(true)
            },
            onUploadFinished = { url ->
                uploadedImageUrl = url
                showImageUploaderGallery = false
                imageButtonState = true

                onIsUploadingChange(false)
                onButtonStateChange(imageButtonState)
            },
            onDismissRequest = {
                showImageUploaderGallery = false
                imageButtonState = true
            }
        )
    }
    if (showImageUploaderCamera)
    {
        UploadImageFromCamera(
            onUploadStarted = {
                onIsUploadingChange(true)
            },
            onUploadFinished = { url ->
                uploadedImageUrl = url
                showImageUploaderCamera = false
                imageButtonState = true

                onIsUploadingChange(false)
                onButtonStateChange(imageButtonState)
            },
            onDismissRequest = {
                showImageUploaderCamera = false
                imageButtonState = true
            }
        )
    }
}