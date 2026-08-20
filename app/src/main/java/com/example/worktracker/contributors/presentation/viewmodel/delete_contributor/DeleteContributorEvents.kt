package com.example.worktracker.contributors.presentation.viewmodel.delete_contributor

sealed class DeleteContributorEvents {
    object Success : DeleteContributorEvents()
    data class ShowError(val message: String) : DeleteContributorEvents()
}
