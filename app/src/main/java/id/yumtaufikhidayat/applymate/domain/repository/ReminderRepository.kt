package id.yumtaufikhidayat.applymate.domain.repository

import id.yumtaufikhidayat.applymate.domain.model.Reminder
import kotlinx.coroutines.flow.Flow

interface ReminderRepository {
    fun getUpcoming(): Flow<List<Reminder>>
    suspend fun insert(reminder: Reminder)
    suspend fun update(reminder: Reminder)
    suspend fun markDone(id: Long)
    suspend fun delete(reminder: Reminder)
}