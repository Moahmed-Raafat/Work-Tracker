package com.example.worktracker.priorities.presentation.viewmodel.delete_priority

sealed class DeletePriorityEvents {
    object Success : DeletePriorityEvents()
    data class ShowError(val message: String) : DeletePriorityEvents()
}
