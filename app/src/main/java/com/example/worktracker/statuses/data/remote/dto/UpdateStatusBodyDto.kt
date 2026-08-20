package com.example.worktracker.statuses.data.remote.dto

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class UpdateStatusBodyDto(
    @SerializedName("action")
    @Expose
    var action: String,
    @SerializedName("id")
    @Expose
    var id: Int,
    @SerializedName("newName")
    @Expose
    var newName: String
)
