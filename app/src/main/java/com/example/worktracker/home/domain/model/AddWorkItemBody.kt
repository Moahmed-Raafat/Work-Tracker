package com.example.worktracker.home.domain.model

data class AddWorkItemBody(
    var action: String,
    var title: String,
    var description: String,
    var workTypeId: Int,
    var assignerId: Int,
    var assigneeId: Int,
    var statusId: Int,
    var priorityId: Int,
    var documentationLinks: MutableList<String> = mutableListOf()
)