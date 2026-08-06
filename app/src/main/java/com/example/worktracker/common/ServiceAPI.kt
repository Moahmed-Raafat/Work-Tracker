package com.example.worktracker.common

import com.example.worktracker.contributors.data.remote.dto.AddContributorBodyDto
import com.example.worktracker.contributors.data.remote.dto.AddContributorResponseDto
import com.example.worktracker.contributors.data.remote.dto.DeleteContributorBodyDto
import com.example.worktracker.contributors.data.remote.dto.DeleteContributorResponseDto
import com.example.worktracker.contributors.data.remote.dto.GetContributorsBodyDto
import com.example.worktracker.contributors.data.remote.dto.GetContributorsResponseDto
import com.example.worktracker.contributors.data.remote.dto.UpdateContributorBodyDto
import com.example.worktracker.contributors.data.remote.dto.UpdateContributorResponseDto
import com.example.worktracker.worktypes.data.remote.dto.AddWorkTypeBodyDto
import com.example.worktracker.worktypes.data.remote.dto.AddWorkTypeResponseDto
import com.example.worktracker.worktypes.data.remote.dto.DeleteWorkTypeBodyDto
import com.example.worktracker.worktypes.data.remote.dto.DeleteWorkTypeResponseDto
import com.example.worktracker.worktypes.data.remote.dto.GetWorkTypesBodyDto
import com.example.worktracker.worktypes.data.remote.dto.GetWorkTypesResponseDto
import com.example.worktracker.worktypes.data.remote.dto.UpdateWorkTypeBodyDto
import com.example.worktracker.worktypes.data.remote.dto.UpdateWorkTypeResponseDto
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface ServiceAPI {

    //contributors

    @Headers("Content-Type: application/json")
    @POST("exec")
    suspend fun getContributors(@Body getContributorsBodyDto: GetContributorsBodyDto) : GetContributorsResponseDto

    @Headers("Content-Type: application/json")
    @POST("exec")
    suspend fun addContributor(@Body addContributorsBodyDto: AddContributorBodyDto) : AddContributorResponseDto

    @Headers("Content-Type: application/json")
    @POST("exec")
    suspend fun updateContributor(@Body updateContributorBodyDto: UpdateContributorBodyDto) : UpdateContributorResponseDto

    @Headers("Content-Type: application/json")
    @POST("exec")
    suspend fun deleteContributor(@Body deleteContributorBodyDto: DeleteContributorBodyDto) : DeleteContributorResponseDto
    
    ////////////////////////////////////////////////////////////////////////////////////////////////
    //work types

    @Headers("Content-Type: application/json")
    @POST("exec")
    suspend fun getWorkTypes(@Body getWorkTypesBodyDto: GetWorkTypesBodyDto) : GetWorkTypesResponseDto

    @Headers("Content-Type: application/json")
    @POST("exec")
    suspend fun addWorkType(@Body addWorkTypeBodyDto: AddWorkTypeBodyDto) : AddWorkTypeResponseDto

    @Headers("Content-Type: application/json")
    @POST("exec")
    suspend fun updateWorkType(@Body updateWorkTypeBodyDto: UpdateWorkTypeBodyDto) : UpdateWorkTypeResponseDto

    @Headers("Content-Type: application/json")
    @POST("exec")
    suspend fun deleteWorkType(@Body deleteWorkTypeBodyDto: DeleteWorkTypeBodyDto) : DeleteWorkTypeResponseDto
    ////////////////////////////////////////////////////////////////////////////////////////////////
    //statuses


    ////////////////////////////////////////////////////////////////////////////////////////////////
    //priorities


    ////////////////////////////////////////////////////////////////////////////////////////////////
    //comments


    ////////////////////////////////////////////////////////////////////////////////////////////////
    //bugs
}