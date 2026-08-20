package com.example.worktracker.priorities.domain.usecase

import com.example.worktracker.common.Constants
import com.example.worktracker.common.DomainException
import com.example.worktracker.common.Resource
import com.example.worktracker.priorities.domain.model.UpdatePriorityBody
import com.example.worktracker.priorities.domain.model.UpdatePriorityResponse
import com.example.worktracker.priorities.domain.repository.PrioritiesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UpdatePriorityUseCase @Inject constructor(private val repository: PrioritiesRepository) {
    operator fun invoke(updatePriorityBody: UpdatePriorityBody): Flow<Resource<UpdatePriorityResponse>> =
        flow {
            if (updatePriorityBody.newName.isBlank()) {
                throw DomainException(Constants.NAME_CAN_NOT_BE_EMPTY)
            }

            emit(Resource.Loading())
            try {
                val result = repository.updatePriority(updatePriorityBody)
                emit(Resource.Success(result))
            } catch (e: DomainException) {
                emit(Resource.Error(e.message ?: "Unexpected error"))
            }
        }
}
