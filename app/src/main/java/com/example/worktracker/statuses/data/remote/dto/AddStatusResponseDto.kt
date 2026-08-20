package com.example.worktracker.statuses.data.remote.dto

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class AddStatusResponseDto(
    @SerializedName("success")
    @Expose
    var success: Boolean,
    @SerializedName("id")
    @Expose
    var id: Int,
    @SerializedName("message")
    @Expose
    var message: String
)
