package com.example.worktracker.priorities.presentation.viewmodel.update_priority

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.worktracker.common.Constants
import com.example.worktracker.common.Resource
import com.example.worktracker.priorities.domain.model.UpdatePriorityBody
import com.example.worktracker.priorities.domain.usecase.UpdatePriorityUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UpdatePriorityViewModel @Inject constructor(private val updatePriorityUseCase: UpdatePriorityUseCase) :
    ViewModel() {
    private val _updatePriorityState = MutableStateFlow(UpdatePriorityState())
    val updatePriorityState: StateFlow<UpdatePriorityState> = _updatePriorityState

    private val _updatePriorityEvents = MutableSharedFlow<UpdatePriorityEvents>(
        extraBufferCapacity = 1
    )
    val updatePriorityEvents: SharedFlow<UpdatePriorityEvents> = _updatePriorityEvents

    fun updatePriority(id: Int, newName: String) = viewModelScope.launch {

        val updatePriorityBody = UpdatePriorityBody(Constants.UPDATE_PRIORITY_ACTION, id, newName)

        updatePriorityUseCase.invoke(updatePriorityBody).collect { result ->
            when (result) {
                is Resource.Loading -> _updatePriorityState.update {
                    it.copy(isLoading = true)
                }
                is Resource.Error -> {
                    _updatePriorityState.update { it.copy(isLoading = false) }
                    _updatePriorityEvents.emit(UpdatePriorityEvents.ShowError(result.message ?: "Unexpected error"))
                }
                is Resource.Success -> {
                    _updatePriorityState.update { it.copy(isLoading = false) }
                    if (result.data?.success == true) {

                        _updatePriorityEvents.emit(
                            UpdatePriorityEvents.Success
                        )

                    } else {

                        _updatePriorityEvents.emit(
                            UpdatePriorityEvents.ShowError(
                                result.data?.message ?: "Failed to update priority"
                            )
                        )
                    }
                }
            }
        }
    }
}
