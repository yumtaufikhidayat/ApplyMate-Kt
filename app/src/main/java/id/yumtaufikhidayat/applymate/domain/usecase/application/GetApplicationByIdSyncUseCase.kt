package id.yumtaufikhidayat.applymate.domain.usecase.application

import id.yumtaufikhidayat.applymate.domain.model.Application
import id.yumtaufikhidayat.applymate.domain.repository.ApplicationRepository

class GetApplicationByIdSyncUseCase(
    private val repository: ApplicationRepository
) {
    suspend operator fun invoke(id: Long): Application? = repository.getApplicationByIdSync(id)
}