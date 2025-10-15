package id.yumtaufikhidayat.applymate.domain.usecase.profile

import id.yumtaufikhidayat.applymate.domain.model.Profile
import id.yumtaufikhidayat.applymate.domain.repository.ProfileRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetProfileUseCase @Inject constructor(
    private val repository: ProfileRepository
) {
    operator fun invoke(): Flow<Profile?> = repository.getProfile()
}