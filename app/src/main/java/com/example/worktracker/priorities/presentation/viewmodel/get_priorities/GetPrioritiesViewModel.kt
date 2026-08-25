package com.example.worktracker.priorities.presentation.viewmodel.get_priorities

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.worktracker.common.Constants
import com.example.worktracker.common.Resource
import com.example.worktracker.priorities.domain.model.GetPrioritiesBody
import com.example.worktracker.priorities.domain.usecase.GetPrioritiesUseCase
import com.example.worktracker.priorities.presentation.mappers.toUI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GetPrioritiesViewModel @Inject constructor(private val getPrioritiesUseCase: GetPrioritiesUseCase) :
    ViewModel() {
    private val _state = MutableStateFlow(GetPrioritiesState())
    val state: StateFlow<GetPrioritiesState> = _state

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

        val getPrioritiesBody = GetPrioritiesBody(
            action = Constants.GET_PRIORITIES_ACTION,
            page = currentPage,
            pageSize = pageSize
        )

        getPrioritiesUseCase.invoke(getPrioritiesBody).collect { result ->
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

                    if (newResponse.priorities.size < pageSize) {
                        isLastPage = true
                    }

                    val oldItems = if (currentPage == 1) {
                        emptyList()
                    } else {
                        _state.value.prioritiesList
                    }

                    val mergedItems = oldItems.plus(newResponse.priorities)

                    val mergedResponse = newResponse.copy(priorities = mergedItems)

                    _state.update { state ->
                        state.copy(
                            isLoading = false,
                            prioritiesList = mergedResponse.priorities,
                            error = null
                        )
                    }
                }
            }
        }
    }

    fun removeItem(id: Int) {
        _state.update { state ->
            state.copy(prioritiesList = state.prioritiesList.filter { it.id != id })
        }
    }
}
