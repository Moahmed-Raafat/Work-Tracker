package com.example.worktracker.statuses.domain.model

data class AddStatusResponse(
    var success: Boolean,
    var id: Int,
    var message: String
)
