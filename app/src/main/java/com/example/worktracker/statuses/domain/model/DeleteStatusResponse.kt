package com.example.worktracker.statuses.domain.model

data class DeleteStatusResponse(
    var success: Boolean,
    var id: Int,
    var message: String
)
