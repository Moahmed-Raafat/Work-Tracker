package com.example.worktracker.contributors.domain.usecase

import com.example.worktracker.common.Constants
import com.example.worktracker.common.DomainException
import com.example.worktracker.common.Resource
import com.example.worktracker.contributors.domain.model.DeleteContributorBody
import com.example.worktracker.contributors.domain.model.DeleteContributorResponse
import com.example.worktracker.contributors.domain.repository.ContributorsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DeleteContributorUseCase @Inject constructor(private val contributorsRepository: ContributorsRepository)
{
    operator fun invoke(deleteContributorBody: DeleteContributorBody): Flow<Resource<DeleteContributorResponse>> = flow {

        emit(Resource.Loading())
        try {
            val result = contributorsRepository.deleteContributor(deleteContributorBody)
            emit(Resource.Success(result))
        } catch (e: DomainException) {
            emit(Resource.Error(e.message ?: "Unexpected error"))
        }
    }
}
