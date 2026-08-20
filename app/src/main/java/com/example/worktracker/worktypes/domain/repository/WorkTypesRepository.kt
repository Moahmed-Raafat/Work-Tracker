package com.example.worktracker.worktypes.domain.repository

import com.example.worktracker.worktypes.domain.model.AddWorkTypeBody
import com.example.worktracker.worktypes.domain.model.AddWorkTypeResponse
import com.example.worktracker.worktypes.domain.model.DeleteWorkTypeBody
import com.example.worktracker.worktypes.domain.model.DeleteWorkTypeResponse
import com.example.worktracker.worktypes.domain.model.GetWorkTypesBody
import com.example.worktracker.worktypes.domain.model.GetWorkTypesResponse
import com.example.worktracker.worktypes.domain.model.UpdateWorkTypeBody
import com.example.worktracker.worktypes.domain.model.UpdateWorkTypeResponse

interface WorkTypesRepository {
    suspend fun getWorkTypes(getWorkTypesBody: GetWorkTypesBody): GetWorkTypesResponse
    suspend fun addWorkType(addWorkTypeBody: AddWorkTypeBody): AddWorkTypeResponse
    suspend fun updateWorkType(updateWorkTypeBody: UpdateWorkTypeBody): UpdateWorkTypeResponse
    suspend fun deleteWorkType(deleteWorkTypeBody: DeleteWorkTypeBody): DeleteWorkTypeResponse
}
