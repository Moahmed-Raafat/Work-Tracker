package com.example.worktracker.contributors.domain.usecase

import com.example.worktracker.common.DomainException
import com.example.worktracker.common.Resource
import com.example.worktracker.contributors.domain.model.GetContributorsBody
import com.example.worktracker.contributors.domain.model.GetContributorsResponse
import com.example.worktracker.contributors.domain.repository.ContributorsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetContributorsUseCase @Inject constructor(private val contributorsRepository: ContributorsRepository)
{
    operator fun invoke(getContributorsBody: GetContributorsBody): Flow<Resource<GetContributorsResponse>> = flow {

        emit(Resource.Loading())
        try {
            val result = contributorsRepository.getContributors(getContributorsBody)
            emit(Resource.Success(result))
        } catch (e: DomainException) {
            emit(Resource.Error(e.message ?: "Unexpected error"))
        }
    }
}