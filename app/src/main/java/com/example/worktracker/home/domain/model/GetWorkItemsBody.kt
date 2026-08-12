package com.example.worktracker.home.domain.model


data class GetWorkItemsBody(
    var action: String,
    var page: Int,
    var pageSize: Int,
    var sortByCreationDateDescending: Boolean,
    var filterByWorkTypeId: Int?,
    var filterByAssignerId: Int?,
    var filterByAssigneeId: Int?,
    var filterByStatusId: Int?,
    var filterByPriorityId: Int?
)
