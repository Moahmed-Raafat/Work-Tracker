package com.example.worktracker.contributors.data.remote.dto

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class UpdateContributorResponseDto(
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
