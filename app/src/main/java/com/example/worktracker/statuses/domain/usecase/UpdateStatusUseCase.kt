package com.example.worktracker.statuses.domain.usecase

import com.example.worktracker.common.Constants
import com.example.worktracker.common.DomainException
import com.example.worktracker.common.Resource
import com.example.worktracker.statuses.domain.model.UpdateStatusBody
import com.example.worktracker.statuses.domain.model.UpdateStatusResponse
import com.example.worktracker.statuses.domain.repository.StatusesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UpdateStatusUseCase @Inject constructor(private val repository: StatusesRepository) {
    operator fun invoke(updateStatusBody: UpdateStatusBody): Flow<Resource<UpdateStatusResponse>> =
        flow {
            if (updateStatusBody.newName.isBlank()) {
                throw DomainException(Constants.NAME_CAN_NOT_BE_EMPTY)
            }

            emit(Resource.Loading())
            try {
                val result = repository.updateStatus(updateStatusBody)
                emit(Resource.Success(result))
            } catch (e: DomainException) {
                emit(Resource.Error(e.message ?: "Unexpected error"))
            }
        }
}
