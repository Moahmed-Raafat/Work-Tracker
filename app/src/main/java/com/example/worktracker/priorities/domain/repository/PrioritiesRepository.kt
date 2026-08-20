package com.example.worktracker.priorities.domain.repository

import com.example.worktracker.priorities.domain.model.AddPriorityBody
import com.example.worktracker.priorities.domain.model.AddPriorityResponse
import com.example.worktracker.priorities.domain.model.DeletePriorityBody
import com.example.worktracker.priorities.domain.model.DeletePriorityResponse
import com.example.worktracker.priorities.domain.model.GetPrioritiesBody
import com.example.worktracker.priorities.domain.model.GetPrioritiesResponse
import com.example.worktracker.priorities.domain.model.UpdatePriorityBody
import com.example.worktracker.priorities.domain.model.UpdatePriorityResponse

interface PrioritiesRepository {
    suspend fun getPriorities(getPrioritiesBody: GetPrioritiesBody): GetPrioritiesResponse
    suspend fun addPriority(addPriorityBody: AddPriorityBody): AddPriorityResponse
    suspend fun updatePriority(updatePriorityBody: UpdatePriorityBody): UpdatePriorityResponse
    suspend fun deletePriority(deletePriorityBody: DeletePriorityBody): DeletePriorityResponse
}
