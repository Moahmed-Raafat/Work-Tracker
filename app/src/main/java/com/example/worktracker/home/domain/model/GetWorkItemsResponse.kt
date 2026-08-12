package com.example.worktracker.home.domain.model

data class GetWorkItemsResponse(
    val success: Boolean,
    val page: Int,
    val pageSize: Int,
    val totalCount: Int,
    val workItems: List<WorkItem>
)
