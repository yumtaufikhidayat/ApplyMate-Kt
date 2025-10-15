package id.yumtaufikhidayat.applymate.data.mapper

import id.yumtaufikhidayat.applymate.data.local.entity.ApplicationEntity
import id.yumtaufikhidayat.applymate.domain.model.Application
import id.yumtaufikhidayat.applymate.domain.model.ApplicationStatus

fun ApplicationEntity.toDomain() = Application(
    id = id,
    position = position,
    company = company,
    city = city,
    salary = salary,
    jobLink = jobLink,
    jobDesc = jobDesc,
    jobRequirement = jobRequirement,
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
    salary = salary,
    jobLink = jobLink,
    jobDesc = jobDesc,
    jobRequirement = jobRequirement,
    note = note,
    status = status.name,
    appliedAt = appliedAt,
    updatedAt = updatedAt
)