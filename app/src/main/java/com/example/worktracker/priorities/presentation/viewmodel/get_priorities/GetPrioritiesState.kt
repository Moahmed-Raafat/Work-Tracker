package com.example.worktracker.priorities.presentation.viewmodel.get_priorities

import com.example.worktracker.priorities.presentation.model.PriorityUI

data class GetPrioritiesState(
    val prioritiesList: List<PriorityUI> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)
