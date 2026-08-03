package com.example.worktracker.contributors.presentation.model

data class GetContributorsResponseUI(
    var success: Boolean,
    var page: Int,
    var pageSize: Int,
    var totalCount: Int,
    var contributors: List<ContributorUI>
)
