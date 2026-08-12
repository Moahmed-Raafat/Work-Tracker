package com.example.worktracker.home.domain.model

data class WorkItem(
    var id: Int,
    var workItemNumber: String,
    var title: String,
    var description: String,
    var createdAt: String,
    var updatedAt: String,
    var workTypeId: Int,
    var assignerId: Int,
    var assigneeId: Int,
    var statusId: Int,
    var priorityId: Int,
    var documentationLinks: List<String>
)
