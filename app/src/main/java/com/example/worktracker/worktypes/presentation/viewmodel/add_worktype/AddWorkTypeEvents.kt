package com.example.worktracker.worktypes.presentation.viewmodel.add_worktype

sealed class AddWorkTypeEvents {
    object Success : AddWorkTypeEvents()
    data class ShowError(val message: String) : AddWorkTypeEvents()
}
