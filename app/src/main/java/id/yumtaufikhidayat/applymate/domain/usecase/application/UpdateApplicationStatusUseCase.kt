package id.yumtaufikhidayat.applymate.domain.usecase.application

import id.yumtaufikhidayat.applymate.domain.model.ApplicationStatus
import id.yumtaufikhidayat.applymate.domain.repository.ApplicationRepository
import id.yumtaufikhidayat.applymate.domain.repository.StatusHistoryRepository
import javax.inject.Inject

class UpdateApplicationStatusUseCase @Inject constructor(
    private val applicationRepo: ApplicationRepository,
    private val historyRepo: StatusHistoryRepository
) {
    suspend operator fun invoke(
        appId: Long,
        from: ApplicationStatus?,
        to: ApplicationStatus,
        note: String?
    ) {
        applicationRepo.updateStatus(appId, to)
        historyRepo.insert(appId, from, to, note)
    }
}