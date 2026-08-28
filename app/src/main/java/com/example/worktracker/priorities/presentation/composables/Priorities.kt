package com.example.worktracker.priorities.presentation.composables

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
import com.example.worktracker.priorities.presentation.model.PriorityUI
import com.example.worktracker.priorities.presentation.viewmodel.add_priority.AddPriorityEvents
import com.example.worktracker.priorities.presentation.viewmodel.add_priority.AddPriorityViewModel
import com.example.worktracker.priorities.presentation.viewmodel.delete_priority.DeletePriorityEvents
import com.example.worktracker.priorities.presentation.viewmodel.delete_priority.DeletePriorityViewModel
import com.example.worktracker.priorities.presentation.viewmodel.get_priorities.GetPrioritiesViewModel
import com.example.worktracker.priorities.presentation.viewmodel.update_priority.UpdatePriorityEvents
import com.example.worktracker.priorities.presentation.viewmodel.update_priority.UpdatePriorityViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Priorities(navController: NavController,
               getPrioritiesViewModel: GetPrioritiesViewModel,
               addPriorityViewModel: AddPriorityViewModel,
               updatePriorityViewModel: UpdatePriorityViewModel,
               deletePriorityViewModel: DeletePriorityViewModel
)
{
    var showAddPriorityDialog by remember { mutableStateOf(false) }

    val context = LocalContext.current.applicationContext
    val getPrioritiesState by getPrioritiesViewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        getPrioritiesViewModel.loadFirstPage()
    }
    var isRefreshing by remember { mutableStateOf(false) }

    LaunchedEffect(getPrioritiesState.error) {
        if (!getPrioritiesState.error.isNullOrBlank()) {
            Toast.makeText(context, getPrioritiesState.error, Toast.LENGTH_SHORT).show()
        }
    }

    ///////////////////////////////////////////////////////////////////////
    //add Priority

    val addPriorityState by addPriorityViewModel.addPriorityState.collectAsStateWithLifecycle()

    //handling success response and error for adding Priority
    LaunchedEffect(Unit) {
        addPriorityViewModel.addPriorityEvents.collect { event ->
            when (event) {
                is AddPriorityEvents.Success -> {
                    Toast.makeText(context, Constants.PRIORITY_IS_ADDED_SUCCESSFULLY, Toast.LENGTH_SHORT).show()
                    getPrioritiesViewModel.loadFirstPage()
                }
                is AddPriorityEvents.ShowError -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    //////////////////////////////////////////////////////////////////////
    //update Priority
    val updatePriorityState by updatePriorityViewModel.updatePriorityState.collectAsStateWithLifecycle()

    //handling success response and error for updating Priority
    LaunchedEffect(Unit) {
        updatePriorityViewModel.updatePriorityEvents.collect { event ->
            when (event) {
                is UpdatePriorityEvents.Success -> {
                    Toast.makeText(context, Constants.PRIORITY_IS_UPDATED_SUCCESSFULLY, Toast.LENGTH_SHORT).show()
                    getPrioritiesViewModel.loadFirstPage()
                }
                is UpdatePriorityEvents.ShowError -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
    //////////////////////////////////////////////////////////////////////
    //delete Priority
    val deletePriorityState by deletePriorityViewModel.deletePriorityState.collectAsStateWithLifecycle()

    //handling success response and error for deleting Priority
    LaunchedEffect(Unit) {
        deletePriorityViewModel.deletePriorityEvents.collect { event ->
            when (event) {
                is DeletePriorityEvents.Success -> {
                    Toast.makeText(context, Constants.PRIORITY_IS_DELETED_SUCCESSFULLY, Toast.LENGTH_SHORT).show()
                    getPrioritiesViewModel.loadFirstPage()
                }
                is DeletePriorityEvents.ShowError -> {
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
                            text = Constants.PRIORITIES,
                            fontWeight = FontWeight.Bold,
                            color = colorResource(R.color.white),
                            textAlign = TextAlign.Center
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = colorResource(R.color.color_a))
            )
        },
        floatingActionButton = {
            androidx.compose.material3.FloatingActionButton(
                onClick = {
                    showAddPriorityDialog=true
                },
                contentColor = Color.White,
                containerColor = colorResource(R.color.color_d)
            ) {
                Icon(
                    Icons.Rounded.Add,
                    contentDescription = Constants.ADD_PRIORITY,
                    modifier = Modifier.size(26.dp),
                    tint = colorResource(R.color.color_a)
                )
            }
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
                        getPrioritiesViewModel.loadFirstPage()
                    }
                )
                {
                    val items = getPrioritiesState.prioritiesList

                    Box(modifier = Modifier.fillMaxSize())
                    {
                        // Show the list (always)
                        if (items.isNotEmpty()) {
                            Spacer(modifier = Modifier.fillMaxWidth().height(10.dp))
                            ShowList(
                                context,
                                list = items,
                                onLoadMore = { getPrioritiesViewModel.loadNextPage() },
                                updatePriorityViewModel= updatePriorityViewModel,
                                deletePriorityViewModel= deletePriorityViewModel
                            )
                        }

                        // Show loading as an OVERLAY (not replacing the list)
                        if (getPrioritiesState.isLoading ||
                            addPriorityState.isLoading ||
                            updatePriorityState.isLoading||
                            deletePriorityState.isLoading)
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
                        if (items.isEmpty() && !getPrioritiesState.isLoading) {
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

    //add Priority alert dialog
    if (showAddPriorityDialog) {
        AddPriorityDialog(
            onDismiss = {
                showAddPriorityDialog = false
            },
            onAdd = { name ->
                showAddPriorityDialog = false
                addPriorityViewModel.addPriority(name)
            }
        )
    }
}

@SuppressLint("UseKtx", "ResourceAsColor")
@Composable
fun ShowList(
    context: Context,
    list: List<PriorityUI>,
    onLoadMore: () -> Unit,
    updatePriorityViewModel: UpdatePriorityViewModel,
    deletePriorityViewModel: DeletePriorityViewModel
)
{
    val listState = rememberLazyListState()
    var showUpdatePriorityDialog by remember { mutableStateOf(false) }
    var showPriorityDetailsDialog by remember { mutableStateOf(false) }
    var showDeletePriorityDialog by remember { mutableStateOf(false) }
    var selectedPriority by remember { mutableStateOf<PriorityUI?>(null) }


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
                        showPriorityDetailsDialog= true
                        selectedPriority= item
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
                            contentDescription = Constants.EDIT_PRIORITY,
                            modifier = Modifier.size(26.dp).clickable{
                                selectedPriority= item
                                showUpdatePriorityDialog= true
                            },
                            tint = colorResource(R.color.color_d)
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
                                    color = colorResource(R.color.muted_gray),
                                    fontSize = 15.sp,
                                    fontStyle = FontStyle.Italic
                                )
                            }

                            //creation date
                            Text(
                                text = Constants.CREATED_AT + item.createdAt,
                                color = colorResource(R.color.muted_gray),
                                fontSize = 15.sp,
                                fontStyle = FontStyle.Italic
                            )
                        }
                    }

                }
            }
        }
    }


    //add Priority alert dialog
    if (showUpdatePriorityDialog) {

        selectedPriority?.let {
            UpdatePriorityDialog(
                priorityUI = selectedPriority!!,
                onDismiss = {
                    showUpdatePriorityDialog = false
                },
                updateOne = { id,name ->
                    showUpdatePriorityDialog = false
                    updatePriorityViewModel.updatePriority(id,name)
                }
            )
        }
    }

    if (showPriorityDetailsDialog)
    {
        selectedPriority?.let {
            ShowPriorityDetailsDialog(
                priorityUI = selectedPriority!!,
                onDismiss = {
                    showPriorityDetailsDialog= false
                },
                onUpdate = {item->
                    showPriorityDetailsDialog= false
                    showUpdatePriorityDialog = true
                    selectedPriority = item
                },
                onDelete = {
                    showPriorityDetailsDialog= false
                    showDeletePriorityDialog = true
                }
            )
        }
    }

    if(showDeletePriorityDialog)
    {
        selectedPriority?.let {
            DeletePriorityDialog (
                priorityUI = selectedPriority!!,
                onDismiss = {
                    showDeletePriorityDialog = false
                },
                onDelete = { priorityUi ->
                    showDeletePriorityDialog = false
                    deletePriorityViewModel.deletePriority(priorityUi.id)
                }
            )
        }
    }
}

@Composable
fun AddPriorityDialog(
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
                        text = Constants.ADD_PRIORITY,
                        style = MaterialTheme.typography.titleLarge,
                        color = colorResource(R.color.color_1)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text(Constants.PRIORITY_NAME) },
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
fun UpdatePriorityDialog(
    priorityUI: PriorityUI,
    onDismiss: () -> Unit,
    updateOne: (Int,String) -> Unit
) {
    var newName by remember { mutableStateOf(priorityUI.name) }

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
                        text = Constants.UPDATE_PRIORITY,
                        style = MaterialTheme.typography.titleLarge,
                        color = colorResource(R.color.color_1)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = newName,
                    onValueChange = { newName = it },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text(Constants.PRIORITY_NAME) },
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(20.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {

                    Button(
                        onClick = {
                            updateOne(priorityUI.id,newName.trim())
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
fun ShowPriorityDetailsDialog(
    priorityUI: PriorityUI,
    onDismiss: () -> Unit,
    onUpdate: (PriorityUI) -> Unit,
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
                        text = Constants.PRIORITY_DETAILS,
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

                    Text(text= priorityUI.name,
                        fontSize = 15.sp,
                        color = colorResource(R.color.primary_text_color))
                }
                Spacer(modifier = Modifier.height(5.dp))

                if(priorityUI.updatedAt != "")
                {
                    Row (modifier = Modifier.fillMaxWidth())
                    {
                        Text(
                            text = Constants.UPDATED_AT,
                            color = colorResource(R.color.secondary_text_color),
                            fontSize = 15.sp
                        )
                        Text(
                            text = priorityUI.updatedAt,
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
                        text = priorityUI.createdAt,
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
                            onUpdate(priorityUI)
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
fun DeletePriorityDialog(
    priorityUI: PriorityUI,
    onDismiss: () -> Unit,
    onDelete: (PriorityUI) -> Unit
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
                        text = Constants.DELETE_PRIORITY,
                        style = MaterialTheme.typography.titleLarge,
                        color = colorResource(R.color.color_1)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = Constants.ARE_YOU_SURE_YOU_WANT_TO_DELETE_THIS_PRIORITY,
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
                            onDelete(priorityUI)
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