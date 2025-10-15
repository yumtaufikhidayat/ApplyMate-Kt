package id.yumtaufikhidayat.applymate.data.mapper

import id.yumtaufikhidayat.applymate.data.local.entity.ApplicationEntity
import id.yumtaufikhidayat.applymate.domain.model.Application
import id.yumtaufikhidayat.applymate.domain.model.ApplicationStatus

fun ApplicationEntity.toDomain() = Application(
    id = id,
    position = position,
    company = company,
    city = city,
    salaryMin = salaryMin,
    salaryMax = salaryMax,
    jobLink = jobLink,
    jobDesc = jobDesc,
    note = note,
    status = ApplicationStatus.valueOf(status),
    appliedAt = appliedAt,
    updatedAt = updatedAt
)

fun Application.toEntity() = ApplicationEntity(
    id = id,
    position = position,
    company = company,
    city = city,
    salaryMin = salaryMin,
    salaryMax = salaryMax,
    jobLink = jobLink,
    jobDesc = jobDesc,
    note = note,
    status = status.name,
    appliedAt = appliedAt,
    updatedAt = updatedAt
)