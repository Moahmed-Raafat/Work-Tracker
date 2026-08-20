package com.example.worktracker.statuses.presentation.viewmodel.add_status

sealed class AddStatusEvents {
    object Success : AddStatusEvents()
    data class ShowError(val message: String) : AddStatusEvents()
}
