package com.example.worktracker.home.presentation.viewmodel.add_work_item


sealed class AddWorkItemEvents {
    object Success : AddWorkItemEvents()
    data class ShowError(val message: String) : AddWorkItemEvents()
}