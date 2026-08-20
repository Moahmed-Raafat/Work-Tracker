package com.example.worktracker.worktypes.presentation.viewmodel.get_worktypes

import com.example.worktracker.worktypes.presentation.model.WorkTypeUI

data class GetWorkTypesState(
    val workTypesList: List<WorkTypeUI> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)
