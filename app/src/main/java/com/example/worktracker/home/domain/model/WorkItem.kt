package com.example.worktracker.home.domain.model

import com.example.worktracker.contributors.domain.model.Contributor
import com.example.worktracker.priorities.domain.model.Priority
import com.example.worktracker.statuses.domain.model.Status
import com.example.worktracker.worktypes.domain.model.WorkType

data class WorkItem(
    var id: Int,
    var workItemNumber: String,
    var title: String,
    var description: String,
    var createdAt: String,
    var updatedAt: String,

    var workType: WorkType?,
    var assigner: Contributor?,
    var assignee: Contributor?,
    var status: Status?,
    var priority: Priority?,

    var documentationLinks: List<String>
)
