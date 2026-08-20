package com.example.worktracker.priorities.domain.usecase

import com.example.worktracker.common.Resource
import com.example.worktracker.priorities.domain.model.GetPrioritiesBody
import com.example.worktracker.priorities.domain.model.GetPrioritiesResponse
import com.example.worktracker.priorities.domain.repository.PrioritiesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetPrioritiesUseCase @Inject constructor(private val repository: PrioritiesRepository) {
    operator fun invoke(getPrioritiesBody: GetPrioritiesBody): Flow<Resource<GetPrioritiesResponse>> =
        flow {
            emit(Resource.Loading())
            try {
                val result = repository.getPriorities(getPrioritiesBody)
                emit(Resource.Success(result))
            } catch (e: Exception) {
                emit(Resource.Error(e.message ?: "Unexpected error"))
            }
        }
}
