package com.example.worktracker.worktypes.presentation.viewmodel.update_worktype

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.worktracker.common.Constants
import com.example.worktracker.common.Resource
import com.example.worktracker.worktypes.domain.model.UpdateWorkTypeBody
import com.example.worktracker.worktypes.domain.usecase.UpdateWorkTypeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UpdateWorkTypeViewModel @Inject constructor(private val updateWorkTypeUseCase: UpdateWorkTypeUseCase) :
    ViewModel() {
    private val _updateWorkTypeState = MutableStateFlow(UpdateWorkTypeState())
    val updateWorkTypeState: StateFlow<UpdateWorkTypeState> = _updateWorkTypeState

    private val _updateWorkTypeEvents = MutableSharedFlow<UpdateWorkTypeEvents>(
        extraBufferCapacity = 1
    )
    val updateWorkTypeEvents: SharedFlow<UpdateWorkTypeEvents> = _updateWorkTypeEvents

    fun updateWorkType(id: Int, newName: String) = viewModelScope.launch {

        val updateWorkTypeBody = UpdateWorkTypeBody(Constants.UPDATE_WORK_TYPE_ACTION, id, newName)

        updateWorkTypeUseCase.invoke(updateWorkTypeBody).collect { result ->
            when (result) {
                is Resource.Loading -> _updateWorkTypeState.update {
                    it.copy(isLoading = true)
                }
                is Resource.Error -> {
                    _updateWorkTypeState.update { it.copy(isLoading = false) }
                    _updateWorkTypeEvents.emit(UpdateWorkTypeEvents.ShowError(result.message ?: "Unexpected error"))
                }
                is Resource.Success -> {
                    _updateWorkTypeState.update { it.copy(isLoading = false) }
                    if (result.data?.success == true) {

                        _updateWorkTypeEvents.emit(
                            UpdateWorkTypeEvents.Success
                        )

                    } else {

                        _updateWorkTypeEvents.emit(
                            UpdateWorkTypeEvents.ShowError(
                                result.data?.message ?: "Failed to update work type"
                            )
                        )
                    }
                }
            }
        }
    }
}
