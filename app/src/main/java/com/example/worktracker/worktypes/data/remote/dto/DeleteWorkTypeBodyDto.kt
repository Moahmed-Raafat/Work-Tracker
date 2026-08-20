package com.example.worktracker.worktypes.data.remote.dto

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class DeleteWorkTypeBodyDto(
    @SerializedName("action")
    @Expose
    var action: String,
    @SerializedName("id")
    @Expose
    var id: Int
)
