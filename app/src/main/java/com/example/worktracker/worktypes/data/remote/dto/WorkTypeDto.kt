package com.example.worktracker.worktypes.data.remote.dto

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class WorkTypeDto(
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
