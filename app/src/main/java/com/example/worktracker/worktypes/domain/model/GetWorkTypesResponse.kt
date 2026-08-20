package com.example.worktracker.worktypes.domain.model

data class GetWorkTypesResponse(
    var success: Boolean,
    var page: Int,
    var pageSize: Int,
    var totalCount: Int,
    var workTypes: List<WorkType>
)
