package com.example.worktracker.priorities.presentation.mappers

import com.example.worktracker.common.formatDateTime
import com.example.worktracker.priorities.domain.model.GetPrioritiesResponse
import com.example.worktracker.priorities.domain.model.Priority
import com.example.worktracker.priorities.presentation.model.GetPrioritiesResponseUI
import com.example.worktracker.priorities.presentation.model.PriorityUI


fun Priority.toUI(): PriorityUI {
    return PriorityUI(
        id = id,
        name = name,
        createdAt = formatDateTime(createdAt),
        updatedAt = formatDateTime(updatedAt)
    )
}

fun GetPrioritiesResponse.toUI(): GetPrioritiesResponseUI {
    return GetPrioritiesResponseUI(
        success = success,
        page = page,
        pageSize = pageSize,
        totalCount = totalCount,
        priorities = priorities.map { it.toUI() }
    )
}
