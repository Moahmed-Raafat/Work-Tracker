package com.example.worktracker.home.presentation.model


data class GetWorkItemsResponseUI(
    val success: Boolean,
    val page: Int,
    val pageSize: Int,
    val totalCount: Int,
    val workItems: List<WorkItemUI>
)
