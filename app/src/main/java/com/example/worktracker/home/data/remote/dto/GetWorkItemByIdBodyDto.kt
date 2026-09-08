package com.example.worktracker.home.data.remote.dto

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class GetWorkItemByIdBodyDto(
    @SerializedName("action")
    @Expose
    var action: String,
    @SerializedName("workItemId")
    @Expose
    var workItemId: Int
)
