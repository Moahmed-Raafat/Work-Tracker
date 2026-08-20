package com.example.worktracker.priorities.domain.model

data class UpdatePriorityBody(
    var action: String,
    var id: Int,
    var newName: String
)
