package com.example.worktracker.statuses.domain.model

data class UpdateStatusBody(
    var action: String,
    var id: Int,
    var newName: String
)
