package com.example.worktracker.priorities.data.repository

import com.example.worktracker.common.ServiceAPI
import com.example.worktracker.common.safeApiCall
import com.example.worktracker.priorities.data.remote.mappers.toDomain
import com.example.worktracker.priorities.data.remote.mappers.toDto
import com.example.worktracker.priorities.domain.model.AddPriorityBody
import com.example.worktracker.priorities.domain.model.AddPriorityResponse
import com.example.worktracker.priorities.domain.model.DeletePriorityBody
import com.example.worktracker.priorities.domain.model.DeletePriorityResponse
import com.example.worktracker.priorities.domain.model.GetPrioritiesBody
import com.example.worktracker.priorities.domain.model.GetPrioritiesResponse
import com.example.worktracker.priorities.domain.model.UpdatePriorityBody
import com.example.worktracker.priorities.domain.model.UpdatePriorityResponse
import com.example.worktracker.priorities.domain.repository.PrioritiesRepository
import javax.inject.Inject

class PrioritiesRepositoryImpl @Inject constructor(private val serviceAPI: ServiceAPI) :
    PrioritiesRepository {
    override suspend fun getPriorities(getPrioritiesBody: GetPrioritiesBody): GetPrioritiesResponse =
        safeApiCall { serviceAPI.getPriorities(getPrioritiesBody.toDto()).toDomain() }

    override suspend fun addPriority(addPriorityBody: AddPriorityBody): AddPriorityResponse =
        safeApiCall { serviceAPI.addPriority(addPriorityBody.toDto()).toDomain() }

    override suspend fun updatePriority(updatePriorityBody: UpdatePriorityBody): UpdatePriorityResponse =
        safeApiCall { serviceAPI.updatePriority(updatePriorityBody.toDto()).toDomain() }

    override suspend fun deletePriority(deletePriorityBody: DeletePriorityBody): DeletePriorityResponse =
        safeApiCall { serviceAPI.deletePriority(deletePriorityBody.toDto()).toDomain() }
}
