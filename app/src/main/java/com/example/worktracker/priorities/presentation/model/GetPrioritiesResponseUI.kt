package com.example.worktracker.priorities.presentation.model

data class GetPrioritiesResponseUI(
    var success: Boolean,
    var page: Int,
    var pageSize: Int,
    var totalCount: Int,
    var priorities: List<PriorityUI>
)
