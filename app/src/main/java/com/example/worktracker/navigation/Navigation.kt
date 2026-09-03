package com.example.worktracker.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.worktracker.about.presentation.composables.About
import com.example.worktracker.contributors.presentation.composables.Contributors
import com.example.worktracker.contributors.presentation.viewmodel.add_contributor.AddContributorViewModel
import com.example.worktracker.contributors.presentation.viewmodel.delete_contributor.DeleteContributorViewModel
import com.example.worktracker.contributors.presentation.viewmodel.get_contributors.GetContributorsViewModel
import com.example.worktracker.contributors.presentation.viewmodel.update_contributor.UpdateContributorViewModel
import com.example.worktracker.home.presentation.composables.AddWorkItem
import com.example.worktracker.home.presentation.composables.Home
import com.example.worktracker.home.presentation.viewmodel.GetWorkItemsViewModel
import com.example.worktracker.home.presentation.viewmodel.add_work_item.AddWorkItemViewModel
import com.example.worktracker.priorities.presentation.composables.Priorities
import com.example.worktracker.priorities.presentation.viewmodel.add_priority.AddPriorityViewModel
import com.example.worktracker.priorities.presentation.viewmodel.delete_priority.DeletePriorityViewModel
import com.example.worktracker.priorities.presentation.viewmodel.get_priorities.GetPrioritiesViewModel
import com.example.worktracker.priorities.presentation.viewmodel.update_priority.UpdatePriorityViewModel
import com.example.worktracker.statuses.presentation.composables.Statuses
import com.example.worktracker.statuses.presentation.viewmodel.add_status.AddStatusViewModel
import com.example.worktracker.statuses.presentation.viewmodel.delete_status.DeleteStatusViewModel
import com.example.worktracker.statuses.presentation.viewmodel.get_statuses.GetStatusesViewModel
import com.example.worktracker.statuses.presentation.viewmodel.update_status.UpdateStatusViewModel
import com.example.worktracker.worktypes.presentation.composables.WorkTypes
import com.example.worktracker.worktypes.presentation.viewmodel.add_worktype.AddWorkTypeViewModel
import com.example.worktracker.worktypes.presentation.viewmodel.delete_worktype.DeleteWorkTypeViewModel
import com.example.worktracker.worktypes.presentation.viewmodel.get_worktypes.GetWorkTypesViewModel
import com.example.worktracker.worktypes.presentation.viewmodel.update_worktype.UpdateWorkTypeViewModel

@Composable
fun Navigation()
{
    //todo: add about screen
    

    val navController= rememberNavController()

    NavHost(navController = navController, startDestination = Screens.Home.route)
    {
        composable(route=Screens.Home.route)
        {
            val getWorkItemsViewModel: GetWorkItemsViewModel = hiltViewModel()
            val getWorkTypesViewModel: GetWorkTypesViewModel = hiltViewModel()
            val getStatusesViewModel: GetStatusesViewModel = hiltViewModel()
            val getPrioritiesViewModel: GetPrioritiesViewModel= hiltViewModel()
            val getAssignersViewModel: GetContributorsViewModel = hiltViewModel()
            val getAssigneesViewModel: GetContributorsViewModel = hiltViewModel()

            Home(navController = navController,
                getWorkItemsViewModel= getWorkItemsViewModel,
                getWorkTypesViewModel= getWorkTypesViewModel,
                getStatusesViewModel= getStatusesViewModel,
                getPrioritiesViewModel= getPrioritiesViewModel,
                getAssignersViewModel= getAssignersViewModel,
                getAssigneesViewModel= getAssigneesViewModel)
        }
        composable(route=Screens.WorkTypes.route)
        {
            val getWorkTypesViewModel: GetWorkTypesViewModel = hiltViewModel()
            val addWorkTypeViewModel: AddWorkTypeViewModel = hiltViewModel()
            val updateWorkTypeViewModel: UpdateWorkTypeViewModel = hiltViewModel()
            val deleteWorkTypeViewModel: DeleteWorkTypeViewModel = hiltViewModel()
            WorkTypes(
                navController = navController,
                getWorkTypesViewModel= getWorkTypesViewModel,
                addWorkTypeViewModel= addWorkTypeViewModel,
                updateWorkTypeViewModel= updateWorkTypeViewModel,
                deleteWorkTypeViewModel= deleteWorkTypeViewModel
                )
        }
        composable(route=Screens.Contributors.route)
        {
            val getContributorsViewModel: GetContributorsViewModel = hiltViewModel()
            val addContributorViewModel: AddContributorViewModel = hiltViewModel()
            val updateContributorViewModel: UpdateContributorViewModel = hiltViewModel()
            val deleteContributorViewModel: DeleteContributorViewModel = hiltViewModel()
            Contributors(
                navController = navController,
                getContributorsViewModel= getContributorsViewModel,
                addContributorViewModel= addContributorViewModel,
                updateContributorViewModel= updateContributorViewModel,
                deleteContributorViewModel= deleteContributorViewModel)
        }
        composable(route=Screens.Statuses.route)
        {
            val getStatusesViewModel: GetStatusesViewModel = hiltViewModel()
            val addStatusViewModel: AddStatusViewModel = hiltViewModel()
            val updateStatusViewModel: UpdateStatusViewModel = hiltViewModel()
            val deleteStatusViewModel: DeleteStatusViewModel = hiltViewModel()

            Statuses(
                navController = navController,
                getStatusesViewModel= getStatusesViewModel,
                addStatusViewModel = addStatusViewModel,
                updateStatusViewModel = updateStatusViewModel,
                deleteStatusViewModel = deleteStatusViewModel)
        }
        composable(route=Screens.Priorities.route)
        {
            val getPrioritiesViewModel: GetPrioritiesViewModel= hiltViewModel()
            val addPriorityViewModel: AddPriorityViewModel = hiltViewModel()
            val updatePriorityViewModel: UpdatePriorityViewModel = hiltViewModel()
            val deletePriorityViewModel: DeletePriorityViewModel = hiltViewModel()

            Priorities(
                navController = navController,
                getPrioritiesViewModel= getPrioritiesViewModel,
                addPriorityViewModel = addPriorityViewModel,
                updatePriorityViewModel = updatePriorityViewModel,
                deletePriorityViewModel = deletePriorityViewModel)
        }
        composable(route=Screens.About.route)
        {
            About(navController = navController)
        }
        composable(route=Screens.ADDWorkItem.route)
        {
            //val getWorkItemsViewModel: GetWorkItemsViewModel = hiltViewModel()
            val getWorkTypesViewModel: GetWorkTypesViewModel = hiltViewModel()
            val getStatusesViewModel: GetStatusesViewModel = hiltViewModel()
            val getPrioritiesViewModel: GetPrioritiesViewModel= hiltViewModel()
            val getAssignersViewModel: GetContributorsViewModel = hiltViewModel()
            val getAssigneesViewModel: GetContributorsViewModel = hiltViewModel()
            val addWorkItemViewModel: AddWorkItemViewModel = hiltViewModel()

            AddWorkItem(
                navController = navController,
                getWorkTypesViewModel= getWorkTypesViewModel,
                getStatusesViewModel= getStatusesViewModel,
                getPrioritiesViewModel= getPrioritiesViewModel,
                getAssignersViewModel= getAssignersViewModel,
                getAssigneesViewModel= getAssigneesViewModel,
                addWorkItemViewModel= addWorkItemViewModel)
        }
    }
}