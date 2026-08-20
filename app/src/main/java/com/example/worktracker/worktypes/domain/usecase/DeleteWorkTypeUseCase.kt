package com.example.worktracker.worktypes.domain.usecase

import com.example.worktracker.common.DomainException
import com.example.worktracker.common.Resource
import com.example.worktracker.worktypes.domain.model.DeleteWorkTypeBody
import com.example.worktracker.worktypes.domain.model.DeleteWorkTypeResponse
import com.example.worktracker.worktypes.domain.repository.WorkTypesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DeleteWorkTypeUseCase @Inject constructor(private val workTypesRepository: WorkTypesRepository) {
    operator fun invoke(deleteWorkTypeBody: DeleteWorkTypeBody): Flow<Resource<DeleteWorkTypeResponse>> =
        flow {
            emit(Resource.Loading())
            try {
                val result = workTypesRepository.deleteWorkType(deleteWorkTypeBody)
                emit(Resource.Success(result))
            } catch (e: DomainException) {
                emit(Resource.Error(e.message ?: "Unexpected error"))
            }
        }
}
