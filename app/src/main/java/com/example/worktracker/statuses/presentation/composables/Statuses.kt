package com.example.worktracker.statuses.presentation.composables


import android.annotation.SuppressLint
import android.content.Context
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.worktracker.R
import com.example.worktracker.common.Constants
import com.example.worktracker.statuses.presentation.model.StatusUI
import com.example.worktracker.statuses.presentation.viewmodel.add_status.AddStatusEvents
import com.example.worktracker.statuses.presentation.viewmodel.add_status.AddStatusViewModel
import com.example.worktracker.statuses.presentation.viewmodel.delete_status.DeleteStatusEvents
import com.example.worktracker.statuses.presentation.viewmodel.delete_status.DeleteStatusViewModel
import com.example.worktracker.statuses.presentation.viewmodel.get_statuses.GetStatusesViewModel
import com.example.worktracker.statuses.presentation.viewmodel.update_status.UpdateStatusEvents
import com.example.worktracker.statuses.presentation.viewmodel.update_status.UpdateStatusViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Statuses(navController: NavController,
             getStatusesViewModel: GetStatusesViewModel,
             addStatusViewModel: AddStatusViewModel,
             updateStatusViewModel: UpdateStatusViewModel,
             deleteStatusViewModel: DeleteStatusViewModel
)
{
    var showAddStatusDialog by remember { mutableStateOf(false) }


    val context = LocalContext.current.applicationContext
    val getStatusesState by getStatusesViewModel.state.collectAsStateWithLifecycle()

    var isRefreshing by remember { mutableStateOf(false) }

    LaunchedEffect(getStatusesState.error) {
        if (!getStatusesState.error.isNullOrBlank()) {
            Toast.makeText(context, getStatusesState.error, Toast.LENGTH_SHORT).show()
        }
    }

    ///////////////////////////////////////////////////////////////////////
    //add status

    val addStatusState by addStatusViewModel.addStatusState.collectAsStateWithLifecycle()

    //handling success response and error for adding status
    LaunchedEffect(Unit) {
        addStatusViewModel.addStatusEvents.collect { event ->
            when (event) {
                is AddStatusEvents.Success -> {
                    Toast.makeText(context, Constants.STATUS_IS_ADDED_SUCCESSFULLY, Toast.LENGTH_SHORT).show()
                    getStatusesViewModel.loadFirstPage()
                }
                is AddStatusEvents.ShowError -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    //////////////////////////////////////////////////////////////////////
    //update status
    val updateStatusState by updateStatusViewModel.updateStatusState.collectAsStateWithLifecycle()

    //handling success response and error for updating status
    LaunchedEffect(Unit) {
        updateStatusViewModel.updateStatusEvents.collect { event ->
            when (event) {
                is UpdateStatusEvents.Success -> {
                    Toast.makeText(context, Constants.STATUS_IS_UPDATED_SUCCESSFULLY, Toast.LENGTH_SHORT).show()
                    getStatusesViewModel.loadFirstPage()
                }
                is UpdateStatusEvents.ShowError -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
    //////////////////////////////////////////////////////////////////////
    //delete status
    val deleteStatusState by deleteStatusViewModel.deleteStatusState.collectAsStateWithLifecycle()

    //handling success response and error for deleting status
    LaunchedEffect(Unit) {
        deleteStatusViewModel.deleteStatusEvents.collect { event ->
            when (event) {
                is DeleteStatusEvents.Success -> {
                    Toast.makeText(context, Constants.STATUS_IS_DELETED_SUCCESSFULLY, Toast.LENGTH_SHORT).show()
                    getStatusesViewModel.loadFirstPage()
                }
                is DeleteStatusEvents.ShowError -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
    Scaffold(
        modifier = Modifier.fillMaxSize().statusBarsPadding().navigationBarsPadding(),
        topBar = {
            TopAppBar(
                modifier = Modifier.height(50.dp),
                title = {
                    Box(modifier = Modifier.fillMaxWidth().fillMaxHeight(), contentAlignment = Alignment.Center)
                    {
                        Text(
                            text = Constants.STATUSES,
                            fontWeight = FontWeight.Bold,
                            color = colorResource(R.color.primary_text_color),
                            textAlign = TextAlign.Center
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = colorResource(R.color.top_bar),
                    titleContentColor = colorResource(R.color.primary_text_color))
            )
        },
        floatingActionButton = {
            androidx.compose.material3.FloatingActionButton(
                onClick = {
                    showAddStatusDialog=true
                },
                contentColor = Color.White,
                containerColor = colorResource(R.color.color_1)
            ) {
                Icon(
                    Icons.Rounded.Add,
                    contentDescription = Constants.ADD_STATUS,
                    modifier = Modifier.size(26.dp),
                    tint = colorResource(R.color.white)
                )
            }
        }
    )

    { innerPadding ->

        Column(
            modifier = Modifier.fillMaxSize().padding(innerPadding)
                .background(color = colorResource(R.color.white)),
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
                        getStatusesViewModel.loadFirstPage()
                    }
                )
                {
                    val items = getStatusesState.statusesList

                    Box(modifier = Modifier.fillMaxSize())
                    {
                        // Show the list (always)
                        if (items.isNotEmpty()) {
                            Spacer(modifier = Modifier.fillMaxWidth().height(10.dp))
                            ShowList(
                                context,
                                list = items,
                                onLoadMore = { getStatusesViewModel.loadNextPage() },
                                updateStatusViewModel= updateStatusViewModel,
                                deleteStatusViewModel= deleteStatusViewModel
                            )
                        }

                        // Show loading as an OVERLAY (not replacing the list)
                        if (getStatusesState.isLoading ||
                            addStatusState.isLoading ||
                            updateStatusState.isLoading||
                            deleteStatusState.isLoading)
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
                        if (items.isEmpty() && !getStatusesState.isLoading) {
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(16.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Text(
                                    text = Constants.LOOKS_EMPTY_HERE,
                                    color = colorResource(R.color.primary_text_color)
                                )
                            }
                        }
                        isRefreshing = false
                    }
                }
            }

        }
    }

    //add status alert dialog
    if (showAddStatusDialog) {
        AddStatusDialog(
            onDismiss = {
                showAddStatusDialog = false
            },
            onAdd = { name ->
                showAddStatusDialog = false
                addStatusViewModel.addStatus(name)
            }
        )
    }
}

@SuppressLint("UseKtx", "ResourceAsColor")
@Composable
fun ShowList(
    context: Context,
    list: List<StatusUI>,
    onLoadMore: () -> Unit,
    updateStatusViewModel: UpdateStatusViewModel,
    deleteStatusViewModel: DeleteStatusViewModel
)
{
    val listState = rememberLazyListState()
    var showUpdateStatusDialog by remember { mutableStateOf(false) }
    var showStatusDetailsDialog by remember { mutableStateOf(false) }
    var showDeleteStatusDialog by remember { mutableStateOf(false) }
    var selectedStatus by remember { mutableStateOf<StatusUI?>(null) }


    //show data
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        state = listState)
    {
        itemsIndexed(
            items = list,
            key = { _, item -> item.id }
        ) { index, item ->

            // Pagination trigger when scrolling near bottom
            if (index >= list.size - 3) {
                onLoadMore()
            }

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp)
                    .clickable {
                        showStatusDetailsDialog= true
                        selectedStatus= item
                    },
                shape = RoundedCornerShape(7.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
                colors = CardDefaults.cardColors(
                    containerColor = colorResource(id = R.color.white)
                )
            )
            {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(5.dp)
                )
                {
                    Spacer(modifier = Modifier.fillMaxWidth().height(3.dp))

                    //name
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text(
                            text = item.name,
                            color = colorResource(R.color.primary_text_color),
                            fontSize = 20.sp
                        )

                        Icon(
                            Icons.Rounded.Edit,
                            contentDescription = Constants.EDIT_STATUS,
                            modifier = Modifier.size(26.dp).clickable{
                                selectedStatus= item
                                showUpdateStatusDialog= true
                            },
                            tint = colorResource(R.color.color_1)
                        )
                    }

                    Spacer(modifier = Modifier.fillMaxWidth().height(10.dp))

                    Row (modifier = Modifier.fillMaxWidth(),horizontalArrangement = Arrangement.End){

                        Column {

                            //updating date
                            if(item.updatedAt != "")
                            {
                                Text(
                                    text = Constants.UPDATED_AT + item.updatedAt,
                                    color = colorResource(R.color.secondary_text_color),
                                    fontSize = 15.sp,
                                    fontStyle = FontStyle.Italic
                                )
                            }

                            //creation date
                            Text(
                                text = Constants.CREATED_AT + item.createdAt,
                                color = colorResource(R.color.secondary_text_color),
                                fontSize = 15.sp,
                                fontStyle = FontStyle.Italic
                            )
                        }
                    }

                }
            }
        }
    }


    //add status alert dialog
    if (showUpdateStatusDialog) {

        selectedStatus?.let {
            UpdateStatusDialog(
                statusUI = selectedStatus!!,
                onDismiss = {
                    showUpdateStatusDialog = false
                },
                updateOne = { id,name ->
                    showUpdateStatusDialog = false
                    updateStatusViewModel.updateStatus(id,name)
                }
            )
        }
    }

    if (showStatusDetailsDialog)
    {
        selectedStatus?.let {
            ShowStatusDetailsDialog(
                statusUI = selectedStatus!!,
                onDismiss = {
                    showStatusDetailsDialog= false
                },
                onUpdate = {item->
                    showStatusDetailsDialog= false
                    showUpdateStatusDialog = true
                    selectedStatus = item
                },
                onDelete = {
                    showStatusDetailsDialog= false
                    showDeleteStatusDialog = true
                }
            )
        }
    }

    if(showDeleteStatusDialog)
    {
        selectedStatus?.let {
            DeleteStatusDialog (
                statusUI = selectedStatus!!,
                onDismiss = {
                    showDeleteStatusDialog = false
                },
                onDelete = { statusUi ->
                    showDeleteStatusDialog = false
                    deleteStatusViewModel.deleteStatus(statusUi.id)
                }
            )
        }
    }
}

@Composable
fun AddStatusDialog(
    onDismiss: () -> Unit,
    onAdd: (String) -> Unit
) {
    var name by remember { mutableStateOf("") }

    Dialog(onDismissRequest = onDismiss) {
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
                        text = Constants.ADD_STATUS,
                        style = MaterialTheme.typography.titleLarge,
                        color = colorResource(R.color.color_1)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text(Constants.STATUS_NAME) },
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(20.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {

                    Button(
                        onClick = {
                            onAdd(name.trim())
                        },
                        enabled = name.trim().isNotEmpty(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = colorResource(R.color.color_1),
                            contentColor = colorResource(R.color.white)
                        )
                    ) {
                        Text(Constants.ADD)
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    TextButton(onClick = onDismiss) {
                        Text(Constants.CANCEL, color = colorResource(R.color.color_1))
                    }
                }
            }
        }
    }
}

@Composable
fun UpdateStatusDialog(
    statusUI: StatusUI,
    onDismiss: () -> Unit,
    updateOne: (Int,String) -> Unit
) {
    var newName by remember { mutableStateOf(statusUI.name) }

    Dialog(onDismissRequest = onDismiss) {
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
                        text = Constants.UPDATE_STATUS,
                        style = MaterialTheme.typography.titleLarge,
                        color = colorResource(R.color.color_1)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = newName,
                    onValueChange = { newName = it },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text(Constants.STATUS_NAME) },
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(20.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {

                    Button(
                        onClick = {
                            updateOne(statusUI.id,newName.trim())
                        },
                        enabled = newName.trim().isNotEmpty(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = colorResource(R.color.color_1),
                            contentColor = colorResource(R.color.white)
                        )
                    ) {
                        Text(Constants.UPDATE)
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    TextButton(onClick = onDismiss) {
                        Text(Constants.CANCEL, color = colorResource(R.color.color_1))
                    }

                }
            }
        }
    }
}


@Composable
fun ShowStatusDetailsDialog(
    statusUI: StatusUI,
    onDismiss: () -> Unit,
    onUpdate: (StatusUI) -> Unit,
    onDelete: () -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Card(
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = colorResource(R.color.white)
            )
        ) {
            Column(
                modifier = Modifier
                    .padding(15.dp)
                    .fillMaxWidth()
            ) {

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center)
                {
                    Text(
                        text = Constants.STATUS_DETAILS,
                        style = MaterialTheme.typography.titleLarge,
                        color = colorResource(R.color.color_1)
                    )
                }


                Spacer(modifier = Modifier.height(10.dp))

                Row (modifier = Modifier.fillMaxWidth()){
                    Text(
                        text= Constants.NAME,
                        fontSize = 15.sp,
                        color = colorResource(R.color.secondary_text_color))

                    Text(text= statusUI.name,
                        fontSize = 15.sp,
                        color = colorResource(R.color.primary_text_color))
                }
                Spacer(modifier = Modifier.height(5.dp))

                if(statusUI.updatedAt != "")
                {
                    Row (modifier = Modifier.fillMaxWidth())
                    {
                        Text(
                            text = Constants.UPDATED_AT,
                            color = colorResource(R.color.secondary_text_color),
                            fontSize = 15.sp
                        )
                        Text(
                            text = statusUI.updatedAt,
                            color = colorResource(R.color.primary_text_color),
                            fontSize = 15.sp
                        )
                        Spacer(modifier = Modifier.height(5.dp))
                    }
                }
                Row (modifier = Modifier.fillMaxWidth())
                {
                    Text(
                        text = Constants.UPDATED_AT,
                        color = colorResource(R.color.secondary_text_color),
                        fontSize = 15.sp
                    )
                    Text(
                        text = statusUI.createdAt,
                        color = colorResource(R.color.primary_text_color),
                        fontSize = 15.sp
                    )
                }

                Spacer(modifier = Modifier.height(15.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {

                    Button(
                        onClick = {
                            onDelete()
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = colorResource(R.color.color_1),
                            contentColor = colorResource(R.color.white)
                        )
                    ) {
                        Text(Constants.DELETE)
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    Button(
                        onClick = {
                            onUpdate(statusUI)
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = colorResource(R.color.color_1),
                            contentColor = colorResource(R.color.white)
                        )
                    ) {
                        Text(Constants.UPDATE)
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    TextButton(onClick = onDismiss)
                    {
                        Text(Constants.CANCEL, color = colorResource(R.color.color_1))
                    }
                }
            }
        }
    }
}


@Composable
fun DeleteStatusDialog(
    statusUI: StatusUI,
    onDismiss: () -> Unit,
    onDelete: (StatusUI) -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
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
                        text = Constants.DELETE_STATUS,
                        style = MaterialTheme.typography.titleLarge,
                        color = colorResource(R.color.color_1)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = Constants.ARE_YOU_SURE_YOU_WANT_TO_DELETE_THIS_STATUS,
                    fontSize = 15.sp,
                    color = colorResource(R.color.primary_text_color)
                )

                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {

                    Button(
                        onClick = {
                            onDelete(statusUI)
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = colorResource(R.color.color_1),
                            contentColor = colorResource(R.color.white)
                        )
                    ) {
                        Text(Constants.DELETE)
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    TextButton(onClick = onDismiss) {
                        Text(Constants.CANCEL, color = colorResource(R.color.color_1))
                    }
                }
            }
        }
    }
}