package com.example.worktracker.home.domain.usecase

import com.example.worktracker.common.Constants
import com.example.worktracker.common.DomainException
import com.example.worktracker.common.Resource
import com.example.worktracker.home.domain.model.AddWorkItemBody
import com.example.worktracker.home.domain.model.AddWorkItemResponse
import com.example.worktracker.home.domain.repository.WorkItemsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AddWorkItemUseCase @Inject constructor(private val workItemsRepository: WorkItemsRepository)
{
    operator fun invoke(addWorkItemBody: AddWorkItemBody): Flow<Resource<AddWorkItemResponse>> =
        flow {

            if(addWorkItemBody.title == "")
            {
                emit(Resource.Error(Constants.TITLE_IS_REQUIRED))
                return@flow
            }
            if(addWorkItemBody.description == "")
            {
                emit(Resource.Error(Constants.DESCRIPTION_IS_REQUIRED))
                return@flow
            }
            if(addWorkItemBody.workTypeId == null)
            {
                emit(Resource.Error(Constants.WORK_TYPE_IS_REQUIRED))
                return@flow
            }
            if(addWorkItemBody.priorityId == null)
            {
                emit(Resource.Error(Constants.PRIORITY_IS_REQUIRED))
                return@flow
            }
            if(addWorkItemBody.statusId == null)
            {
                emit(Resource.Error(Constants.STATUS_IS_REQUIRED))
                return@flow
            }
            if(addWorkItemBody.assignerId == null)
            {
                emit(Resource.Error(Constants.ASSIGNER_IS_REQUIRED))
                return@flow
            }
            if(addWorkItemBody.assigneeId == null)
            {
                emit(Resource.Error(Constants.ASSIGNEE_IS_REQUIRED))
                return@flow
            }


            emit(Resource.Loading())
            try {
                val result = workItemsRepository.addWorkItem(addWorkItemBody)
                emit(Resource.Success(result))
            } catch (e: Exception) {
                emit(Resource.Error(e.message ?: "Unexpected error"))
            }
        }
}