package com.example.worktracker.home.presentation.mappers

import com.example.worktracker.common.formatDateTime
import com.example.worktracker.home.domain.model.GetWorkItemsResponse
import com.example.worktracker.home.domain.model.WorkItem
import com.example.worktracker.home.presentation.model.GetWorkItemsResponseUI
import com.example.worktracker.home.presentation.model.WorkItemUI

fun WorkItem.toUI(): WorkItemUI {
    return WorkItemUI(
        id = this.id,
        workItemNumber = this.workItemNumber,
        title = this.title,
        description = this.description,
        createdAt = formatDateTime(createdAt),
        updatedAt = formatDateTime(updatedAt),
        workTypeId = this.workTypeId,
        assignerId = this.assignerId,
        assigneeId = this.assigneeId,
        statusId = this.statusId,
        priorityId = this.priorityId,
        documentationLinks = this.documentationLinks
    )
}

fun GetWorkItemsResponse.toUI(): GetWorkItemsResponseUI{
    return GetWorkItemsResponseUI(
        success = this.success,
        page = this.page,
        pageSize = this.pageSize,
        totalCount = this.totalCount,
        workItems = this.workItems.map { it.toUI() }
    )
}