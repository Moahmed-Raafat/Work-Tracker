package com.example.worktracker.common

import com.example.worktracker.contributors.data.remote.dto.AddContributorBodyDto
import com.example.worktracker.contributors.data.remote.dto.AddContributorResponseDto
import com.example.worktracker.contributors.data.remote.dto.DeleteContributorBodyDto
import com.example.worktracker.contributors.data.remote.dto.DeleteContributorResponseDto
import com.example.worktracker.contributors.data.remote.dto.GetContributorsBodyDto
import com.example.worktracker.contributors.data.remote.dto.GetContributorsResponseDto
import com.example.worktracker.contributors.data.remote.dto.UpdateContributorBodyDto
import com.example.worktracker.contributors.data.remote.dto.UpdateContributorResponseDto
import com.example.worktracker.home.data.remote.dto.AddWorkItemBodyDto
import com.example.worktracker.home.data.remote.dto.AddWorkItemResponseDto
import com.example.worktracker.home.data.remote.dto.GetWorkItemsBodyDto
import com.example.worktracker.home.data.remote.dto.GetWorkItemsResponseDto
import com.example.worktracker.worktypes.data.remote.dto.AddWorkTypeBodyDto
import com.example.worktracker.worktypes.data.remote.dto.AddWorkTypeResponseDto
import com.example.worktracker.worktypes.data.remote.dto.DeleteWorkTypeBodyDto
import com.example.worktracker.worktypes.data.remote.dto.DeleteWorkTypeResponseDto
import com.example.worktracker.worktypes.data.remote.dto.GetWorkTypesBodyDto
import com.example.worktracker.worktypes.data.remote.dto.GetWorkTypesResponseDto
import com.example.worktracker.worktypes.data.remote.dto.UpdateWorkTypeBodyDto
import com.example.worktracker.worktypes.data.remote.dto.UpdateWorkTypeResponseDto
import com.example.worktracker.statuses.data.remote.dto.AddStatusBodyDto
import com.example.worktracker.statuses.data.remote.dto.AddStatusResponseDto
import com.example.worktracker.statuses.data.remote.dto.DeleteStatusBodyDto
import com.example.worktracker.statuses.data.remote.dto.DeleteStatusResponseDto
import com.example.worktracker.statuses.data.remote.dto.GetStatusesBodyDto
import com.example.worktracker.statuses.data.remote.dto.GetStatusesResponseDto
import com.example.worktracker.statuses.data.remote.dto.UpdateStatusBodyDto
import com.example.worktracker.statuses.data.remote.dto.UpdateStatusResponseDto
import com.example.worktracker.priorities.data.remote.dto.AddPriorityBodyDto
import com.example.worktracker.priorities.data.remote.dto.AddPriorityResponseDto
import com.example.worktracker.priorities.data.remote.dto.DeletePriorityBodyDto
import com.example.worktracker.priorities.data.remote.dto.DeletePriorityResponseDto
import com.example.worktracker.priorities.data.remote.dto.GetPrioritiesBodyDto
import com.example.worktracker.priorities.data.remote.dto.GetPrioritiesResponseDto
import com.example.worktracker.priorities.data.remote.dto.UpdatePriorityBodyDto
import com.example.worktracker.priorities.data.remote.dto.UpdatePriorityResponseDto
import retrofit2.http.Body
import retrofit2.http.POST

interface ServiceAPI {

    //contributors

    @POST("exec")
    suspend fun getContributors(@Body getContributorsBodyDto: GetContributorsBodyDto) : GetContributorsResponseDto

    @POST("exec")
    suspend fun addContributor(@Body addContributorsBodyDto: AddContributorBodyDto) : AddContributorResponseDto

    @POST("exec")
    suspend fun updateContributor(@Body updateContributorBodyDto: UpdateContributorBodyDto) : UpdateContributorResponseDto

    @POST("exec")
    suspend fun deleteContributor(@Body deleteContributorBodyDto: DeleteContributorBodyDto) : DeleteContributorResponseDto
    
    ////////////////////////////////////////////////////////////////////////////////////////////////
    //work types

    @POST("exec")
    suspend fun getWorkTypes(@Body getWorkTypesBodyDto: GetWorkTypesBodyDto) : GetWorkTypesResponseDto

    @POST("exec")
    suspend fun addWorkType(@Body addWorkTypeBodyDto: AddWorkTypeBodyDto) : AddWorkTypeResponseDto

    @POST("exec")
    suspend fun updateWorkType(@Body updateWorkTypeBodyDto: UpdateWorkTypeBodyDto) : UpdateWorkTypeResponseDto

    @POST("exec")
    suspend fun deleteWorkType(@Body deleteWorkTypeBodyDto: DeleteWorkTypeBodyDto) : DeleteWorkTypeResponseDto
    ////////////////////////////////////////////////////////////////////////////////////////////////
    //statuses

    @POST("exec")
    suspend fun getStatuses(@Body getStatusesBodyDto: GetStatusesBodyDto) : GetStatusesResponseDto

    @POST("exec")
    suspend fun addStatus(@Body addStatusBodyDto: AddStatusBodyDto) : AddStatusResponseDto

    @POST("exec")
    suspend fun updateStatus(@Body updateStatusBodyDto: UpdateStatusBodyDto) : UpdateStatusResponseDto

    @POST("exec")
    suspend fun deleteStatus(@Body deleteStatusBodyDto: DeleteStatusBodyDto) : DeleteStatusResponseDto

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //priorities

    @POST("exec")
    suspend fun getPriorities(@Body getPrioritiesBodyDto: GetPrioritiesBodyDto) : GetPrioritiesResponseDto

    @POST("exec")
    suspend fun addPriority(@Body addPriorityBodyDto: AddPriorityBodyDto) : AddPriorityResponseDto

    @POST("exec")
    suspend fun updatePriority(@Body updatePriorityBodyDto: UpdatePriorityBodyDto) : UpdatePriorityResponseDto

    @POST("exec")
    suspend fun deletePriority(@Body deletePriorityBodyDto: DeletePriorityBodyDto) : DeletePriorityResponseDto


    ////////////////////////////////////////////////////////////////////////////////////////////////
    //comments


    ////////////////////////////////////////////////////////////////////////////////////////////////
    //work items

    @POST("exec")
    suspend fun getWorkItems(@Body getWorkItemsBodyDto: GetWorkItemsBodyDto) : GetWorkItemsResponseDto

    @POST("exec")
    suspend fun addWorkItem(@Body addWorkItemBodyDto: AddWorkItemBodyDto) : AddWorkItemResponseDto
}