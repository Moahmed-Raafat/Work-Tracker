package com.example.worktracker.worktypes.presentation.viewmodel.add_worktype

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.worktracker.common.Constants
import com.example.worktracker.common.Resource
import com.example.worktracker.worktypes.domain.model.AddWorkTypeBody
import com.example.worktracker.worktypes.domain.usecase.AddWorkTypeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddWorkTypeViewModel @Inject constructor(private val addWorkTypeUseCase: AddWorkTypeUseCase) :
    ViewModel() {
    private val _addWorkTypeState = MutableStateFlow(AddWorkTypeState())
    val addWorkTypeState: StateFlow<AddWorkTypeState> = _addWorkTypeState

    private val _addWorkTypeEvents = MutableSharedFlow<AddWorkTypeEvents>(
        extraBufferCapacity = 1
    )
    val addWorkTypeEvents: SharedFlow<AddWorkTypeEvents> = _addWorkTypeEvents

    fun addWorkType(name: String) = viewModelScope.launch {

        val addWorkTypeBody = AddWorkTypeBody(Constants.ADD_WORK_TYPE_ACTION, name)

        addWorkTypeUseCase.invoke(addWorkTypeBody).collect { result ->
            when (result) {
                is Resource.Loading -> _addWorkTypeState.update {
                    it.copy(isLoading = true)
                }
                is Resource.Error -> {
                    _addWorkTypeState.update { it.copy(isLoading = false) }
                    _addWorkTypeEvents.emit(AddWorkTypeEvents.ShowError(result.message ?: "Unexpected error"))
                }
                is Resource.Success -> {
                    _addWorkTypeState.update { it.copy(isLoading = false) }
                    if (result.data?.success == true) {

                        _addWorkTypeEvents.emit(
                            AddWorkTypeEvents.Success
                        )

                    } else {

                        _addWorkTypeEvents.emit(
                            AddWorkTypeEvents.ShowError(
                                result.data?.message ?: "Failed to add work type"
                            )
                        )
                    }
                }
            }
        }
    }
}
