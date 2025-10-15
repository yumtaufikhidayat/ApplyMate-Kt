package id.yumtaufikhidayat.applymate.domain.usecase.reminder

import id.yumtaufikhidayat.applymate.domain.repository.ReminderRepository
import javax.inject.Inject

class MarkReminderDoneUseCase @Inject constructor(
    private val repository: ReminderRepository
) {
    suspend operator fun invoke(id: Long) {
        repository.markDone(id)
    }
}