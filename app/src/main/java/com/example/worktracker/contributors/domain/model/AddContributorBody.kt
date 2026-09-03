package com.example.worktracker.contributors.domain.model

data class AddContributorBody(
    var action: String,
    var name: String,
    var imageUrl: String ?= null
)
