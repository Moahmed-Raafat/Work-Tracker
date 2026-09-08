package com.example.worktracker.home.domain.repository


import com.example.worktracker.home.domain.model.AddWorkItemBody
import com.example.worktracker.home.domain.model.AddWorkItemResponse
import com.example.worktracker.home.domain.model.GetWorkItemByIdBody
import com.example.worktracker.home.domain.model.GetWorkItemByIdResponse
import com.example.worktracker.home.domain.model.GetWorkItemsBody
import com.example.worktracker.home.domain.model.GetWorkItemsResponse


interface WorkItemsRepository {
    suspend fun getWorkItems(getWorkItemsBody: GetWorkItemsBody) : GetWorkItemsResponse
    suspend fun addWorkItem(addWorkItemBody: AddWorkItemBody) : AddWorkItemResponse
    suspend fun getWorkItemById(getWorkItemByIdBody: GetWorkItemByIdBody) : GetWorkItemByIdResponse
}