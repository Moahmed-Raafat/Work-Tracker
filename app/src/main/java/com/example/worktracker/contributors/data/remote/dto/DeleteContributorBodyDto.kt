package com.example.worktracker.contributors.data.remote.dto

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class DeleteContributorBodyDto(
    @SerializedName("action")
    @Expose
    var action: String,
    @SerializedName("id")
    @Expose
    var id: Int
)
