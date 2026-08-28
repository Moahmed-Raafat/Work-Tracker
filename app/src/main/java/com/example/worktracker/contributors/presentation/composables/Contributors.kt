package com.example.worktracker.contributors.presentation.composables

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
import com.example.worktracker.contributors.presentation.model.ContributorUI
import com.example.worktracker.contributors.presentation.viewmodel.add_contributor.AddContributorEvents
import com.example.worktracker.contributors.presentation.viewmodel.add_contributor.AddContributorViewModel
import com.example.worktracker.contributors.presentation.viewmodel.delete_contributor.DeleteContributorEvents
import com.example.worktracker.contributors.presentation.viewmodel.delete_contributor.DeleteContributorViewModel
import com.example.worktracker.contributors.presentation.viewmodel.get_contributors.GetContributorsViewModel
import com.example.worktracker.contributors.presentation.viewmodel.update_contributor.UpdateContributorEvents
import com.example.worktracker.contributors.presentation.viewmodel.update_contributor.UpdateContributorViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Contributors(navController: NavController,
                 getContributorsViewModel: GetContributorsViewModel,
                 addContributorViewModel: AddContributorViewModel,
                 updateContributorViewModel: UpdateContributorViewModel,
                 deleteContributorViewModel: DeleteContributorViewModel
)
{
    var showAddContributorDialog by remember { mutableStateOf(false) }


    val context = LocalContext.current.applicationContext
    val getContributorsState by getContributorsViewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        getContributorsViewModel.loadFirstPage()
    }

    var isRefreshing by remember { mutableStateOf(false) }

    LaunchedEffect(getContributorsState.error) {
        if (!getContributorsState.error.isNullOrBlank()) {
            Toast.makeText(context, getContributorsState.error, Toast.LENGTH_SHORT).show()
        }
    }

    ///////////////////////////////////////////////////////////////////////
    //add contributor

    val addContributorState by addContributorViewModel.addContributorState.collectAsStateWithLifecycle()

    //handling success response and error for adding contributor
    LaunchedEffect(Unit) {
        addContributorViewModel.addContributorEvents.collect { event ->
            when (event) {
                is AddContributorEvents.Success -> {
                    Toast.makeText(context, Constants.CONTRIBUTOR_IS_ADDED_SUCCESSFULLY, Toast.LENGTH_SHORT).show()
                    getContributorsViewModel.loadFirstPage()
                }
                is AddContributorEvents.ShowError -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    //////////////////////////////////////////////////////////////////////
    //update contributor
    val updateContributorState by updateContributorViewModel.updateContributorState.collectAsStateWithLifecycle()

    //handling success response and error for updating contributor
    LaunchedEffect(Unit) {
        updateContributorViewModel.updateContributorEvents.collect { event ->
            when (event) {
                is UpdateContributorEvents.Success -> {
                    Toast.makeText(context, Constants.CONTRIBUTOR_IS_UPDATED_SUCCESSFULLY, Toast.LENGTH_SHORT).show()
                    getContributorsViewModel.loadFirstPage()
                }
                is UpdateContributorEvents.ShowError -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
    //////////////////////////////////////////////////////////////////////
    //delete contributor
    val deleteContributorState by deleteContributorViewModel.deleteContributorState.collectAsStateWithLifecycle()

    //handling success response and error for deleting contributor
    LaunchedEffect(Unit) {
        deleteContributorViewModel.deleteContributorEvents.collect { event ->
            when (event) {
                is DeleteContributorEvents.Success -> {
                    Toast.makeText(context, Constants.CONTRIBUTOR_IS_DELETED_SUCCESSFULLY, Toast.LENGTH_SHORT).show()
                    getContributorsViewModel.loadFirstPage()
                }
                is DeleteContributorEvents.ShowError -> {
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
                            text = Constants.CONTRIBUTORS,
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
                    showAddContributorDialog=true
                },
                contentColor = Color.White,
                containerColor = colorResource(R.color.color_d)
            ) {
                Icon(
                    Icons.Rounded.Add,
                    contentDescription = Constants.ADD_CONTRIBUTOR,
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
                        getContributorsViewModel.loadFirstPage()
                    }
                )
                {
                    val items = getContributorsState.contributorsList

                    Box(modifier = Modifier.fillMaxSize())
                    {
                        // Show the list (always)
                        if (items.isNotEmpty()) {
                            Spacer(modifier = Modifier.fillMaxWidth().height(10.dp))
                            ShowList(
                                context,
                                list = items,
                                onLoadMore = { getContributorsViewModel.loadNextPage() },
                                updateContributorViewModel= updateContributorViewModel,
                                deleteContributorViewModel= deleteContributorViewModel
                            )
                        }

                        // Show loading as an OVERLAY (not replacing the list)
                        if (getContributorsState.isLoading ||
                            addContributorState.isLoading ||
                            updateContributorState.isLoading||
                            deleteContributorState.isLoading)
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
                        if (items.isEmpty() && !getContributorsState.isLoading) {
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

    //add contributor alert dialog
    if (showAddContributorDialog) {
        AddContributorDialog(
            onDismiss = {
                showAddContributorDialog = false
            },
            onAdd = { name ->
                showAddContributorDialog = false
                addContributorViewModel.addContributor(name)
            }
        )
    }
}

@SuppressLint("UseKtx", "ResourceAsColor")
@Composable
fun ShowList(
    context: Context,
    list: List<ContributorUI>,
    onLoadMore: () -> Unit,
    updateContributorViewModel: UpdateContributorViewModel,
    deleteContributorViewModel: DeleteContributorViewModel
)
{
    val listState = rememberLazyListState()
    var showUpdateContributorDialog by remember { mutableStateOf(false) }
    var showContributorDetailsDialog by remember { mutableStateOf(false) }
    var showDeleteConributorDialog by remember { mutableStateOf(false) }
    var selectedContributor by remember { mutableStateOf<ContributorUI?>(null) }


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
                        showContributorDetailsDialog= true
                        selectedContributor= item
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
                            contentDescription = Constants.EDIT_CONTRIBUTOR,
                            modifier = Modifier.size(26.dp).clickable{
                                selectedContributor= item
                                showUpdateContributorDialog= true
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

    //add contributor alert dialog
    if (showUpdateContributorDialog) {
        selectedContributor?.let {
            UpdateContributorDialog(
                contributorUI = selectedContributor!!,
                onDismiss = {
                    showUpdateContributorDialog = false
                },
                updateOne = { id,name ->
                    showUpdateContributorDialog = false
                    updateContributorViewModel.updateContributor(id,name)
                }
            )
        }
    }
    if(showContributorDetailsDialog)
    {
        selectedContributor?.let {
            ShowContributorDetailsDialog(contributorUI = selectedContributor!!,
                onDismiss = {
                    showContributorDetailsDialog = false
                },
                onUpdate = { item->
                    showContributorDetailsDialog = false
                    showUpdateContributorDialog = true
                    selectedContributor= item
                },
                onDelete = {
                    showContributorDetailsDialog = false
                    showDeleteConributorDialog = true
                }
            )
        }
    }

    if(showDeleteConributorDialog)
    {
        selectedContributor.let {
            DeleteContributorDialog(
                contributorUI = selectedContributor!!,
                onDismiss = {
                    showDeleteConributorDialog = false
                },
                onDelete = { contributorUi ->
                    showDeleteConributorDialog = false
                    deleteContributorViewModel.deleteContributor(contributorUi.id)
                }
            )
        }
    }
}

@Composable
fun AddContributorDialog(
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
                        text = Constants.ADD_CONTRIBUTOR,
                        style = MaterialTheme.typography.titleLarge,
                        color = colorResource(R.color.color_1)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text(Constants.CONTRIBUTOR_NAME) },
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
fun UpdateContributorDialog(
    contributorUI: ContributorUI,
    onDismiss: () -> Unit,
    updateOne: (Int,String) -> Unit
) {
    var newName by remember { mutableStateOf(contributorUI.name) }

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
                        text = Constants.UPDATE_CONTRIBUTOR,
                        style = MaterialTheme.typography.titleLarge,
                        color = colorResource(R.color.color_1)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = newName,
                    onValueChange = { newName = it },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text(Constants.CONTRIBUTOR_NAME) },
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(20.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {

                    Button(
                        onClick = {
                            updateOne(contributorUI.id,newName.trim())
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
fun ShowContributorDetailsDialog(
    contributorUI: ContributorUI,
    onDismiss: () -> Unit,
    onUpdate: (ContributorUI) -> Unit,
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
                        text = Constants.CONTRIBUTOR_DETAILS,
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

                    Text(text= contributorUI.name,
                        fontSize = 15.sp,
                        color = colorResource(R.color.primary_text_color))
                }
                Spacer(modifier = Modifier.height(5.dp))

                if(contributorUI.updatedAt != "")
                {
                    Row (modifier = Modifier.fillMaxWidth())
                    {
                        Text(
                            text = Constants.UPDATED_AT,
                            color = colorResource(R.color.secondary_text_color),
                            fontSize = 15.sp
                        )
                        Text(
                            text = contributorUI.updatedAt,
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
                        text = contributorUI.createdAt,
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
                            onUpdate(contributorUI)
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
fun DeleteContributorDialog(
    contributorUI: ContributorUI,
    onDismiss: () -> Unit,
    onDelete: (ContributorUI) -> Unit
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
                        text = Constants.DELETE_CONTRIBUTOR,
                        style = MaterialTheme.typography.titleLarge,
                        color = colorResource(R.color.color_1)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = Constants.ARE_YOU_SURE_YOU_WANT_TO_DELETE_THIS_CONTRIBUTOR,
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
                            onDelete(contributorUI)
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