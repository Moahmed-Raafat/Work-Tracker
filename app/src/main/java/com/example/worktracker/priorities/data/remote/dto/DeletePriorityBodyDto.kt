package com.example.worktracker.priorities.data.remote.dto

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class DeletePriorityBodyDto(
    @SerializedName("action")
    @Expose
    var action: String,
    @SerializedName("id")
    @Expose
    var id: Int
)
