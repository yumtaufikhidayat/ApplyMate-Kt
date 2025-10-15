package id.yumtaufikhidayat.applymate.domain.usecase.reminder

import id.yumtaufikhidayat.applymate.domain.model.Reminder
import id.yumtaufikhidayat.applymate.domain.repository.ReminderRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUpcomingRemindersUseCase @Inject constructor(
    private val repository: ReminderRepository
) {
    operator fun invoke(): Flow<List<Reminder>> = repository.getUpcoming()
}