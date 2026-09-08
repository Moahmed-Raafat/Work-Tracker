package com.example.worktracker.home.presentation.viewmodel.get_work_item_by_id

import com.example.worktracker.home.domain.model.GetWorkItemByIdResponse

data class GetWorkItemByIdState(
    val isLoading: Boolean = false,
    val getWorkItemByIdResponse: GetWorkItemByIdResponse ?= null,
    val error: String? = null
)
