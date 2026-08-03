package com.example.worktracker.contributors.domain.model

data class UpdateContributorBody(
    var action: String,
    var id: Int,
    var newName: String
)
