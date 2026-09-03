package com.example.worktracker.contributors.presentation.viewmodel.add_contributor

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.worktracker.common.Constants
import com.example.worktracker.common.Resource
import com.example.worktracker.contributors.domain.model.AddContributorBody
import com.example.worktracker.contributors.domain.usecase.AddContributorUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddContributorViewModel @Inject constructor(private val addContributorUseCase: AddContributorUseCase):
    ViewModel()
{
    // Persistent UI state (loading, error) — survives rotation
    private val _addContributorState= MutableStateFlow(AddContributorState())
    val addContributorState: StateFlow<AddContributorState> = _addContributorState


    // One-shot events (success navigation/toast) — no replay, fires once
    private val _addContributorEvents = MutableSharedFlow<AddContributorEvents>(
        extraBufferCapacity = 1
    )
    val addContributorEvents: SharedFlow<AddContributorEvents> = _addContributorEvents


    fun addContributor(name: String,imageUrl: String ?= null)= viewModelScope.launch {

        val addContributorBody= AddContributorBody(
            action = Constants.ADD_CONTRIBUTOR_ACTION,
            name = name,
            imageUrl = imageUrl
        )

        addContributorUseCase.invoke(addContributorBody).collect{ result ->
            when(result){
                is Resource.Loading -> _addContributorState.update {
                    it.copy(isLoading = true)
                }
                is Resource.Error -> {
                    _addContributorState.update { it.copy(isLoading = false) }
                    _addContributorEvents.emit(AddContributorEvents.ShowError(result.message ?: "Unexpected error"))
                }
                is Resource.Success -> {
                    _addContributorState.update { it.copy(isLoading = false) }
                    if (result.data?.success == true) {

                        _addContributorEvents.emit(
                            AddContributorEvents.Success
                        )

                    } else {

                        _addContributorEvents.emit(
                            AddContributorEvents.ShowError(
                                result.data?.message ?: "Failed to add contributor"
                            )
                        )
                    }
                }
            }
        }
    }
}