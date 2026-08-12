package com.example.worktracker.home.domain.usecase

import com.example.worktracker.common.Resource
import com.example.worktracker.home.domain.model.GetWorkItemsBody
import com.example.worktracker.home.domain.model.GetWorkItemsResponse
import com.example.worktracker.home.domain.repository.WorkItemsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetWorkItemsUseCase  @Inject constructor(private val repository: WorkItemsRepository) {
    operator fun invoke(getWorkItemsBody: GetWorkItemsBody): Flow<Resource<GetWorkItemsResponse>> =
        flow {
            emit(Resource.Loading())
            try {
                val result = repository.getWorkItems(getWorkItemsBody)
                emit(Resource.Success(result))
            } catch (e: Exception) {
                emit(Resource.Error(e.message ?: "Unexpected error"))
            }
        }
}