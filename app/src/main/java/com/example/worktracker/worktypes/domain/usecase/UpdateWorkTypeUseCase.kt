package com.example.worktracker.worktypes.domain.usecase

import com.example.worktracker.common.Constants
import com.example.worktracker.common.DomainException
import com.example.worktracker.common.Resource
import com.example.worktracker.worktypes.domain.model.UpdateWorkTypeBody
import com.example.worktracker.worktypes.domain.model.UpdateWorkTypeResponse
import com.example.worktracker.worktypes.domain.repository.WorkTypesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UpdateWorkTypeUseCase @Inject constructor(private val workTypesRepository: WorkTypesRepository) {
    operator fun invoke(updateWorkTypeBody: UpdateWorkTypeBody): Flow<Resource<UpdateWorkTypeResponse>> =
        flow {
            if (updateWorkTypeBody.newName.isBlank()) {
                throw DomainException(Constants.NAME_CAN_NOT_BE_EMPTY)
            }

            emit(Resource.Loading())
            try {
                val result = workTypesRepository.updateWorkType(updateWorkTypeBody)
                emit(Resource.Success(result))
            } catch (e: DomainException) {
                emit(Resource.Error(e.message ?: "Unexpected error"))
            }
        }
}
