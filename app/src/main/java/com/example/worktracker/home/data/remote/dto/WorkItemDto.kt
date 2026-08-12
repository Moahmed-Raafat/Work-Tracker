package com.example.worktracker.home.data.remote.dto

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class WorkItemDto(
    @SerializedName("id")
    @Expose
    var id: Int,
    @SerializedName("workItemNumber")
    @Expose
    var workItemNumber: String,
    @SerializedName("title")
    @Expose
    var title: String,
    @SerializedName("description")
    @Expose
    var description: String,
    @SerializedName("createdAt")
    @Expose
    var createdAt: String,
    @SerializedName("updatedAt")
    @Expose
    var updatedAt: String,
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
    var documentationLinks: List<String>
)
