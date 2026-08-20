package com.example.worktracker.worktypes.presentation.viewmodel.update_worktype

sealed class UpdateWorkTypeEvents {
    object Success : UpdateWorkTypeEvents()
    data class ShowError(val message: String) : UpdateWorkTypeEvents()
}
