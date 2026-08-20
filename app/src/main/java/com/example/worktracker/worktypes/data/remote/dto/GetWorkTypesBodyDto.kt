package com.example.worktracker.worktypes.data.remote.dto

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class GetWorkTypesBodyDto(
    @SerializedName("action")
    @Expose
    var action: String,
    @SerializedName("page")
    @Expose
    var page: Int,
    @SerializedName("pageSize")
    @Expose
    var pageSize: Int
)
