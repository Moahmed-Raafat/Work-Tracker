package com.example.worktracker.priorities.domain.model

data class GetPrioritiesResponse(
    var success: Boolean,
    var page: Int,
    var pageSize: Int,
    var totalCount: Int,
    var priorities: List<Priority>
)
