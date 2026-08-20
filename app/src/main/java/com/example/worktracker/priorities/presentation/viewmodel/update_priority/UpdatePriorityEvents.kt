package com.example.worktracker.priorities.presentation.viewmodel.update_priority

sealed class UpdatePriorityEvents {
    object Success : UpdatePriorityEvents()
    data class ShowError(val message: String) : UpdatePriorityEvents()
}
