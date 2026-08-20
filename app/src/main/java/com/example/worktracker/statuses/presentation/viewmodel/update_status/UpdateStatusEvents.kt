package com.example.worktracker.statuses.presentation.viewmodel.update_status

sealed class UpdateStatusEvents {
    object Success : UpdateStatusEvents()
    data class ShowError(val message: String) : UpdateStatusEvents()
}
