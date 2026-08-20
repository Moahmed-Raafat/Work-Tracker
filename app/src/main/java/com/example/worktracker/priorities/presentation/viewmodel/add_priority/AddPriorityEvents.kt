package com.example.worktracker.priorities.presentation.viewmodel.add_priority

sealed class AddPriorityEvents {
    object Success : AddPriorityEvents()
    data class ShowError(val message: String) : AddPriorityEvents()
}
