package com.example.worktracker.home.data.remote.dto

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class GetWorkItemByIdResponseDto(
    @SerializedName("success")
    @Expose
    var success: Boolean,
    @SerializedName("workItem")
    @Expose
    var workItem: WorkItemDto
)
