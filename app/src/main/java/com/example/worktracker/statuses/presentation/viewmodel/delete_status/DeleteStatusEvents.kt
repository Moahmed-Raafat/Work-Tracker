package com.example.worktracker.statuses.presentation.viewmodel.delete_status

sealed class DeleteStatusEvents {
    object Success : DeleteStatusEvents()
    data class ShowError(val message: String) : DeleteStatusEvents()
}
