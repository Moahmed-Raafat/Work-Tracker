package com.example.worktracker.contributors.domain.usecase

import com.example.worktracker.common.Constants
import com.example.worktracker.common.DomainException
import com.example.worktracker.common.Resource
import com.example.worktracker.contributors.domain.model.AddContributorBody
import com.example.worktracker.contributors.domain.model.AddContributorResponse
import com.example.worktracker.contributors.domain.repository.ContributorsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AddContributorUseCase @Inject constructor(private val contributorsRepository: ContributorsRepository)
{
    operator fun invoke(addContributorBody: AddContributorBody): Flow<Resource<AddContributorResponse>> = flow {

        if(addContributorBody.name.isBlank())
        {
            throw DomainException(Constants.NAME_CAN_NOT_BE_EMPTY)
        }

        emit(Resource.Loading())
        try {
            val result = contributorsRepository.addContributor(addContributorBody)
            emit(Resource.Success(result))
        } catch (e: DomainException) {
            emit(Resource.Error(e.message ?: "Unexpected error"))
        }
    }
}