package com.example.worktracker.home.data.remote.dto

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class AddWorkItemBodyDto(
    @SerializedName("action")
    @Expose
    var action: String,
    @SerializedName("title")
    @Expose
    var title: String,
    @SerializedName("description")
    @Expose
    var description: String,
    @SerializedName("workTypeId")
    @Expose
    var workTypeId: Int,
    @SerializedName("assignerId")
    @Expose
    var assignerId: Int,
    @SerializedName("assigneeId")
    @Expose
    var assigneeId: Int,
    @SerializedName("statusId")
    @Expose
    var statusId: Int,
    @SerializedName("priorityId")
    @Expose
    var priorityId: Int,
    @SerializedName("documentationLinks")
    @Expose
    var documentationLinks: MutableList<String> = mutableListOf()
)