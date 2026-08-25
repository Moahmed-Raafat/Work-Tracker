package com.example.worktracker.contributors.presentation.viewmodel.get_contributors

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.worktracker.common.Constants
import com.example.worktracker.common.Resource
import com.example.worktracker.contributors.domain.model.GetContributorsBody
import com.example.worktracker.contributors.domain.usecase.GetContributorsUseCase
import com.example.worktracker.contributors.presentation.mappers.toUI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GetContributorsViewModel @Inject constructor(private val getContributorsUseCase: GetContributorsUseCase):
    ViewModel()
{
    private val _state= MutableStateFlow(GetContributorsState())
    val state: StateFlow<GetContributorsState> = _state


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


    private fun requestPage()= viewModelScope.launch {

        isLoadingPage = true

        val getContributorsBody= GetContributorsBody(
            action = Constants.GET_CONTRIBUTORS_ACTION,
            page = currentPage,
            pageSize = pageSize
        )

        getContributorsUseCase.invoke(getContributorsBody).collect{ result ->
            when(result){

                is Resource.Loading -> {
                    //loading appears at each calling
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

                    if (newResponse.contributors.size < pageSize) {
                        isLastPage = true
                    }

                    val oldItems = if (currentPage == 1) {
                        emptyList()
                    } else {
                        _state.value.contributorsList
                    }

                    val mergedItems = oldItems.plus(newResponse.contributors)

                    val mergedResponse = newResponse.copy(contributors = mergedItems)


                    _state.update { state ->
                        state.copy(
                            isLoading = false,
                            contributorsList = mergedResponse.contributors,
                            error = null
                        )
                    }
                }
            }
        }
    }


    fun removeItem(id: Int) {
        _state.update { state ->
            state.copy(contributorsList = state.contributorsList.filter { it.id != id })
        }
    }

}