package id.yumtaufikhidayat.applymate.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "profiles")
data class ProfileEntity(
    @PrimaryKey val id: Long = 1,
    val name: String,
    val headline: String?,
    val location: String?,
    val level: String?,
    val skillsCsv: String?,
    val completionScore: Int
)