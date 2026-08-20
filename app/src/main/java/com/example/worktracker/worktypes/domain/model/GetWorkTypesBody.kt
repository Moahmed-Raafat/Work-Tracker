package com.example.worktracker.worktypes.domain.model

data class GetWorkTypesBody(
    var action: String,
    var page: Int,
    var pageSize: Int
)
