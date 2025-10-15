package id.yumtaufikhidayat.applymate.data.repository

import id.yumtaufikhidayat.applymate.data.local.dao.ApplicationDao
import id.yumtaufikhidayat.applymate.data.mapper.toDomain
import id.yumtaufikhidayat.applymate.data.mapper.toEntity
import id.yumtaufikhidayat.applymate.domain.model.Application
import id.yumtaufikhidayat.applymate.domain.model.ApplicationStatus
import id.yumtaufikhidayat.applymate.domain.repository.ApplicationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.Instant
import javax.inject.Inject

class ApplicationRepositoryImpl @Inject constructor(
    private val dao: ApplicationDao
) : ApplicationRepository {

    override fun getAll(): Flow<List<Application>> =
        dao.getAll().map { it.map { entity -> entity.toDomain() } }

    override fun getById(id: Long): Flow<Application?> =
        dao.getById(id).map { it?.toDomain() }

    override suspend fun insert(application: Application) =
        dao.insert(application.toEntity())

    override suspend fun update(application: Application) =
        dao.update(application.toEntity())

    override suspend fun updateStatus(id: Long, status: ApplicationStatus) =
        dao.updateStatus(id, status.name, Instant.now().toEpochMilli())

    override suspend fun delete(application: Application) =
        dao.delete(application.toEntity())
}