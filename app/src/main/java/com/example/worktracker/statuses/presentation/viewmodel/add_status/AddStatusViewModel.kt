package com.example.worktracker.statuses.presentation.viewmodel.add_status

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.worktracker.common.Constants
import com.example.worktracker.common.Resource
import com.example.worktracker.statuses.domain.model.AddStatusBody
import com.example.worktracker.statuses.domain.usecase.AddStatusUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddStatusViewModel @Inject constructor(private val addStatusUseCase: AddStatusUseCase) :
    ViewModel() {
    private val _addStatusState = MutableStateFlow(AddStatusState())
    val addStatusState: StateFlow<AddStatusState> = _addStatusState

    private val _addStatusEvents = MutableSharedFlow<AddStatusEvents>(
        extraBufferCapacity = 1
    )
    val addStatusEvents: SharedFlow<AddStatusEvents> = _addStatusEvents

    fun addStatus(name: String) = viewModelScope.launch {

        val addStatusBody = AddStatusBody(Constants.ADD_STATUS_ACTION, name)

        addStatusUseCase.invoke(addStatusBody).collect { result ->
            when (result) {
                is Resource.Loading -> _addStatusState.update {
                    it.copy(isLoading = true)
                }
                is Resource.Error -> {
                    _addStatusState.update { it.copy(isLoading = false) }
                    _addStatusEvents.emit(AddStatusEvents.ShowError(result.message ?: "Unexpected error"))
                }
                is Resource.Success -> {
                    _addStatusState.update { it.copy(isLoading = false) }
                    if (result.data?.success == true) {

                        _addStatusEvents.emit(
                            AddStatusEvents.Success
                        )

                    } else {

                        _addStatusEvents.emit(
                            AddStatusEvents.ShowError(
                                result.data?.message ?: "Failed to add status"
                            )
                        )
                    }
                }
            }
        }
    }
}
