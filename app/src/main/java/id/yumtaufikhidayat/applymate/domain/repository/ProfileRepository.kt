package id.yumtaufikhidayat.applymate.domain.repository

import id.yumtaufikhidayat.applymate.domain.model.Profile
import kotlinx.coroutines.flow.Flow

interface ProfileRepository {
    fun getProfile(): Flow<Profile?>
    suspend fun saveProfile(profile: Profile)
    suspend fun clear()
}