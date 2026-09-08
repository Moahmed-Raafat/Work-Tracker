package com.example.worktracker.home.presentation.viewmodel.get_work_items

import com.example.worktracker.home.presentation.model.WorkItemUI

data class GetWorkItemsState(
    val workItemsList: List<WorkItemUI> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val resetToken: Int = 0
)