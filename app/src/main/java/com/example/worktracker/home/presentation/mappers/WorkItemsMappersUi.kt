package com.example.worktracker.home.presentation.mappers

import com.example.worktracker.common.formatDateTime
import com.example.worktracker.contributors.presentation.mappers.toUI
import com.example.worktracker.home.domain.model.GetWorkItemsResponse
import com.example.worktracker.home.domain.model.WorkItem
import com.example.worktracker.home.presentation.model.GetWorkItemsResponseUI
import com.example.worktracker.home.presentation.model.WorkItemUI
import com.example.worktracker.priorities.presentation.mappers.toUI
import com.example.worktracker.statuses.presentation.mappers.toUI
import com.example.worktracker.worktypes.presentation.mappers.toUI

fun WorkItem.toUI(): WorkItemUI {
    return WorkItemUI(
        id = this.id,
        workItemNumber = this.workItemNumber,
        title = this.title,
        description = this.description,
        createdAt = formatDateTime(createdAt),
        updatedAt = formatDateTime(updatedAt),

        workType = workType?.toUI(),
        assigner = assigner?.toUI(),
        assignee = assignee?.toUI(),
        status = status?.toUI(),
        priority = priority?.toUI(),

        documentationLinks = this.documentationLinks
    )
}

fun GetWorkItemsResponse.toUI(): GetWorkItemsResponseUI {
    return GetWorkItemsResponseUI(
        success = this.success,
        page = this.page,
        pageSize = this.pageSize,
        totalCount = this.totalCount,
        workItems = this.workItems.map { it.toUI() }
    )
}