package com.example.worktracker.priorities.domain.model

data class GetPrioritiesBody(
    var action: String,
    var page: Int,
    var pageSize: Int
)
