package com.example.worktracker.statuses.presentation.viewmodel.get_statuses

import com.example.worktracker.statuses.presentation.model.StatusUI

data class GetStatusesState(
    val statusesList: List<StatusUI> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)
