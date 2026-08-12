package com.example.worktracker.home.data.remote.dto

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class GetWorkItemsBodyDto(
    @SerializedName("action")
    @Expose
    var action: String,
    @SerializedName("page")
    @Expose
    var page: Int,
    @SerializedName("pageSize")
    @Expose
    var pageSize: Int,
    @SerializedName("sortByCreationDateDescending")
    @Expose
    var sortByCreationDateDescending: Boolean,
    @SerializedName("filterByWorkTypeId")
    @Expose
    var filterByWorkTypeId: Int?,
    @SerializedName("filterByAssignerId")
    @Expose
    var filterByAssignerId: Int?,
    @SerializedName("filterByAssigneeId")
    @Expose
    var filterByAssigneeId: Int?,
    @SerializedName("filterByStatusId")
    @Expose
    var filterByStatusId: Int?,
    @SerializedName("filterByPriorityId")
    @Expose
    var filterByPriorityId: Int?
)

