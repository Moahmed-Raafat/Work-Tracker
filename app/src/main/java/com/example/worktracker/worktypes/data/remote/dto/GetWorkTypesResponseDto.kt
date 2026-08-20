package com.example.worktracker.worktypes.data.remote.dto

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class GetWorkTypesResponseDto(
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
    @SerializedName("workTypes")
    @Expose
    var workTypes: List<WorkTypeDto>
)
