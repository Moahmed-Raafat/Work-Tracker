package com.example.worktracker.statuses.domain.usecase

import com.example.worktracker.common.Resource
import com.example.worktracker.statuses.domain.model.GetStatusesBody
import com.example.worktracker.statuses.domain.model.GetStatusesResponse
import com.example.worktracker.statuses.domain.repository.StatusesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetStatusesUseCase @Inject constructor(private val repository: StatusesRepository) {
    operator fun invoke(getStatusesBody: GetStatusesBody): Flow<Resource<GetStatusesResponse>> =
        flow {
            emit(Resource.Loading())
            try {
                val result = repository.getStatuses(getStatusesBody)
                emit(Resource.Success(result))
            } catch (e: Exception) {
                emit(Resource.Error(e.message ?: "Unexpected error"))
            }
        }
}
