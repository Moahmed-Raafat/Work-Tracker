package com.example.worktracker.contributors.domain.repository

import com.example.worktracker.contributors.domain.model.AddContributorBody
import com.example.worktracker.contributors.domain.model.AddContributorResponse
import com.example.worktracker.contributors.domain.model.DeleteContributorBody
import com.example.worktracker.contributors.domain.model.DeleteContributorResponse
import com.example.worktracker.contributors.domain.model.GetContributorsBody
import com.example.worktracker.contributors.domain.model.GetContributorsResponse
import com.example.worktracker.contributors.domain.model.UpdateContributorBody
import com.example.worktracker.contributors.domain.model.UpdateContributorResponse

interface ContributorsRepository {
    suspend fun getContributors(getContributorsBody: GetContributorsBody) : GetContributorsResponse
    suspend fun addContributor(addContributorBody: AddContributorBody) : AddContributorResponse
    suspend fun updateContributor(updateContributorBody: UpdateContributorBody) : UpdateContributorResponse
    suspend fun deleteContributor(deleteContributorBody: DeleteContributorBody) : DeleteContributorResponse
}