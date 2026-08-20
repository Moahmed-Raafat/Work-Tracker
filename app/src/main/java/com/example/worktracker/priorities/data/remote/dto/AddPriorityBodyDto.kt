package com.example.worktracker.priorities.data.remote.dto

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class AddPriorityBodyDto(
    @SerializedName("action")
    @Expose
    var action: String,
    @SerializedName("name")
    @Expose
    var name: String
)
