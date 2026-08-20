package com.example.worktracker.statuses.domain.model

data class GetStatusesBody(
    var action: String,
    var page: Int,
    var pageSize: Int
)
