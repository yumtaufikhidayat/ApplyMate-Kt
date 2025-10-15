package id.yumtaufikhidayat.applymate.data.repository

import id.yumtaufikhidayat.applymate.data.local.dao.StatusHistoryDao
import id.yumtaufikhidayat.applymate.data.local.entity.StatusHistoryEntity
import id.yumtaufikhidayat.applymate.domain.model.ApplicationStatus
import id.yumtaufikhidayat.applymate.domain.repository.StatusHistoryRepository
import kotlinx.coroutines.flow.Flow
import java.time.Instant
import javax.inject.Inject

class StatusHistoryRepositoryImpl @Inject constructor(
    private val dao: StatusHistoryDao
) : StatusHistoryRepository {

    override fun getByApplication(appId: Long): Flow<List<StatusHistoryEntity>> =
        dao.getByApplication(appId)

    override suspend fun insert(
        appId: Long,
        from: ApplicationStatus?,
        to: ApplicationStatus,
        note: String?
    ) {
        val history = StatusHistoryEntity(
            applicationId = appId,
            fromStatus = from?.name,
            toStatus = to.name,
            note = note,
            changedAt = Instant.now()
        )
        dao.insert(history)
    }

    override suspend fun clearByApplication(appId: Long) =
        dao.deleteByApplication(appId)
}