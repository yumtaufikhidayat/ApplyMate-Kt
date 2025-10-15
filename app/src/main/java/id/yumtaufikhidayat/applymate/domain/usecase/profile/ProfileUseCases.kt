package id.yumtaufikhidayat.applymate.domain.usecase.profile

data class ProfileUseCases(
    val getProfile: GetProfileUseCase,
    val saveProfile: SaveProfileUseCase,
    val calculateProfileStrength: CalculateProfileStrengthUseCase
)