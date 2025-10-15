package id.yumtaufikhidayat.applymate.domain.usecase.profile

import id.yumtaufikhidayat.applymate.domain.model.Profile
import id.yumtaufikhidayat.applymate.domain.repository.ProfileRepository
import javax.inject.Inject

class SaveProfileUseCase @Inject constructor(
    private val repository: ProfileRepository
) {
    suspend operator fun invoke(profile: Profile) {
        repository.saveProfile(profile)
    }
}