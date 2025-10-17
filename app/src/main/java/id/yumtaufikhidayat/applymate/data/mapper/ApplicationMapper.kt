package id.yumtaufikhidayat.applymate.data.mapper

import id.yumtaufikhidayat.applymate.data.local.entity.ApplicationEntity
import id.yumtaufikhidayat.applymate.domain.model.Application
import id.yumtaufikhidayat.applymate.domain.model.ApplicationStatus

fun ApplicationEntity.toDomain() = Application(
    id = id,
    position = position,
    company = company,
    companyAbout = companyAbout,
    city = city,
    salary = salary,
    jobLink = jobLink,
    jobDesc = jobDesc,
    jobRequirement = jobRequirement,
    note = note,
    status = status,
    initialStatus = initialStatus,
    appliedAt = appliedAt,
    updatedAt = updatedAt,
    interviewDateTime = interviewDateTime,
    interviewLink = interviewLink
)

fun Application.toEntity() = ApplicationEntity(
    id = id,
    position = position,
    company = company,
    companyAbout = companyAbout,
    city = city,
    salary = salary,
    jobLink = jobLink,
    jobDesc = jobDesc,
    jobRequirement = jobRequirement,
    note = note,
    status = status,
    initialStatus = initialStatus,
    appliedAt = appliedAt,
    updatedAt = updatedAt,
    interviewDateTime = interviewDateTime,
    interviewLink = interviewLink
)