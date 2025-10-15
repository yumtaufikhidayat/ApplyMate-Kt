package id.yumtaufikhidayat.applymate.data.repository

import id.yumtaufikhidayat.applymate.data.local.dao.ReminderDao
import id.yumtaufikhidayat.applymate.data.mapper.toDomain
import id.yumtaufikhidayat.applymate.data.mapper.toEntity
import id.yumtaufikhidayat.applymate.domain.model.Reminder
import id.yumtaufikhidayat.applymate.domain.repository.ReminderRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ReminderRepositoryImpl @Inject constructor(
    private val dao: ReminderDao
) : ReminderRepository {

    override fun getUpcoming(): Flow<List<Reminder>> =
        dao.getUpcoming().map { it.map { entity -> entity.toDomain() } }

    override suspend fun insert(reminder: Reminder) =
        dao.insert(reminder.toEntity())

    override suspend fun update(reminder: Reminder) =
        dao.update(reminder.toEntity())

    override suspend fun markDone(id: Long) =
        dao.markDone(id)

    override suspend fun delete(reminder: Reminder) =
        dao.delete(reminder.toEntity())
}