package com.example.worktracker.statuses.presentation.viewmodel.delete_status

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.worktracker.common.Constants
import com.example.worktracker.common.Resource
import com.example.worktracker.statuses.domain.model.DeleteStatusBody
import com.example.worktracker.statuses.domain.usecase.DeleteStatusUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DeleteStatusViewModel @Inject constructor(private val deleteStatusUseCase: DeleteStatusUseCase) :
    ViewModel() {
    private val _deleteStatusState = MutableStateFlow(DeleteStatusState())
    val deleteStatusState: StateFlow<DeleteStatusState> = _deleteStatusState

    private val _deleteStatusEvents = MutableSharedFlow<DeleteStatusEvents>(
        extraBufferCapacity = 1
    )
    val deleteStatusEvents: SharedFlow<DeleteStatusEvents> = _deleteStatusEvents

    fun deleteStatus(id: Int) = viewModelScope.launch {

        val deleteStatusBody = DeleteStatusBody(Constants.DELETE_STATUS_ACTION, id)

        deleteStatusUseCase.invoke(deleteStatusBody).collect { result ->
            when (result) {
                is Resource.Loading -> _deleteStatusState.update {
                    it.copy(isLoading = true)
                }
                is Resource.Error -> {
                    _deleteStatusState.update { it.copy(isLoading = false) }
                    _deleteStatusEvents.emit(DeleteStatusEvents.ShowError(result.message ?: "Unexpected error"))
                }
                is Resource.Success -> {
                    _deleteStatusState.update { it.copy(isLoading = false) }
                    if (result.data?.success == true) {

                        _deleteStatusEvents.emit(
                            DeleteStatusEvents.Success
                        )

                    } else {

                        _deleteStatusEvents.emit(
                            DeleteStatusEvents.ShowError(
                                result.data?.message ?: "Failed to delete status"
                            )
                        )
                    }
                }
            }
        }
    }
}
