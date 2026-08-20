package com.example.worktracker.home.presentation.composables

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
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
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
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
import com.example.worktracker.home.presentation.model.WorkItemUI
import com.example.worktracker.home.presentation.viewmodel.GetWorkItemsViewModel
import com.example.worktracker.navigation.Screens
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Home(navController: NavController,
         getWorkItemsViewModel: GetWorkItemsViewModel/*,
         getWorkTypesViewModel: GetWorkTypesViewModel,
         getContributorsViewModel: GetContributorsViewModel,
         getStatusesViewModel: GetStatusesViewModel,
         getPrioritiesViewModel: GetPrioritiesViewModel*/
)
{
    val context = LocalContext.current.applicationContext

    //////////////////////////////////////////////////////////////////////////////////////////////
    //work types

    /*val getWorkTypesState by getWorkTypesViewModel.state.collectAsStateWithLifecycle()

    var isRefreshingWorkTypes by remember { mutableStateOf(false) }

    LaunchedEffect(getWorkTypesState.error) {
        if (!getWorkTypesState.error.isNullOrBlank()) {
            Toast.makeText(context, getWorkTypesState.error, Toast.LENGTH_SHORT).show()
        }
    }*/

    //////////////////////////////////////////////////////////////////////////////////////////////
    //contributors

    /*val getContributorsState by getContributorsViewModel.state.collectAsStateWithLifecycle()

    var isRefreshingContributors by remember { mutableStateOf(false) }

    LaunchedEffect(getContributorsState.error) {
        if (!getContributorsState.error.isNullOrBlank()) {
            Toast.makeText(context, getContributorsState.error, Toast.LENGTH_SHORT).show()
        }
    }*/

    //////////////////////////////////////////////////////////////////////////////////////////////
    //statuses

    /*val getStatusesState by getStatusesViewModel.state.collectAsStateWithLifecycle()

    var isRefreshingStatuses by remember { mutableStateOf(false) }

    LaunchedEffect(getStatusesState.error) {
        if (!getStatusesState.error.isNullOrBlank()) {
            Toast.makeText(context, getStatusesState.error, Toast.LENGTH_SHORT).show()
        }
    }*/

    //////////////////////////////////////////////////////////////////////////////////////////////
    //priorities

    /*val getPrioritiesState by getPrioritiesViewModel.state.collectAsStateWithLifecycle()

    var isRefreshingPriorities by remember { mutableStateOf(false) }

    LaunchedEffect(getPrioritiesState.error) {
        if (!getPrioritiesState.error.isNullOrBlank()) {
            Toast.makeText(context, getPrioritiesState.error, Toast.LENGTH_SHORT).show()
        }
    }*/

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //work items list

    val getWorkItemsState by getWorkItemsViewModel.state.collectAsStateWithLifecycle()

    var isRefreshing by remember { mutableStateOf(false) }

    LaunchedEffect(getWorkItemsState.error) {
        if (!getWorkItemsState.error.isNullOrBlank()) {
            Toast.makeText(context, getWorkItemsState.error, Toast.LENGTH_SHORT).show()
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //filters
    var isFiltersAreaExpanded by remember { mutableStateOf(false) }
    var isSortingAreaExpanded by remember { mutableStateOf(false) }
    var isDescending by remember { mutableStateOf(false) }


    ////////////////////////////////////////////////////////////////////////////////////////////////
    //navigation drawer
    val coroutineScope = rememberCoroutineScope()
    val drawerState = rememberDrawerState( initialValue = DrawerValue.Closed )

    //navigation drawer
    ModalNavigationDrawer(
        drawerState =drawerState,
        gesturesEnabled = true,
        drawerContent = {
            ModalDrawerSheet {
                Box(modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .background(colorResource(R.color.white)),
                )
                {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center) {

                        Image(
                            painter = painterResource(id = R.drawable.work_tracker),
                            contentDescription = "",
                            modifier = Modifier.height(70.dp).width(70.dp)
                        )
                        Spacer(modifier = Modifier.fillMaxWidth().height(10.dp))
                        Text(
                            text = Constants.WORK_TRACKER,
                            color = colorResource(R.color.primary_text_color),
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp)
                    }
                }

                Spacer(modifier = Modifier.fillMaxWidth().height(20.dp))

                //work types
                NavigationDrawerItem(

                    label = { Text( text = Constants.WORK_TYPES , color = colorResource(R.color.primary_text_color), fontSize = 15.sp)},
                    selected = false,
                    icon = {
                        Icon(
                            //todo change icon
                            painter = painterResource(R.drawable.work_types) ,
                            contentDescription= Constants.WORK_TYPES,
                            modifier = Modifier.size(30.dp)) },
                    onClick= {
                        //closing the navigation drawer
                        coroutineScope.launch{
                            //drawerState.close()
                        }

                        //navigating to selected screen
                        navController.navigate(Screens.WorkTypes.route){
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                )

                //contributors
                NavigationDrawerItem(

                    label = { Text( text = Constants.CONTRIBUTORS , color = colorResource(R.color.primary_text_color), fontSize = 15.sp)},
                    selected = false,
                    icon = {
                        Icon(
                            painter = painterResource(R.drawable.contributor) ,
                            contentDescription= Constants.CONTRIBUTORS,
                            modifier = Modifier.size(30.dp)) },
                    onClick= {
                        //closing the navigation drawer
                        coroutineScope.launch{
                            //drawerState.close()
                        }

                        //navigating to selected screen
                        navController.navigate(Screens.Contributors.route){
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                )

                //statuses
                NavigationDrawerItem(

                    label = { Text( text = Constants.STATUSES , color = colorResource(R.color.primary_text_color), fontSize = 15.sp)},
                    selected = false,
                    icon = {Icon(painter = painterResource(R.drawable.status),
                        contentDescription= Constants.STATUSES,
                        modifier = Modifier.size(30.dp)) },
                    onClick= {
                        //closing the navigation drawer
                        coroutineScope.launch{
                            //drawerState.close()
                        }

                        //navigating to selected screen
                        navController.navigate(Screens.Statuses.route){
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                )

                //priorities
                NavigationDrawerItem(

                    label = {
                        Text(text = Constants.PRIORITIES , color = colorResource(R.color.primary_text_color), fontSize = 15.sp)
                            },
                    selected = false,
                    icon = {Icon(painter = painterResource(R.drawable.priorities),
                        contentDescription= Constants.PRIORITIES,
                        modifier = Modifier.size(30.dp)) },
                    onClick= {
                        //closing the navigation drawer
                        coroutineScope.launch{
                            //drawerState.close()
                        }

                        //navigating to selected screen
                        navController.navigate(Screens.Priorities.route){
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                )

                //about
                NavigationDrawerItem(

                    label = {
                        Text(text = Constants.ABOUT , color = colorResource(R.color.primary_text_color), fontSize = 15.sp)
                    },
                    selected = false,
                    icon = {Icon(painter = painterResource(R.drawable.about),
                        contentDescription= Constants.ABOUT,
                        modifier = Modifier.size(30.dp)) },
                    onClick= {
                        //closing the navigation drawer
                        coroutineScope.launch{
                            //drawerState.close()
                        }

                        //navigating to selected screen
                        navController.navigate(Screens.About.route){
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                )
            }
        })

    {
        Scaffold(
            modifier = Modifier.fillMaxSize().statusBarsPadding().navigationBarsPadding(),
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
                        titleContentColor = colorResource(R.color.white),
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
                        //todo add work item
                    },
                    contentColor = Color.White,
                    containerColor = colorResource(R.color.color_a)
                ) {
                    Icon(
                        Icons.Rounded.Add,
                        contentDescription = Constants.ADD_WORK_ITEM,
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
                            getWorkItemsViewModel.loadFirstPage(sortByCreationDateDescending = false)
                        }
                    )
                    {
                        val items = getWorkItemsState.workItemsList

                        Box(modifier = Modifier.fillMaxSize())
                        {
                            if (items.isNotEmpty())
                            {
                                Spacer(modifier = Modifier.fillMaxWidth().height(10.dp))


                                Column(modifier = Modifier.fillMaxSize()) {

                                    //todo show the ascending and descending sort button and the filters button
                                    ShowFilters(
                                        isFiltersAreaExpanded = isFiltersAreaExpanded,
                                        isSortingAreaExpanded= isSortingAreaExpanded,
                                        onFiltersAreaExpandedChange = {
                                            isFiltersAreaExpanded = it
                                            },
                                        onSortingAreaExpandedChange = {
                                            isSortingAreaExpanded = it
                                        },
                                        onDescendingChange = {
                                            isDescending = it
                                            getWorkItemsViewModel.loadFirstPage(sortByCreationDateDescending = isDescending)
                                        }
                                    )

                                    ShowList(
                                        context= context,
                                        list = items,
                                        onLoadMore = { getWorkItemsViewModel.loadNextPage() }
                                    )
                                }

                            }


                            if (getWorkItemsState.isLoading/*||
                                getWorkTypesState.isLoading ||
                                getContributorsState.isLoading ||
                                getStatusesState.isLoading ||
                                getPrioritiesState.isLoading*/)
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
                            if (items.isEmpty() && !getWorkItemsState.isLoading) {
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
    }
}

@Composable
fun ShowFilters(
    isFiltersAreaExpanded: Boolean,
    isSortingAreaExpanded: Boolean,
    onFiltersAreaExpandedChange: (Boolean) -> Unit,
    onSortingAreaExpandedChange: (Boolean) -> Unit,
    onDescendingChange: (Boolean) -> Unit )
{
    var isDescending by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxWidth())
    {
        Row(
            modifier = Modifier.fillMaxWidth().padding(20.dp,10.dp,20.dp,5.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Column(/*horizontalAlignment = Alignment.CenterHorizontally*/
                modifier = Modifier.weight(1f)) {
                Text(
                    text = "Filters",
                    color = colorResource(R.color.color_a),
                    fontSize = 20.sp
                )
            }
            Column(/*horizontalAlignment = Alignment.CenterHorizontally*/
                modifier = Modifier.weight(1f)) {
                Text(
                    text = "Filters",
                    color = colorResource(R.color.color_a),
                    fontSize = 20.sp
                )
            }
        }
        Divider(
            modifier = Modifier.fillMaxWidth().padding(10.dp, 2.dp,10.dp,2.dp),
            color = colorResource(R.color.color_a),
            thickness = 1.dp
        )


        /*Row(
            modifier = Modifier.fillMaxWidth().padding(20.dp,10.dp,20.dp,5.dp),
            horizontalArrangement = Arrangement.SpaceBetween)
        {
            Text(
                text = "Filters",
                color = colorResource(R.color.color_a),
                fontSize = 20.sp
            )
            Icon(
                imageVector =
                    if(isFiltersListExpanded) Icons.Default.KeyboardArrowDown
                    else Icons.Default.KeyboardArrowUp,
                contentDescription = "Filters",
                tint = colorResource(R.color.color_a),
                modifier = Modifier.clickable {
                    onExpandedChange(!isFiltersListExpanded)
                }.size(30.dp)
            )
        }
        if(isFiltersListExpanded)
        {
            Column(modifier = Modifier.fillMaxWidth().padding(20.dp,0.dp))
            {
                Row()
                {
                    Text(
                        text = Constants.WORK_TYPES,
                        color = colorResource(R.color.color_c),
                        fontSize = 15.sp
                    )
                    Text(
                        text = Constants.PRIORITIES,
                        color = colorResource(R.color.color_c),
                        fontSize = 15.sp
                    )
                    Text(
                        text = Constants.STATUSES,
                        color = colorResource(R.color.color_c),
                        fontSize = 15.sp
                    )
                    Text(
                        text = Constants.ASSIGNEE,
                        color = colorResource(R.color.color_c),
                        fontSize = 15.sp
                    )
                    Text(
                        text = Constants.ASSIGNER,
                        color = colorResource(R.color.color_c),
                        fontSize = 15.sp
                    )
                }
                *//*Text(
                    text = "sort by creation date",
                    color = colorResource(R.color.color_c),
                    fontSize = 15.sp
                )
                Row(modifier = Modifier.fillMaxWidth().padding(10.dp,2.dp),
                    verticalAlignment = Alignment.CenterVertically
                )
                {

                    RadioButton(
                        selected = !isDescending,
                        onClick = {
                            isDescending = false
                            onDescendingChange(false)
                        }
                    )

                    Text(
                        text = "Ascending",
                        color = colorResource(R.color.color_c),
                        fontSize = 15.sp)

                    RadioButton(
                        selected = isDescending,
                        onClick = {
                            isDescending = true
                            onDescendingChange(true)
                        }
                    )

                    Text(
                        text = "Descending",
                        color = colorResource(R.color.color_c),
                        fontSize = 15.sp
                    )
                }*//*


            }


        }*/

    }
}
//todo how we will show the filters
//todo how we will use the filters
//todo what data to show in the list
//todo apply colors

//todo i need to get the priorities, contributors, statuses, work types and show them in the list with their names instead of ids
@SuppressLint("UseKtx", "ResourceAsColor")
@Composable
fun ShowList(
    context: Context,
    list: List<WorkItemUI>,
    onLoadMore: () -> Unit
)
{
    val listState = rememberLazyListState()
    var selectedWorkItem by remember { mutableStateOf<WorkItemUI?>(null) }


    //show data
    LazyColumn(
        modifier = Modifier.fillMaxWidth().padding(5.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        state = listState)
    {
        itemsIndexed(
            items = list,
            key = { _, item -> item.id }
        ) { index, item ->

            if (index >= list.size - 3) {
                onLoadMore()
            }

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp)
                    .clickable {
                        //todo show details
                        selectedWorkItem= item
                    },
                shape = RoundedCornerShape(7.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
                colors = CardDefaults.cardColors(
                    containerColor = colorResource(id = R.color.color_b)
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
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text(
                            text = item.workItemNumber,
                            color = colorResource(R.color.color_a),
                            fontSize = 20.sp
                        )
                        Text(
                            text = item.priority.name,
                            color = colorResource(R.color.color_a),
                            fontSize = 20.sp
                        )
                    }
                    Spacer(modifier = Modifier.fillMaxWidth().height(10.dp))

                    //title
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
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
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text(
                            text = item.workType.name,
                            color = colorResource(R.color.color_a),
                            fontSize = 20.sp
                        )
                        Text(
                            text = item.status.name,
                            color = colorResource(R.color.color_a),
                            fontSize = 20.sp
                        )
                    }

                    Spacer(modifier = Modifier.fillMaxWidth().height(15.dp))

                    //assignee
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text(
                            text = Constants.ASSIGNED_TO + item.assignee.name,
                            color = colorResource(R.color.color_a),
                            fontSize = 20.sp
                        )
                    }

                    //created at and updated at
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Column() {
                            if(item.updatedAt != "")
                            {
                                Text(
                                    text = Constants.UPDATED_AT + item.updatedAt,
                                    color = colorResource(R.color.color_a),
                                    fontSize = 15.sp,
                                    fontStyle = FontStyle.Italic
                                )
                            }
                            Text(
                                text = Constants.CREATED_AT + item.createdAt,
                                color = colorResource(R.color.color_a),
                                fontSize = 20.sp
                            )
                        }
                    }
                }
            }
        }
    }

}



