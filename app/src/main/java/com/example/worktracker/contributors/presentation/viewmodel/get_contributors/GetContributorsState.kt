package com.example.worktracker.contributors.presentation.viewmodel.get_contributors

import com.example.worktracker.contributors.presentation.model.ContributorUI

data class GetContributorsState(
    val contributorsList: List<ContributorUI> = emptyList(),
    val isLoading:Boolean = false,
    val error:String? = null
)
