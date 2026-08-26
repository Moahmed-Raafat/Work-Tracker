package com.example.worktracker.home.data.remote.mappers

import com.example.worktracker.contributors.data.remote.mappers.toDomain
import com.example.worktracker.home.data.remote.dto.GetWorkItemsBodyDto
import com.example.worktracker.home.data.remote.dto.GetWorkItemsResponseDto
import com.example.worktracker.home.data.remote.dto.WorkItemDto
import com.example.worktracker.home.domain.model.GetWorkItemsBody
import com.example.worktracker.home.domain.model.GetWorkItemsResponse
import com.example.worktracker.home.domain.model.WorkItem
import com.example.worktracker.priorities.data.remote.mappers.toDomain
import com.example.worktracker.statuses.data.remote.mappers.toDomain
import com.example.worktracker.worktypes.data.remote.mappers.toDomain

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

        workType = workType?.toDomain(),
        assigner = assigner?.toDomain(),
        assignee = assignee?.toDomain(),
        status = status?.toDomain(),
        priority = priority?.toDomain(),

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