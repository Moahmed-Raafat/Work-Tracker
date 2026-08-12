package com.example.worktracker.home.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.worktracker.common.Constants
import com.example.worktracker.common.Resource
import com.example.worktracker.home.domain.model.GetWorkItemsBody
import com.example.worktracker.home.domain.usecase.GetWorkItemsUseCase
import com.example.worktracker.home.presentation.mappers.toUI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GetWorkItemsViewModel @Inject constructor(private val getWorkItemsUseCase: GetWorkItemsUseCase):
    ViewModel()
{

    private val _state = MutableStateFlow(GetWorkItemsState())
    val state: StateFlow<GetWorkItemsState> = _state

    private var currentPage = 1
    private val pageSize = 10
    private var isLastPage = false
    private var isLoadingPage = false

    var sortByCreationDateDescending: Boolean = false
    var filterByWorkTypeId: Int? = null
    var filterByAssignerId: Int? = null
    var filterByAssigneeId: Int? = null
    var filterByStatusId: Int? = null
    var filterByPriorityId: Int? = null

    init {
        loadFirstPage(
            sortByCreationDateDescending = sortByCreationDateDescending,
            filterByWorkTypeId= filterByWorkTypeId,
            filterByAssignerId= filterByAssignerId,
            filterByAssigneeId= filterByAssigneeId,
            filterByStatusId= filterByStatusId,
            filterByPriorityId= filterByPriorityId
        )
    }

    fun loadFirstPage(sortByCreationDateDescending: Boolean,
                      filterByWorkTypeId: Int? = null,
                      filterByAssignerId: Int? = null,
                      filterByAssigneeId: Int? = null,
                      filterByStatusId: Int? = null,
                      filterByPriorityId: Int? = null)
    {
        this.sortByCreationDateDescending = sortByCreationDateDescending
        this.filterByWorkTypeId = filterByWorkTypeId
        this.filterByAssignerId = filterByAssignerId
        this.filterByAssigneeId = filterByAssigneeId
        this.filterByStatusId = filterByStatusId
        this.filterByPriorityId = filterByPriorityId

        currentPage = 1
        isLastPage = false
        isLoadingPage = false
        requestPage()
    }

    fun loadNextPage()
    {
        if (isLoadingPage || isLastPage) return

        currentPage++
        requestPage()
    }


    //sortByCreationDateDescending  is mandatory,
    //filterByWorkTypeId is optional,
    //filterByAssignerId is optional,
    //filterByAssigneeId is optional,
    //filterByStatusId is optional,
    //filterByPriorityId is optional

    private fun requestPage() = viewModelScope.launch {

        isLoadingPage = true

        val getWorkItemsBody = GetWorkItemsBody(
            action = Constants.GET_WORK_ITEMS_ACTION,
            page = currentPage,
            pageSize = pageSize,
            sortByCreationDateDescending = sortByCreationDateDescending,
            filterByWorkTypeId = filterByWorkTypeId,
            filterByAssignerId = filterByAssignerId,
            filterByAssigneeId = filterByAssigneeId,
            filterByStatusId = filterByStatusId,
            filterByPriorityId = filterByPriorityId
        )


        getWorkItemsUseCase.invoke(getWorkItemsBody).collect { result ->
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

                    if (newResponse.workItems.size < pageSize) {
                        isLastPage = true
                    }

                    val oldItems = if (currentPage == 1) {
                        emptyList()
                    } else {
                        _state.value.workItemsList
                    }

                    val mergedItems = oldItems.plus(newResponse.workItems)

                    val mergedResponse = newResponse.copy(workItems = mergedItems)

                    _state.update { state ->
                        state.copy(
                            isLoading = false,
                            workItemsList = mergedResponse.workItems,
                            error = null
                        )
                    }
                }
            }
        }
    }

    fun removeItem(id: Int) {
        _state.update { state ->
            state.copy(workItemsList = state.workItemsList.filter { it.id != id })
        }
    }
}