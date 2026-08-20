package com.example.worktracker.worktypes.data.remote.mappers

import com.example.worktracker.worktypes.data.remote.dto.AddWorkTypeBodyDto
import com.example.worktracker.worktypes.data.remote.dto.AddWorkTypeResponseDto
import com.example.worktracker.worktypes.data.remote.dto.DeleteWorkTypeBodyDto
import com.example.worktracker.worktypes.data.remote.dto.DeleteWorkTypeResponseDto
import com.example.worktracker.worktypes.data.remote.dto.GetWorkTypesBodyDto
import com.example.worktracker.worktypes.data.remote.dto.GetWorkTypesResponseDto
import com.example.worktracker.worktypes.data.remote.dto.UpdateWorkTypeBodyDto
import com.example.worktracker.worktypes.data.remote.dto.UpdateWorkTypeResponseDto
import com.example.worktracker.worktypes.data.remote.dto.WorkTypeDto
import com.example.worktracker.worktypes.domain.model.AddWorkTypeBody
import com.example.worktracker.worktypes.domain.model.AddWorkTypeResponse
import com.example.worktracker.worktypes.domain.model.DeleteWorkTypeBody
import com.example.worktracker.worktypes.domain.model.DeleteWorkTypeResponse
import com.example.worktracker.worktypes.domain.model.GetWorkTypesBody
import com.example.worktracker.worktypes.domain.model.GetWorkTypesResponse
import com.example.worktracker.worktypes.domain.model.UpdateWorkTypeBody
import com.example.worktracker.worktypes.domain.model.UpdateWorkTypeResponse
import com.example.worktracker.worktypes.domain.model.WorkType


fun WorkTypeDto.toDomain(): WorkType {
    return WorkType(
        id = id,
        name = name,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}

fun GetWorkTypesBodyDto.toDomain(): GetWorkTypesBody {
    return GetWorkTypesBody(
        action = action,
        page = page,
        pageSize = pageSize
    )
}

fun GetWorkTypesBody.toDto(): GetWorkTypesBodyDto {
    return GetWorkTypesBodyDto(
        action = action,
        page = page,
        pageSize = pageSize
    )
}

fun GetWorkTypesResponseDto.toDomain(): GetWorkTypesResponse {
    return GetWorkTypesResponse(
        success = success,
        page = page,
        pageSize = pageSize,
        totalCount = totalCount,
        workTypes = workTypes.map { it.toDomain() }
    )
}

fun AddWorkTypeBodyDto.toDomain(): AddWorkTypeBody {
    return AddWorkTypeBody(
        action = action,
        name = name
    )
}

fun AddWorkTypeBody.toDto(): AddWorkTypeBodyDto {
    return AddWorkTypeBodyDto(
        action = action,
        name = name
    )
}

fun AddWorkTypeResponseDto.toDomain(): AddWorkTypeResponse {
    return AddWorkTypeResponse(
        success = success,
        id = id,
        message = message
    )
}

fun UpdateWorkTypeBodyDto.toDomain(): UpdateWorkTypeBody {
    return UpdateWorkTypeBody(
        action = action,
        id = id,
        newName = newName
    )
}

fun UpdateWorkTypeBody.toDto(): UpdateWorkTypeBodyDto {
    return UpdateWorkTypeBodyDto(
        action = action,
        id = id,
        newName = newName
    )
}

fun UpdateWorkTypeResponseDto.toDomain(): UpdateWorkTypeResponse {
    return UpdateWorkTypeResponse(
        success = success,
        id = id,
        message = message
    )
}

fun DeleteWorkTypeBodyDto.toDomain(): DeleteWorkTypeBody {
    return DeleteWorkTypeBody(
        action = action,
        id = id
    )
}

fun DeleteWorkTypeBody.toDto(): DeleteWorkTypeBodyDto {
    return DeleteWorkTypeBodyDto(
        action = action,
        id = id
    )
}

fun DeleteWorkTypeResponseDto.toDomain(): DeleteWorkTypeResponse {
    return DeleteWorkTypeResponse(
        success = success,
        id = id,
        message = message
    )
}
