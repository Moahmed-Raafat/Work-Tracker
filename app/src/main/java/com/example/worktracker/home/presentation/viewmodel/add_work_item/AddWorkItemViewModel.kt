package com.example.worktracker.home.presentation.viewmodel.add_work_item

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.worktracker.common.Constants
import com.example.worktracker.common.Resource
import com.example.worktracker.home.domain.model.AddWorkItemBody
import com.example.worktracker.home.domain.usecase.AddWorkItemUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddWorkItemViewModel @Inject constructor(private val addWorkItemUseCase: AddWorkItemUseCase) :
    ViewModel()
{
    private val _addWorkItemState = MutableStateFlow(AddWorkItemState())
    val addWorkItemState: StateFlow<AddWorkItemState> = _addWorkItemState

    private val _addWorkItemEvents = MutableSharedFlow<AddWorkItemEvents>(
        extraBufferCapacity = 1
    )
    val addWorkItemEvents: SharedFlow<AddWorkItemEvents> = _addWorkItemEvents

    fun addWorkItem(title: String,
                    description: String,
                    workTypeId: Int,
                    assignerId: Int,
                    assigneeId: Int,
                    statusId: Int,
                    priorityId: Int,
                    documentationLinks: MutableList<String>) = viewModelScope.launch {

        val addWorkItemBody = AddWorkItemBody(
            action = Constants.ADD_WORK_ITEM_ACTION,
            title = title,
            description = description,
            workTypeId = workTypeId,
            assignerId = assignerId,
            assigneeId = assigneeId,
            statusId = statusId,
            priorityId = priorityId,
            documentationLinks = documentationLinks
        )

        addWorkItemUseCase.invoke(addWorkItemBody).collect { result ->
            when (result) {
                is Resource.Loading -> _addWorkItemState.update {
                    it.copy(isLoading = true)
                }
                is Resource.Error -> {
                    _addWorkItemState.update { it.copy(isLoading = false) }
                    _addWorkItemEvents.emit(AddWorkItemEvents.ShowError(result.message ?: "Unexpected error"))
                }
                is Resource.Success -> {
                    _addWorkItemState.update { it.copy(isLoading = false) }
                    if (result.data?.success == true) {

                        _addWorkItemEvents.emit(
                            AddWorkItemEvents.Success
                        )
                    } else {
                        _addWorkItemEvents.emit(
                            AddWorkItemEvents.ShowError(
                                result.data?.message ?: "Failed to add work item"
                            )
                        )
                    }
                }
            }
        }
    }
}