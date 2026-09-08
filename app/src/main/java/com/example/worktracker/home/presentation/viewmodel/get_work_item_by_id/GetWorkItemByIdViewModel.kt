package com.example.worktracker.home.presentation.viewmodel.get_work_item_by_id

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.worktracker.common.Constants
import com.example.worktracker.common.Resource
import com.example.worktracker.home.domain.model.GetWorkItemByIdBody
import com.example.worktracker.home.domain.usecase.GetWorkItemByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class GetWorkItemByIdViewModel @Inject constructor(private val getWorkItemByIdUseCase: GetWorkItemByIdUseCase): ViewModel()
{
    private val _getWorkItemByIdState = MutableStateFlow(GetWorkItemByIdState())
    val getWorkItemByIdState: StateFlow<GetWorkItemByIdState> = _getWorkItemByIdState

    fun getWorkItemById(workItemId: Int) = viewModelScope.launch {

        val getWorkItemByIdBody= GetWorkItemByIdBody(action = Constants.GET_WORK_ITEM_BY_ID_ACTION, workItemId = workItemId)

        getWorkItemByIdUseCase.invoke(getWorkItemByIdBody).collect { result ->
            when (result) {
                is Resource.Loading -> _getWorkItemByIdState.update {
                    it.copy(isLoading = true)
                }
                is Resource.Error -> {
                    _getWorkItemByIdState.update { it.copy(isLoading = false, error = result.message ?: "Unexpected error") }
                }
                is Resource.Success -> {
                    _getWorkItemByIdState.update { state ->
                        state.copy(
                            isLoading = false,
                            getWorkItemByIdResponse = result.data,
                            error = null
                        )
                    }
                }
            }
        }
    }
}