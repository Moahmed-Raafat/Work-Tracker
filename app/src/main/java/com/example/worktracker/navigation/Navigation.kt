package com.example.worktracker.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.worktracker.about.About
import com.example.worktracker.contributors.presentation.composables.Contributors
import com.example.worktracker.contributors.presentation.viewmodel.add_contributor.AddContributorViewModel
import com.example.worktracker.contributors.presentation.viewmodel.delete_contributor.DeleteContributorViewModel
import com.example.worktracker.contributors.presentation.viewmodel.get_contributors.GetContributorsViewModel
import com.example.worktracker.contributors.presentation.viewmodel.update_contributor.UpdateContributorViewModel
import com.example.worktracker.home.Home
import com.example.worktracker.priorities.Priorities
import com.example.worktracker.statuses.Statuses
import com.example.worktracker.work_types.WorkTypes

@Composable
fun Navigation()
{
    //todo: add about screen
    

    val navController= rememberNavController()

    NavHost(navController = navController, startDestination = Screens.Home.route)
    {
        composable(route=Screens.Home.route)
        {
            Home(navController = navController)
        }
        composable(route=Screens.WorkTypes.route)
        {
            WorkTypes(navController = navController)
        }
        composable(route=Screens.Contributors.route)
        {
            val getContributorsViewModel: GetContributorsViewModel = hiltViewModel()
            val addContributorViewModel: AddContributorViewModel = hiltViewModel()
            val updateContributorViewModel: UpdateContributorViewModel = hiltViewModel()
            val deleteContributorViewModel: DeleteContributorViewModel = hiltViewModel()
            Contributors(
                navController = navController,
                getContributorsViewModel,
                addContributorViewModel,
                updateContributorViewModel,
                deleteContributorViewModel)
        }
        composable(route=Screens.Statuses.route)
        {
            Statuses(navController = navController)
        }
        composable(route=Screens.Priorities.route)
        {
            Priorities(navController = navController)
        }
        composable(route=Screens.About.route)
        {
            About(navController = navController)
        }

    }

}