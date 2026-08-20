package com.example.worktracker.statuses.domain.repository

import com.example.worktracker.statuses.domain.model.AddStatusBody
import com.example.worktracker.statuses.domain.model.AddStatusResponse
import com.example.worktracker.statuses.domain.model.DeleteStatusBody
import com.example.worktracker.statuses.domain.model.DeleteStatusResponse
import com.example.worktracker.statuses.domain.model.GetStatusesBody
import com.example.worktracker.statuses.domain.model.GetStatusesResponse
import com.example.worktracker.statuses.domain.model.UpdateStatusBody
import com.example.worktracker.statuses.domain.model.UpdateStatusResponse

interface StatusesRepository {
    suspend fun getStatuses(getStatusesBody: GetStatusesBody): GetStatusesResponse
    suspend fun addStatus(addStatusBody: AddStatusBody): AddStatusResponse
    suspend fun updateStatus(updateStatusBody: UpdateStatusBody): UpdateStatusResponse
    suspend fun deleteStatus(deleteStatusBody: DeleteStatusBody): DeleteStatusResponse
}
