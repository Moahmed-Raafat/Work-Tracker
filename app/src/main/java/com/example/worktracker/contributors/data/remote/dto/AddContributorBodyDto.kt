package com.example.worktracker.contributors.data.remote.dto

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class AddContributorBodyDto(
    @SerializedName("action")
    @Expose
    var action: String,
    @SerializedName("name")
    @Expose
    var name: String
)
