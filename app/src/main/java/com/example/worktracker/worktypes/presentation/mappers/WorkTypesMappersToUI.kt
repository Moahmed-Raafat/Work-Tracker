package com.example.worktracker.worktypes.presentation.mappers

import com.example.worktracker.common.formatDateTime
import com.example.worktracker.worktypes.domain.model.GetWorkTypesResponse
import com.example.worktracker.worktypes.domain.model.WorkType
import com.example.worktracker.worktypes.presentation.model.GetWorkTypesResponseUI
import com.example.worktracker.worktypes.presentation.model.WorkTypeUI


fun WorkType.toUI(): WorkTypeUI {
    return WorkTypeUI(
        id = id,
        name = name,
        createdAt = formatDateTime(createdAt),
        updatedAt = formatDateTime(updatedAt)
    )
}

fun GetWorkTypesResponse.toUI(): GetWorkTypesResponseUI {
    return GetWorkTypesResponseUI(
        success = success,
        page = page,
        pageSize = pageSize,
        totalCount = totalCount,
        workTypes = workTypes.map { it.toUI() }
    )
}
