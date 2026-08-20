package com.example.worktracker.statuses.domain.model

data class GetStatusesResponse(
    var success: Boolean,
    var page: Int,
    var pageSize: Int,
    var totalCount: Int,
    var statuses: List<Status>
)
