package id.yumtaufikhidayat.applymate.data.repository

import id.yumtaufikhidayat.applymate.data.local.dao.ProfileDao
import id.yumtaufikhidayat.applymate.data.local.entity.ProfileEntity
import id.yumtaufikhidayat.applymate.domain.model.Profile
import id.yumtaufikhidayat.applymate.domain.repository.ProfileRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(
    private val dao: ProfileDao
) : ProfileRepository {

    override fun getProfile(): Flow<Profile?> =
        dao.getProfile().map { it?.toDomain() }

    override suspend fun saveProfile(profile: Profile) =
        dao.insertOrUpdate(profile.toEntity())

    override suspend fun clear() = dao.clear()
}

// Mappers untuk profile bisa ditaruh di file yang sama:
private fun ProfileEntity.toDomain() = Profile(
    id = id,
    name = name,
    headline = headline,
    location = location,
    level = level,
    skills = skillsCsv?.split(",")?.map { it.trim() } ?: emptyList(),
    completionScore = completionScore
)

private fun Profile.toEntity() = ProfileEntity(
    id = id,
    name = name,
    headline = headline,
    location = location,
    level = level,
    skillsCsv = if (skills.isNotEmpty()) skills.joinToString(",") else null,
    completionScore = completionScore
)