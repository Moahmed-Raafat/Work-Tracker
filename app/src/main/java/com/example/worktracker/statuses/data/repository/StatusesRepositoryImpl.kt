package com.example.worktracker.statuses.data.repository

import com.example.worktracker.common.ServiceAPI
import com.example.worktracker.common.safeApiCall
import com.example.worktracker.statuses.data.remote.mappers.toDomain
import com.example.worktracker.statuses.data.remote.mappers.toDto
import com.example.worktracker.statuses.domain.model.AddStatusBody
import com.example.worktracker.statuses.domain.model.AddStatusResponse
import com.example.worktracker.statuses.domain.model.DeleteStatusBody
import com.example.worktracker.statuses.domain.model.DeleteStatusResponse
import com.example.worktracker.statuses.domain.model.GetStatusesBody
import com.example.worktracker.statuses.domain.model.GetStatusesResponse
import com.example.worktracker.statuses.domain.model.UpdateStatusBody
import com.example.worktracker.statuses.domain.model.UpdateStatusResponse
import com.example.worktracker.statuses.domain.repository.StatusesRepository
import javax.inject.Inject

class StatusesRepositoryImpl @Inject constructor(private val serviceAPI: ServiceAPI) :
    StatusesRepository {
    override suspend fun getStatuses(getStatusesBody: GetStatusesBody): GetStatusesResponse =
        safeApiCall { serviceAPI.getStatuses(getStatusesBody.toDto()).toDomain() }

    override suspend fun addStatus(addStatusBody: AddStatusBody): AddStatusResponse =
        safeApiCall { serviceAPI.addStatus(addStatusBody.toDto()).toDomain() }

    override suspend fun updateStatus(updateStatusBody: UpdateStatusBody): UpdateStatusResponse =
        safeApiCall { serviceAPI.updateStatus(updateStatusBody.toDto()).toDomain() }

    override suspend fun deleteStatus(deleteStatusBody: DeleteStatusBody): DeleteStatusResponse =
        safeApiCall { serviceAPI.deleteStatus(deleteStatusBody.toDto()).toDomain() }
}
