package id.yumtaufikhidayat.applymate.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import id.yumtaufikhidayat.applymate.data.local.entity.StatusHistoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface StatusHistoryDao {

    @Query("SELECT * FROM status_history WHERE applicationId = :appId ORDER BY changedAt DESC")
    fun getByApplication(appId: Long): Flow<List<StatusHistoryEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(history: StatusHistoryEntity)

    @Query("DELETE FROM status_history WHERE applicationId = :appId")
    suspend fun deleteByApplication(appId: Long)
}