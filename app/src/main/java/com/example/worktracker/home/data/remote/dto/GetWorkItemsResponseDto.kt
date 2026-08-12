package com.example.worktracker.home.data.remote.dto

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class GetWorkItemsResponseDto(
    @SerializedName("success")
    @Expose
    val success: Boolean,
    @SerializedName("page")
    @Expose
    val page: Int,
    @SerializedName("pageSize")
    @Expose
    val pageSize: Int,
    @SerializedName("totalCount")
    @Expose
    val totalCount: Int,
    @SerializedName("workItems")
    @Expose
    val workItems: List<WorkItemDto>
)
