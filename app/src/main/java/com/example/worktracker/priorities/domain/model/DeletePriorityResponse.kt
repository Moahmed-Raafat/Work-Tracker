package com.example.worktracker.priorities.domain.model

data class DeletePriorityResponse(
    var success: Boolean,
    var id: Int,
    var message: String
)
