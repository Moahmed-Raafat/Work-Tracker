package com.example.worktracker.contributors.data.repository

import com.example.worktracker.common.ServiceAPI
import com.example.worktracker.common.safeApiCall
import com.example.worktracker.contributors.data.remote.mappers.toDomain
import com.example.worktracker.contributors.data.remote.mappers.toDto
import com.example.worktracker.contributors.domain.model.AddContributorBody
import com.example.worktracker.contributors.domain.model.AddContributorResponse
import com.example.worktracker.contributors.domain.model.DeleteContributorBody
import com.example.worktracker.contributors.domain.model.DeleteContributorResponse
import com.example.worktracker.contributors.domain.model.GetContributorsBody
import com.example.worktracker.contributors.domain.model.GetContributorsResponse
import com.example.worktracker.contributors.domain.model.UpdateContributorBody
import com.example.worktracker.contributors.domain.model.UpdateContributorResponse
import com.example.worktracker.contributors.domain.repository.ContributorsRepository
import javax.inject.Inject

class ContributorsRepositoryImpl @Inject constructor(private val serviceAPI: ServiceAPI):ContributorsRepository
{
    override suspend fun getContributors(getContributorsBody: GetContributorsBody): GetContributorsResponse =
        safeApiCall { serviceAPI.getContributors(getContributorsBody.toDto()).toDomain() }

    override suspend fun addContributor(addContributorBody: AddContributorBody): AddContributorResponse =
        safeApiCall { serviceAPI.addContributor(addContributorBody.toDto()).toDomain() }

    override suspend fun updateContributor(updateContributorBody: UpdateContributorBody): UpdateContributorResponse =
        safeApiCall { serviceAPI.updateContributor(updateContributorBody.toDto()).toDomain() }

    override suspend fun deleteContributor(deleteContributorBody: DeleteContributorBody): DeleteContributorResponse =
        safeApiCall { serviceAPI.deleteContributor(deleteContributorBody.toDto()).toDomain() }
}