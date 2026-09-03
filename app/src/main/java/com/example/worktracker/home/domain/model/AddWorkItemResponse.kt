package com.example.worktracker.home.domain.model

data class AddWorkItemResponse(
    var success: Boolean,
    var id: Int,
    var workItemNumber: String,
    var message: String
)