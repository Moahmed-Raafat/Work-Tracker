package com.example.worktracker.home.domain.usecase

import com.example.worktracker.common.Resource
import com.example.worktracker.home.domain.model.GetWorkItemByIdBody
import com.example.worktracker.home.domain.model.GetWorkItemByIdResponse
import com.example.worktracker.home.domain.repository.WorkItemsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetWorkItemByIdUseCase  @Inject constructor(private val repository: WorkItemsRepository) {
    operator fun invoke(getWorkItemByIdBody: GetWorkItemByIdBody): Flow<Resource<GetWorkItemByIdResponse>> =
        flow {
            emit(Resource.Loading())
            try {
                val result = repository.getWorkItemById(getWorkItemByIdBody)
                emit(Resource.Success(result))
            } catch (e: Exception) {
                emit(Resource.Error(e.message ?: "Unexpected error"))
            }
        }
}