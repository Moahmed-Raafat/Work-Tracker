package com.example.worktracker.worktypes.presentation.model

data class GetWorkTypesResponseUI(
    var success: Boolean,
    var page: Int,
    var pageSize: Int,
    var totalCount: Int,
    var workTypes: List<WorkTypeUI>
)
