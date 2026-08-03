package com.example.worktracker.contributors.presentation.viewmodel.add_contributor

sealed class AddContributorEvents {
    object Success : AddContributorEvents()
    data class ShowError(val message: String) : AddContributorEvents()
}