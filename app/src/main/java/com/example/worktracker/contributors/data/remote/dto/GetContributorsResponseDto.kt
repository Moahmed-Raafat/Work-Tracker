package com.example.worktracker.contributors.data.remote.dto

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class GetContributorsResponseDto(

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
    @SerializedName("contributors")
    @Expose
    var contributors: List<ContributorDto>
)