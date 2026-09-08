package com.example.worktracker.home.data.remote.mappers

import com.example.worktracker.contributors.data.remote.mappers.toDomain
import com.example.worktracker.contributors.data.remote.mappers.toDto
import com.example.worktracker.home.domain.model.AddWorkItemBody
import com.example.worktracker.home.domain.model.AddWorkItemResponse
import com.example.worktracker.home.data.remote.dto.AddWorkItemBodyDto
import com.example.worktracker.home.data.remote.dto.AddWorkItemResponseDto
import com.example.worktracker.home.data.remote.dto.GetWorkItemByIdBodyDto
import com.example.worktracker.home.data.remote.dto.GetWorkItemByIdResponseDto
import com.example.worktracker.home.data.remote.dto.GetWorkItemsBodyDto
import com.example.worktracker.home.data.remote.dto.GetWorkItemsResponseDto
import com.example.worktracker.home.data.remote.dto.WorkItemDto
import com.example.worktracker.home.domain.model.GetWorkItemByIdBody
import com.example.worktracker.home.domain.model.GetWorkItemByIdResponse
import com.example.worktracker.home.domain.model.GetWorkItemsBody
import com.example.worktracker.home.domain.model.GetWorkItemsResponse
import com.example.worktracker.home.domain.model.WorkItem
import com.example.worktracker.priorities.data.remote.mappers.toDomain
import com.example.worktracker.priorities.data.remote.mappers.toDto
import com.example.worktracker.statuses.data.remote.mappers.toDomain
import com.example.worktracker.statuses.data.remote.mappers.toDto
import com.example.worktracker.worktypes.data.remote.mappers.toDomain
import com.example.worktracker.worktypes.data.remote.mappers.toDto

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

fun WorkItem.toDto(): WorkItemDto
{
    return WorkItemDto(
        id = this.id,
        workItemNumber= this.workItemNumber,
        title = this.title,
        description = this.description,
        createdAt = this.createdAt,
        updatedAt = this.updatedAt,

        workType = workType?.toDto(),
        assigner = assigner?.toDto(),
        assignee = assignee?.toDto(),
        status = status?.toDto(),
        priority = priority?.toDto(),

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

fun AddWorkItemBodyDto.toDomain(): AddWorkItemBody {
    return AddWorkItemBody(
        action = this.action,
        title = this.title,
        description = this.description,
        workTypeId = this.workTypeId,
        assignerId = this.assignerId,
        assigneeId = this.assigneeId,
        statusId = this.statusId,
        priorityId = this.priorityId,
        documentationLinks = this.documentationLinks
    )
}

fun AddWorkItemResponseDto.toDomain(): AddWorkItemResponse {
    return AddWorkItemResponse(
        success = this.success,
        id = this.id,
        workItemNumber = this.workItemNumber,
        message = this.message
    )
}

fun AddWorkItemBody.toDto(): AddWorkItemBodyDto {
    return AddWorkItemBodyDto(
        action = this.action,
        title = this.title,
        description = this.description,
        workTypeId = this.workTypeId,
        assignerId = this.assignerId,
        assigneeId = this.assigneeId,
        statusId = this.statusId,
        priorityId = this.priorityId,
        documentationLinks = this.documentationLinks
    )
}

fun GetWorkItemByIdBodyDto.toDomain(): GetWorkItemByIdBody {
    return GetWorkItemByIdBody(
        action = this.action,
        workItemId = this.workItemId
    )
}

fun GetWorkItemByIdBody.toDto(): GetWorkItemByIdBodyDto {
    return GetWorkItemByIdBodyDto(
        action = this.action,
        workItemId = this.workItemId
    )
}

fun GetWorkItemByIdResponseDto.toDomain(): GetWorkItemByIdResponse {
    return GetWorkItemByIdResponse(
        success = this.success,
        workItem = this.workItem.toDomain()
    )
}