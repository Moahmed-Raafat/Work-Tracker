package com.example.worktracker.home.data.remote.dto

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class AddWorkItemResponseDto(
    @SerializedName("success")
    @Expose
    var success: Boolean,
    @SerializedName("id")
    @Expose
    var id: Int,
    @SerializedName("workItemNumber")
    @Expose
    var workItemNumber: String,
    @SerializedName("message")
    @Expose
    var message: String
)