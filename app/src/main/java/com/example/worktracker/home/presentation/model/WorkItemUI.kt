package com.example.worktracker.home.presentation.model

import com.example.worktracker.contributors.presentation.model.ContributorUI
import com.example.worktracker.priorities.presentation.model.PriorityUI
import com.example.worktracker.statuses.presentation.model.StatusUI
import com.example.worktracker.worktypes.presentation.model.WorkTypeUI

data class WorkItemUI(
    var id: Int,
    var workItemNumber: String,
    var title: String,
    var description: String,
    var createdAt: String,
    var updatedAt: String,

    var workType: WorkTypeUI?,
    var assigner: ContributorUI?,
    var assignee: ContributorUI?,
    var status: StatusUI?,
    var priority: PriorityUI?,

    var documentationLinks: List<String>
)
