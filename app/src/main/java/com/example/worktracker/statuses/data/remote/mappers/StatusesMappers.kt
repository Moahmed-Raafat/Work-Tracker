package com.example.worktracker.statuses.data.remote.mappers

import com.example.worktracker.statuses.data.remote.dto.AddStatusBodyDto
import com.example.worktracker.statuses.data.remote.dto.AddStatusResponseDto
import com.example.worktracker.statuses.data.remote.dto.DeleteStatusBodyDto
import com.example.worktracker.statuses.data.remote.dto.DeleteStatusResponseDto
import com.example.worktracker.statuses.data.remote.dto.GetStatusesBodyDto
import com.example.worktracker.statuses.data.remote.dto.GetStatusesResponseDto
import com.example.worktracker.statuses.data.remote.dto.StatusDto
import com.example.worktracker.statuses.data.remote.dto.UpdateStatusBodyDto
import com.example.worktracker.statuses.data.remote.dto.UpdateStatusResponseDto
import com.example.worktracker.statuses.domain.model.AddStatusBody
import com.example.worktracker.statuses.domain.model.AddStatusResponse
import com.example.worktracker.statuses.domain.model.DeleteStatusBody
import com.example.worktracker.statuses.domain.model.DeleteStatusResponse
import com.example.worktracker.statuses.domain.model.GetStatusesBody
import com.example.worktracker.statuses.domain.model.GetStatusesResponse
import com.example.worktracker.statuses.domain.model.Status
import com.example.worktracker.statuses.domain.model.UpdateStatusBody
import com.example.worktracker.statuses.domain.model.UpdateStatusResponse


fun StatusDto.toDomain(): Status {
    return Status(
        id = id,
        name = name,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}

fun GetStatusesBodyDto.toDomain(): GetStatusesBody {
    return GetStatusesBody(
        action = action,
        page = page,
        pageSize = pageSize
    )
}

fun GetStatusesBody.toDto(): GetStatusesBodyDto {
    return GetStatusesBodyDto(
        action = action,
        page = page,
        pageSize = pageSize
    )
}

fun GetStatusesResponseDto.toDomain(): GetStatusesResponse {
    return GetStatusesResponse(
        success = success,
        page = page,
        pageSize = pageSize,
        totalCount = totalCount,
        statuses = statuses.map { it.toDomain() }
    )
}

fun AddStatusBodyDto.toDomain(): AddStatusBody {
    return AddStatusBody(
        action = action,
        name = name
    )
}

fun AddStatusBody.toDto(): AddStatusBodyDto {
    return AddStatusBodyDto(
        action = action,
        name = name
    )
}

fun AddStatusResponseDto.toDomain(): AddStatusResponse {
    return AddStatusResponse(
        success = success,
        id = id,
        message = message
    )
}

fun UpdateStatusBodyDto.toDomain(): UpdateStatusBody {
    return UpdateStatusBody(
        action = action,
        id = id,
        newName = newName
    )
}

fun UpdateStatusBody.toDto(): UpdateStatusBodyDto {
    return UpdateStatusBodyDto(
        action = action,
        id = id,
        newName = newName
    )
}

fun UpdateStatusResponseDto.toDomain(): UpdateStatusResponse {
    return UpdateStatusResponse(
        success = success,
        id = id,
        message = message
    )
}

fun DeleteStatusBodyDto.toDomain(): DeleteStatusBody {
    return DeleteStatusBody(
        action = action,
        id = id
    )
}

fun DeleteStatusBody.toDto(): DeleteStatusBodyDto {
    return DeleteStatusBodyDto(
        action = action,
        id = id
    )
}

fun DeleteStatusResponseDto.toDomain(): DeleteStatusResponse {
    return DeleteStatusResponse(
        success = success,
        id = id,
        message = message
    )
}
