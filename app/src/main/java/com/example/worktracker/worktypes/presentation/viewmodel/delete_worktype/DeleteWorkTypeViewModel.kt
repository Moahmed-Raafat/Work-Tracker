package com.example.worktracker.worktypes.presentation.viewmodel.delete_worktype

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.worktracker.common.Constants
import com.example.worktracker.common.Resource
import com.example.worktracker.worktypes.domain.model.DeleteWorkTypeBody
import com.example.worktracker.worktypes.domain.usecase.DeleteWorkTypeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DeleteWorkTypeViewModel @Inject constructor(private val deleteWorkTypeUseCase: DeleteWorkTypeUseCase) :
    ViewModel() {
    private val _deleteWorkTypeState = MutableStateFlow(DeleteWorkTypeState())
    val deleteWorkTypeState: StateFlow<DeleteWorkTypeState> = _deleteWorkTypeState

    private val _deleteWorkTypeEvents = MutableSharedFlow<DeleteWorkTypeEvents>(
        extraBufferCapacity = 1
    )
    val deleteWorkTypeEvents: SharedFlow<DeleteWorkTypeEvents> = _deleteWorkTypeEvents

    fun deleteWorkType(id: Int) = viewModelScope.launch {

        val deleteWorkTypeBody = DeleteWorkTypeBody(Constants.DELETE_WORK_TYPE_ACTION, id)

        deleteWorkTypeUseCase.invoke(deleteWorkTypeBody).collect { result ->
            when (result) {
                is Resource.Loading -> _deleteWorkTypeState.update {
                    it.copy(isLoading = true)
                }
                is Resource.Error -> {
                    _deleteWorkTypeState.update { it.copy(isLoading = false) }
                    _deleteWorkTypeEvents.emit(DeleteWorkTypeEvents.ShowError(result.message ?: "Unexpected error"))
                }
                is Resource.Success -> {
                    _deleteWorkTypeState.update { it.copy(isLoading = false) }
                    if (result.data?.success == true) {

                        _deleteWorkTypeEvents.emit(
                            DeleteWorkTypeEvents.Success
                        )

                    } else {

                        _deleteWorkTypeEvents.emit(
                            DeleteWorkTypeEvents.ShowError(
                                result.data?.message ?: "Failed to delete work type"
                            )
                        )
                    }
                }
            }
        }
    }
}
