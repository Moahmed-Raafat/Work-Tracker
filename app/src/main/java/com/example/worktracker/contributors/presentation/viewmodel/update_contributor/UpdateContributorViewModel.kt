package com.example.worktracker.contributors.presentation.viewmodel.update_contributor

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.worktracker.common.Constants
import com.example.worktracker.common.Resource
import com.example.worktracker.contributors.domain.model.UpdateContributorBody
import com.example.worktracker.contributors.domain.usecase.UpdateContributorUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UpdateContributorViewModel @Inject constructor(private val updateContributorUseCase: UpdateContributorUseCase):
    ViewModel()
{
    // Persistent UI state (loading, error) — survives rotation
    private val _updateContributorState= MutableStateFlow(UpdateContributorState())
    val updateContributorState: StateFlow<UpdateContributorState> = _updateContributorState


    // One-shot events (success navigation/toast) — no replay, fires once
    private val _updateContributorEvents = MutableSharedFlow<UpdateContributorEvents>(
        extraBufferCapacity = 1
    )
    val updateContributorEvents: SharedFlow<UpdateContributorEvents> = _updateContributorEvents


    fun updateContributor(id: Int, newName: String)= viewModelScope.launch {

        val updateContributorBody= UpdateContributorBody(Constants.UPDATE_CONTRIBUTOR_ACTION,id,newName)

        updateContributorUseCase.invoke(updateContributorBody).collect{ result ->
            when(result){
                is Resource.Loading -> _updateContributorState.update {
                    it.copy(isLoading = true)
                }
                is Resource.Error -> {
                    _updateContributorState.update { it.copy(isLoading = false) }
                    _updateContributorEvents.emit(UpdateContributorEvents.ShowError(result.message ?: "Unexpected error"))
                }
                is Resource.Success -> {
                    _updateContributorState.update { it.copy(isLoading = false) }
                    if (result.data?.success == true) {

                        _updateContributorEvents.emit(
                            UpdateContributorEvents.Success
                        )

                    } else {

                        _updateContributorEvents.emit(
                            UpdateContributorEvents.ShowError(
                                result.data?.message ?: "Failed to add contributor"
                            )
                        )
                    }
                }
            }
        }
    }
}