package com.example.worktracker.priorities.data.remote.dto

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class GetPrioritiesBodyDto(
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
