package com.example.worktracker.worktypes.domain.model

data class DeleteWorkTypeResponse(
    var success: Boolean,
    var id: Int,
    var message: String
)
