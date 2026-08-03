package com.example.worktracker.contributors.domain.model

data class GetContributorsBody(
    var action: String,
    var page: Int,
    var pageSize: Int
)
