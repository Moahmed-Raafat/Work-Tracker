package com.example.worktracker.statuses.presentation.mappers

import com.example.worktracker.common.formatDateTime
import com.example.worktracker.statuses.domain.model.GetStatusesResponse
import com.example.worktracker.statuses.domain.model.Status
import com.example.worktracker.statuses.presentation.model.GetStatusesResponseUI
import com.example.worktracker.statuses.presentation.model.StatusUI


fun Status.toUI(): StatusUI {
    return StatusUI(
        id = id,
        name = name,
        createdAt = formatDateTime(createdAt),
        updatedAt = formatDateTime(updatedAt)
    )
}

fun GetStatusesResponse.toUI(): GetStatusesResponseUI {
    return GetStatusesResponseUI(
        success = success,
        page = page,
        pageSize = pageSize,
        totalCount = totalCount,
        statuses = statuses.map { it.toUI() }
    )
}
