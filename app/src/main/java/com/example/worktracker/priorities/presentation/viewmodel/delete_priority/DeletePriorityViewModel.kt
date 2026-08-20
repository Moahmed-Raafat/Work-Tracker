package com.example.worktracker.priorities.presentation.viewmodel.delete_priority

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.worktracker.common.Constants
import com.example.worktracker.common.Resource
import com.example.worktracker.priorities.domain.model.DeletePriorityBody
import com.example.worktracker.priorities.domain.usecase.DeletePriorityUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DeletePriorityViewModel @Inject constructor(private val deletePriorityUseCase: DeletePriorityUseCase) :
    ViewModel() {
    private val _deletePriorityState = MutableStateFlow(DeletePriorityState())
    val deletePriorityState: StateFlow<DeletePriorityState> = _deletePriorityState

    private val _deletePriorityEvents = MutableSharedFlow<DeletePriorityEvents>(
        extraBufferCapacity = 1
    )
    val deletePriorityEvents: SharedFlow<DeletePriorityEvents> = _deletePriorityEvents

    fun deletePriority(id: Int) = viewModelScope.launch {

        val deletePriorityBody = DeletePriorityBody(Constants.DELETE_PRIORITY_ACTION, id)

        deletePriorityUseCase.invoke(deletePriorityBody).collect { result ->
            when (result) {
                is Resource.Loading -> _deletePriorityState.update {
                    it.copy(isLoading = true)
                }
                is Resource.Error -> {
                    _deletePriorityState.update { it.copy(isLoading = false) }
                    _deletePriorityEvents.emit(DeletePriorityEvents.ShowError(result.message ?: "Unexpected error"))
                }
                is Resource.Success -> {
                    _deletePriorityState.update { it.copy(isLoading = false) }
                    if (result.data?.success == true) {

                        _deletePriorityEvents.emit(
                            DeletePriorityEvents.Success
                        )

                    } else {

                        _deletePriorityEvents.emit(
                            DeletePriorityEvents.ShowError(
                                result.data?.message ?: "Failed to delete priority"
                            )
                        )
                    }
                }
            }
        }
    }
}
