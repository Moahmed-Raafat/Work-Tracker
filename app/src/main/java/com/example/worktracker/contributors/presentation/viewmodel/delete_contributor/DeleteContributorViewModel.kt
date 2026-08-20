package com.example.worktracker.contributors.presentation.viewmodel.delete_contributor

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.worktracker.common.Constants
import com.example.worktracker.common.Resource
import com.example.worktracker.contributors.domain.model.DeleteContributorBody
import com.example.worktracker.contributors.domain.usecase.DeleteContributorUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DeleteContributorViewModel @Inject constructor(private val deleteContributorUseCase: DeleteContributorUseCase):
    ViewModel()
{
    // Persistent UI state (loading, error) — survives rotation
    private val _deleteContributorState = MutableStateFlow(DeleteContributorState())
    val deleteContributorState: StateFlow<DeleteContributorState> = _deleteContributorState


    // One-shot events (success navigation/toast) — no replay, fires once
    private val _deleteContributorEvents = MutableSharedFlow<DeleteContributorEvents>(
        extraBufferCapacity = 1
    )
    val deleteContributorEvents: SharedFlow<DeleteContributorEvents> = _deleteContributorEvents


    fun deleteContributor(id: Int) = viewModelScope.launch {

        val deleteContributorBody = DeleteContributorBody(Constants.DELETE_CONTRIBUTOR_ACTION, id)

        deleteContributorUseCase.invoke(deleteContributorBody).collect { result ->
            when(result) {
                is Resource.Loading -> _deleteContributorState.update {
                    it.copy(isLoading = true)
                }
                is Resource.Error -> {
                    _deleteContributorState.update { it.copy(isLoading = false) }
                    _deleteContributorEvents.emit(DeleteContributorEvents.ShowError(result.message ?: "Unexpected error"))
                }
                is Resource.Success -> {
                    _deleteContributorState.update { it.copy(isLoading = false) }
                    if (result.data?.success == true) {

                        _deleteContributorEvents.emit(
                            DeleteContributorEvents.Success
                        )

                    } else {

                        _deleteContributorEvents.emit(
                            DeleteContributorEvents.ShowError(
                                result.data?.message ?: "Failed to delete contributor"
                            )
                        )
                    }
                }
            }
        }
    }
}
