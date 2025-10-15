package id.yumtaufikhidayat.applymate.domain.usecase.profile

import id.yumtaufikhidayat.applymate.domain.model.Profile
import javax.inject.Inject

class CalculateProfileStrengthUseCase @Inject constructor() {
    operator fun invoke(profile: Profile): Int {
        var score = 0
        if (profile.name.isNotBlank()) score += 20
        if (!profile.headline.isNullOrBlank()) score += 20
        if (!profile.location.isNullOrBlank()) score += 20
        if (!profile.level.isNullOrBlank()) score += 20
        if (profile.skills.isNotEmpty()) score += 20
        return score
    }
}