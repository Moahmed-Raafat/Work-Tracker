package com.example.worktracker.home.domain.model

data class GetWorkItemByIdBody(
    var action: String,
    var workItemId: Int
)
