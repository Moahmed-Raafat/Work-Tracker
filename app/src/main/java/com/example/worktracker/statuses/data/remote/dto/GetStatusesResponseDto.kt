package com.example.worktracker.statuses.data.remote.dto

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class GetStatusesResponseDto(
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
    @SerializedName("statuses")
    @Expose
    var statuses: List<StatusDto>
)
