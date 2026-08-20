package com.example.worktracker.priorities.data.remote.dto

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class GetPrioritiesResponseDto(
    @SerializedName("success")
    @Expose
    var success: Boolean,
    @SerializedName("page")
    @Expose
    var page: Int,
    @SerializedName("pageSize")
    @Expose
    var pageSize: Int,
    @SerializedName("totalCount")
    @Expose
    var totalCount: Int,
    @SerializedName("priorities")
    @Expose
    var priorities: List<PriorityDto>
)
