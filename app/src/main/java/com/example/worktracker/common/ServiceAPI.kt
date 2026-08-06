package com.example.worktracker.common

import com.example.worktracker.contributors.data.remote.dto.AddContributorBodyDto
import com.example.worktracker.contributors.data.remote.dto.AddContributorResponseDto
import com.example.worktracker.contributors.data.remote.dto.DeleteContributorBodyDto
import com.example.worktracker.contributors.data.remote.dto.DeleteContributorResponseDto
import com.example.worktracker.contributors.data.remote.dto.GetContributorsBodyDto
import com.example.worktracker.contributors.data.remote.dto.GetContributorsResponseDto
import com.example.worktracker.contributors.data.remote.dto.UpdateContributorBodyDto
import com.example.worktracker.contributors.data.remote.dto.UpdateContributorResponseDto
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
    //statuses


    ////////////////////////////////////////////////////////////////////////////////////////////////
    //priorities


    ////////////////////////////////////////////////////////////////////////////////////////////////
    //comments


    ////////////////////////////////////////////////////////////////////////////////////////////////
    //bugs
}