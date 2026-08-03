package com.example.worktracker.contributors.domain.usecase

import com.example.worktracker.common.Constants
import com.example.worktracker.common.DomainException
import com.example.worktracker.common.Resource
import com.example.worktracker.contributors.domain.model.UpdateContributorBody
import com.example.worktracker.contributors.domain.model.UpdateContributorResponse
import com.example.worktracker.contributors.domain.repository.ContributorsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UpdateContributorUseCase @Inject constructor(private val contributorsRepository: ContributorsRepository)
{
    operator fun invoke(updateContributorBody: UpdateContributorBody): Flow<Resource<UpdateContributorResponse>> = flow {

        if(updateContributorBody.newName.isBlank())
        {
            throw DomainException(Constants.NAME_CAN_NOT_BE_EMPTY)
        }

        emit(Resource.Loading())
        try {
            val result = contributorsRepository.updateContributor(updateContributorBody)
            emit(Resource.Success(result))
        } catch (e: DomainException) {
            emit(Resource.Error(e.message ?: "Unexpected error"))
        }
    }
}