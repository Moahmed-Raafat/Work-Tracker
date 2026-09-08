package com.example.worktracker.home.domain.model

data class GetWorkItemByIdResponse(
    var success: Boolean,
    var workItem: WorkItem
)
