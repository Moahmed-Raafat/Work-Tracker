package com.example.worktracker.contributors.domain.model

data class GetContributorsResponse(
    var success: Boolean,
    var page: Int,
    var pageSize: Int,
    var totalCount: Int,
    var contributors: List<Contributor>
)
