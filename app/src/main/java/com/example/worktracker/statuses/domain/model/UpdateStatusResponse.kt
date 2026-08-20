package com.example.worktracker.statuses.domain.model

data class UpdateStatusResponse(
    var success: Boolean,
    var id: Int,
    var message: String
)
