package com.example.worktracker.priorities.presentation.viewmodel.add_priority

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.worktracker.common.Constants
import com.example.worktracker.common.Resource
import com.example.worktracker.priorities.domain.model.AddPriorityBody
import com.example.worktracker.priorities.domain.usecase.AddPriorityUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddPriorityViewModel @Inject constructor(private val addPriorityUseCase: AddPriorityUseCase) :
    ViewModel() {
    private val _addPriorityState = MutableStateFlow(AddPriorityState())
    val addPriorityState: StateFlow<AddPriorityState> = _addPriorityState

    private val _addPriorityEvents = MutableSharedFlow<AddPriorityEvents>(
        extraBufferCapacity = 1
    )
    val addPriorityEvents: SharedFlow<AddPriorityEvents> = _addPriorityEvents

    fun addPriority(name: String) = viewModelScope.launch {

        val addPriorityBody = AddPriorityBody(Constants.ADD_PRIORITY_ACTION, name)

        addPriorityUseCase.invoke(addPriorityBody).collect { result ->
            when (result) {
                is Resource.Loading -> _addPriorityState.update {
                    it.copy(isLoading = true)
                }
                is Resource.Error -> {
                    _addPriorityState.update { it.copy(isLoading = false) }
                    _addPriorityEvents.emit(AddPriorityEvents.ShowError(result.message ?: "Unexpected error"))
                }
                is Resource.Success -> {
                    _addPriorityState.update { it.copy(isLoading = false) }
                    if (result.data?.success == true) {

                        _addPriorityEvents.emit(
                            AddPriorityEvents.Success
                        )

                    } else {

                        _addPriorityEvents.emit(
                            AddPriorityEvents.ShowError(
                                result.data?.message ?: "Failed to add priority"
                            )
                        )
                    }
                }
            }
        }
    }
}
