package id.yumtaufikhidayat.applymate.domain.repository

import id.yumtaufikhidayat.applymate.data.local.entity.StatusHistoryEntity
import id.yumtaufikhidayat.applymate.domain.model.ApplicationStatus
import kotlinx.coroutines.flow.Flow

interface StatusHistoryRepository {
    fun getByApplication(appId: Long): Flow<List<StatusHistoryEntity>>
    suspend fun insert(appId: Long, from: ApplicationStatus?, to: ApplicationStatus, note: String?)
    suspend fun clearByApplication(appId: Long)
}