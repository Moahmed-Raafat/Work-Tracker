package com.example.worktracker.statuses.data.remote.dto

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class AddStatusBodyDto(
    @SerializedName("action")
    @Expose
    var action: String,
    @SerializedName("name")
    @Expose
    var name: String
)
