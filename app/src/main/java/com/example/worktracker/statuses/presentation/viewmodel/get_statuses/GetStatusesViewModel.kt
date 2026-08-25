package com.example.worktracker.statuses.presentation.viewmodel.get_statuses

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.worktracker.common.Constants
import com.example.worktracker.common.Resource
import com.example.worktracker.statuses.domain.model.GetStatusesBody
import com.example.worktracker.statuses.domain.usecase.GetStatusesUseCase
import com.example.worktracker.statuses.presentation.mappers.toUI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GetStatusesViewModel @Inject constructor(private val getStatusesUseCase: GetStatusesUseCase) : ViewModel() {
    private val _state = MutableStateFlow(GetStatusesState())
    val state: StateFlow<GetStatusesState> = _state

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

        val getStatusesBody = GetStatusesBody(
            action = Constants.GET_STATUSES_ACTION,
            page = currentPage,
            pageSize = pageSize
        )

        getStatusesUseCase.invoke(getStatusesBody).collect { result ->
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

                    if (newResponse.statuses.size < pageSize) {
                        isLastPage = true
                    }

                    val oldItems = if (currentPage == 1) {
                        emptyList()
                    } else {
                        _state.value.statusesList
                    }

                    val mergedItems = oldItems.plus(newResponse.statuses)

                    val mergedResponse = newResponse.copy(statuses = mergedItems)

                    _state.update { state ->
                        state.copy(
                            isLoading = false,
                            statusesList = mergedResponse.statuses,
                            error = null
                        )
                    }
                }
            }
        }
    }

    fun removeItem(id: Int) {
        _state.update { state ->
            state.copy(statusesList = state.statusesList.filter { it.id != id })
        }
    }
}
