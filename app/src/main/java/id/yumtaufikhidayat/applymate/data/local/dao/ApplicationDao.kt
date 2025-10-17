package id.yumtaufikhidayat.applymate.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import id.yumtaufikhidayat.applymate.data.local.entity.ApplicationEntity
import id.yumtaufikhidayat.applymate.domain.model.Application
import kotlinx.coroutines.flow.Flow

@Dao
interface ApplicationDao {

    @Query("SELECT * FROM applications ORDER BY appliedAt DESC")
    fun getAll(): Flow<List<ApplicationEntity>>

    @Query("SELECT * FROM applications WHERE id = :id")
    fun getById(id: Long): Flow<ApplicationEntity?>

    @Query("SELECT * FROM applications WHERE id = :id LIMIT 1")
    suspend fun getApplicationByIdSync(id: Long): Application?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(application: ApplicationEntity)

    @Update
    suspend fun update(application: ApplicationEntity)

    @Query("UPDATE applications SET status = :status, updatedAt = :updatedAt WHERE id = :id")
    suspend fun updateStatus(id: Long, status: String, updatedAt: Long)

    @Delete
    suspend fun delete(application: ApplicationEntity)
}