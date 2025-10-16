package id.yumtaufikhidayat.applymate.domain.model

import java.time.Instant

data class Application(
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
    val appliedAt: Instant,
    val updatedAt: Instant
)