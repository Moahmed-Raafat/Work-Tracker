package com.example.worktracker.worktypes.domain.model

data class UpdateWorkTypeBody(
    var action: String,
    var id: Int,
    var newName: String
)
