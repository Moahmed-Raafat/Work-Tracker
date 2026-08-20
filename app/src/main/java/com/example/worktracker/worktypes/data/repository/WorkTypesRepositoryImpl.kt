package com.example.worktracker.worktypes.data.repository

import com.example.worktracker.common.ServiceAPI
import com.example.worktracker.common.safeApiCall
import com.example.worktracker.worktypes.data.remote.mappers.toDomain
import com.example.worktracker.worktypes.data.remote.mappers.toDto
import com.example.worktracker.worktypes.domain.model.AddWorkTypeBody
import com.example.worktracker.worktypes.domain.model.AddWorkTypeResponse
import com.example.worktracker.worktypes.domain.model.DeleteWorkTypeBody
import com.example.worktracker.worktypes.domain.model.DeleteWorkTypeResponse
import com.example.worktracker.worktypes.domain.model.GetWorkTypesBody
import com.example.worktracker.worktypes.domain.model.GetWorkTypesResponse
import com.example.worktracker.worktypes.domain.model.UpdateWorkTypeBody
import com.example.worktracker.worktypes.domain.model.UpdateWorkTypeResponse
import com.example.worktracker.worktypes.domain.repository.WorkTypesRepository
import javax.inject.Inject

class WorkTypesRepositoryImpl @Inject constructor(private val serviceAPI: ServiceAPI) :
    WorkTypesRepository {
    override suspend fun getWorkTypes(getWorkTypesBody: GetWorkTypesBody): GetWorkTypesResponse =
        safeApiCall { serviceAPI.getWorkTypes(getWorkTypesBody.toDto()).toDomain() }

    override suspend fun addWorkType(addWorkTypeBody: AddWorkTypeBody): AddWorkTypeResponse =
        safeApiCall { serviceAPI.addWorkType(addWorkTypeBody.toDto()).toDomain() }

    override suspend fun updateWorkType(updateWorkTypeBody: UpdateWorkTypeBody): UpdateWorkTypeResponse =
        safeApiCall { serviceAPI.updateWorkType(updateWorkTypeBody.toDto()).toDomain() }

    override suspend fun deleteWorkType(deleteWorkTypeBody: DeleteWorkTypeBody): DeleteWorkTypeResponse =
        safeApiCall { serviceAPI.deleteWorkType(deleteWorkTypeBody.toDto()).toDomain() }
}
