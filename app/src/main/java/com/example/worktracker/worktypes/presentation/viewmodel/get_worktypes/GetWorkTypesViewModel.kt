package com.example.worktracker.worktypes.presentation.viewmodel.get_worktypes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.worktracker.common.Constants
import com.example.worktracker.common.Resource
import com.example.worktracker.worktypes.domain.model.GetWorkTypesBody
import com.example.worktracker.worktypes.domain.usecase.GetWorkTypesUseCase
import com.example.worktracker.worktypes.presentation.mappers.toUI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GetWorkTypesViewModel @Inject constructor(private val getWorkTypesUseCase: GetWorkTypesUseCase) :
    ViewModel() {
    private val _state = MutableStateFlow(GetWorkTypesState())
    val state: StateFlow<GetWorkTypesState> = _state

    private var currentPage = 1
    private val pageSize = 10
    private var isLastPage = false
    private var isLoadingPage = false

    /*init {
        loadFirstPage()
    }*/

    fun loadFirstPage() {
        currentPage = 1
        isLastPage = false
        isLoadingPage = false
        requestPage()
    }

    fun loadNextPage() {
        if (isLoadingPage || isLastPage) return

        currentPage++
        requestPage()
    }

    private fun requestPage() = viewModelScope.launch {

        isLoadingPage = true

        val getWorkTypesBody = GetWorkTypesBody(
            action = Constants.GET_WORK_TYPES_ACTION,
            page = currentPage,
            pageSize = pageSize
        )

        getWorkTypesUseCase.invoke(getWorkTypesBody).collect { result ->
            when (result) {

                is Resource.Loading -> {
                    _state.update { state ->
                        state.copy(isLoading = true, error = null)
                    }
                }

                is Resource.Error -> {

                    isLoadingPage = false

                    _state.update { state ->
                        state.copy(
                            isLoading = false,
                            error = result.message ?: "Unexpected error"
                        )
                    }
                }

                is Resource.Success -> {

                    isLoadingPage = false

                    val newResponse = result.data?.toUI() ?: return@collect

                    if (newResponse.workTypes.size < pageSize) {
                        isLastPage = true
                    }

                    val oldItems = if (currentPage == 1) {
                        emptyList()
                    } else {
                        _state.value.workTypesList
                    }

                    val mergedItems = oldItems.plus(newResponse.workTypes)

                    val mergedResponse = newResponse.copy(workTypes = mergedItems)

                    _state.update { state ->
                        state.copy(
                            isLoading = false,
                            workTypesList = mergedResponse.workTypes,
                            error = null
                        )
                    }
                }
            }
        }
    }

    fun removeItem(id: Int) {
        _state.update { state ->
            state.copy(workTypesList = state.workTypesList.filter { it.id != id })
        }
    }
}
