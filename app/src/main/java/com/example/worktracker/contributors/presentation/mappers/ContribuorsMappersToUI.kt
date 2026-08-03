package com.example.worktracker.contributors.presentation.mappers

import com.example.worktracker.common.formatDateTime
import com.example.worktracker.contributors.domain.model.Contributor
import com.example.worktracker.contributors.domain.model.GetContributorsResponse
import com.example.worktracker.contributors.presentation.model.ContributorUI
import com.example.worktracker.contributors.presentation.model.GetContributorsResponseUI


fun Contributor.toUI(): ContributorUI
{
    return ContributorUI(
        id= id,
        name= name,
        createdAt= formatDateTime(createdAt),
        updatedAt= formatDateTime(updatedAt)
    )
}

fun GetContributorsResponse.toUI(): GetContributorsResponseUI
{
    return GetContributorsResponseUI(
        success= success,
        page= page,
        pageSize= pageSize,
        totalCount= totalCount,
        contributors= contributors.map { it.toUI() }
    )
}
