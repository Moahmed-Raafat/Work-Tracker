package com.example.worktracker.home.data.repository

import com.example.worktracker.common.ServiceAPI
import com.example.worktracker.common.safeApiCall
import com.example.worktracker.home.data.remote.mappers.toDomain
import com.example.worktracker.home.data.remote.mappers.toDto
import com.example.worktracker.home.domain.model.AddWorkItemBody
import com.example.worktracker.home.domain.model.AddWorkItemResponse
import com.example.worktracker.home.domain.model.GetWorkItemsBody
import com.example.worktracker.home.domain.model.GetWorkItemsResponse
import com.example.worktracker.home.domain.repository.WorkItemsRepository
import javax.inject.Inject

class WorkItemsRepositoryImpl@Inject constructor(private val serviceAPI: ServiceAPI):
    WorkItemsRepository
{
    override suspend fun getWorkItems(getWorkItemsBody: GetWorkItemsBody): GetWorkItemsResponse =
        safeApiCall{ serviceAPI.getWorkItems(getWorkItemsBody.toDto()).toDomain() }

    override suspend fun addWorkItem(addWorkItemBody: AddWorkItemBody): AddWorkItemResponse =
        safeApiCall { serviceAPI.addWorkItem(addWorkItemBody.toDto()).toDomain() }
}