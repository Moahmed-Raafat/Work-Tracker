package com.example.worktracker.home.presentation.viewmodel.home_viewmodel

import com.example.worktracker.contributors.presentation.model.ContributorUI
import com.example.worktracker.home.presentation.model.WorkItemUI
import com.example.worktracker.priorities.presentation.model.PriorityUI
import com.example.worktracker.statuses.presentation.model.StatusUI
import com.example.worktracker.worktypes.presentation.model.WorkTypeUI

data class HomeState(
    val isLoading: Boolean = false,
    val isReady: Boolean = false,
    val error: String? = null,

    val workItems: List<WorkItemUI> = emptyList(),
    val workTypes: List<WorkTypeUI> = emptyList(),
    val contributors: List<ContributorUI> = emptyList(),
    val statuses: List<StatusUI> = emptyList(),
    val priorities: List<PriorityUI> = emptyList()
)
