package com.example.worktracker.home.presentation.composables

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.worktracker.R
import com.example.worktracker.common.Constants
import com.example.worktracker.contributors.presentation.model.ContributorUI
import com.example.worktracker.contributors.presentation.viewmodel.get_contributors.GetContributorsViewModel
import com.example.worktracker.home.presentation.model.WorkItemUI
import com.example.worktracker.home.presentation.viewmodel.GetWorkItemsViewModel
import com.example.worktracker.navigation.Screens
import com.example.worktracker.priorities.presentation.model.PriorityUI
import com.example.worktracker.priorities.presentation.viewmodel.get_priorities.GetPrioritiesViewModel
import com.example.worktracker.statuses.presentation.model.StatusUI
import com.example.worktracker.statuses.presentation.viewmodel.get_statuses.GetStatusesViewModel
import com.example.worktracker.worktypes.presentation.model.WorkTypeUI
import com.example.worktracker.worktypes.presentation.viewmodel.get_worktypes.GetWorkTypesViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.launch

//todo change all un proper icons in the app
//todo when network is not available or the list is empty i can not refresh to retry the api

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Home(navController: NavController,
         getWorkItemsViewModel: GetWorkItemsViewModel,
         getWorkTypesViewModel: GetWorkTypesViewModel,
         getStatusesViewModel: GetStatusesViewModel,
         getPrioritiesViewModel: GetPrioritiesViewModel,
         getAssignersViewModel: GetContributorsViewModel,
         getAssigneesViewModel: GetContributorsViewModel
)
{
    val context = LocalContext.current.applicationContext

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //work items list

    val getWorkItemsState by getWorkItemsViewModel.state.collectAsStateWithLifecycle()

    var isRefreshing by remember { mutableStateOf(false) }

    LaunchedEffect(getWorkItemsState.error) {
        if (!getWorkItemsState.error.isNullOrBlank()) {
            Toast.makeText(context, getWorkItemsState.error, Toast.LENGTH_SHORT).show()
        }
    }

    /*LaunchedEffect(getWorkItemsState.isLoading) {
        if (!getWorkItemsState.isLoading) {
            isRefreshing = false
        }
    }*/

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //filters
    var isFiltersAreaExpanded by remember { mutableStateOf(false) }
    var isDescending by remember { mutableStateOf(false) }
    var selectedWorkTypeFilter by remember { mutableStateOf<WorkTypeUI?>(null) }
    var selectedPriorityFilter by remember { mutableStateOf<PriorityUI?>(null) }
    var selectedStatusFilter by remember { mutableStateOf<StatusUI?>(null) }
    var selectedAssignerFilter by remember { mutableStateOf<ContributorUI?>(null) }
    var selectedAssigneeFilter by remember { mutableStateOf<ContributorUI?>(null) }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //navigation drawer
    val coroutineScope = rememberCoroutineScope()
    val drawerState = rememberDrawerState( initialValue = DrawerValue.Closed )

    //navigation drawer
    ModalNavigationDrawer(
        drawerState =drawerState,
        gesturesEnabled = true,
        drawerContent = {

            ModalDrawerSheet(
                drawerContainerColor = colorResource(R.color.white)
            ) {
                Box(modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .background(colorResource(R.color.color_a)),
                )
                {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center) {

                        //icon
                        Box(
                            modifier = Modifier
                                .size(80.dp)
                                .background(
                                    color = colorResource(R.color.white),
                                    shape = CircleShape
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.work_tracker),
                                contentDescription = null,
                                modifier = Modifier.size(70.dp)
                            )
                        }

                        Spacer(modifier = Modifier.fillMaxWidth().height(10.dp))
                        Text(
                            text = Constants.WORK_TRACKER,
                            color = colorResource(R.color.white),
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp)
                    }
                }

                Spacer(modifier = Modifier.fillMaxWidth().height(20.dp))

                //work types
                //todo change icon
                NavigationDrawerItem(
                    navController= navController,
                    drawerState= drawerState,
                    label= Constants.WORK_TYPES,
                    icon= R.drawable.work_types,
                    Screens.WorkTypes.route)

                //contributors
                NavigationDrawerItem(
                    navController= navController,
                    drawerState= drawerState,
                    label= Constants.CONTRIBUTORS,
                    icon= R.drawable.contributor,
                    Screens.Contributors.route)

                //statuses
                NavigationDrawerItem(
                    navController= navController,
                    drawerState= drawerState,
                    label= Constants.STATUSES,
                    icon= R.drawable.status,
                    Screens.Statuses.route)

                //priorities
                NavigationDrawerItem(
                    navController= navController,
                    drawerState= drawerState,
                    label= Constants.PRIORITIES,
                    icon= R.drawable.priorities,
                    Screens.Priorities.route)

                //about
                NavigationDrawerItem(
                    navController= navController,
                    drawerState= drawerState,
                    label= Constants.ABOUT,
                    icon= R.drawable.about,
                    Screens.About.route)
            }
        })
    {
        Scaffold(
            modifier = Modifier.fillMaxSize().
            statusBarsPadding().
            navigationBarsPadding(),
            topBar = {
                TopAppBar(
                    modifier = Modifier.height(50.dp),
                    title = {
                        Box(modifier = Modifier.fillMaxWidth().fillMaxHeight().padding(0.dp,0.dp,10.dp,0.dp),
                            contentAlignment = Alignment.CenterEnd)
                        {
                            Text(
                                text = Constants.DASHBOARD,
                                fontWeight = FontWeight.Bold,
                                color = colorResource(R.color.white)
                            )
                        }
                    },
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                        containerColor = colorResource(R.color.color_a),
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
                                tint = colorResource(R.color.white))
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
            },
            floatingActionButton = {
                androidx.compose.material3.FloatingActionButton(
                    onClick = {
                        navController.navigate(Screens.ADDWorkItem.route)
                    },
                    contentColor = Color.White,
                    containerColor = colorResource(R.color.color_d)
                ) {
                    Icon(
                        Icons.Rounded.Add,
                        contentDescription = Constants.ADD_WORK_ITEM,
                        modifier = Modifier.size(26.dp),
                        tint = colorResource(R.color.color_a)
                    )
                }
            },

        )
        { innerPadding ->

            Column(
                modifier =
                    Modifier.
                    fillMaxSize().
                    padding(innerPadding)
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
                            getWorkItemsViewModel.loadFirstPage(
                                sortByCreationDateDescending = isDescending,
                                filterByWorkTypeId = selectedWorkTypeFilter?.id,
                                filterByPriorityId = selectedPriorityFilter?.id,
                                filterByStatusId = selectedStatusFilter?.id,
                                filterByAssignerId = selectedAssignerFilter?.id,
                                filterByAssigneeId = selectedAssigneeFilter?.id
                            )
                        }
                    )
                    {
                        val items = getWorkItemsState.workItemsList

                        Box(modifier = Modifier.fillMaxSize())
                        {
                            Column(modifier = Modifier.fillMaxSize()) {

                                ShowFilters(
                                    getWorkTypesViewModel = getWorkTypesViewModel,
                                    getPrioritiesViewModel = getPrioritiesViewModel,
                                    getStatusesViewModel = getStatusesViewModel,
                                    getAssignersViewModel = getAssignersViewModel,
                                    getAssigneesViewModel = getAssigneesViewModel,
                                    isFiltersAreaExpanded = isFiltersAreaExpanded,
                                    onSortingAreaExpandedChange = {
                                        isDescending = it
                                        getWorkItemsViewModel.loadFirstPage(
                                            sortByCreationDateDescending = isDescending,
                                            filterByWorkTypeId = selectedWorkTypeFilter?.id,
                                            filterByPriorityId = selectedPriorityFilter?.id,
                                            filterByStatusId = selectedStatusFilter?.id,
                                            filterByAssignerId = selectedAssignerFilter?.id,
                                            filterByAssigneeId = selectedAssigneeFilter?.id)
                                    },
                                    onFiltersAreaExpandedChange = {
                                        isFiltersAreaExpanded = it
                                    },
                                    onSelectWorkTypeFilter = { workTypeUI ->
                                        selectedWorkTypeFilter= workTypeUI
                                        getWorkItemsViewModel.loadFirstPage(
                                            sortByCreationDateDescending = isDescending,
                                            filterByWorkTypeId = workTypeUI?.id,
                                            filterByPriorityId = selectedPriorityFilter?.id,
                                            filterByStatusId = selectedStatusFilter?.id,
                                            filterByAssignerId = selectedAssignerFilter?.id,
                                            filterByAssigneeId = selectedAssigneeFilter?.id,
                                        )
                                    },
                                    onSelectPriorityFilter = { priorityUI->
                                        selectedPriorityFilter= priorityUI
                                        getWorkItemsViewModel.loadFirstPage(
                                            sortByCreationDateDescending = isDescending,
                                            filterByWorkTypeId = selectedWorkTypeFilter?.id,
                                            filterByPriorityId = priorityUI?.id,
                                            filterByStatusId = selectedStatusFilter?.id,
                                            filterByAssignerId = selectedAssignerFilter?.id,
                                            filterByAssigneeId = selectedAssigneeFilter?.id,
                                        )
                                    },
                                    onSelectStatusFilter = { statusUI->
                                        selectedStatusFilter= statusUI
                                        getWorkItemsViewModel.loadFirstPage(
                                            sortByCreationDateDescending = isDescending,
                                            filterByWorkTypeId = selectedWorkTypeFilter?.id,
                                            filterByPriorityId = selectedPriorityFilter?.id,
                                            filterByStatusId = statusUI?.id,
                                            filterByAssignerId = selectedAssignerFilter?.id,
                                            filterByAssigneeId = selectedAssigneeFilter?.id,
                                        )
                                    },
                                    onSelectAssignerFilter = { contributorUI->
                                        selectedAssignerFilter= contributorUI
                                        getWorkItemsViewModel.loadFirstPage(
                                            sortByCreationDateDescending = isDescending,
                                            filterByWorkTypeId = selectedWorkTypeFilter?.id,
                                            filterByPriorityId = selectedPriorityFilter?.id,
                                            filterByStatusId = selectedStatusFilter?.id,
                                            filterByAssignerId = contributorUI?.id,
                                            filterByAssigneeId = selectedAssigneeFilter?.id,
                                        )
                                    },
                                    onSelectAssigneeFilter = { contributorUI->
                                        selectedAssigneeFilter= contributorUI
                                        getWorkItemsViewModel.loadFirstPage(
                                            sortByCreationDateDescending = isDescending,
                                            filterByWorkTypeId = selectedWorkTypeFilter?.id,
                                            filterByPriorityId = selectedPriorityFilter?.id,
                                            filterByStatusId = selectedStatusFilter?.id,
                                            filterByAssignerId = selectedAssignerFilter?.id,
                                            filterByAssigneeId = contributorUI?.id,
                                        )
                                    },
                                    context = context
                                )

                                if (items.isNotEmpty())
                                {
                                    Spacer(modifier = Modifier.fillMaxWidth().height(10.dp))

                                    ShowList(
                                        context = context,
                                        list = items,
                                        resetSignal = getWorkItemsState.resetToken,
                                        onLoadMore = { getWorkItemsViewModel.loadNextPage() }
                                    )
                                }
                                else if(!getWorkItemsState.isLoading)
                                {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .weight(1f),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = Constants.LOOKS_EMPTY_HERE,
                                            color = colorResource(R.color.color_a)
                                        )
                                    }
                                }
                            }

                            if (getWorkItemsState.isLoading)
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
                            isRefreshing = false
                        }
                    }
                }
            }
        }
    }
}
@Composable
fun ShowFilters(
    getWorkTypesViewModel: GetWorkTypesViewModel,
    getPrioritiesViewModel: GetPrioritiesViewModel,
    getStatusesViewModel: GetStatusesViewModel,
    getAssignersViewModel: GetContributorsViewModel,
    getAssigneesViewModel: GetContributorsViewModel,
    isFiltersAreaExpanded: Boolean,
    onSortingAreaExpandedChange: (Boolean) -> Unit,
    onFiltersAreaExpandedChange: (Boolean) -> Unit,
    onSelectWorkTypeFilter: (WorkTypeUI?) -> Unit,
    onSelectPriorityFilter: (PriorityUI?) -> Unit,
    onSelectStatusFilter: (StatusUI?) -> Unit,
    onSelectAssignerFilter: (ContributorUI?) -> Unit,
    onSelectAssigneeFilter: (ContributorUI?) -> Unit,
    context: Context
    )
{
    var isDescending by remember { mutableStateOf(false) }

    var isWorkTypeFilterAreaExpanded by remember { mutableStateOf(false) }
    var isPriorityFilterAreaExpanded by remember { mutableStateOf(false) }
    var isStatusFilterAreaExpanded by remember { mutableStateOf(false) }
    var isAssigneeFilterAreaExpanded by remember { mutableStateOf(false) }
    var isAssignerFilterAreaExpanded by remember { mutableStateOf(false) }

    var selectedWorkType by remember { mutableStateOf<WorkTypeUI?>(null) }
    var selectedPriory by remember { mutableStateOf<PriorityUI?>(null) }
    var selectedStatus by remember { mutableStateOf<StatusUI?>(null) }
    var selectedAssigner by remember { mutableStateOf<ContributorUI?>(null) }
    var selectedAssignee by remember { mutableStateOf<ContributorUI?>(null) }

    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(max = 500.dp)
            .background(colorResource(R.color.white))
            .padding(10.dp,10.dp,10.dp,0.dp)
            .border(width = 1.dp, color = colorResource(R.color.light_gray))
            .padding(10.dp)
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.Center
        )
    {
        Row(modifier = Modifier.fillMaxWidth(),
            horizontalArrangement =Arrangement.SpaceBetween )
        {
            //sorting area
            Row(
                modifier = Modifier.clickable{
                    isDescending = !isDescending
                    onSortingAreaExpandedChange(isDescending)
            }, verticalAlignment = Alignment.CenterVertically)
            {
                Text(
                    text = "Sort: ",
                    color = colorResource(R.color.color_a),
                    fontSize = 15.sp,
                    modifier = Modifier
                )
                Text(
                    text = "creation date",
                    color = colorResource(R.color.color_a),
                    fontSize = 15.sp,
                    modifier = Modifier
                )
                Icon(
                    painter =
                        if(isDescending) painterResource(id = R.drawable.arrow_up)
                        else  painterResource(id = R.drawable.arrow_down),
                    contentDescription = "Filters",
                    tint = colorResource(R.color.color_a),
                    modifier = Modifier.clickable {
                        isDescending = !isDescending
                        onSortingAreaExpandedChange(isDescending)
                    }.size(15.dp)
                )
            }

            //filters area
            Row(
                modifier = Modifier.clickable{

                    if (isFiltersAreaExpanded) {
                        isWorkTypeFilterAreaExpanded = false
                        isPriorityFilterAreaExpanded = false
                        isStatusFilterAreaExpanded = false
                        isAssignerFilterAreaExpanded = false
                        isAssigneeFilterAreaExpanded = false
                    }
                    onFiltersAreaExpandedChange(!isFiltersAreaExpanded)

            }, verticalAlignment = Alignment.CenterVertically)
            {
                Text(
                    text = "filter",
                    color =
                        if(isFiltersAreaExpanded) colorResource(R.color.color_b)
                        else colorResource(R.color.color_a),
                    fontSize = 15.sp,
                    modifier = Modifier
                )
                Spacer(modifier = Modifier.width(5.dp))
                Icon(
                    imageVector = Icons.Default.Settings,
                    contentDescription = "Filters",
                    tint =
                        if(isFiltersAreaExpanded) colorResource(R.color.color_b)
                        else colorResource(R.color.color_a),
                    modifier = Modifier.clickable {
                        if (isFiltersAreaExpanded) {
                            isWorkTypeFilterAreaExpanded = false
                            isPriorityFilterAreaExpanded = false
                            isStatusFilterAreaExpanded = false
                            isAssignerFilterAreaExpanded = false
                            isAssigneeFilterAreaExpanded = false
                        }
                        onFiltersAreaExpandedChange(!isFiltersAreaExpanded)
                    }.size(15.dp)
                )
            }
        }

        //selected filter
        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalArrangement = Arrangement.spacedBy(5.dp))
        {

            selectedWorkType?.let {
                SelectedFilter(
                    label = Constants.WORK_TYPE,
                    value = it.name,
                    onClear = {
                        selectedWorkType = null
                        onSelectWorkTypeFilter(null)
                    }
                )
            }

            selectedPriory?.let {
                SelectedFilter(
                    label = Constants.PRIORITY,
                    value = it.name,
                    onClear = {
                        selectedPriory = null
                        onSelectPriorityFilter(null)
                    }
                )
            }

            selectedStatus?.let {
                SelectedFilter(
                    label = Constants.STATUS,
                    value = it.name,
                    onClear = {
                        selectedStatus = null
                        onSelectStatusFilter(null)
                    }
                )
            }

            selectedAssigner?.let {
                SelectedFilter(
                    label = Constants.ASSIGNER,
                    value = it.name,
                    onClear = {
                        selectedAssigner = null
                        onSelectAssignerFilter(null)
                    }
                )
            }

            selectedAssignee?.let {
                SelectedFilter(
                    label = Constants.ASSIGNEE,
                    value = it.name,
                    onClear = {
                        selectedAssignee = null
                        onSelectAssigneeFilter(null)
                    }
                )
            }
        }

        //filters are expanded
        if(isFiltersAreaExpanded)
        {
            HorizontalDivider(
                modifier = Modifier.fillMaxWidth().padding(0.dp, 5.dp,0.dp,5.dp),
                color = colorResource(R.color.light_gray),
                thickness = 1.dp
            )

            Spacer(modifier = Modifier.height(5.dp))

            //work types filter
            ShowFilterOption(
                label= Constants.WORK_TYPES,
                isExpanded= isWorkTypeFilterAreaExpanded,
                onClick= {
                    getWorkTypesViewModel.loadFirstPage()
                    isWorkTypeFilterAreaExpanded= !isWorkTypeFilterAreaExpanded
                }
            )

            //work types list
            if(isWorkTypeFilterAreaExpanded)
            {
                val getWorkTypesState by getWorkTypesViewModel.state.collectAsStateWithLifecycle()
                //var isRefreshingWorkTypes by remember { mutableStateOf(false) }

                LaunchedEffect(getWorkTypesState.error) {
                    if (!getWorkTypesState.error.isNullOrBlank()) {
                        Toast.makeText(context, getWorkTypesState.error, Toast.LENGTH_SHORT).show()
                    }
                }

                val workTypesItems= getWorkTypesState.workTypesList
                val listState = rememberLazyListState()

                ShowFilterItems(
                    items = workTypesItems,
                    isLoading = getWorkTypesState.isLoading,
                    listState = listState,
                    key = { it.id },
                    itemText = { it.name },
                    onLoadNextPage = {
                        getWorkTypesViewModel.loadNextPage()
                    },
                    onItemSelected = { item ->
                        selectedWorkType = item
                        onSelectWorkTypeFilter(item)
                    }
                )
            }
            Spacer(modifier = Modifier.height(10.dp))

            ////////////////////////////////////////////////////////////////////////////////////////
            //priority filter
            ShowFilterOption(
                label= Constants.PRIORITY,
                isExpanded= isPriorityFilterAreaExpanded,
                onClick= {
                    getPrioritiesViewModel.loadFirstPage()
                    isPriorityFilterAreaExpanded= !isPriorityFilterAreaExpanded
                }
            )

            //priorities list
            if(isPriorityFilterAreaExpanded) {
                val getPrioritiesState by getPrioritiesViewModel.state.collectAsStateWithLifecycle()
                //var isRefreshingPriorities by remember { mutableStateOf(false) }

                LaunchedEffect(getPrioritiesState.error) {
                    if (!getPrioritiesState.error.isNullOrBlank()) {
                        Toast.makeText(context, getPrioritiesState.error, Toast.LENGTH_SHORT).show()
                    }
                }

                val prioritiesItems = getPrioritiesState.prioritiesList
                val listState = rememberLazyListState()

                ShowFilterItems(
                    items = prioritiesItems,
                    isLoading = getPrioritiesState.isLoading,
                    listState = listState,
                    key = { it.id },
                    itemText = { it.name },
                    onLoadNextPage = {
                        getPrioritiesViewModel.loadNextPage()
                    },
                    onItemSelected = { item ->
                        selectedPriory = item
                        onSelectPriorityFilter(item)
                    }
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            ////////////////////////////////////////////////////////////////////////////////////////
            //status filter
            ShowFilterOption(
                label= Constants.STATUS,
                isExpanded= isStatusFilterAreaExpanded,
                onClick= {
                    getStatusesViewModel.loadFirstPage()
                    isStatusFilterAreaExpanded= !isStatusFilterAreaExpanded
                }
            )

            //status list
            if(isStatusFilterAreaExpanded)
            {
                val getStatusesState by getStatusesViewModel.state.collectAsStateWithLifecycle()
                //var isRefreshingStatuses by remember { mutableStateOf(false) }

                LaunchedEffect(getStatusesState.error) {
                    if (!getStatusesState.error.isNullOrBlank()) {
                        Toast.makeText(context, getStatusesState.error, Toast.LENGTH_SHORT).show()
                    }
                }

                val statusItems = getStatusesState.statusesList
                val listState = rememberLazyListState()

                ShowFilterItems(
                    items = statusItems,
                    isLoading = getStatusesState.isLoading,
                    listState = listState,
                    key = { it.id },
                    itemText = { it.name },
                    onLoadNextPage = {
                        getStatusesViewModel.loadNextPage()
                    },
                    onItemSelected = { item ->
                        selectedStatus = item
                        onSelectStatusFilter(item)
                    }
                )

            }
            Spacer(modifier = Modifier.height(10.dp))

            ////////////////////////////////////////////////////////////////////////////////////////
            //assigner filter
            ShowFilterOption(
                label= Constants.ASSIGNER,
                isExpanded= isAssignerFilterAreaExpanded,
                onClick= {
                    getAssignersViewModel.loadFirstPage()
                    isAssignerFilterAreaExpanded= !isAssignerFilterAreaExpanded
                }
            )

            //assigner list
            if(isAssignerFilterAreaExpanded)
            {
                val getContributorsState by getAssignersViewModel.state.collectAsStateWithLifecycle()
                //var isRefreshingContributors by remember { mutableStateOf(false) }

                LaunchedEffect(getContributorsState.error) {
                    if (!getContributorsState.error.isNullOrBlank()) {
                        Toast.makeText(context, getContributorsState.error, Toast.LENGTH_SHORT).show()
                    }
                }

                val contributorsItems = getContributorsState.contributorsList
                val listState = rememberLazyListState()

                ShowFilterItems(
                    items = contributorsItems,
                    isLoading = getContributorsState.isLoading,
                    listState = listState,
                    key = { it.id },
                    itemText = { it.name },
                    onLoadNextPage = {
                        getAssignersViewModel.loadNextPage()
                    },
                    onItemSelected = { item ->
                        selectedAssigner = item
                        onSelectAssignerFilter(item)
                    }
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            ////////////////////////////////////////////////////////////////////////////////////////
            //assignee filter
            ShowFilterOption(
                label= Constants.ASSIGNEE,
                isExpanded= isAssigneeFilterAreaExpanded,
                onClick= {
                    getAssigneesViewModel.loadFirstPage()
                    isAssigneeFilterAreaExpanded= !isAssigneeFilterAreaExpanded
                }
            )

            //assignee list
            if(isAssigneeFilterAreaExpanded)
            {
                val getContributorsState by getAssigneesViewModel.state.collectAsStateWithLifecycle()
                //var isRefreshingContributors by remember { mutableStateOf(false) }

                LaunchedEffect(getContributorsState.error) {
                    if (!getContributorsState.error.isNullOrBlank()) {
                        Toast.makeText(context, getContributorsState.error, Toast.LENGTH_SHORT).show()
                    }
                }

                val contributorsItems = getContributorsState.contributorsList
                val listState = rememberLazyListState()

                ShowFilterItems(
                    items = contributorsItems,
                    isLoading = getContributorsState.isLoading,
                    listState = listState,
                    key = { it.id },
                    itemText = { it.name },
                    onLoadNextPage = {
                        getAssigneesViewModel.loadNextPage()
                    },
                    onItemSelected = { item ->
                        selectedAssignee = item
                        onSelectAssigneeFilter(item)
                    }
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}

@SuppressLint("UseKtx", "ResourceAsColor")
@Composable
fun ShowList(
    context: Context,
    list: List<WorkItemUI>,
    resetSignal: Int,
    onLoadMore: () -> Unit
) {
    val listState = rememberLazyListState()
    var selectedWorkItem by remember { mutableStateOf<WorkItemUI?>(null) }

    LaunchedEffect(resetSignal) {
        listState.scrollToItem(0, 0)
    }
    //show data
    LazyColumn(
        modifier = Modifier.fillMaxWidth().padding(5.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        state = listState
    )
    {
        itemsIndexed(
            items = list,
            key = { _, item -> item.id }
        ) { index, item ->

            LaunchedEffect(item.id, list.size) {
                if (index >= list.size - 3) {
                    onLoadMore()
                }
            }

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp)
                    .clickable {
                        //todo show details
                        selectedWorkItem = item
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
                        .padding(10.dp)
                )
                {
                    Spacer(modifier = Modifier.fillMaxWidth().height(3.dp))

                    //number and priority
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = item.workItemNumber,
                            color = colorResource(R.color.color_a),
                            fontSize = 20.sp
                        )
                        Text(
                            text = item.priority?.name ?: Constants.NOT_AVAILABLE,
                            color = colorResource(R.color.color_d),
                            fontSize = 20.sp
                        )

                    }
                    Spacer(modifier = Modifier.fillMaxWidth().height(10.dp))

                    //title
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = if (item.title.length > 50) {
                                item.title.take(50) + "..."
                            } else {
                                item.title
                            },
                            color = colorResource(R.color.color_a),
                            fontSize = 20.sp
                        )
                    }

                    Spacer(modifier = Modifier.fillMaxWidth().height(15.dp))

                    //work type and status
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = item.workType?.name ?: Constants.NOT_AVAILABLE,
                            color = colorResource(R.color.color_b),
                            fontSize = 20.sp
                        )
                        Text(
                            text = item.status?.name ?: Constants.NOT_AVAILABLE,
                            color = colorResource(R.color.color_b),
                            fontSize = 20.sp
                        )

                    }

                    Spacer(modifier = Modifier.fillMaxWidth().height(15.dp))

                    //assignee
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = Constants.ASSIGNED_TO + (item.assignee?.name
                                ?: Constants.NOT_AVAILABLE),
                            color = colorResource(R.color.color_e),
                            fontSize = 20.sp
                        )
                    }

                    //created at and updated at
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column() {
                            if (item.updatedAt != "") {
                                Text(
                                    text = Constants.UPDATED_AT + item.updatedAt,
                                    color = colorResource(R.color.color_e),
                                    fontSize = 15.sp,
                                    fontStyle = FontStyle.Italic
                                )
                            }
                            Text(
                                text = Constants.CREATED_AT + item.createdAt,
                                color = colorResource(R.color.color_e),
                                fontSize = 20.sp
                            )
                        }
                    }
                }
            }
        }
    }
}



