package com.example.worktracker.home.data.remote.dto

import com.example.worktracker.contributors.data.remote.dto.ContributorDto
import com.example.worktracker.priorities.data.remote.dto.PriorityDto
import com.example.worktracker.statuses.data.remote.dto.StatusDto
import com.example.worktracker.worktypes.data.remote.dto.WorkTypeDto
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

    @SerializedName("workType")
    @Expose
    var workType: WorkTypeDto,
    @SerializedName("assigner")
    @Expose
    var assigner: ContributorDto,
    @SerializedName("assignee")
    @Expose
    var assignee: ContributorDto,
    @SerializedName("status")
    @Expose
    var status: StatusDto,
    @SerializedName("priority")
    @Expose
    var priority: PriorityDto,

    @SerializedName("documentationLinks")
    @Expose
    var documentationLinks: List<String>
)
