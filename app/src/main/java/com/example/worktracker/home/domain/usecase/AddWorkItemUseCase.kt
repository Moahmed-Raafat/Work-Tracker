package com.example.worktracker.home.domain.usecase

import com.example.worktracker.common.Resource
import com.example.worktracker.home.domain.model.AddWorkItemBody
import com.example.worktracker.home.domain.model.AddWorkItemResponse
import com.example.worktracker.home.domain.repository.WorkItemsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AddWorkItemUseCase @Inject constructor(private val workItemsRepository: WorkItemsRepository)
{
    operator fun invoke(addWorkItemBody: AddWorkItemBody): Flow<Resource<AddWorkItemResponse>> =
        flow {
            emit(Resource.Loading())
            try {
                val result = workItemsRepository.addWorkItem(addWorkItemBody)
                emit(Resource.Success(result))
            } catch (e: Exception) {
                emit(Resource.Error(e.message ?: "Unexpected error"))
            }
        }
}