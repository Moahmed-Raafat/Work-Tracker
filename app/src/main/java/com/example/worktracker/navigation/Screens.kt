package com.example.worktracker.navigation

import com.example.worktracker.common.Constants

sealed class Screens (val route:String)
{
    data object Home:Screens(Constants.HOME)
    data object WorkTypes:Screens(Constants.WORK_TYPES)
    data object Contributors:Screens(Constants.CONTRIBUTORS)
    data object Statuses:Screens(Constants.STATUSES)
    data object Priorities:Screens(Constants.PRIORITIES)
    data object About:Screens(Constants.ABOUT)
}