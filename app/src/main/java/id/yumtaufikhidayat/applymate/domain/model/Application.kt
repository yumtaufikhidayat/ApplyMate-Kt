package id.yumtaufikhidayat.applymate.domain.model

import java.time.Instant

data class Application(
    val id: Long = 0,
    val position: String,
    val company: String,
    val city: String?,
    val salaryMin: Long?,
    val salaryMax: Long?,
    val jobLink: String?,
    val jobDesc: String?,
    val note: String?,
    val status: ApplicationStatus,
    val appliedAt: Instant,
    val updatedAt: Instant
)