package id.yumtaufikhidayat.applymate.domain.usecase.reminder

import id.yumtaufikhidayat.applymate.domain.model.Reminder
import id.yumtaufikhidayat.applymate.domain.repository.ReminderRepository
import javax.inject.Inject

class AddReminderUseCase @Inject constructor(
    private val repository: ReminderRepository
) {
    suspend operator fun invoke(reminder: Reminder) {
        repository.insert(reminder)
    }
}