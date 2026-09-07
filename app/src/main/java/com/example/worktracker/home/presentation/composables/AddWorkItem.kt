package com.example.worktracker.home.presentation.composables

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import androidx.compose.ui.graphics.vector.ImageVector
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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.worktracker.R
import com.example.worktracker.common.Constants
import com.example.worktracker.contributors.presentation.model.ContributorUI
import com.example.worktracker.contributors.presentation.viewmodel.get_contributors.GetContributorsViewModel
import com.example.worktracker.home.presentation.viewmodel.add_work_item.AddWorkItemEvents
import com.example.worktracker.home.presentation.viewmodel.add_work_item.AddWorkItemViewModel
import com.example.worktracker.navigation.Screens
import com.example.worktracker.priorities.presentation.model.PriorityUI
import com.example.worktracker.priorities.presentation.viewmodel.get_priorities.GetPrioritiesViewModel
import com.example.worktracker.statuses.presentation.model.StatusUI
import com.example.worktracker.statuses.presentation.viewmodel.get_statuses.GetStatusesViewModel
import com.example.worktracker.worktypes.presentation.model.WorkTypeUI
import com.example.worktracker.worktypes.presentation.viewmodel.get_worktypes.GetWorkTypesViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddWorkItem(navController: NavController,
                getWorkTypesViewModel: GetWorkTypesViewModel,
                getStatusesViewModel: GetStatusesViewModel,
                getPrioritiesViewModel: GetPrioritiesViewModel,
                getAssignersViewModel: GetContributorsViewModel,
                getAssigneesViewModel: GetContributorsViewModel,
                addWorkItemViewModel: AddWorkItemViewModel)
{
    val context = LocalContext.current.applicationContext

    val getWorkTypesState by getWorkTypesViewModel.state.collectAsStateWithLifecycle()
    val getStatusesState by getStatusesViewModel.state.collectAsStateWithLifecycle()
    val getPrioritiesState by getPrioritiesViewModel.state.collectAsStateWithLifecycle()
    val getAssignersState by getAssignersViewModel.state.collectAsStateWithLifecycle()
    val getAssigneesState by getAssigneesViewModel.state.collectAsStateWithLifecycle()

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

    var isInitialDataLoaded by remember { mutableStateOf(false) }

    ////////////////////////////////////////////////////////////////////////////
    //add work item

    val addWorkItemState by addWorkItemViewModel.addWorkItemState.collectAsStateWithLifecycle()

    //handling success response and error for adding Priority
    LaunchedEffect(Unit) {
        addWorkItemViewModel.addWorkItemEvents.collect { event ->
            when (event) {
                is AddWorkItemEvents.Success -> {
                    //todo what to do after adding the work item successfully
                    // we can open the details of work item screen where the user can
                    // update the work item or add comments
                    Toast.makeText(context, Constants.WORK_ITEM_IS_ADDED_SUCCESSFULLY, Toast.LENGTH_SHORT).show()
                    navController.popBackStack()
                }
                is AddWorkItemEvents.ShowError -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    var selectedWorkType by remember { mutableStateOf<WorkTypeUI?>(null) }
    var selectedStatus by remember { mutableStateOf<StatusUI?>(null) }
    var selectedPriority by remember { mutableStateOf<PriorityUI?>(null) }
    var selectedAssigner by remember { mutableStateOf<ContributorUI?>(null) }
    var selectedAssignee by remember { mutableStateOf<ContributorUI?>(null) }
    var addedTitle by remember { mutableStateOf<String>("") }
    var addedDescription by remember { mutableStateOf<String>("") }
    var addedDocumentations by remember {
        mutableStateOf<List<String>>(emptyList())
    }

    ////////////////////////////////////////////////////////////////////////////

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

                        //todo a bug. fix it| the refresh layout does not work
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
                                !hasError &&
                                workTypesItems.isNotEmpty() &&
                                prioritiesItems.isNotEmpty() &&
                                statusesItems.isNotEmpty() &&
                                assignersItems.isNotEmpty() &&
                                assigneesItems.isNotEmpty()

                    LaunchedEffect(canCreateWorkItem) {
                        if (canCreateWorkItem) {
                            isInitialDataLoaded = true
                        }
                    }

                    Box(modifier = Modifier.fillMaxSize())
                    {
                        if ( (isLoading && !isInitialDataLoaded) || addWorkItemState.isLoading)
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
                        else if (hasError) {

                            Column(modifier = Modifier.fillMaxSize(),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center)
                            {
                                Text(
                                    text = Constants.SOMETHING_WENT_WRONG,
                                    color = colorResource(R.color.color_a),
                                    fontSize = 20.sp
                                )
                                Spacer(modifier = Modifier.height(20.dp))
                                Button(
                                    onClick = {
                                        getWorkTypesViewModel.loadFirstPage()
                                        getStatusesViewModel.loadFirstPage()
                                        getPrioritiesViewModel.loadFirstPage()
                                        getAssignersViewModel.loadFirstPage()
                                        getAssigneesViewModel.loadFirstPage()
                                    },
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = colorResource(R.color.color_a),
                                        contentColor = colorResource(R.color.white))
                                    ) {
                                    Text(
                                        text = Constants.RETRY,
                                        color = colorResource(R.color.white),
                                        fontSize = 20.sp
                                    )
                                }
                            }
                        }
                        else if (canCreateWorkItem)
                        {
                            Column(modifier = Modifier.fillMaxSize(),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center)
                            {
                                AddWorkItem(
                                    navController = navController,

                                    workTypesList = workTypesItems,
                                    statusesList = statusesItems,
                                    prioritiesList = prioritiesItems,
                                    assignersList = assignersItems,
                                    assigneesList = assigneesItems,

                                    isLoadingMoreWorkTypes = getWorkTypesState.isLoading,
                                    isLoadingMoreStatuses = getStatusesState.isLoading,
                                    isLoadingMorePriorities = getPrioritiesState.isLoading,
                                    isLoadingMoreAssigners = getAssignersState.isLoading,
                                    isLoadingMoreAssignees = getAssigneesState.isLoading,

                                    onLoadMoreWorkTypes = {
                                        getWorkTypesViewModel.loadNextPage()
                                    },
                                    onLoadMoreStatuses = {
                                        getStatusesViewModel.loadNextPage()
                                    },
                                    onLoadMorePriorities = {
                                        getPrioritiesViewModel.loadNextPage()
                                    },
                                    onLoadMoreAssigners = {
                                        getAssignersViewModel.loadNextPage()
                                    },
                                    onLoadMoreAssignees = {
                                        getAssigneesViewModel.loadNextPage()
                                    },
                                    onSelectWorkType = { workTypeUI ->
                                        selectedWorkType = workTypeUI
                                    },
                                    onSelectStatus = { statusUI ->
                                        selectedStatus= statusUI
                                    },
                                    onSelectPriority = { priorityUI ->
                                        selectedPriority= priorityUI
                                    },
                                    onSelectAssigner = { assignerUI ->
                                        selectedAssigner= assignerUI
                                    },
                                    onSelectAssignee = { assigneeUI ->
                                        selectedAssignee= assigneeUI
                                    },
                                    onAddingTitle = { title ->
                                        addedTitle = title
                                    },
                                    onAddingDescription = { description ->
                                        addedDescription = description
                                    },
                                    onSubmit = {
                                        addWorkItemViewModel.addWorkItem(
                                            title = addedTitle,
                                            description = addedDescription,
                                            workTypeId = selectedWorkType?.id,
                                            assignerId = selectedAssigner?.id,
                                            assigneeId = selectedAssignee?.id,
                                            priorityId = selectedPriority?.id,
                                            statusId = selectedStatus?.id,
                                            documentationLinks = addedDocumentations
                                        )
                                    }
                                )
                            }
                        }
                        else {
                            if(workTypesItems.isEmpty() && isInitialDataLoaded)
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
                            else if(prioritiesItems.isEmpty() && isInitialDataLoaded)
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
                            else if(statusesItems.isEmpty() && isInitialDataLoaded)
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
                            else if(assigneesItems.isEmpty() && isInitialDataLoaded)
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
                            else if(assignersItems.isEmpty() && isInitialDataLoaded)
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

@Composable
fun AddWorkItem(
    navController: NavController,

    workTypesList: List<WorkTypeUI>,
    statusesList: List<StatusUI>,
    prioritiesList: List<PriorityUI>,
    assignersList: List<ContributorUI>,
    assigneesList: List<ContributorUI>,

    isLoadingMoreWorkTypes: Boolean,
    isLoadingMoreStatuses: Boolean,
    isLoadingMorePriorities: Boolean,
    isLoadingMoreAssigners: Boolean,
    isLoadingMoreAssignees: Boolean,

    onLoadMoreWorkTypes: () -> Unit,
    onLoadMoreStatuses: () -> Unit,
    onLoadMorePriorities: () -> Unit,
    onLoadMoreAssigners: () -> Unit,
    onLoadMoreAssignees: () -> Unit,

    onSelectWorkType: (WorkTypeUI?) -> Unit,
    onSelectStatus: (StatusUI?) -> Unit,
    onSelectPriority: (PriorityUI?) -> Unit,
    onSelectAssigner: (ContributorUI?) -> Unit,
    onSelectAssignee: (ContributorUI?) -> Unit,
    onAddingTitle: (String) -> Unit,
    onAddingDescription: (String) -> Unit,

    onSubmit: () -> Unit
)
{
    val workTypesListState = rememberLazyListState()
    val statusesListState = rememberLazyListState()
    val prioritiesListState = rememberLazyListState()
    val assignersListState = rememberLazyListState()
    val assigneesListState = rememberLazyListState()

    var selectedWorkType by remember { mutableStateOf<WorkTypeUI?>(null) }
    var selectedStatus by remember { mutableStateOf<StatusUI?>(null) }
    var selectedPriority by remember { mutableStateOf<PriorityUI?>(null) }
    var selectedAssigner by remember { mutableStateOf<ContributorUI?>(null) }
    var selectedAssignee by remember { mutableStateOf<ContributorUI?>(null) }
    var addedTitle by remember { mutableStateOf<String>("") }
    var addedDescription by remember { mutableStateOf<String>("") }

    var addAttachmentsButtonIsClicked by remember { mutableStateOf(false) }
    var showImageUploaderGallery by remember { mutableStateOf(false) }
    var showImageUploaderCamera by remember { mutableStateOf(false) }
    var uploadedImageUrl by remember { mutableStateOf<String?>(null) }

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxSize()
            .verticalScroll(scrollState)
    )
    {
        Spacer(modifier = Modifier.height(10.dp))


        //work type
        Text(
            text = buildAnnotatedString {
                withStyle(style = SpanStyle(color = colorResource(R.color.color_a))){
                    append(Constants.WORK_TYPE) }
                withStyle(style = SpanStyle(color = Color.Red)){
                    append(" *") }
            },
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(2.dp))
        LazyRow(
            verticalAlignment = Alignment.CenterVertically,
            state = workTypesListState)
        {
            itemsIndexed(
                items = workTypesList,
                key = { _, item -> item.id }
            )
            { index, item ->

                // Pagination trigger when scrolling near bottom
                if (index >= workTypesList.size - 3)
                {
                    onLoadMoreWorkTypes()
                }

                Card(
                    modifier = Modifier
                        .padding(5.dp)
                        .clickable {
                            if(selectedWorkType?.id != item.id)
                            {
                                selectedWorkType = item
                                onSelectWorkType(item)
                            }
                            else
                            {
                                selectedWorkType = null
                                onSelectWorkType(null)
                            }

                        },
                    shape = RoundedCornerShape(20.dp),
                    //elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = colorResource(id = R.color.white)
                    )
                )
                {
                    Row(verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier.padding(10.dp))
                    {
                        Text(
                            text = item.name,
                            fontSize = 12.sp,
                            color =
                                if(selectedWorkType?.id == item.id)colorResource(R.color.color_b)
                                else colorResource(R.color.muted_gray)
                        )
                    }
                }
            }

            //show the assignees progress bar
            item {
                if (isLoadingMoreWorkTypes) {
                    Box(
                        modifier = Modifier
                            .padding(10.dp)
                            .size(24.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(20.dp),
                            strokeWidth = 2.dp
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(15.dp))


        //title
        Text(
            text = buildAnnotatedString {
                withStyle(style = SpanStyle(color = colorResource(R.color.color_a))){
                    append(Constants.TITLE) }
                withStyle(style = SpanStyle(color = Color.Red)){
                    append(" *") }
            },
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(2.dp))
        OutlinedTextField(
            value = addedTitle ?: "",
            onValueChange = {
                addedTitle = it
                onAddingTitle(it) },
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
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(2.dp))
        OutlinedTextField(
            value = addedDescription ?: "",
            onValueChange = {
                addedDescription = it
                onAddingDescription(it) },
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

        //priority
        Text(
            text = buildAnnotatedString {
                withStyle(style = SpanStyle(color = colorResource(R.color.color_a))){
                    append(Constants.PRIORITY) }
                withStyle(style = SpanStyle(color = Color.Red)){
                    append(" *") }
            },
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(2.dp))
        LazyRow(
            verticalAlignment = Alignment.CenterVertically,
            state = prioritiesListState)
        {
            itemsIndexed(
                items = prioritiesList,
                key = { _, item -> item.id }
            )
            { index, item ->

                // Pagination trigger when scrolling near bottom
                if (index >= prioritiesList.size - 3)
                {
                    onLoadMorePriorities()
                }

                Card(
                    modifier = Modifier
                        .padding(5.dp)
                        .clickable {
                            if(selectedPriority?.id != item.id)
                            {
                                selectedPriority = item
                                onSelectPriority(item)
                            }
                            else
                            {
                                selectedPriority = null
                                onSelectPriority(null)
                            }

                        },
                    shape = RoundedCornerShape(20.dp),
                    //elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = colorResource(id = R.color.white)
                    )
                )
                {
                    Row(verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier.padding(10.dp))
                    {
                        Text(
                            text = item.name,
                            fontSize = 12.sp,
                            color =
                                if(selectedPriority?.id == item.id)colorResource(R.color.color_b)
                                else colorResource(R.color.muted_gray)
                        )
                    }
                }
            }

            //show the assignees progress bar
            item {
                if (isLoadingMorePriorities) {
                    Box(
                        modifier = Modifier
                            .padding(10.dp)
                            .size(24.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(20.dp),
                            strokeWidth = 2.dp
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(15.dp))

        //status
        Text(
            text = buildAnnotatedString {
                withStyle(style = SpanStyle(color = colorResource(R.color.color_a))){
                    append(Constants.STATUS) }
                withStyle(style = SpanStyle(color = Color.Red)){
                    append(" *") }
            },
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(2.dp))
        LazyRow(
            verticalAlignment = Alignment.CenterVertically,
            state = statusesListState)
        {
            itemsIndexed(
                items = statusesList,
                key = { _, item -> item.id }
            )
            { index, item ->

                // Pagination trigger when scrolling near bottom
                if (index >= statusesList.size - 3)
                {
                    onLoadMoreStatuses()
                }

                Card(
                    modifier = Modifier
                        .padding(5.dp)
                        .clickable {
                            if(selectedStatus?.id != item.id)
                            {
                                selectedStatus = item
                                onSelectStatus(item)
                            }
                            else
                            {
                                selectedStatus = null
                                onSelectStatus(null)
                            }

                        },
                    shape = RoundedCornerShape(20.dp),
                    //elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = colorResource(id = R.color.white)
                    )
                )
                {
                    Row(verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier.padding(10.dp))
                    {
                        Text(
                            text = item.name,
                            fontSize = 12.sp,
                            color =
                                if(selectedStatus?.id == item.id)colorResource(R.color.color_b)
                                else colorResource(R.color.muted_gray)
                        )
                    }
                }
            }

            //show the statuses progress bar
            item {
                if (isLoadingMoreStatuses) {
                    Box(
                        modifier = Modifier
                            .padding(10.dp)
                            .size(24.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(20.dp),
                            strokeWidth = 2.dp
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(15.dp))

        //assignee
        Text(
            text = buildAnnotatedString {
                withStyle(style = SpanStyle(color = colorResource(R.color.color_a))){
                    append(Constants.ASSIGNEE) }
                withStyle(style = SpanStyle(color = Color.Red)){
                    append(" *") }
            },
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(2.dp))
        LazyRow(
            verticalAlignment = Alignment.CenterVertically,
            state = assigneesListState)
        {
            itemsIndexed(
                items = assigneesList,
                key = { _, item -> item.id }
            ) { index, item ->

                // Pagination trigger when scrolling near bottom
                if (index >= assigneesList.size - 3)
                {
                    onLoadMoreAssignees()
                }

                Card(
                    modifier = Modifier
                        .padding(0.dp)
                        .clickable {
                            if(selectedAssignee?.id != item.id)
                            {
                                selectedAssignee = item
                                onSelectAssignee(item)
                            }
                            else
                            {
                                selectedAssignee = null
                                onSelectAssignee(null)
                            }
                        },
                    //shape = RoundedCornerShape(20.dp),
                    //elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.Transparent
                    )
                )
                {
                    Column(modifier = Modifier.padding(5.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally)
                    {
                        Box(modifier = Modifier.size(70.dp))
                        {
                            if(item.imageUrl.isNullOrBlank())
                            {
                                Icon(
                                    painter = painterResource(R.drawable.person),
                                    contentDescription = "",
                                    modifier = Modifier.fillMaxSize().clip(CircleShape),
                                    tint = colorResource(R.color.color_a)
                                )
                            }
                            else{
                                AsyncImage(
                                    model = item.imageUrl,
                                    contentDescription = "",
                                    contentScale = ContentScale.Crop,
                                    error = painterResource(R.drawable.person),
                                    modifier = Modifier.fillMaxSize().clip(CircleShape)
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(5.dp))
                        Text(
                            text = item.name,
                            fontSize = 12.sp,
                            color =
                                if(selectedAssignee?.id == item.id)colorResource(R.color.color_b)
                                else colorResource(R.color.muted_gray)
                        )
                    }
                }
            }

            //show the assignees progress bar
            item {
                if (isLoadingMoreAssignees) {
                    Box(
                        modifier = Modifier
                            .padding(10.dp)
                            .size(24.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(20.dp),
                            strokeWidth = 2.dp
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(15.dp))

        //assigner
        Text(
            text = buildAnnotatedString {
                withStyle(style = SpanStyle(color = colorResource(R.color.color_a))){
                    append(Constants.ASSIGNER) }
                withStyle(style = SpanStyle(color = Color.Red)){
                    append(" *") }
            },
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(2.dp))
        LazyRow(
            verticalAlignment = Alignment.CenterVertically,
            state = assignersListState)
        {
            itemsIndexed(
                items = assignersList,
                key = { _, item -> item.id }
            ) { index, item ->

                // Pagination trigger when scrolling near bottom
                if (index >= assignersList.size - 3)
                {
                    onLoadMoreAssigners()
                }

                Card(
                    modifier = Modifier
                        .padding(0.dp)
                        .clickable {
                            if(selectedAssigner?.id != item.id)
                            {
                                selectedAssigner = item
                                onSelectAssigner(item)
                            }
                            else
                            {
                                selectedAssigner = null
                                onSelectAssigner(null)
                            }
                        },
                    //shape = RoundedCornerShape(20.dp),
                    //elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.Transparent
                    )
                )
                {
                    Column(modifier = Modifier.padding(5.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally)
                    {
                        Box(modifier = Modifier.size(70.dp))
                        {
                            if(item.imageUrl.isNullOrBlank())
                            {
                                Icon(
                                    painter = painterResource(R.drawable.person),
                                    contentDescription = "",
                                    modifier = Modifier.fillMaxSize().clip(CircleShape),
                                    tint = colorResource(R.color.color_a)
                                )
                            }
                            else{
                                AsyncImage(
                                    model = item.imageUrl,
                                    contentDescription = "",
                                    contentScale = ContentScale.Crop,
                                    error = painterResource(R.drawable.person),
                                    modifier = Modifier.fillMaxSize().clip(CircleShape)
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(5.dp))
                        Text(
                            text = item.name,
                            fontSize = 12.sp,
                            color =
                                if(selectedAssigner?.id == item.id)colorResource(R.color.color_b)
                                else colorResource(R.color.muted_gray)
                        )
                    }
                }
            }

            //show the assigners progress bar
            item {
                if (isLoadingMoreAssigners) {
                    Box(
                        modifier = Modifier
                            .padding(10.dp)
                            .size(24.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(20.dp),
                            strokeWidth = 2.dp
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(15.dp))

        //attachments
        Card(
            modifier = Modifier.padding(10.dp).fillMaxWidth(),
            shape = RoundedCornerShape(10.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
            colors = CardDefaults.cardColors(containerColor = colorResource(R.color.background))
        )
        {
            Column (
                modifier = Modifier.fillMaxWidth().padding(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally)
            {
                Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically)
                {
                    Icon(
                        painter = painterResource(R.drawable.attachments),
                        contentDescription = "",
                        modifier = Modifier.size(20.dp),
                        tint = colorResource(R.color.color_a)
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                    Text(
                        text = Constants.ATTACHMENTS,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = colorResource(R.color.color_a)
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                    Text(
                        text = Constants.ADD_ONE_IMAGE_AT_A_TIME,
                        fontSize = 10.sp,
                        color = colorResource(R.color.color_b)
                    )
                }

                //todo show added images here
                Spacer(modifier = Modifier.height(25.dp))

                Row(modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically)
                {
                    OutlinedButton(
                        onClick = {
                            addAttachmentsButtonIsClicked= true
                        },
                        enabled = !addAttachmentsButtonIsClicked,
                        shape = RoundedCornerShape(5.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = colorResource(R.color.background),
                            //contentColor = colorResource(R.color.background)
                        ),
                        border = BorderStroke(
                            width = 1.dp,
                            color = colorResource(R.color.color_a)
                        )
                    ) {
                        Row(modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically)
                        {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = "",
                                modifier = Modifier.size(30.dp),
                                tint = colorResource(R.color.color_a)
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(
                                text = Constants.ADD_ATTACHMENTS,
                                fontSize = 12.sp,
                                color = colorResource(R.color.color_a))
                        }
                    }
                }

                Spacer(modifier = Modifier.height(5.dp))

                if(addAttachmentsButtonIsClicked)
                {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    )
                    {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center,
                            modifier = Modifier
                                .height(70.dp)
                                .clickable {
                                    showImageUploaderGallery = true
                                    addAttachmentsButtonIsClicked = false
                                })
                        {
                            Icon(
                                painter = painterResource(R.drawable.gallery),
                                contentDescription = "",
                                modifier = Modifier.size(30.dp),
                                tint = colorResource(R.color.color_a)
                            )
                            Spacer(modifier = Modifier.height(5.dp))
                            Text(
                                text = "gallery",
                                color = colorResource(R.color.color_a),
                                fontSize = 12.sp
                            )
                        }

                        VerticalDivider(
                            color = colorResource(R.color.color_b),
                            thickness = 1.dp,
                            modifier = Modifier
                                .padding(5.dp)
                                .height(70.dp)
                        )

                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center,
                            modifier = Modifier
                                .height(70.dp)
                                .clickable {
                                    showImageUploaderCamera = true
                                    addAttachmentsButtonIsClicked = false
                                })
                        {
                            Icon(
                                painter = painterResource(R.drawable.camera),
                                contentDescription = "",
                                modifier = Modifier.size(30.dp),
                                tint = colorResource(R.color.color_a)
                            )
                            Spacer(modifier = Modifier.height(5.dp))
                            Text(
                                text = "camera",
                                color = colorResource(R.color.color_a),
                                fontSize = 12.sp
                            )
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(25.dp))

        //submit
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center)
        {
            Button(
                onClick = {
                    onSubmit()
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(R.color.color_a),
                    contentColor = colorResource(R.color.white)
                )
            ) {
                Text(Constants.SUBMIT)
            }
        }

        Spacer(modifier = Modifier.height(50.dp))
    }

}

