package com.example.worktracker.statuses.presentation.viewmodel.update_status

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.worktracker.common.Constants
import com.example.worktracker.common.Resource
import com.example.worktracker.statuses.domain.model.UpdateStatusBody
import com.example.worktracker.statuses.domain.usecase.UpdateStatusUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UpdateStatusViewModel @Inject constructor(private val updateStatusUseCase: UpdateStatusUseCase) :
    ViewModel() {
    private val _updateStatusState = MutableStateFlow(UpdateStatusState())
    val updateStatusState: StateFlow<UpdateStatusState> = _updateStatusState

    private val _updateStatusEvents = MutableSharedFlow<UpdateStatusEvents>(
        extraBufferCapacity = 1
    )
    val updateStatusEvents: SharedFlow<UpdateStatusEvents> = _updateStatusEvents

    fun updateStatus(id: Int, newName: String) = viewModelScope.launch {

        val updateStatusBody = UpdateStatusBody(Constants.UPDATE_STATUS_ACTION, id, newName)

        updateStatusUseCase.invoke(updateStatusBody).collect { result ->
            when (result) {
                is Resource.Loading -> _updateStatusState.update {
                    it.copy(isLoading = true)
                }
                is Resource.Error -> {
                    _updateStatusState.update { it.copy(isLoading = false) }
                    _updateStatusEvents.emit(UpdateStatusEvents.ShowError(result.message ?: "Unexpected error"))
                }
                is Resource.Success -> {
                    _updateStatusState.update { it.copy(isLoading = false) }
                    if (result.data?.success == true) {

                        _updateStatusEvents.emit(
                            UpdateStatusEvents.Success
                        )

                    } else {

                        _updateStatusEvents.emit(
                            UpdateStatusEvents.ShowError(
                                result.data?.message ?: "Failed to update status"
                            )
                        )
                    }
                }
            }
        }
    }
}
