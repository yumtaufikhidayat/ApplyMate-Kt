package id.yumtaufikhidayat.applymate.domain.repository

import id.yumtaufikhidayat.applymate.domain.model.Application
import id.yumtaufikhidayat.applymate.domain.model.ApplicationStatus
import kotlinx.coroutines.flow.Flow

interface ApplicationRepository {
    fun getAll(): Flow<List<Application>>
    fun getById(id: Long): Flow<Application?>
    suspend fun insert(application: Application)
    suspend fun update(application: Application)
    suspend fun updateStatus(id: Long, status: ApplicationStatus)
    suspend fun delete(application: Application)
}