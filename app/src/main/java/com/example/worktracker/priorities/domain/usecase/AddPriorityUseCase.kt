package com.example.worktracker.priorities.domain.usecase

import com.example.worktracker.common.Constants
import com.example.worktracker.common.DomainException
import com.example.worktracker.common.Resource
import com.example.worktracker.priorities.domain.model.AddPriorityBody
import com.example.worktracker.priorities.domain.model.AddPriorityResponse
import com.example.worktracker.priorities.domain.repository.PrioritiesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AddPriorityUseCase @Inject constructor(private val repository: PrioritiesRepository) {
    operator fun invoke(addPriorityBody: AddPriorityBody): Flow<Resource<AddPriorityResponse>> =
        flow {
            if (addPriorityBody.name.isBlank()) {
                throw DomainException(Constants.NAME_CAN_NOT_BE_EMPTY)
            }

            emit(Resource.Loading())
            try {
                val result = repository.addPriority(addPriorityBody)
                emit(Resource.Success(result))
            } catch (e: DomainException) {
                emit(Resource.Error(e.message ?: "Unexpected error"))
            }
        }
}
