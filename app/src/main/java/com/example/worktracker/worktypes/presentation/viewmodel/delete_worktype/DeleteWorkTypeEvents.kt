package com.example.worktracker.worktypes.presentation.viewmodel.delete_worktype

sealed class DeleteWorkTypeEvents {
    object Success : DeleteWorkTypeEvents()
    data class ShowError(val message: String) : DeleteWorkTypeEvents()
}
