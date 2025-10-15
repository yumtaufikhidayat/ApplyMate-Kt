package id.yumtaufikhidayat.applymate.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.Instant

@Entity(tableName = "applications")
data class ApplicationEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val position: String,
    val company: String,
    val city: String?,
    val salaryMin: Long?,
    val salaryMax: Long?,
    val jobLink: String?,
    val jobDesc: String?,
    val note: String?,
    val status: String,
    val appliedAt: Instant,
    val updatedAt: Instant
)