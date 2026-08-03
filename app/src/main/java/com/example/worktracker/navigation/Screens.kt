package com.example.worktracker.navigation

import com.example.worktracker.common.Constants

sealed class Screens (val route:String)
{
    data object Home:Screens(Constants.HOME)
    data object Contributors:Screens(Constants.CONTRIBUTORS)
    data object Statuses:Screens(Constants.STATUSES)
    data object Priorities:Screens(Constants.PRIORITIES)
}