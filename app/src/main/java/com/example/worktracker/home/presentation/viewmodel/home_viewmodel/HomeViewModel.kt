package com.example.worktracker.home.presentation.viewmodel.home_viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.worktracker.common.Constants
import com.example.worktracker.common.Resource
import com.example.worktracker.contributors.domain.usecase.GetContributorsUseCase
import com.example.worktracker.contributors.presentation.viewmodel.get_contributors.GetContributorsViewModel
import com.example.worktracker.home.domain.model.GetWorkItemsBody
import com.example.worktracker.home.domain.usecase.GetWorkItemsUseCase
import com.example.worktracker.home.presentation.viewmodel.GetWorkItemsViewModel
import com.example.worktracker.priorities.domain.usecase.GetPrioritiesUseCase
import com.example.worktracker.priorities.presentation.viewmodel.get_priorities.GetPrioritiesViewModel
import com.example.worktracker.statuses.domain.usecase.GetStatusesUseCase
import com.example.worktracker.statuses.presentation.viewmodel.get_statuses.GetStatusesViewModel
import com.example.worktracker.worktypes.domain.usecase.GetWorkTypesUseCase
import com.example.worktracker.worktypes.presentation.viewmodel.get_worktypes.GetWorkTypesViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getWorkItemsUseCase: GetWorkItemsUseCase,
    private val getWorkTypesUseCase: GetWorkTypesUseCase,
    private val getContributorsUseCase: GetContributorsUseCase,
    private val getStatusesUseCase: GetStatusesUseCase,
    private val getPrioritiesUseCase: GetPrioritiesUseCase
): ViewModel() {
    /*private val _state = MutableStateFlow(HomeState())
    val state: StateFlow<HomeState> = _state

    fun loadHomeData() {

        viewModelScope.launch {

            _state.update {
                it.copy(
                    isLoading = true,
                    error = null
                )
            }

            supervisorScope {

                val workItemsDeferred = async {
                    getWorkItems()
                }

                val workTypesDeferred = async {
                    getWorkTypes()
                }

                val contributorsDeferred = async {
                    getContributors()
                }

                val statusesDeferred = async {
                    getStatuses()
                }

                val prioritiesDeferred = async {
                    getPriorities()
                }

                val workItemsResult = workItemsDeferred.await()
                val workTypesResult = workTypesDeferred.await()
                val contributorsResult = contributorsDeferred.await()
                val statusesResult = statusesDeferred.await()
                val prioritiesResult = prioritiesDeferred.await()

                val allSuccessful =
                    workItemsResult.isSuccess &&
                            workTypesResult.isSuccess &&
                            contributorsResult.isSuccess &&
                            statusesResult.isSuccess &&
                            prioritiesResult.isSuccess

                if (allSuccessful) {

                    val workItems =
                        workItemsResult.data?.toUI()?.workItems
                            ?: emptyList()

                    val workTypes =
                        workTypesResult.data?.toUI()
                            ?: emptyList()

                    val contributors =
                        contributorsResult.data?.toUI()
                            ?: emptyList()

                    val statuses =
                        statusesResult.data?.toUI()
                            ?: emptyList()

                    val priorities =
                        prioritiesResult.data?.toUI()
                            ?: emptyList()


                    _state.update {
                        it.copy(
                            isLoading = false,
                            isReady = true,
                            error = null,

                            workItems = workItems,

                            workTypes = workTypes,

                            contributors = contributors,

                            statuses = statuses,

                            priorities = priorities
                        )
                    }

                } else {

                    val errorMessage =
                        getErrorMessage(
                            workItemsResult,
                            workTypesResult,
                            contributorsResult,
                            statusesResult,
                            prioritiesResult
                        )

                    _state.update {
                        it.copy(
                            isLoading = false,
                            isReady = false,
                            error = errorMessage
                        )
                    }
                }
            }
        }
    }*/
}
/*private suspend fun loadWorkItems(): Resource<*> {

    val body = GetWorkItemsBody(
        action = Constants.GET_WORK_ITEMS_ACTION,

        page = 1,

        pageSize = 10,

        sortByCreationDateDescending = false,

        filterByWorkTypeId = null,

        filterByAssignerId = null,

        filterByAssigneeId = null,

        filterByStatusId = null,

        filterByPriorityId = null
    )

    return getFirstResult(
        getWorkItemsUseCase.invoke(body)
    )
}*/
/*
private suspend fun loadWorkTypes(): Resource<*> {

    */
/*
     * Put the same body that your existing
     * GetWorkTypesViewModel currently uses here.
     *//*


    return getFirstResult(
        getWorkTypesUseCase.invoke(
            */
/* your GetWorkTypesBody *//*

        )
    )
}


private suspend fun loadContributors(): Resource<*> {

    */
/*
     * Put the same body that your existing
     * GetContributorsViewModel currently uses here.
     *//*


    return getFirstResult(
        getContributorsUseCase.invoke(
            */
/* your GetContributorsBody *//*

        )
    )
}


private suspend fun loadStatuses(): Resource<*> {

    */
/*
     * Put the same body that your existing
     * GetStatusesViewModel currently uses here.
     *//*


    return getFirstResult(
        getStatusesUseCase.invoke(
            */
/* your GetStatusesBody *//*

        )
    )
}


private suspend fun loadPriorities(): Resource<*> {

    */
/*
     * Put the same body that your existing
     * GetPrioritiesViewModel currently uses here.
     *//*


    return getFirstResult(
        getPrioritiesUseCase.invoke(
            */
/* your GetPrioritiesBody *//*

        )
    )
}


private suspend fun <T> getFirstResult(
    flow: Flow<Resource<T>>
): Resource<T> {

    return flow.first { result ->

        result !is Resource.Loading
    }
}


private fun getErrorMessage(
    workItemsResult: Resource<*>,
    workTypesResult: Resource<*>,
    contributorsResult: Resource<*>,
    statusesResult: Resource<*>,
    prioritiesResult: Resource<*>
): String {

    return when {

        workItemsResult is Resource.Error ->
            workItemsResult.message ?: "Failed to load work items"

        workTypesResult is Resource.Error ->
            workTypesResult.message ?: "Failed to load work types"

        contributorsResult is Resource.Error ->
            contributorsResult.message ?: "Failed to load contributors"

        statusesResult is Resource.Error ->
            statusesResult.message ?: "Failed to load statuses"

        prioritiesResult is Resource.Error ->
            prioritiesResult.message ?: "Failed to load priorities"

        else ->
            "Failed to load dashboard data"
    }
}


fun retry() {

    loadHomeData()
}*/
