package com.example.worktracker.worktypes.data.remote.dto

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class AddWorkTypeBodyDto(
    @SerializedName("action")
    @Expose
    var action: String,
    @SerializedName("name")
    @Expose
    var name: String
)
