package com.example.worktracker.statuses.domain.usecase

import com.example.worktracker.common.DomainException
import com.example.worktracker.common.Resource
import com.example.worktracker.statuses.domain.model.DeleteStatusBody
import com.example.worktracker.statuses.domain.model.DeleteStatusResponse
import com.example.worktracker.statuses.domain.repository.StatusesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DeleteStatusUseCase @Inject constructor(private val repository: StatusesRepository) {
    operator fun invoke(deleteStatusBody: DeleteStatusBody): Flow<Resource<DeleteStatusResponse>> =
        flow {
            emit(Resource.Loading())
            try {
                val result = repository.deleteStatus(deleteStatusBody)
                emit(Resource.Success(result))
            } catch (e: DomainException) {
                emit(Resource.Error(e.message ?: "Unexpected error"))
            }
        }
}
