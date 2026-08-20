package com.example.worktracker.priorities.data.remote.mappers

import com.example.worktracker.priorities.data.remote.dto.AddPriorityBodyDto
import com.example.worktracker.priorities.data.remote.dto.AddPriorityResponseDto
import com.example.worktracker.priorities.data.remote.dto.DeletePriorityBodyDto
import com.example.worktracker.priorities.data.remote.dto.DeletePriorityResponseDto
import com.example.worktracker.priorities.data.remote.dto.GetPrioritiesBodyDto
import com.example.worktracker.priorities.data.remote.dto.GetPrioritiesResponseDto
import com.example.worktracker.priorities.data.remote.dto.PriorityDto
import com.example.worktracker.priorities.data.remote.dto.UpdatePriorityBodyDto
import com.example.worktracker.priorities.data.remote.dto.UpdatePriorityResponseDto
import com.example.worktracker.priorities.domain.model.AddPriorityBody
import com.example.worktracker.priorities.domain.model.AddPriorityResponse
import com.example.worktracker.priorities.domain.model.DeletePriorityBody
import com.example.worktracker.priorities.domain.model.DeletePriorityResponse
import com.example.worktracker.priorities.domain.model.GetPrioritiesBody
import com.example.worktracker.priorities.domain.model.GetPrioritiesResponse
import com.example.worktracker.priorities.domain.model.Priority
import com.example.worktracker.priorities.domain.model.UpdatePriorityBody
import com.example.worktracker.priorities.domain.model.UpdatePriorityResponse


fun PriorityDto.toDomain(): Priority {
    return Priority(
        id = id,
        name = name,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}

fun GetPrioritiesBodyDto.toDomain(): GetPrioritiesBody {
    return GetPrioritiesBody(
        action = action,
        page = page,
        pageSize = pageSize
    )
}

fun GetPrioritiesBody.toDto(): GetPrioritiesBodyDto {
    return GetPrioritiesBodyDto(
        action = action,
        page = page,
        pageSize = pageSize
    )
}

fun GetPrioritiesResponseDto.toDomain(): GetPrioritiesResponse {
    return GetPrioritiesResponse(
        success = success,
        page = page,
        pageSize = pageSize,
        totalCount = totalCount,
        priorities = priorities.map { it.toDomain() }
    )
}

fun AddPriorityBodyDto.toDomain(): AddPriorityBody {
    return AddPriorityBody(
        action = action,
        name = name
    )
}

fun AddPriorityBody.toDto(): AddPriorityBodyDto {
    return AddPriorityBodyDto(
        action = action,
        name = name
    )
}

fun AddPriorityResponseDto.toDomain(): AddPriorityResponse {
    return AddPriorityResponse(
        success = success,
        id = id,
        message = message
    )
}

fun UpdatePriorityBodyDto.toDomain(): UpdatePriorityBody {
    return UpdatePriorityBody(
        action = action,
        id = id,
        newName = newName
    )
}

fun UpdatePriorityBody.toDto(): UpdatePriorityBodyDto {
    return UpdatePriorityBodyDto(
        action = action,
        id = id,
        newName = newName
    )
}

fun UpdatePriorityResponseDto.toDomain(): UpdatePriorityResponse {
    return UpdatePriorityResponse(
        success = success,
        id = id,
        message = message
    )
}

fun DeletePriorityBodyDto.toDomain(): DeletePriorityBody {
    return DeletePriorityBody(
        action = action,
        id = id
    )
}

fun DeletePriorityBody.toDto(): DeletePriorityBodyDto {
    return DeletePriorityBodyDto(
        action = action,
        id = id
    )
}

fun DeletePriorityResponseDto.toDomain(): DeletePriorityResponse {
    return DeletePriorityResponse(
        success = success,
        id = id,
        message = message
    )
}
