package com.example.worktracker.home.data.remote.mappers

import com.example.worktracker.home.data.remote.dto.GetWorkItemsBodyDto
import com.example.worktracker.home.data.remote.dto.GetWorkItemsResponseDto
import com.example.worktracker.home.data.remote.dto.WorkItemDto
import com.example.worktracker.home.domain.model.GetWorkItemsBody
import com.example.worktracker.home.domain.model.GetWorkItemsResponse
import com.example.worktracker.home.domain.model.WorkItem

fun GetWorkItemsBodyDto.toDomain(): GetWorkItemsBody {
    return GetWorkItemsBody(
        action = this.action,
        page = this.page,
        pageSize = this.pageSize,
        sortByCreationDateDescending = this.sortByCreationDateDescending,
        filterByWorkTypeId = this.filterByWorkTypeId,
        filterByAssignerId = this.filterByAssignerId,
        filterByAssigneeId = this.filterByAssigneeId,
        filterByStatusId = this.filterByStatusId,
        filterByPriorityId = this.filterByPriorityId
    )
}

fun GetWorkItemsBody.toDto(): GetWorkItemsBodyDto {
    return GetWorkItemsBodyDto(
        action = this.action,
        page = this.page,
        pageSize = this.pageSize,
        sortByCreationDateDescending = this.sortByCreationDateDescending,
        filterByWorkTypeId = this.filterByWorkTypeId,
        filterByAssignerId = this.filterByAssignerId,
        filterByAssigneeId = this.filterByAssigneeId,
        filterByStatusId = this.filterByStatusId,
        filterByPriorityId = this.filterByPriorityId
    )
}

fun WorkItemDto.toDomain(): WorkItem
{
    return WorkItem(
        id = this.id,
        workItemNumber= this.workItemNumber,
        title = this.title,
        description = this.description,
        createdAt = this.createdAt,
        updatedAt = this.updatedAt,
        workTypeId = this.workTypeId,
        assignerId = this.assignerId,
        assigneeId = this.assigneeId,
        statusId = this.statusId,
        priorityId = this.priorityId,
        documentationLinks = this.documentationLinks
    )
}

fun GetWorkItemsResponseDto.toDomain(): GetWorkItemsResponse
{
    return GetWorkItemsResponse(
        success = this.success,
        page = this.page,
        pageSize = this.pageSize,
        totalCount = this.totalCount,
        workItems = this.workItems.map { it.toDomain() }
    )
}