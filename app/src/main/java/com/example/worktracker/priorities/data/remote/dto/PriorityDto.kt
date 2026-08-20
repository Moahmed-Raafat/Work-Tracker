package com.example.worktracker.priorities.data.remote.dto

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class PriorityDto(
    @SerializedName("id")
    @Expose
    var id: Int,
    @SerializedName("name")
    @Expose
    var name: String,
    @SerializedName("createdAt")
    @Expose
    var createdAt: String,
    @SerializedName("updatedAt")
    @Expose
    var updatedAt: String
)
