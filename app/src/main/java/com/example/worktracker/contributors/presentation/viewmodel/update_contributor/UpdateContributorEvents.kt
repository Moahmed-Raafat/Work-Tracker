package com.example.worktracker.contributors.presentation.viewmodel.update_contributor


sealed class UpdateContributorEvents {
    object Success : UpdateContributorEvents()
    data class ShowError(val message: String) : UpdateContributorEvents()
}