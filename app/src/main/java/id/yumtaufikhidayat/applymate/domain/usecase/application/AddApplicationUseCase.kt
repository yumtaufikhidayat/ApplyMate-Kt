package id.yumtaufikhidayat.applymate.domain.usecase.application

import id.yumtaufikhidayat.applymate.domain.model.Application
import id.yumtaufikhidayat.applymate.domain.repository.ApplicationRepository
import javax.inject.Inject

class AddApplicationUseCase @Inject constructor(
    private val repository: ApplicationRepository
) {
    suspend operator fun invoke(application: Application) {
        repository.insert(application)
    }
}