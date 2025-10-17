package id.yumtaufikhidayat.applymate.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import id.yumtaufikhidayat.applymate.domain.model.ApplicationStatus
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime

@Entity(tableName = "applications")
data class ApplicationEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val position: String,
    val company: String,
    val companyAbout: String,
    val city: String,
    val salary: String,
    val jobLink: String,
    val jobDesc: String,
    val jobRequirement: String,
    val note: String,
    val status: ApplicationStatus,
    val initialStatus: ApplicationStatus,
    val appliedAt: Instant,
    val updatedAt: Instant,
    val interviewDateTime: LocalDateTime?,
    val interviewLink: String?,
)