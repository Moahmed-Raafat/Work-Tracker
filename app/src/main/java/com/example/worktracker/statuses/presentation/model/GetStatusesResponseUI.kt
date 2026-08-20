package com.example.worktracker.statuses.presentation.model

data class GetStatusesResponseUI(
    var success: Boolean,
    var page: Int,
    var pageSize: Int,
    var totalCount: Int,
    var statuses: List<StatusUI>
)
