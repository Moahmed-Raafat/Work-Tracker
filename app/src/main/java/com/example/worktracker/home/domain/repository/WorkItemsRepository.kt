package com.example.worktracker.home.domain.repository

import com.example.worktracker.home.domain.model.AddWorkItemBody
import com.example.worktracker.home.domain.model.AddWorkItemResponse
import com.example.worktracker.home.domain.model.GetWorkItemsBody
import com.example.worktracker.home.domain.model.GetWorkItemsResponse


interface WorkItemsRepository {
    suspend fun getWorkItems(getWorkItemsBody: GetWorkItemsBody) : GetWorkItemsResponse
    suspend fun addWorkItem(addWorkItemBody: AddWorkItemBody) : AddWorkItemResponse
}