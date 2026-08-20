package com.example.worktracker.priorities.domain.usecase

import com.example.worktracker.common.DomainException
import com.example.worktracker.common.Resource
import com.example.worktracker.priorities.domain.model.DeletePriorityBody
import com.example.worktracker.priorities.domain.model.DeletePriorityResponse
import com.example.worktracker.priorities.domain.repository.PrioritiesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DeletePriorityUseCase @Inject constructor(private val repository: PrioritiesRepository) {
    operator fun invoke(deletePriorityBody: DeletePriorityBody): Flow<Resource<DeletePriorityResponse>> =
        flow {
            emit(Resource.Loading())
            try {
                val result = repository.deletePriority(deletePriorityBody)
                emit(Resource.Success(result))
            } catch (e: DomainException) {
                emit(Resource.Error(e.message ?: "Unexpected error"))
            }
        }
}
